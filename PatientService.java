package com.his.service;

import com.his.entity.Patient;
import java.util.List;

public interface PatientService {
    Patient getById(Long patientId);
    Patient getByPatientNo(String patientNo);
    List<Patient> getList(String keyword);
    Patient add(Patient patient);
    boolean update(Patient patient);
    boolean delete(Long patientId);
}
