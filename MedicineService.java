package com.his.service;

import com.his.entity.Medicine;
import java.util.List;

public interface MedicineService {
    Medicine getById(Long medicineId);
    Medicine getByCode(String medicineCode);
    List<Medicine> getList(String keyword);
    boolean add(Medicine medicine);
    boolean update(Medicine medicine);
    boolean updateStock(Long medicineId, Integer quantity);
    boolean delete(Long medicineId);
}
