package com.his.service.impl;

import com.his.entity.MedicineStockLog;
import com.his.entity.Prescription;
import com.his.entity.PrescriptionDetail;
import com.his.mapper.MedicineMapper;
import com.his.mapper.MedicineStockLogMapper;
import com.his.mapper.PrescriptionDetailMapper;
import com.his.mapper.PrescriptionMapper;
import com.his.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PharmacyServiceImpl implements PharmacyService {

    @Autowired
    private PrescriptionMapper prescriptionMapper;
    @Autowired
    private PrescriptionDetailMapper prescriptionDetailMapper;
    @Autowired
    private MedicineMapper medicineMapper;
    @Autowired
    private MedicineStockLogMapper medicineStockLogMapper;

    @Override
    public List<Prescription> getDispenseList() {
        // 查询所有已缴费待发药的处方
        Prescription query = new Prescription();
        query.setStatus("PAID");
        return enrichPrescriptions(prescriptionMapper.selectList(query));
    }

    @Override
    @Transactional
    public boolean dispense(Long presId, Long operatorId) {
        Prescription pres = prescriptionMapper.selectById(presId);
        if (pres == null) {
            throw new RuntimeException("处方不存在");
        }
        if (!"PAID".equals(pres.getStatus())) {
            throw new RuntimeException("只有已缴费处方才能发药，当前状态：" + pres.getStatus());
        }
        // 查询处方明细，逐项扣减库存并记录流水
        List<PrescriptionDetail> details = prescriptionDetailMapper.selectByPresId(presId);
        if (details != null) {
            for (PrescriptionDetail d : details) {
                // 扣减库存（quantity 传负数）
                medicineMapper.updateStock(d.getMedicineId(), -d.getQuantity());
                // 记录发药流水
                MedicineStockLog log = new MedicineStockLog();
                log.setMedicineId(d.getMedicineId());
                log.setOptType("DISPENSE");
                log.setQuantity(d.getQuantity());
                log.setRelateId(presId);
                log.setOperatorId(operatorId);
                log.setRemark("发药，处方号：" + pres.getPresNo());
                medicineStockLogMapper.insert(log);
            }
        }
        // 处方状态置为已发药
        prescriptionMapper.updateStatus(presId, "DISPENSED");
        return true;
    }

    @Override
    @Transactional
    public boolean refund(Long presId, Long operatorId) {
        Prescription pres = prescriptionMapper.selectById(presId);
        if (pres == null) {
            throw new RuntimeException("处方不存在");
        }
        if (!"DISPENSED".equals(pres.getStatus())) {
            throw new RuntimeException("只有已发药处方才能退药，当前状态：" + pres.getStatus());
        }
        // 逐项加回库存并记录退药流水
        List<PrescriptionDetail> details = prescriptionDetailMapper.selectByPresId(presId);
        if (details != null) {
            for (PrescriptionDetail d : details) {
                medicineMapper.updateStock(d.getMedicineId(), d.getQuantity());
                MedicineStockLog log = new MedicineStockLog();
                log.setMedicineId(d.getMedicineId());
                log.setOptType("RETURN");
                log.setQuantity(d.getQuantity());
                log.setRelateId(presId);
                log.setOperatorId(operatorId);
                log.setRemark("退药，处方号：" + pres.getPresNo());
                medicineStockLogMapper.insert(log);
            }
        }
        // 处方状态回退为未缴费（已退款状态）
        prescriptionMapper.updateStatus(presId, "RETURNED");
        return true;
    }

    @Override
    public List<MedicineStockLog> getStockLogs(String optType) {
        return medicineStockLogMapper.selectList(optType);
    }

    private List<Prescription> enrichPrescriptions(List<Prescription> prescriptions) {
        if (prescriptions == null || prescriptions.isEmpty()) {
            return prescriptions;
        }
        return prescriptions.stream().map(this::enrichPrescription).collect(Collectors.toList());
    }

    private Prescription enrichPrescription(Prescription prescription) {
        if (prescription == null || prescription.getPresId() == null) {
            return prescription;
        }
        List<PrescriptionDetail> details = prescriptionDetailMapper.selectByPresId(prescription.getPresId());
        prescription.setDetails(details);
        BigDecimal total = BigDecimal.ZERO;
        if (details != null) {
            for (PrescriptionDetail detail : details) {
                if (detail.getAmount() != null) {
                    total = total.add(detail.getAmount());
                }
            }
        }
        prescription.setTotalAmount(total);
        return prescription;
    }
}
