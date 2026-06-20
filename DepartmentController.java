package com.his.controller;

import com.his.entity.Department;
import com.his.service.DepartmentService;
import com.his.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/department")
@CrossOrigin
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/{id}")
    public Result<Department> getById(@PathVariable Long id) {
        return Result.success(departmentService.getById(id));
    }

    @GetMapping("/list")
    public Result<List<Department>> getAll() {
        return Result.success(departmentService.getAll());
    }

    @GetMapping("/type/{type}")
    public Result<List<Department>> getByType(@PathVariable String type) {
        return Result.success(departmentService.getByType(type));
    }

    @PostMapping
    public Result<Void> add(@RequestBody Department department) {
        departmentService.add(department);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody Department department) {
        departmentService.update(department);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        departmentService.delete(id);
        return Result.success();
    }
}
