package com.his.service;

import com.his.entity.Prescription;
import java.util.List;

public interface PrescriptionService {
    Prescription getById(Long presId);
    List<Prescription> getList(Prescription prescription);
    List<Prescription> getByRegId(Long regId);
    List<Prescription> getByPatient(Long patientId, String status);
    Prescription add(Prescription prescription);
    boolean update(Prescription prescription);
    boolean updateStatus(Long presId, String status);
}
