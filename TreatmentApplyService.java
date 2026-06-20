package com.his.service;

import com.his.entity.TreatmentApply;
import com.his.entity.TreatmentItem;
import java.util.List;

public interface TreatmentApplyService {
    TreatmentApply getById(Long applyId);
    List<TreatmentApply> getList(TreatmentApply treatmentApply);
    List<TreatmentApply> getByRegId(Long regId);
    List<TreatmentApply> getByPatient(Long patientId, String status);
    TreatmentApply addApply(TreatmentApply treatmentApply);
    boolean update(TreatmentApply treatmentApply);
    boolean updateStatus(Long applyId, String status);
    boolean executeResult(TreatmentApply treatmentApply);

    // 处置项目
    List<TreatmentItem> getItemList(String keyword);
    boolean addItem(TreatmentItem item);
}
