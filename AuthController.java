package com.his.controller;

import com.his.entity.SysUser;
import com.his.service.SysUserService;
import com.his.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
    
    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        
        SysUser user = sysUserService.login(username, password);
        if (user != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("user", user);
            data.put("token", "token_" + user.getUserId() + "_" + System.currentTimeMillis());
            return Result.success("登录成功", data);
        }
        return Result.error("用户名或密码错误");
    }

    @GetMapping("/info")
    public Result<SysUser> getInfo(@RequestParam Long userId) {
        SysUser user = sysUserService.getById(userId);
        user.setPassword(null);
        return Result.success(user);
    }

    @PostMapping("/logout")
    public Result<Void> logout() {
        return Result.success();
    }
}
