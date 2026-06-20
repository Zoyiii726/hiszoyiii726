package com.his.mapper;

import com.his.entity.LabItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface LabItemMapper {
    LabItem selectById(@Param("itemId") Long itemId);
    List<LabItem> selectAll();
    List<LabItem> selectList(@Param("keyword") String keyword);
    int insert(LabItem item);
    int update(LabItem item);
    int deleteById(@Param("itemId") Long itemId);
}
