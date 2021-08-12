package com.ems.employeems.services;

import com.ems.employeems.model.Address;

import java.util.List;

public interface AddressServices {
    List<Address> findAddressByStreet(String street);
    List<Address> findAddressByCity(String city);
    void deleteById(Long id);
}
