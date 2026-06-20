package com.his.controller;

import com.his.entity.Patient;
import com.his.service.PatientService;
import com.his.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/patient")
@CrossOrigin
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/{id}")
    public Result<Patient> getById(@PathVariable Long id) {
        return Result.success(patientService.getById(id));
    }

    @GetMapping("/no/{patientNo}")
    public Result<Patient> getByPatientNo(@PathVariable String patientNo) {
        return Result.success(patientService.getByPatientNo(patientNo));
    }

    @GetMapping("/list")
    public Result<List<Patient>> getList(@RequestParam(required = false) String keyword) {
        return Result.success(patientService.getList(keyword));
    }

    @PostMapping
    public Result<Patient> add(@RequestBody Patient patient) {
        return Result.success(patientService.add(patient));
    }

    @PutMapping
    public Result<Void> update(@RequestBody Patient patient) {
        patientService.update(patient);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        patientService.delete(id);
        return Result.success();
    }
}
