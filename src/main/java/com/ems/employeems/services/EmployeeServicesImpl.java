package com.ems.employeems.services;

import com.ems.employeems.exceptionHandling.DepartmentNotFoundException;
import com.ems.employeems.exceptionHandling.UserAlreadyExistsException;
import com.ems.employeems.model.Department;
import com.ems.employeems.model.Employee;
import com.ems.employeems.repository.DepartmentRepo;
import com.ems.employeems.repository.EmployeeRepo;
import com.ems.employeems.exceptionHandling.UserNotFoundException;

import org.hibernate.internal.build.AllowPrintStacktrace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServicesImpl implements EmployeeServices
{
    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private EmployeeServices employeeServices;

    @Autowired
    private AddressServices addressServices;

    @Autowired
    JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    RowMapper<Employee> rowMapper;

    @Override
    @Transactional
    public Employee addEmployee(Employee employee) {
        if (employeeServices.findEmployeeByCode(employee.getEmployeeCode()).isEmpty() && (employeeServices.checkDepartment(employee.getDepartment()).size() > 0) && (addressServices.findAddressByStreet(employee.getAddress().getStreet()).isEmpty())) {
            return employeeRepo.save(employee);
        } else {
            throw new UserAlreadyExistsException("Employee with code " + employee.getEmployeeCode() + " already exists.");
        }
    }

    @Override
    public Employee getEmployeeById(long id) {
        Optional<Employee> opt = employeeRepo.findById(id);
        //Optional is used to remove the NullPointerException.
        Employee employee = null;
        if (opt.isPresent()) {
            employee = opt.get();
        } else {
            throw new UserNotFoundException("Employee by id " + id + " was not found!");
        }
        return employee;
    }

    @Override
    public List<Employee> getAllEmployees()
    {
//        String query = "Select * from employees";
//        List<Employee> employeeList = jdbcTemplate.query(query, rowMapper);
//        return employeeList;
        return employeeRepo.findAll();
    }

    @Override
    public List<Employee> findEmployeeByCode(Long code) {
        return employeeRepo.findByCode(code);
    }

    @Override
    public List<Employee> findEmployeeByName(String name) {
        return employeeRepo.findByName(name);
    }

    @Override
    public List<Employee> findEmployeeByNameAndCode(Long code, String name) {
        return employeeRepo.findByNameAndCode(code, name);
    }

    @Override
    @Transactional
    public Employee updateEmployee(Employee employee) {
        Employee employee1 = employeeServices.getEmployeeById(employee.getId());
        if ((employeeServices.checkDepartment(employee.getDepartment()).size() > 0) && (employee1.getEmployeeCode().equals(employee.getEmployeeCode())) ) {
            return employeeRepo.save(employee);
        } else if ((employeeServices.checkDepartment(employee.getDepartment()).size() > 0) &&  (employeeServices.findEmployeeByCode(employee.getEmployeeCode()).isEmpty())) { //(employee1.getEmployeeCode() != employee.getEmployeeCode())
            return employeeRepo.save(employee);
        } else {
            throw new DepartmentNotFoundException("Department with name " + employee.getDepartment() + " not there in this company");
        }
    }

    @Override
    public void deleteEmployee(Long id) {
//        String query = "DELETE FROM employees where id=" + id;
//        jdbcTemplate.update(query);
        this.employeeRepo.deleteById(id);
    }

    @Override
    public void deleteEmployeeByCode(Long code) {
        this.employeeRepo.deleteEmployeeByCode(code);
    }

    @Override
    public void deleteAll() {
//        String query = "DELETE FROM employees";
//        jdbcTemplate.update(query);
        this.employeeRepo.deleteAll();
    }

    @Override
    public List<Department> checkDepartment(String department) {
        List<Department> departments = departmentRepo.findByName(department);
        return departments;
    }

    @Override
    public List<Employee> findEmployeeByKeyword(String keyword) {
        return employeeRepo.findByKeyword(keyword);
    }

    @Override
    public List<Employee> findEmployeeByCity(String city) {
        return employeeRepo.findEmployeeByCity(city);
    }
}
