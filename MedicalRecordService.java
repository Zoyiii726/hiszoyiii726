package com.his.service;

import com.his.entity.MedicalRecord;
import java.util.List;

public interface MedicalRecordService {
    MedicalRecord getById(Long recordId);
    MedicalRecord getByRegId(Long regId);
    List<MedicalRecord> getByPatient(Long patientId);
    MedicalRecord save(MedicalRecord record);
    boolean update(MedicalRecord record);
}
