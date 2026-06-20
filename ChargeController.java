package com.his.controller;

import com.his.entity.ChargeRecord;
import com.his.service.ChargeService;
import com.his.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/charge")
@CrossOrigin
public class ChargeController {

    @Autowired
    private ChargeService chargeService;

    @GetMapping("/{id}")
    public Result<ChargeRecord> getById(@PathVariable Long id) {
        return Result.success(chargeService.getById(id));
    }

    @GetMapping("/list")
    public Result<List<ChargeRecord>> getList(ChargeRecord chargeRecord) {
        return Result.success(chargeService.getList(chargeRecord));
    }

    @GetMapping("/patient/{patientId}")
    public Result<List<ChargeRecord>> getByPatient(@PathVariable Long patientId) {
        return Result.success(chargeService.getByPatient(patientId));
    }

    @GetMapping("/date")
    public Result<List<ChargeRecord>> getByDateRange(@RequestParam Date startDate,
                                                      @RequestParam Date endDate) {
        return Result.success(chargeService.getByDateRange(startDate, endDate));
    }

    @PostMapping
    public Result<ChargeRecord> add(@RequestBody ChargeRecord chargeRecord) {
        // 收费：生成单号 + 置 PAID + 联动把对应申请单状态改为 PAID
        return Result.success(chargeService.charge(chargeRecord));
    }

    @PutMapping
    public Result<Void> update(@RequestBody ChargeRecord chargeRecord) {
        chargeService.update(chargeRecord);
        return Result.success();
    }

    @PutMapping("/{id}/refund")
    public Result<Void> refund(@PathVariable Long id, @RequestParam Long userId) {
        // 退费：标记 REFUNDED + 联动把对应申请单状态回退
        chargeService.refund(id, userId);
        return Result.success();
    }
}
