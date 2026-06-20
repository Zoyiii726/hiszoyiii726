package com.his.controller;

import com.his.entity.MedicineStockLog;
import com.his.entity.Prescription;
import com.his.service.PharmacyService;
import com.his.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 药房管理：发药、退药、库存流水查询（需求第 3 条"药房管理"）
 */
@RestController
@RequestMapping("/pharmacy")
@CrossOrigin
public class PharmacyController {

    @Autowired
    private PharmacyService pharmacyService;

    /**
     * 待发药处方列表（状态 PAID 的处方）
     */
    @GetMapping("/dispense/list")
    public Result<List<Prescription>> dispenseList() {
        return Result.success(pharmacyService.getDispenseList());
    }

    /**
     * 发药：校验 PAID → 置 DISPENSED + 扣减库存 + 记发药流水
     */
    @PutMapping("/dispense/{presId}")
    public Result<Void> dispense(@PathVariable Long presId, @RequestParam Long operatorId) {
        try {
            pharmacyService.dispense(presId, operatorId);
            return Result.success();
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 退药：校验 DISPENSED → 回退状态 + 库存加回 + 记退药流水
     */
    @PutMapping("/refund/{presId}")
    public Result<Void> refund(@PathVariable Long presId, @RequestParam Long operatorId) {
        try {
            pharmacyService.refund(presId, operatorId);
            return Result.success();
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 库存流水记录（发药/退药/出入库），按操作类型筛选
     */
    @GetMapping("/records")
    public Result<List<MedicineStockLog>> records(@RequestParam(required = false) String optType) {
        return Result.success(pharmacyService.getStockLogs(optType));
    }
}
