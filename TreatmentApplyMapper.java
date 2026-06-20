package com.his.mapper;

import com.his.entity.TreatmentApply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface TreatmentApplyMapper {
    TreatmentApply selectById(@Param("applyId") Long applyId);
    List<TreatmentApply> selectList(TreatmentApply treatmentApply);
    List<TreatmentApply> selectByRegId(@Param("regId") Long regId);
    List<TreatmentApply> selectByPatient(@Param("patientId") Long patientId, @Param("status") String status);
    int insert(TreatmentApply treatmentApply);
    int update(TreatmentApply treatmentApply);
    int updateStatus(@Param("applyId") Long applyId, @Param("status") String status);
}
