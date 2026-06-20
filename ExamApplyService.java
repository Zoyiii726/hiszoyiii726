package com.his.service;

import com.his.entity.ExamApply;
import com.his.entity.ExamItem;
import java.util.List;

public interface ExamApplyService {
    ExamApply getById(Long applyId);
    List<ExamApply> getList(ExamApply examApply);
    List<ExamApply> getByRegId(Long regId);
    List<ExamApply> getByPatient(Long patientId, String status);
    ExamApply addApply(ExamApply examApply);
    boolean update(ExamApply examApply);
    boolean updateStatus(Long applyId, String status);
    boolean executeResult(ExamApply examApply);

    // 检查项目
    List<ExamItem> getItemList(String keyword);
    boolean addItem(ExamItem item);
}
