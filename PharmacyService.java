package com.his.service;

import com.his.entity.MedicineStockLog;
import com.his.entity.Prescription;
import java.util.List;

public interface PharmacyService {
    /** 待发药处方列表（已缴费 PAID） */
    List<Prescription> getDispenseList();

    /** 发药：处方状态置 DISPENSED + 扣减库存 + 记录发药流水 */
    boolean dispense(Long presId, Long operatorId);

    /** 退药：处方状态回退 UNPAID + 库存加回 + 记录退药流水 */
    boolean refund(Long presId, Long operatorId);

    /** 库存流水记录（发药/退药/出入库） */
    List<MedicineStockLog> getStockLogs(String optType);
}
