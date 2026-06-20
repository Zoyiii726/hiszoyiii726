package com.his.mapper;

import com.his.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface SysUserMapper {
    SysUser selectByUsername(@Param("username") String username);
    SysUser selectById(@Param("userId") Long userId);
    List<SysUser> selectByRole(@Param("roleCode") String roleCode);
    List<SysUser> selectByDept(@Param("deptId") Long deptId);
    List<SysUser> selectAll();
    List<SysUser> selectDoctorByDeptId(Integer deptId);
    List<SysUser> selectByDeptId(@Param("deptId") Integer deptId);
    int insert(SysUser user);
    int update(SysUser user);
    int deleteById(@Param("userId") Long userId);
}
