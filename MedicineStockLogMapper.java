package com.his.mapper;

import com.his.entity.MedicineStockLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MedicineStockLogMapper {
    /** 查询流水（按药品、按操作类型筛选） */
    List<MedicineStockLog> selectList(@Param("optType") String optType);
    int insert(MedicineStockLog log);
}
