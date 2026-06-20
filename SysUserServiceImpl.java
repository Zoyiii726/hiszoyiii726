package com.his.service.impl;

import com.his.entity.SysUser;
import com.his.mapper.SysUserMapper;
import com.his.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {
    
    @Autowired
    private SysUserMapper sysUserMapper;
    
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public SysUser login(String username, String password) {
        SysUser user = sysUserMapper.selectByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            user.setPassword(null);
            return user;
        }
        return null;
    }

    @Override
    public SysUser getById(Long userId) {
        return sysUserMapper.selectById(userId);
    }

    @Override
    public List<SysUser> getByRole(String roleCode) {
        return sysUserMapper.selectByRole(roleCode);
    }

    @Override
    public List<SysUser> getByDept(Long deptId) {
        return sysUserMapper.selectByDept(deptId);
    }

    @Override
    public List<SysUser> getDoctorsByDept(Integer deptId) {
        return sysUserMapper.selectDoctorByDeptId(deptId);
    }

    @Override
    public List<SysUser> getUsersByDept(Integer deptId) {
        return sysUserMapper.selectByDeptId(deptId);
    }

    @Override
    public List<SysUser> getAll() {
        return sysUserMapper.selectAll();
    }

    @Override
    public boolean add(SysUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return sysUserMapper.insert(user) > 0;
    }

    @Override
    public boolean update(SysUser user) {
        return sysUserMapper.update(user) > 0;
    }

    @Override
    public boolean delete(Long userId) {
        return sysUserMapper.deleteById(userId) > 0;
    }
}
