package com.his.service;

import com.his.entity.Department;
import java.util.List;

public interface DepartmentService {
    Department getById(Long deptId);
    List<Department> getAll();
    List<Department> getByType(String deptType);
    boolean add(Department department);
    boolean update(Department department);
    boolean delete(Long deptId);
}
