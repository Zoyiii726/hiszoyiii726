package com.his.mapper;

import com.his.entity.LabApply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface LabApplyMapper {
    LabApply selectById(@Param("applyId") Long applyId);
    List<LabApply> selectList(LabApply labApply);
    List<LabApply> selectByRegId(@Param("regId") Long regId);
    List<LabApply> selectByPatient(@Param("patientId") Long patientId, @Param("status") String status);
    int insert(LabApply labApply);
    int update(LabApply labApply);
    int updateStatus(@Param("applyId") Long applyId, @Param("status") String status);
}
