package com.his.controller;

import com.his.entity.Medicine;
import com.his.service.MedicineService;
import com.his.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/medicine")
@CrossOrigin
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @GetMapping("/{id}")
    public Result<Medicine> getById(@PathVariable Long id) {
        return Result.success(medicineService.getById(id));
    }

    @GetMapping("/code/{code}")
    public Result<Medicine> getByCode(@PathVariable String code) {
        return Result.success(medicineService.getByCode(code));
    }

    @GetMapping("/list")
    public Result<List<Medicine>> getList(@RequestParam(required = false) String keyword) {
        return Result.success(medicineService.getList(keyword));
    }

    @PostMapping
    public Result<Void> add(@RequestBody Medicine medicine) {
        medicineService.add(medicine);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody Medicine medicine) {
        medicineService.update(medicine);
        return Result.success();
    }

    @PutMapping("/{id}/stock")
    public Result<Void> updateStock(@PathVariable Long id, @RequestParam Integer quantity) {
        // quantity 为正加库存，为负减库存
        medicineService.updateStock(id, quantity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        medicineService.delete(id);
        return Result.success();
    }
}
