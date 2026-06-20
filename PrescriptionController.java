package com.his.controller;

import com.his.entity.Prescription;
import com.his.service.PrescriptionService;
import com.his.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/prescription")
@CrossOrigin
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping("/{id}")
    public Result<Prescription> getById(@PathVariable Long id) {
        // getById 会带出明细 + 合计金额
        return Result.success(prescriptionService.getById(id));
    }

    @GetMapping("/list")
    public Result<List<Prescription>> getList(Prescription prescription) {
        return Result.success(prescriptionService.getList(prescription));
    }

    @GetMapping("/reg/{regId}")
    public Result<List<Prescription>> getByReg(@PathVariable Long regId) {
        return Result.success(prescriptionService.getByRegId(regId));
    }

    @GetMapping("/patient/{patientId}")
    public Result<List<Prescription>> getByPatient(@PathVariable Long patientId,
                                                   @RequestParam(required = false) String status) {
        return Result.success(prescriptionService.getByPatient(patientId, status));
    }

    @PostMapping
    public Result<Prescription> add(@RequestBody Prescription prescription) {
        // add：生成单号 + 插主表 + 批量插明细 + 计算合计（事务在 Service 层）
        return Result.success(prescriptionService.add(prescription));
    }

    @PutMapping
    public Result<Void> update(@RequestBody Prescription prescription) {
        prescriptionService.update(prescription);
        return Result.success();
    }

    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestParam String status) {
        prescriptionService.updateStatus(id, status);
        return Result.success();
    }
}
