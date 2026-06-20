package com.his.service.impl;

import com.his.entity.LabApply;
import com.his.entity.LabItem;
import com.his.mapper.LabApplyMapper;
import com.his.mapper.LabItemMapper;
import com.his.service.LabApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class LabApplyServiceImpl implements LabApplyService {

    @Autowired
    private LabApplyMapper labApplyMapper;

    @Autowired
    private LabItemMapper labItemMapper;

    @Override
    public LabApply getById(Long applyId) {
        return labApplyMapper.selectById(applyId);
    }

    @Override
    public List<LabApply> getList(LabApply labApply) {
        return labApplyMapper.selectList(labApply);
    }

    @Override
    public List<LabApply> getByRegId(Long regId) {
        return labApplyMapper.selectByRegId(regId);
    }

    @Override
    public List<LabApply> getByPatient(Long patientId, String status) {
        return labApplyMapper.selectByPatient(patientId, status);
    }

    @Override
    public LabApply addApply(LabApply labApply) {
        labApply.setApplyNo("LAB" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        labApply.setStatus("UNPAID");
        labApplyMapper.insert(labApply);
        return labApply;
    }

    @Override
    public boolean update(LabApply labApply) {
        return labApplyMapper.update(labApply) > 0;
    }

    @Override
    public boolean updateStatus(Long applyId, String status) {
        return labApplyMapper.updateStatus(applyId, status) > 0;
    }

    @Override
    public boolean executeResult(LabApply labApply) {
        labApply.setLabTime(new Date());
        labApply.setStatus("COMPLETED");
        return labApplyMapper.update(labApply) > 0;
    }

    @Override
    public List<LabItem> getItemList(String keyword) {
        return labItemMapper.selectList(keyword);
    }

    @Override
    public boolean addItem(LabItem item) {
        return labItemMapper.insert(item) > 0;
    }
}
