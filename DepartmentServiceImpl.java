package com.his.service.impl;

import com.his.entity.Department;
import com.his.mapper.DepartmentMapper;
import com.his.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public Department getById(Long deptId) {
        return departmentMapper.selectById(deptId);
    }

    @Override
    public List<Department> getAll() {
        return departmentMapper.selectAll();
    }

    @Override
    public List<Department> getByType(String deptType) {
        return departmentMapper.selectByType(deptType);
    }

    @Override
    public boolean add(Department department) {
        return departmentMapper.insert(department) > 0;
    }

    @Override
    public boolean update(Department department) {
        return departmentMapper.update(department) > 0;
    }

    @Override
    public boolean delete(Long deptId) {
        return departmentMapper.deleteById(deptId) > 0;
    }
}
