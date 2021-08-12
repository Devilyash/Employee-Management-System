package com.ems.employeems;

import com.ems.employeems.controller.EmployeeCont;
import com.ems.employeems.exceptionHandling.UserAlreadyExistsException;
import com.ems.employeems.exceptionHandling.UserNotFoundException;
import com.ems.employeems.model.Address;
import com.ems.employeems.model.Department;
import com.ems.employeems.model.Employee;
import com.ems.employeems.model.Gender;
import com.ems.employeems.repository.DepartmentRepo;
import com.ems.employeems.repository.EmployeeRepo;
import com.ems.employeems.services.AddressServices;
import com.ems.employeems.services.DepartmentServices;
import com.ems.employeems.services.EmployeeServices;
import com.ems.employeems.services.EmployeeServicesImpl;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;



//@RunWith(SpringRunner.class)
@SpringBootTest //It will automatically create ApplicationContext used in out tests through spring application.
class DepartmentApplicatinTest {

    @Autowired
    EmployeeServices employeeServices;

    @Autowired
    DepartmentServices departmentServices;

    @Autowired
    EmployeeCont employeeCont;

    @Autowired
    AddressServices addressServices;

    @MockBean
    EmployeeRepo employeeRepo;

	@MockBean
	DepartmentRepo departmentRepo;

    @Test
    void addDepartmentTestOne() {
        Department department = new Department();
        department.setName("Development");
        department.setEmail("development@lentra.ai");
        department.setPhoneNumber(1234566322L);

		when(departmentRepo.save(department)).thenReturn(department);
        assertEquals(department, departmentServices.addDepartment(department));
    }

//    @Test
//    void addDepartmentTestTwo() {
//
//        Department department = new Department();
//        department.setName("Development");
//        department.setEmail("development@lentra.ai");
//        department.setPhoneNumber(1234566322L);
////        when(departmentRepo.save(department)).thenReturn(department);
//        assertThrows(UserAlreadyExistsException.class,
//                () -> {
//                    departmentServices.addDepartment(department);
//                });
//    }
}
