package com.his.controller;

import com.his.entity.LabApply;
import com.his.entity.LabItem;
import com.his.service.LabApplyService;
import com.his.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/lab")
@CrossOrigin
public class LabController {

    @Autowired
    private LabApplyService labApplyService;

    @GetMapping("/item/list")
    public Result<List<LabItem>> getItemList(@RequestParam(required = false) String keyword) {
        return Result.success(labApplyService.getItemList(keyword));
    }

    @PostMapping("/item")
    public Result<Void> addItem(@RequestBody LabItem item) {
        labApplyService.addItem(item);
        return Result.success();
    }

    @GetMapping("/apply/{id}")
    public Result<LabApply> getApplyById(@PathVariable Long id) {
        return Result.success(labApplyService.getById(id));
    }

    @GetMapping("/apply/list")
    public Result<List<LabApply>> getApplyList(LabApply labApply) {
        return Result.success(labApplyService.getList(labApply));
    }

    @GetMapping("/apply/reg/{regId}")
    public Result<List<LabApply>> getApplyByReg(@PathVariable Long regId) {
        return Result.success(labApplyService.getByRegId(regId));
    }

    @GetMapping("/apply/patient/{patientId}")
    public Result<List<LabApply>> getApplyByPatient(@PathVariable Long patientId,
                                                    @RequestParam(required = false) String status) {
        return Result.success(labApplyService.getByPatient(patientId, status));
    }

    @PostMapping("/apply")
    public Result<LabApply> addApply(@RequestBody LabApply labApply) {
        return Result.success(labApplyService.addApply(labApply));
    }

    @PutMapping("/apply")
    public Result<Void> updateApply(@RequestBody LabApply labApply) {
        // 录入检验结果时走 executeResult（置 COMPLETED + 记录时间）
        if (labApply.getLabResult() != null) {
            labApplyService.executeResult(labApply);
        } else {
            labApplyService.update(labApply);
        }
        return Result.success();
    }

    @PutMapping("/apply/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestParam String status) {
        labApplyService.updateStatus(id, status);
        return Result.success();
    }
}
