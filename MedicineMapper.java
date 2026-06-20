package com.his.mapper;

import com.his.entity.Medicine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface MedicineMapper {
    Medicine selectById(@Param("medicineId") Long medicineId);
    Medicine selectByCode(@Param("medicineCode") String medicineCode);
    List<Medicine> selectList(@Param("keyword") String keyword);
    int insert(Medicine medicine);
    int update(Medicine medicine);
    int updateStock(@Param("medicineId") Long medicineId, @Param("quantity") Integer quantity);
    int deleteById(@Param("medicineId") Long medicineId);
}
