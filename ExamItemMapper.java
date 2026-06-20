package com.his.mapper;

import com.his.entity.ExamItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ExamItemMapper {
    ExamItem selectById(@Param("itemId") Long itemId);
    List<ExamItem> selectAll();
    List<ExamItem> selectList(@Param("keyword") String keyword);
    int insert(ExamItem item);
    int update(ExamItem item);
    int deleteById(@Param("itemId") Long itemId);
}
