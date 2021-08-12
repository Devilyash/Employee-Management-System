package com.ems.employeems.services;

import com.ems.employeems.model.Address;
import com.ems.employeems.repository.AddressRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServicesImpl implements AddressServices {

    @Autowired
    private AddressRepo addressRepo;

    @Override
    public List<Address> findAddressByStreet(String street) {
        return addressRepo.findEmployeeByStreet(street);
    }

    public List<Address> findAddressByCity(String city) {
        return addressRepo.findAddressByCity(city);
    }

    @Override
    public void deleteById(Long id) {
        this.addressRepo.deleteById(id);
    }
}
