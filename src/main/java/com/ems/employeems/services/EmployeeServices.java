package com.ems.employeems.services;

import com.ems.employeems.model.Department;
import com.ems.employeems.model.Employee;

import java.util.List;

public interface EmployeeServices {
    Employee addEmployee(Employee employee);
    Employee getEmployeeById(long id);
    List<Employee> getAllEmployees();
    Employee updateEmployee(Employee employee);
    void deleteEmployee(Long id);
    List<Employee> findEmployeeByCode(Long code);
    List<Employee> findEmployeeByName(String name);
    List<Employee> findEmployeeByNameAndCode(Long code, String name);
    void deleteAll();
    List<Department> checkDepartment(String department);
    void deleteEmployeeByCode(Long code);
    List<Employee> findEmployeeByKeyword(String keyword);
    List<Employee> findEmployeeByCity(String city);
}
