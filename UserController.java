package com.his.controller;

import com.his.entity.SysUser;
import com.his.service.SysUserService;
import com.his.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/dept/{deptId}")
    public Result<List<SysUser>> getDoctorsByDept(@PathVariable Integer deptId) {
        List<SysUser> users = sysUserService.getDoctorsByDept(deptId);
        if (users == null || users.isEmpty()) {
            users = sysUserService.getUsersByDept(deptId);
        }
        return Result.success(users);
    }
}
