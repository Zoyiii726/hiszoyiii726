package com.his.service.impl;

import com.his.entity.Medicine;
import com.his.mapper.MedicineMapper;
import com.his.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineServiceImpl implements MedicineService {

    @Autowired
    private MedicineMapper medicineMapper;

    @Override
    public Medicine getById(Long medicineId) {
        return medicineMapper.selectById(medicineId);
    }

    @Override
    public Medicine getByCode(String medicineCode) {
        return medicineMapper.selectByCode(medicineCode);
    }

    @Override
    public List<Medicine> getList(String keyword) {
        return medicineMapper.selectList(keyword);
    }

    @Override
    public boolean add(Medicine medicine) {
        return medicineMapper.insert(medicine) > 0;
    }

    @Override
    public boolean update(Medicine medicine) {
        return medicineMapper.update(medicine) > 0;
    }

    @Override
    public boolean updateStock(Long medicineId, Integer quantity) {
        // quantity 为正则增加库存，为负则减少库存
        return medicineMapper.updateStock(medicineId, quantity) > 0;
    }

    @Override
    public boolean delete(Long medicineId) {
        return medicineMapper.deleteById(medicineId) > 0;
    }
}
