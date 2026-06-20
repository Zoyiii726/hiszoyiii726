package com.his.controller;

import com.his.entity.TreatmentApply;
import com.his.entity.TreatmentItem;
import com.his.service.TreatmentApplyService;
import com.his.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/treatment")
@CrossOrigin
public class TreatmentController {

    @Autowired
    private TreatmentApplyService treatmentApplyService;

    @GetMapping("/item/list")
    public Result<List<TreatmentItem>> getItemList(@RequestParam(required = false) String keyword) {
        return Result.success(treatmentApplyService.getItemList(keyword));
    }

    @PostMapping("/item")
    public Result<Void> addItem(@RequestBody TreatmentItem item) {
        treatmentApplyService.addItem(item);
        return Result.success();
    }

    @GetMapping("/apply/{id}")
    public Result<TreatmentApply> getApplyById(@PathVariable Long id) {
        return Result.success(treatmentApplyService.getById(id));
    }

    @GetMapping("/apply/list")
    public Result<List<TreatmentApply>> getApplyList(TreatmentApply treatmentApply) {
        return Result.success(treatmentApplyService.getList(treatmentApply));
    }

    @GetMapping("/apply/reg/{regId}")
    public Result<List<TreatmentApply>> getApplyByReg(@PathVariable Long regId) {
        return Result.success(treatmentApplyService.getByRegId(regId));
    }

    @GetMapping("/apply/patient/{patientId}")
    public Result<List<TreatmentApply>> getApplyByPatient(@PathVariable Long patientId,
                                                         @RequestParam(required = false) String status) {
        return Result.success(treatmentApplyService.getByPatient(patientId, status));
    }

    @PostMapping("/apply")
    public Result<TreatmentApply> addApply(@RequestBody TreatmentApply treatmentApply) {
        return Result.success(treatmentApplyService.addApply(treatmentApply));
    }

    @PutMapping("/apply")
    public Result<Void> updateApply(@RequestBody TreatmentApply treatmentApply) {
        // 录入处置结果时走 executeResult（置 COMPLETED + 记录时间）
        if (treatmentApply.getTreatResult() != null) {
            treatmentApplyService.executeResult(treatmentApply);
        } else {
            treatmentApplyService.update(treatmentApply);
        }
        return Result.success();
    }

    @PutMapping("/apply/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestParam String status) {
        treatmentApplyService.updateStatus(id, status);
        return Result.success();
    }
}
