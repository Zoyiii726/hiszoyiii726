package com.his.service.impl;

import com.his.entity.Patient;
import com.his.mapper.PatientMapper;
import com.his.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientMapper patientMapper;

    @Override
    public Patient getById(Long patientId) {
        return patientMapper.selectById(patientId);
    }

    @Override
    public Patient getByPatientNo(String patientNo) {
        return patientMapper.selectByPatientNo(patientNo);
    }

    @Override
    public List<Patient> getList(String keyword) {
        return patientMapper.selectList(keyword);
    }

    @Override
    public Patient add(Patient patient) {
        // 未传病历号时由 Mapper 自动生成（P+日期+随机数），兼容前端传入的编号
        patientMapper.insert(patient);
        return patientMapper.selectById(patient.getPatientId());
    }

    @Override
    public boolean update(Patient patient) {
        return patientMapper.update(patient) > 0;
    }

    @Override
    public boolean delete(Long patientId) {
        return patientMapper.deleteById(patientId) > 0;
    }
}
