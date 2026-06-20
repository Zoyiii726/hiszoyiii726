package com.his.controller;

import com.his.entity.Registration;
import com.his.service.RegistrationService;
import com.his.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/registration")
@CrossOrigin
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/{id}")
    public Result<Registration> getById(@PathVariable Long id) {
        return Result.success(registrationService.getById(id));
    }

    @GetMapping("/no/{regNo}")
    public Result<Registration> getByRegNo(@PathVariable String regNo) {
        return Result.success(registrationService.getByRegNo(regNo));
    }

    @GetMapping("/list")
    public Result<List<Registration>> getList(Registration registration) {
        return Result.success(registrationService.getList(registration));
    }

    @GetMapping("/doctor/{doctorId}")
    public Result<List<Registration>> getByDoctor(@PathVariable Long doctorId,
                                                  @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                                  @RequestParam(required = false) String status) {
        return Result.success(registrationService.getByDoctorAndDate(doctorId, date, status));
    }

    @GetMapping("/patient/{patientId}")
    public Result<List<Registration>> getByPatient(@PathVariable Long patientId) {
        return Result.success(registrationService.getByPatient(patientId));
    }

    @PostMapping
    public Result<Registration> add(@RequestBody Registration registration) {
        try {
            return Result.success(registrationService.register(registration));
        } catch (IllegalArgumentException e) {
            return Result.error(400, e.getMessage());
        }
    }

    @PutMapping
    public Result<Void> update(@RequestBody Registration registration) {
        registrationService.update(registration);
        return Result.success();
    }

    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestParam String status) {
        registrationService.updateStatus(id, status);
        return Result.success();
    }
}
