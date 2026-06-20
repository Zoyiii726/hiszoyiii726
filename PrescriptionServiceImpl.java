package com.his.service.impl;

import com.his.entity.Prescription;
import com.his.entity.PrescriptionDetail;
import com.his.mapper.PrescriptionDetailMapper;
import com.his.mapper.PrescriptionMapper;
import com.his.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    private PrescriptionMapper prescriptionMapper;

    @Autowired
    private PrescriptionDetailMapper prescriptionDetailMapper;

    @Override
    public Prescription getById(Long presId) {
        Prescription pres = prescriptionMapper.selectById(presId);
        if (pres != null) {
            List<PrescriptionDetail> details = prescriptionDetailMapper.selectByPresId(presId);
            pres.setDetails(details);
            // 合计金额
            BigDecimal total = BigDecimal.ZERO;
            if (details != null) {
                for (PrescriptionDetail d : details) {
                    if (d.getAmount() != null) {
                        total = total.add(d.getAmount());
                    }
                }
            }
            pres.setTotalAmount(total);
        }
        return pres;
    }

    @Override
    public List<Prescription> getList(Prescription prescription) {
        return enrichPrescriptions(prescriptionMapper.selectList(prescription));
    }

    @Override
    public List<Prescription> getByRegId(Long regId) {
        return enrichPrescriptions(prescriptionMapper.selectByRegId(regId));
    }

    @Override
    public List<Prescription> getByPatient(Long patientId, String status) {
        return enrichPrescriptions(prescriptionMapper.selectByPatient(patientId, status));
    }

    @Override
    @Transactional
    public Prescription add(Prescription prescription) {
        prescription.setPresNo("PRES" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        prescription.setStatus("UNPAID");
        // 先插主表，拿到 presId
        prescriptionMapper.insert(prescription);

        // 计算合计 + 回填明细
        BigDecimal total = BigDecimal.ZERO;
        if (prescription.getDetails() != null && !prescription.getDetails().isEmpty()) {
            for (PrescriptionDetail detail : prescription.getDetails()) {
                detail.setPresId(prescription.getPresId());
                // 单条金额 = 单价 × 数量
                if (detail.getPrice() != null && detail.getQuantity() != null) {
                    detail.setAmount(detail.getPrice().multiply(new BigDecimal(detail.getQuantity())));
                    total = total.add(detail.getAmount());
                }
            }
            prescriptionDetailMapper.batchInsert(prescription.getDetails());
        }
        prescription.setTotalAmount(total);
        return prescription;
    }

    @Override
    public boolean update(Prescription prescription) {
        return prescriptionMapper.update(prescription) > 0;
    }

    @Override
    public boolean updateStatus(Long presId, String status) {
        return prescriptionMapper.updateStatus(presId, status) > 0;
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
