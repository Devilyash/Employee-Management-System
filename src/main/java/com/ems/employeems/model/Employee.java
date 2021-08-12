package com.ems.employeems.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import jdk.nashorn.internal.codegen.ObjectClassGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

//This class will be the table in the Database.
@Entity
@Table(name = "employees") //uniqueConstraints = { @UniqueConstraint(columnNames = {"employee_code"})}
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //Provides generation strategy for the value of primary key.
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "designation", nullable = false)
    private String designation;

    @Column(name = "phone_number")
    private Long phoneNumber;

    @Column(name = "employee_code", nullable = false) //unique = true;
    private Long employeeCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "department")
    private String department;

    @OneToOne(cascade = CascadeType.ALL) //OneToOne Unidirectional Mapping.
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Employee() {
    }

    public Employee(String name, String email, String designation, Long phoneNumber, Long employeeCode, Gender gender, String department, Address address) {
        this.name = name;
        this.email = email;
        this.designation = designation;
        this.phoneNumber = phoneNumber;
        this.employeeCode = employeeCode;
        this.gender = gender;
        this.department = department;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(Long employeeCode) {
        this.employeeCode = employeeCode;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }


    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", designation='" + designation + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", employeeCode=" + employeeCode +
                ", gender=" + gender +
                ", department='" + department + '\'' +
                ", address=" + address +
                '}';
    }
}
