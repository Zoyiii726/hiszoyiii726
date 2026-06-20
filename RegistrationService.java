package com.his.service;

import com.his.entity.Registration;
import java.util.Date;
import java.util.List;

public interface RegistrationService {
    Registration getById(Long regId);
    Registration getByRegNo(String regNo);
    List<Registration> getList(Registration registration);
    List<Registration> getByDoctorAndDate(Long doctorId, Date regDate, String status);
    List<Registration> getByPatient(Long patientId);
    Registration register(Registration registration);
    boolean update(Registration registration);
    boolean updateStatus(Long regId, String status);
}
