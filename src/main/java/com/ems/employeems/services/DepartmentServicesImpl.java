package com.ems.employeems.services;

import com.ems.employeems.exceptionHandling.UserAlreadyExistsException;
import com.ems.employeems.exceptionHandling.UserNotFoundException;
import com.ems.employeems.model.Department;
import com.ems.employeems.repository.DepartmentRepo;
import com.sun.rowset.internal.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServicesImpl implements DepartmentServices {

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private DepartmentServices departmentServices;

    @Autowired
    private JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    RowMapper<Department> rowMapper;

    @Override
    public Department addDepartment(Department department) {
        if (departmentServices.getDepartmentByName(department.getName()).isEmpty()) {
            return departmentRepo.save(department);
        } else {
            throw new UserAlreadyExistsException("Department with name " + department.getName() + " already exists.");
        }
    }

    @Override
    public Department getDepartmentById(long id) {
        Optional<Department> opt = departmentRepo.findById(id);
        Department department = null;
        if (opt.isPresent()) {
            department = opt.get();
        } else {
            throw new UserNotFoundException("Department by id " + id + " was not found!");
        }
        return department;
    }

    @Override
    public List<Department> getDepartmentByName(String name) {
        return departmentRepo.findByName(name);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepo.findAll();
    }

    @Override
    public Department updateDepartment(Department department) {
        return departmentRepo.save(department);
    }

    @Override
    public void deleteDepartment(long id) {
        this.departmentRepo.deleteById(id);
    }

    @Override
    public List<Department> findDepartmentByKeyword(String keyword) {
        return departmentRepo.findByKeyword(keyword);
    }
}
