package com.ems.employeems.services;

import com.ems.employeems.model.Department;

import java.util.List;

public interface DepartmentServices {
    Department addDepartment(Department department);
    Department getDepartmentById(long id);
    List<Department> getDepartmentByName(String name);
    List<Department> getAllDepartments();
    Department updateDepartment(Department department);
    void deleteDepartment(long id);
    List<Department> findDepartmentByKeyword(String keyword);
}
