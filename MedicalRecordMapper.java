package com.his.mapper;

import com.his.entity.MedicalRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface MedicalRecordMapper {
    MedicalRecord selectById(@Param("recordId") Long recordId);
    MedicalRecord selectByRegId(@Param("regId") Long regId);
    List<MedicalRecord> selectByPatient(@Param("patientId") Long patientId);
    int insert(MedicalRecord record);
    int update(MedicalRecord record);
}
