package com.his.mapper;

import com.his.entity.TreatmentItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface TreatmentItemMapper {
    TreatmentItem selectById(@Param("itemId") Long itemId);
    List<TreatmentItem> selectAll();
    List<TreatmentItem> selectList(@Param("keyword") String keyword);
    int insert(TreatmentItem item);
    int update(TreatmentItem item);
    int deleteById(@Param("itemId") Long itemId);
}
