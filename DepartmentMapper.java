package com.his.mapper;

import com.his.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface DepartmentMapper {
    Department selectById(@Param("deptId") Long deptId);
    List<Department> selectAll();
    List<Department> selectByType(@Param("deptType") String deptType);
    int insert(Department department);
    int update(Department department);
    int deleteById(@Param("deptId") Long deptId);
}
