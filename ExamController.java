package com.his.controller;

import com.his.entity.ExamApply;
import com.his.entity.ExamItem;
import com.his.service.ExamApplyService;
import com.his.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/exam")
@CrossOrigin
public class ExamController {

    @Autowired
    private ExamApplyService examApplyService;

    // 检查项目
    @GetMapping("/item/list")
    public Result<List<ExamItem>> getItemList(@RequestParam(required = false) String keyword) {
        return Result.success(examApplyService.getItemList(keyword));
    }

    @PostMapping("/item")
    public Result<Void> addItem(@RequestBody ExamItem item) {
        examApplyService.addItem(item);
        return Result.success();
    }

    // 检查申请
    @GetMapping("/apply/{id}")
    public Result<ExamApply> getApplyById(@PathVariable Long id) {
        return Result.success(examApplyService.getById(id));
    }

    @GetMapping("/apply/list")
    public Result<List<ExamApply>> getApplyList(ExamApply examApply) {
        return Result.success(examApplyService.getList(examApply));
    }

    @GetMapping("/apply/reg/{regId}")
    public Result<List<ExamApply>> getApplyByReg(@PathVariable Long regId) {
        return Result.success(examApplyService.getByRegId(regId));
    }

    @GetMapping("/apply/patient/{patientId}")
    public Result<List<ExamApply>> getApplyByPatient(@PathVariable Long patientId,
                                                     @RequestParam(required = false) String status) {
        return Result.success(examApplyService.getByPatient(patientId, status));
    }

    @PostMapping("/apply")
    public Result<ExamApply> addApply(@RequestBody ExamApply examApply) {
        return Result.success(examApplyService.addApply(examApply));
    }

    @PutMapping("/apply")
    public Result<Void> updateApply(@RequestBody ExamApply examApply) {
        // 录入检查结果时走 executeResult（置 COMPLETED + 记录时间）
        if (examApply.getExamResult() != null) {
            examApplyService.executeResult(examApply);
        } else {
            examApplyService.update(examApply);
        }
        return Result.success();
    }

    @PutMapping("/apply/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestParam String status) {
        examApplyService.updateStatus(id, status);
        return Result.success();
    }
}
