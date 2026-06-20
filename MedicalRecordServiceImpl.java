package com.his.service.impl;

import com.his.entity.MedicalRecord;
import com.his.mapper.MedicalRecordMapper;
import com.his.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    @Autowired
    private MedicalRecordMapper medicalRecordMapper;

    @Override
    public MedicalRecord getById(Long recordId) {
        return medicalRecordMapper.selectById(recordId);
    }

    @Override
    public MedicalRecord getByRegId(Long regId) {
        return medicalRecordMapper.selectByRegId(regId);
    }

    @Override
    public List<MedicalRecord> getByPatient(Long patientId) {
        return medicalRecordMapper.selectByPatient(patientId);
    }

    @Override
    public MedicalRecord save(MedicalRecord record) {
        // 同一挂号已有病历则更新，否则新增（医生可反复保存病历）
        if (record.getRegId() != null) {
            MedicalRecord exist = medicalRecordMapper.selectByRegId(record.getRegId());
            if (exist != null) {
                record.setRecordId(exist.getRecordId());
                medicalRecordMapper.update(record);
                return record;
            }
        }
        medicalRecordMapper.insert(record);
        return record;
    }

    @Override
    public boolean update(MedicalRecord record) {
        return medicalRecordMapper.update(record) > 0;
    }
}
