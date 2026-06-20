package com.his.mapper;

import com.his.entity.Prescription;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface PrescriptionMapper {
    Prescription selectById(@Param("presId") Long presId);
    List<Prescription> selectList(Prescription prescription);
    List<Prescription> selectByRegId(@Param("regId") Long regId);
    List<Prescription> selectByPatient(@Param("patientId") Long patientId, @Param("status") String status);
    int insert(Prescription prescription);
    int update(Prescription prescription);
    int updateStatus(@Param("presId") Long presId, @Param("status") String status);
}
