package com.his.service;

import com.his.entity.LabApply;
import com.his.entity.LabItem;
import java.util.List;

public interface LabApplyService {
    LabApply getById(Long applyId);
    List<LabApply> getList(LabApply labApply);
    List<LabApply> getByRegId(Long regId);
    List<LabApply> getByPatient(Long patientId, String status);
    LabApply addApply(LabApply labApply);
    boolean update(LabApply labApply);
    boolean updateStatus(Long applyId, String status);
    boolean executeResult(LabApply labApply);

    // 检验项目
    List<LabItem> getItemList(String keyword);
    boolean addItem(LabItem item);
}
