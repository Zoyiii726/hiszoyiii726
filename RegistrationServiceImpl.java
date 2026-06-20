package com.his.service.impl;

import com.his.entity.Registration;
import com.his.entity.Patient;
import com.his.mapper.RegistrationMapper;
import com.his.service.PatientService;
import com.his.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private RegistrationMapper registrationMapper;

    @Autowired
    private PatientService patientService;

    @Override
    public Registration getById(Long regId) {
        return registrationMapper.selectById(regId);
    }

    @Override
    public Registration getByRegNo(String regNo) {
        return registrationMapper.selectByRegNo(regNo);
    }

    @Override
    public List<Registration> getList(Registration registration) {
        return registrationMapper.selectList(registration);
    }

    @Override
    public List<Registration> getByDoctorAndDate(Long doctorId, Date regDate, String status) {
        return registrationMapper.selectByDoctorAndDate(doctorId, regDate, status);
    }

    @Override
    public List<Registration> getByPatient(Long patientId) {
        return registrationMapper.selectByPatient(patientId);
    }

    @Override
    public Registration register(Registration registration) {
        if (registration.getPatientId() == null && registration.getPatientNo() != null && !registration.getPatientNo().trim().isEmpty()) {
            Patient patient = patientService.getByPatientNo(registration.getPatientNo().trim());
            if (patient != null) {
                registration.setPatientId(patient.getPatientId());
            }
        }
        if (registration.getPatientId() == null) {
            throw new IllegalArgumentException("请先查询或创建患者后再挂号");
        }
        if (registration.getDoctorId() == null || registration.getDeptId() == null) {
            throw new IllegalArgumentException("请选择挂号科室和挂号医生");
        }
        if (registration.getRegDate() == null) {
            throw new IllegalArgumentException("请选择看诊日期");
        }
        if (registration.getTimeSlot() == null || registration.getTimeSlot().trim().isEmpty()) {
            throw new IllegalArgumentException("请选择午别");
        }
        // 生成挂号单号：REG + 时间戳
        registration.setRegNo("REG" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        // 计算排队号：当前医生当天当午别的最大排队号 + 1
        int maxQueue = registrationMapper.getMaxQueueNumber(
                registration.getDoctorId(),
                registration.getRegDate(),
                registration.getTimeSlot());
        registration.setQueueNumber(maxQueue + 1);
        if (registration.getStatus() == null) {
            registration.setStatus("WAITING");
        }
        registrationMapper.insert(registration);
        return registration;
    }

    @Override
    public boolean update(Registration registration) {
        return registrationMapper.update(registration) > 0;
    }

    @Override
    public boolean updateStatus(Long regId, String status) {
        return registrationMapper.updateStatus(regId, status) > 0;
    }
}
