package com.ems.employeems.controller;

import com.ems.employeems.exceptionHandling.ResourceNotFoundException;
import com.ems.employeems.exceptionHandling.UserAlreadyExistsException;
import com.ems.employeems.model.Department;
import com.ems.employeems.model.Employee;
import com.ems.employeems.services.DepartmentServices;
import com.ems.employeems.services.EmployeeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/department") //base URL
public class DepartmentCont {
    @Autowired
    private DepartmentServices departmentServices;

    @Autowired
    private EmployeeServices employeeServices;

    @Autowired
    JdbcTemplate jdbcTemplate;

//    @Transactional
//    @PostMapping("/addDepartment")
//    public ResponseEntity<Department> addNewDepartment(@RequestBody Department department) {
//        Department newDepartment = departmentServices.addDepartment(department);
//        return new ResponseEntity<>(newDepartment, HttpStatus.CREATED);
//    }

    @GetMapping("/newDepartmentForm")
    public String showDepartmentForm(Model model) {
        Department department = new Department();
        model.addAttribute("department", department);
        return "newDepartmentForm";
    }

    @Transactional
    @PostMapping("/addDepartment")
    public String addDepartment(@ModelAttribute("department") Department department) {
        departmentServices.addDepartment(department);
        return "redirect:/department/all";
    }

//    @GetMapping("/all")
//    public ResponseEntity<List<Department>> findAllDepartments() {
//        List<Department> departments = departmentServices.getAllDepartments();
//        return new ResponseEntity<>(departments, HttpStatus.OK);
//    }

    @GetMapping("/all")
    public String findAllDepartments(Model model, String keyword) {
        if (keyword != null) {
            model.addAttribute("departments", departmentServices.findDepartmentByKeyword(keyword));
        } else {
            model.addAttribute("departments", departmentServices.getAllDepartments());
        }
        return "departments";
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Department> findDepartmentById(@PathVariable("id") Long id) {
        Department department = departmentServices.getDepartmentById(id);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    @RequestMapping(value = "/findName", method = RequestMethod.GET)
    @ResponseBody
    public List<Department> findDepartmentByName(@RequestParam(value = "name") String name) {
        List<Department> department = departmentServices.getDepartmentByName(name);
        return department;
    }

//    @Transactional
//    @PutMapping("/updateDepartment/{id}")
//    public ResponseEntity<Department> updateDepartment(@PathVariable("id") Long id, @RequestBody Department department) throws ResourceNotFoundException {
//        Department department1 = departmentServices.getDepartmentById(id);
//        department1.setEmail(department.getEmail());
//        department1.setName(department.getName());
//        department1.setPhoneNumber(department.getPhoneNumber());
//        Department updateDepartment = departmentServices.updateDepartment(department1);
//        return new ResponseEntity<>(updateDepartment, HttpStatus.OK);
//    }

    @GetMapping("/showUpdateForm/{id}/update")
    public String updateDepartment(Model model, @PathVariable(value = "id") Long id) {
        Department department = departmentServices.getDepartmentById(id);
        model.addAttribute("department", department);
        return "departmentUpdateForm";
    }

    @Transactional
    @PostMapping("/updateDepartment")
    public String updateDepartment(@ModelAttribute("department") Department department) {
        departmentServices.updateDepartment(department);
        return "redirect:/department/all";
    }

//    @DeleteMapping("/deleteDepartment/{id}")
//    public ResponseEntity<?> deleteDepartment(@PathVariable("id") Long id) {
//        departmentServices.deleteDepartment(id); //Since we are returning anything
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @GetMapping("/deleteDepartment/{id}")
    public String deleteDepartment(@PathVariable("id") Long id) {
        this.departmentServices.deleteDepartment(id);
        return "redirect:/department/all";
    }
}
