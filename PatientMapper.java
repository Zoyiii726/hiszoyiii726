package com.his.mapper;

import com.his.entity.Patient;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface PatientMapper {
    Patient selectByIdCard(String idCard);
    Patient selectById(@Param("patientId") Long patientId);
    Patient selectByPatientNo(@Param("patientNo") String patientNo);
    List<Patient> selectList(@Param("keyword") String keyword);
    int insert(Patient patient);
    int update(Patient patient);
    int deleteById(@Param("patientId") Long patientId);
}
