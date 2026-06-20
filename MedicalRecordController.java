package com.his.controller;

import com.his.entity.MedicalRecord;
import com.his.service.MedicalRecordService;
import com.his.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/medical")
@CrossOrigin
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @GetMapping("/{id}")
    public Result<MedicalRecord> getById(@PathVariable Long id) {
        return Result.success(medicalRecordService.getById(id));
    }

    @GetMapping("/reg/{regId}")
    public Result<MedicalRecord> getByRegId(@PathVariable Long regId) {
        return Result.success(medicalRecordService.getByRegId(regId));
    }

    @GetMapping("/patient/{patientId}")
    public Result<List<MedicalRecord>> getByPatient(@PathVariable Long patientId) {
        return Result.success(medicalRecordService.getByPatient(patientId));
    }

    @PostMapping
    public Result<MedicalRecord> add(@RequestBody MedicalRecord record) {
        // save：同一挂号已有病历则更新，否则新增
        return Result.success(medicalRecordService.save(record));
    }

    @PutMapping
    public Result<Void> update(@RequestBody MedicalRecord record) {
        medicalRecordService.update(record);
        return Result.success();
    }
}
