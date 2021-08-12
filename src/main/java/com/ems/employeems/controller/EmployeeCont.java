package com.ems.employeems.controller;

import ch.qos.logback.core.joran.spi.EventPlayer;
import com.ems.employeems.exceptionHandling.DepartmentNotFoundException;
import com.ems.employeems.model.Address;
import com.ems.employeems.model.Department;
import com.ems.employeems.model.Employee;
import com.ems.employeems.repository.AddressRepo;
import com.ems.employeems.repository.EmployeeRepo;
import com.ems.employeems.services.AddressServices;
import com.ems.employeems.services.DepartmentServices;
import com.ems.employeems.services.EmployeeServices;
import com.ems.employeems.exceptionHandling.ResourceNotFoundException;
import com.ems.employeems.exceptionHandling.UserAlreadyExistsException;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.ui.Model;

import javax.management.Query;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employee") //base URL
public class EmployeeCont {
    @Autowired
    private EmployeeServices employeeServices;

    @Autowired
    private AddressServices addressServices;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private DepartmentServices departmentServices;

//    @Autowired
//    JdbcTemplate jdbcTemplate;

//    @Transactional
//    @PostMapping("/addEmployee")
//    public ResponseEntity<Employee> addNewEmployee(@RequestBody Employee employee) {
//        Employee newEmployee = employeeServices.addEmployee(employee);
//        return new ResponseEntity<>(newEmployee, HttpStatus.OK);
//    }

//        String query = "SELECT * from employees where employee_code=" + employee.getEmployeeCode();
//        RowMapper<Employee> rowMapper = new RowMapperImpl();
//        List<Employee> employeeList = jdbcTemplate.query(query, rowMapper);
//        if (employeeList.isEmpty()) {
//            Employee newEmployee = employeeServices.addEmployee(employee);
//            return new ResponseEntity<>(newEmployee, HttpStatus.CREATED); //httpstatus.created because we just created the new information.
//        } else {
//            throw new UserAlreadyExistsException("Employee with code " + employee.getEmployeeCode() + " already exists.");
//        }

//        if (employeeServices.findEmployeeByCode(employee.getEmployeeCode()).isEmpty() && (employeeServices.checkDepartment(employee.getDepartment()).size() > 0) && (addressServices.findAddressByStreet(employee.getAddress().getStreet()).isEmpty())) {
//            Employee newEmployee = employeeServices.addEmployee(employee);
//            return new ResponseEntity<>(newEmployee, HttpStatus.CREATED); //httpstatus.created because we just created the new information.
//        } else {
//            throw new UserAlreadyExistsException("Employee with code " + employee.getEmployeeCode() + " already exists.");
//        }

    @GetMapping("/newEmployeeForm")
    public String showEmployeeForm(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        List<Department> departments = departmentServices.getAllDepartments();
        model.addAttribute("departments", departments);
        return "newEmployeeForm";
    }

    @Transactional
    @PostMapping("/addEmployee")
    public String addEmployee(@ModelAttribute("employee") Employee employee) {
        employeeServices.addEmployee(employee);
        return "redirect:/employee/all";
    }

    @GetMapping("/all")
    public String findAllEmployees(Model model, String keyword, String city) {
        if (keyword == null && city == null) {
            System.out.println(2);
            model.addAttribute("employees", employeeServices.getAllEmployees());
        } else if (keyword.matches("[0-9]+")) {
            System.out.println(3);
            model.addAttribute("employees", employeeServices.findEmployeeByCode(Long.parseLong(keyword)));
        } else if (keyword != null && city.equals("")) {
            System.out.println(4);
            model.addAttribute("employees", employeeServices.findEmployeeByKeyword(keyword));
        } else if (city != null) {
            System.out.println(1);
            model.addAttribute("employees", employeeServices.findEmployeeByCity(city));
        }
        return "employees";
    }

//    @GetMapping("/all")
//    public ResponseEntity<List<Employee>> findAllEmployees() {
//        List<Employee> employees = employeeServices.getAllEmployees();
//        return new ResponseEntity<>(employees, HttpStatus.OK);
//    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Employee> findEmployeeById(@PathVariable("id") Long id) {
        Employee employee = employeeServices.getEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

//    @RequestMapping(value = "/findName/{name}", method = RequestMethod.GET)
//    @ResponseBody
//    public List<Employee> findEmployeeByName(@PathVariable("name") String name) {
//        List<Employee> employee = employeeServices.findEmployeeByName(name);
//        return employee;
//    }

    @RequestMapping(value = "/findName", method = RequestMethod.GET)
    @ResponseBody
    public List<Employee> findEmployeeByName(@RequestParam(value = "name") String name) {
        List<Employee> employee = employeeServices.findEmployeeByName(name);
        return employee;
    }

//    @GetMapping("/find/{code}")
    @RequestMapping(value = "/findCode/{code}", method = RequestMethod.GET)
    @ResponseBody
    public List<Employee> findEmployeeByCode(@PathVariable("code") Long code) {
        List<Employee> employee = employeeServices.findEmployeeByCode(code);
        return employee;
    }

//    @Transactional
//    @PutMapping("/updateEmployee/{id}")
//    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long id , @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
//        Employee employee = employeeServices.getEmployeeById(id);
//        if (employeeServices.findEmployeeByNameAndCode(employeeDetails.getEmployeeCode(), employeeDetails.getName()).isEmpty() && (employeeServices.checkDepartment(employeeDetails.getDepartment()).size() > 0) ) {
//            employee.setEmployeeCode(employeeDetails.getEmployeeCode());
//            employee.setEmail(employeeDetails.getEmail());
//            employee.setDesignation(employeeDetails.getDesignation());
//            employee.setPhoneNumber(employeeDetails.getPhoneNumber());
//            employee.setName(employeeDetails.getName());
//            employee.setGender(employeeDetails.getGender());
//            employee.setDepartment(employeeDetails.getDepartment());
//            employee.setAddress(employeeDetails.getAddress());
//            Employee updateEmployee = employeeServices.updateEmployee(employee);
//            return new ResponseEntity<>(updateEmployee, HttpStatus.OK);
//        } else if (employeeServices.findEmployeeByNameAndCode(employeeDetails.getEmployeeCode(), employeeDetails.getName()).size() > 0){
//            throw new UserAlreadyExistsException("Employee with code " + employeeDetails.getEmployeeCode() + " already exists");
//        } else {
//            throw new DepartmentNotFoundException("Department with name " + employeeDetails.getDepartment() + " not there in this company");
//        }
//    }

    @GetMapping("/showUpdateForm/{id}/update")
    public String updateEmployee(Model model, @PathVariable(value = "id") Long id) {
        Employee employee = employeeServices.getEmployeeById(id);
        model.addAttribute("employee", employee);
        List<Department> departments = departmentServices.getAllDepartments();
        model.addAttribute("departments", departments);
        return "showUpdateForm";
    }

    @Transactional
    @PostMapping("/updateEmployee")
    public String updateEmployee(@ModelAttribute("employee") Employee employee) {
        employeeServices.updateEmployee(employee);
        return "redirect:/employee/all";
    }

//    @DeleteMapping("/deleteEmployee/{id}")
//    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id) {
//        employeeServices.deleteEmployee(id); //Since we are returning anything
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
    
    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable("id") Long id) {
        this.employeeServices.deleteEmployee(id);
        return "redirect:/employee/all";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<?> deleteEmployeeByCode(@RequestParam(value = "code") Long code) {
        employeeServices.deleteEmployeeByCode(code);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAll() {
        employeeServices.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
