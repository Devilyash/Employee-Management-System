package com.ems.employeems.repository;

import com.ems.employeems.model.Address;
import com.ems.employeems.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {

    @Query(value = "select * from address where street = ?1", nativeQuery = true)
    List<Address> findEmployeeByStreet(String street);

    @Query(value = "select * from address where city = ?1", nativeQuery = true)
    List<Address> findAddressByCity(String city);
}
