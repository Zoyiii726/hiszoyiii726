package com.his.service.impl;

import com.his.entity.ExamApply;
import com.his.entity.ExamItem;
import com.his.mapper.ExamApplyMapper;
import com.his.mapper.ExamItemMapper;
import com.his.service.ExamApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ExamApplyServiceImpl implements ExamApplyService {

    @Autowired
    private ExamApplyMapper examApplyMapper;

    @Autowired
    private ExamItemMapper examItemMapper;

    @Override
    public ExamApply getById(Long applyId) {
        return examApplyMapper.selectById(applyId);
    }

    @Override
    public List<ExamApply> getList(ExamApply examApply) {
        return examApplyMapper.selectList(examApply);
    }

    @Override
    public List<ExamApply> getByRegId(Long regId) {
        return examApplyMapper.selectByRegId(regId);
    }

    @Override
    public List<ExamApply> getByPatient(Long patientId, String status) {
        return examApplyMapper.selectByPatient(patientId, status);
    }

    @Override
    public ExamApply addApply(ExamApply examApply) {
        examApply.setApplyNo("EXAM" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        examApply.setStatus("UNPAID");
        examApplyMapper.insert(examApply);
        return examApply;
    }

    @Override
    public boolean update(ExamApply examApply) {
        return examApplyMapper.update(examApply) > 0;
    }

    @Override
    public boolean updateStatus(Long applyId, String status) {
        return examApplyMapper.updateStatus(applyId, status) > 0;
    }

    @Override
    public boolean executeResult(ExamApply examApply) {
        // 执行检查并录入结果
        examApply.setExamTime(new Date());
        examApply.setStatus("COMPLETED");
        return examApplyMapper.update(examApply) > 0;
    }

    @Override
    public List<ExamItem> getItemList(String keyword) {
        return examItemMapper.selectList(keyword);
    }

    @Override
    public boolean addItem(ExamItem item) {
        return examItemMapper.insert(item) > 0;
    }
}
