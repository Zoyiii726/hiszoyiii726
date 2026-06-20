package com.his.mapper;

import com.his.entity.ExamApply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ExamApplyMapper {
    ExamApply selectById(@Param("applyId") Long applyId);
    List<ExamApply> selectList(ExamApply examApply);
    List<ExamApply> selectByRegId(@Param("regId") Long regId);
    List<ExamApply> selectByPatient(@Param("patientId") Long patientId, @Param("status") String status);
    int insert(ExamApply examApply);
    int update(ExamApply examApply);
    int updateStatus(@Param("applyId") Long applyId, @Param("status") String status);
}
