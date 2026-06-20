package com.his.service;

import com.his.entity.ChargeRecord;
import java.util.Date;
import java.util.List;

public interface ChargeService {
    ChargeRecord getById(Long chargeId);
    List<ChargeRecord> getList(ChargeRecord chargeRecord);
    List<ChargeRecord> getByPatient(Long patientId);
    List<ChargeRecord> getByDateRange(Date startDate, Date endDate);
    ChargeRecord charge(ChargeRecord chargeRecord);
    boolean update(ChargeRecord chargeRecord);
    boolean refund(Long chargeId, Long userId);
}
