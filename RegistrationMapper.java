package com.his.mapper;

import com.his.entity.Registration;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import java.util.List;

@Mapper
public interface RegistrationMapper {
    Registration selectById(@Param("regId") Long regId);
    Registration selectByRegNo(@Param("regNo") String regNo);
    List<Registration> selectList(Registration registration);
    List<Registration> selectByDoctorAndDate(@Param("doctorId") Long doctorId, @Param("regDate") Date regDate, @Param("status") String status);
    List<Registration> selectByPatient(@Param("patientId") Long patientId);
    int insert(Registration registration);
    int update(Registration registration);
    int updateStatus(@Param("regId") Long regId, @Param("status") String status);
    int getMaxQueueNumber(@Param("doctorId") Long doctorId, @Param("regDate") Date regDate, @Param("timeSlot") String timeSlot);
}
