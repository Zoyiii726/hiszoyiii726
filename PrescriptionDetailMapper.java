package com.his.mapper;

import com.his.entity.PrescriptionDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface PrescriptionDetailMapper {
    List<PrescriptionDetail> selectByPresId(@Param("presId") Long presId);
    int insert(PrescriptionDetail detail);
    int batchInsert(@Param("list") List<PrescriptionDetail> list);
    int deleteByPresId(@Param("presId") Long presId);
}
