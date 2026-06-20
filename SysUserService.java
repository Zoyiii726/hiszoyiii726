package com.his.service;

import com.his.entity.SysUser;
import java.util.List;

public interface SysUserService {
    SysUser login(String username, String password);
    SysUser getById(Long userId);
    List<SysUser> getByRole(String roleCode);
    List<SysUser> getByDept(Long deptId);
    List<SysUser> getDoctorsByDept(Integer deptId);
    List<SysUser> getUsersByDept(Integer deptId);
    List<SysUser> getAll();
    boolean add(SysUser user);
    boolean update(SysUser user);
    boolean delete(Long userId);
}
