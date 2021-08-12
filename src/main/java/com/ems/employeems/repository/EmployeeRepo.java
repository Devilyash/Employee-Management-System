package com.ems.employeems.repository;

import com.ems.employeems.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository //This interface is completely dedicated for performing database operations.
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    //Native query refers to actual sql queries (referring to actual database objects). These queries are the sql statements which can be directly executed in database using a database client.
    @Query(value = "select * from employees where name = :name", nativeQuery = true)
    List<Employee> findByName(@Param("name") String name);

    @Query(value = "select * from employees where employee_code = :code", nativeQuery = true)
    List<Employee> findByCode(@Param("code") Long code);

    @Query(value = "select * from employees where employee_code = ?1 AND NOT name = ?2", nativeQuery = true)
    List<Employee> findByNameAndCode(Long code, String name);

    @Modifying
    @Query(value = "delete from employees where employee_code = ?1", nativeQuery = true)
    void deleteEmployeeByCode(Long code);

    @Query(value = "select * from employees where name like %:keyword%", nativeQuery = true)
    List<Employee> findByKeyword(@Param("keyword") String keyword);

    @Query(value = "select * from employees where address_id in (select address.id from address where address.city = ?1)", nativeQuery = true)
    List<Employee> findEmployeeByCity(String city);

}
