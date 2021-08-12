package com.ems.employeems.controller;

import com.ems.employeems.model.Address;
import com.ems.employeems.model.Employee;
import com.ems.employeems.repository.AddressRepo;
import com.ems.employeems.services.AddressServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/address")
public class AddressCont {

    @Autowired
    private AddressServices addressServices;

    @Autowired
    private AddressRepo addressRepo;

    @GetMapping("/all")
    public List<Address> getAll() {
        return addressRepo.findAll();
    }

    @DeleteMapping("/deleteAll")
    public void deleteAll() {
        this.addressRepo.deleteAll();
    }

//    @RequestMapping(value = "/find", method = RequestMethod.GET)
//    @ResponseBody
//    public List<String> findEmployeeByCity(@RequestParam(value = "city") String city) {
//        List<Address> addresses = addressServices.findAddressByCity(city);
//        List<String> employees = new ArrayList<>();
//        for (Address address: addresses) {
//            employees.add(address.getEmpName());
//        }
//        return employees;
//    }

    @DeleteMapping("/deleteAddress/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable("id") Long id) {
        addressServices.deleteById(id); //Since we are returning anything
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
