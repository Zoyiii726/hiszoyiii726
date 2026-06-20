package com.his.mapper;

import com.his.entity.ChargeRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import java.util.List;

@Mapper
public interface ChargeRecordMapper {
    ChargeRecord selectById(@Param("chargeId") Long chargeId);
    List<ChargeRecord> selectList(ChargeRecord chargeRecord);
    List<ChargeRecord> selectByPatient(@Param("patientId") Long patientId);
    List<ChargeRecord> selectByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
    int insert(ChargeRecord chargeRecord);
    int update(ChargeRecord chargeRecord);
}
