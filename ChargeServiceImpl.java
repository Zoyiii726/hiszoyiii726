package com.his.service.impl;

import com.his.entity.ChargeRecord;
import com.his.mapper.ChargeRecordMapper;
import com.his.mapper.ExamApplyMapper;
import com.his.mapper.LabApplyMapper;
import com.his.mapper.PrescriptionMapper;
import com.his.mapper.RegistrationMapper;
import com.his.mapper.TreatmentApplyMapper;
import com.his.service.ChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ChargeServiceImpl implements ChargeService {

    @Autowired
    private ChargeRecordMapper chargeRecordMapper;

    // 收费联动：收费后需把对应申请单状态置为 PAID
    @Autowired
    private ExamApplyMapper examApplyMapper;
    @Autowired
    private LabApplyMapper labApplyMapper;
    @Autowired
    private TreatmentApplyMapper treatmentApplyMapper;
    @Autowired
    private PrescriptionMapper prescriptionMapper;
    @Autowired
    private RegistrationMapper registrationMapper;

    @Override
    public ChargeRecord getById(Long chargeId) {
        return chargeRecordMapper.selectById(chargeId);
    }

    @Override
    public List<ChargeRecord> getList(ChargeRecord chargeRecord) {
        return chargeRecordMapper.selectList(chargeRecord);
    }

    @Override
    public List<ChargeRecord> getByPatient(Long patientId) {
        return chargeRecordMapper.selectByPatient(patientId);
    }

    @Override
    public List<ChargeRecord> getByDateRange(Date startDate, Date endDate) {
        return chargeRecordMapper.selectByDateRange(startDate, endDate);
    }

    @Override
    @Transactional
    public ChargeRecord charge(ChargeRecord chargeRecord) {
        // 生成收费单号
        chargeRecord.setChargeNo("CHG" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
                + ThreadLocalRandom.current().nextInt(100, 1000));
        chargeRecord.setStatus("PAID");
        chargeRecordMapper.insert(chargeRecord);

        // 收费联动：把关联业务单据状态改为 PAID，使其后续可被执行/发药
        if (chargeRecord.getRelateId() != null && chargeRecord.getChargeType() != null) {
            Long relateId = chargeRecord.getRelateId();
            String type = chargeRecord.getChargeType();
            switch (type) {
                case "REG":
                    // 挂号费：无需改 registration 状态（挂号本身已 WAITING）
                    break;
                case "EXAM":
                    examApplyMapper.updateStatus(relateId, "PAID");
                    break;
                case "LAB":
                    labApplyMapper.updateStatus(relateId, "PAID");
                    break;
                case "TREAT":
                    treatmentApplyMapper.updateStatus(relateId, "PAID");
                    break;
                case "PRES":
                    prescriptionMapper.updateStatus(relateId, "PAID");
                    break;
                default:
                    break;
            }
        }
        return chargeRecord;
    }

    @Override
    public boolean update(ChargeRecord chargeRecord) {
        return chargeRecordMapper.update(chargeRecord) > 0;
    }

    @Override
    @Transactional
    public boolean refund(Long chargeId, Long userId) {
        ChargeRecord record = chargeRecordMapper.selectById(chargeId);
        if (record == null) {
            return false;
        }
        // 标记退费
        record.setStatus("REFUNDED");
        record.setRefundUserId(userId);
        record.setRefundTime(new Date());
        int rows = chargeRecordMapper.update(record);

        // 退费联动：把对应申请单状态从 PAID 回退到 UNPAID（药品处方回退为 UNPAID）
        if (record.getRelateId() != null && record.getChargeType() != null) {
            Long relateId = record.getRelateId();
            String type = record.getChargeType();
            switch (type) {
                case "EXAM":
                    examApplyMapper.updateStatus(relateId, "UNPAID");
                    break;
                case "LAB":
                    labApplyMapper.updateStatus(relateId, "UNPAID");
                    break;
                case "TREAT":
                    treatmentApplyMapper.updateStatus(relateId, "UNPAID");
                    break;
                case "PRES":
                    prescriptionMapper.updateStatus(relateId, "UNPAID");
                    break;
                default:
                    break;
            }
        }
        return rows > 0;
    }
}
