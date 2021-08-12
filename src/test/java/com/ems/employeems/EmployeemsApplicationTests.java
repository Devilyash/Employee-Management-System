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
class EmployeemsApplicationTests {

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

	@Test
	void addEmployeeTestOne() {
		Employee employee = new Employee();

		Address address = new Address();
		address.setId(1L);
		address.setStreet("176 lvii, kunhadi");
		address.setCity("kota");
		address.setState("rajasthan");
		address.setPincode(324008L);


		employee.setName("yash");
		employee.setId(2L);
		employee.setPhoneNumber(123L);
		employee.setEmployeeCode(245L);
		employee.setAddress(address);
		employee.setGender(Gender.MALE);
		employee.setDepartment("R&D");
		employee.setDesignation("SE");
		employee.setEmail("yash@lentra.ai");

		when(employeeRepo.save(employee)).thenReturn(employee);
		assertEquals(employee, employeeServices.addEmployee(employee));
	}

	@Test
	void addEmployeeTestTwo() {
		Employee employee = new Employee();

		Address address = new Address();
		address.setId(1L);
		address.setStreet("176 lvii, kunhadi");
		address.setCity("kota");
		address.setState("rajasthan");
		address.setPincode(324008L);


		employee.setName("yash");
		employee.setId(2L);
		employee.setPhoneNumber(123L);
		employee.setEmployeeCode(1234L);
		employee.setAddress(address);
		employee.setGender(Gender.MALE);
		employee.setDepartment("R&abcd");
		employee.setDesignation("SE");
		employee.setEmail("yash@lentra.ai");

		assertThrows(UserAlreadyExistsException.class,
				() -> {
					employeeServices.addEmployee(employee);
				});
	}

	@Test
	void getEmployeeByIdTestOne() {
		Employee employee = new Employee();

		Address address = new Address();
		address.setStreet("176 lvii, kunhadi");
		address.setCity("kota");
		address.setState("rajasthan");
		address.setPincode(324008L);

		Department department = new Department();
		department.setName("Development");
		department.setEmail("development@lentra.ai");
		department.setPhoneNumber(1234566322L);

		employee.setId(3L);
		employee.setName("yash");
		employee.setPhoneNumber(123L);
		employee.setEmployeeCode(1234L);
		employee.setAddress(address);
		employee.setGender(Gender.MALE);
		employee.setDepartment("Development");
		employee.setDesignation("SE");
		employee.setEmail("yash@lentra.ai");

		when(employeeRepo.findById(employee.getId())).thenReturn(Optional.of(employee));
		assertEquals(employee, employeeServices.getEmployeeById(employee.getId()));
	}

	@Test
	void getEmployeeByIdTestTwo() {

		assertThrows(UserNotFoundException.class,
				() -> {
					employeeServices.getEmployeeById(14865);
				});
	}

	@Test
	void getAllEmployeesTestOne() {
		Employee employee = new Employee();

		Address address = new Address();
		address.setStreet("176 lvii, kunhadi");
		address.setCity("kota");
		address.setState("rajasthan");
		address.setPincode(324008L);

		Department department = new Department();
		department.setName("Development");
		department.setEmail("development@lentra.ai");
		department.setPhoneNumber(1234566322L);

		employee.setName("yash");
		employee.setPhoneNumber(123L);
		employee.setEmployeeCode(1234L);
		employee.setAddress(address);
		employee.setGender(Gender.MALE);
		employee.setDepartment("Development");
		employee.setDesignation("SE");
		employee.setEmail("yash@lentra.ai");

		when(employeeRepo.findAll()).thenReturn(Stream.of(employee).collect(Collectors.toList()));
		assertEquals(1, employeeServices.getAllEmployees().size());

		//The Stream.of returns a sequential ordered stream whose elements are the specified values. Stream.of() method simply calls the Arrays.stream() method for non-primitive types.
	}

	@Test
	void getAllEmployeesTestTwo() {
		assertNotEquals(1, employeeServices.getAllEmployees().size());
	}

	@Test
	void getEmployeeByNameTestOne() {
		Employee employee = new Employee();

		Address address = new Address();
		address.setStreet("176 lvii, kunhadi");
		address.setCity("kota");
		address.setState("rajasthan");
		address.setPincode(324008L);

		Department department = new Department();
		department.setName("Development");
		department.setEmail("development@lentra.ai");
		department.setPhoneNumber(1234566322L);

		employee.setName("yash");
		employee.setPhoneNumber(123L);
		employee.setEmployeeCode(1234L);
		employee.setAddress(address);
		employee.setGender(Gender.MALE);
		employee.setDepartment("Development");
		employee.setDesignation("SE");
		employee.setEmail("yash@lentra.ai");

		when(employeeRepo.findByName("yash")).thenReturn(Stream.of(employee).collect(Collectors.toList()));
		assertEquals(1, employeeServices.findEmployeeByName("yash").size());
	}

	@Test
	void getEmployeeByNameTestTwo() {
		assertNotEquals(4, employeeServices.findEmployeeByName("kjabsf").size());
	}

	@Test
	void deleteEmployeeByNameTest() {
		Employee employee = new Employee();

		Address address = new Address();
		address.setStreet("176 lvii, kunhadi");
		address.setCity("kota");
		address.setState("rajasthan");
		address.setPincode(324008L);

		Department department = new Department();
		department.setName("Development");
		department.setEmail("development@lentra.ai");
		department.setPhoneNumber(1234566322L);

		employee.setName("yash");
		employee.setPhoneNumber(123L);
		employee.setEmployeeCode(1234L);
		employee.setAddress(address);
		employee.setGender(Gender.MALE);
		employee.setDepartment("Development");
		employee.setDesignation("SE");
		employee.setEmail("yash@lentra.ai");

		employeeServices.deleteEmployeeByCode(employee.getEmployeeCode());
		verify(employeeRepo, times(1)).deleteEmployeeByCode(employee.getEmployeeCode());
	}

	@Test
	void deleteEmployeeByIdTest() {
		Employee employee = new Employee();

		Address address = new Address();
		address.setStreet("176 lvii, kunhadi");
		address.setCity("kota");
		address.setState("rajasthan");
		address.setPincode(324008L);

		Department department = new Department();
		department.setName("Development");
		department.setEmail("development@lentra.ai");
		department.setPhoneNumber(1234566322L);

		employee.setId(2L);
		employee.setName("yash");
		employee.setPhoneNumber(123L);
		employee.setEmployeeCode(1234L);
		employee.setAddress(address);
		employee.setGender(Gender.MALE);
		employee.setDepartment("Development");
		employee.setDesignation("SE");
		employee.setEmail("yash@lentra.ai");

		employeeServices.deleteEmployee(employee.getId());
		verify(employeeRepo, times(1)).deleteById(employee.getId());

	}
}