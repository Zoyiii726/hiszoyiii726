package com.his.service.impl;

import com.his.entity.TreatmentApply;
import com.his.entity.TreatmentItem;
import com.his.mapper.TreatmentApplyMapper;
import com.his.mapper.TreatmentItemMapper;
import com.his.service.TreatmentApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TreatmentApplyServiceImpl implements TreatmentApplyService {

    @Autowired
    private TreatmentApplyMapper treatmentApplyMapper;

    @Autowired
    private TreatmentItemMapper treatmentItemMapper;

    @Override
    public TreatmentApply getById(Long applyId) {
        return treatmentApplyMapper.selectById(applyId);
    }

    @Override
    public List<TreatmentApply> getList(TreatmentApply treatmentApply) {
        return treatmentApplyMapper.selectList(treatmentApply);
    }

    @Override
    public List<TreatmentApply> getByRegId(Long regId) {
        return treatmentApplyMapper.selectByRegId(regId);
    }

    @Override
    public List<TreatmentApply> getByPatient(Long patientId, String status) {
        return treatmentApplyMapper.selectByPatient(patientId, status);
    }

    @Override
    public TreatmentApply addApply(TreatmentApply treatmentApply) {
        treatmentApply.setApplyNo("TREAT" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        treatmentApply.setStatus("UNPAID");
        treatmentApplyMapper.insert(treatmentApply);
        return treatmentApply;
    }

    @Override
    public boolean update(TreatmentApply treatmentApply) {
        return treatmentApplyMapper.update(treatmentApply) > 0;
    }

    @Override
    public boolean updateStatus(Long applyId, String status) {
        return treatmentApplyMapper.updateStatus(applyId, status) > 0;
    }

    @Override
    public boolean executeResult(TreatmentApply treatmentApply) {
        treatmentApply.setTreatTime(new Date());
        treatmentApply.setStatus("COMPLETED");
        return treatmentApplyMapper.update(treatmentApply) > 0;
    }

    @Override
    public List<TreatmentItem> getItemList(String keyword) {
        return treatmentItemMapper.selectList(keyword);
    }

    @Override
    public boolean addItem(TreatmentItem item) {
        return treatmentItemMapper.insert(item) > 0;
    }
}
