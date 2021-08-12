package com.ems.employeems.extras;

import com.ems.employeems.model.Department;
import com.ems.employeems.model.Employee;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EmployeeRowMapperImpl implements RowMapper<Employee> {

    @Override
    public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
        Employee employee = new Employee();
        employee.setId(resultSet.getLong(1));
        employee.setDesignation(resultSet.getString(2));
        employee.setEmail(resultSet.getString(3));
        employee.setEmployeeCode(resultSet.getLong(4));
        employee.setName(resultSet.getString(6));
        employee.setPhoneNumber(resultSet.getLong(7));
        return employee;
    }
}
