package com.ems.employeems.extras;

import com.ems.employeems.model.Department;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentRowMapperImpl implements RowMapper<Department> {
    @Override
    public Department mapRow(ResultSet resultSet, int i) throws SQLException {

        Department department = new Department();
        department.setId(resultSet.getLong(1));
        department.setName(resultSet.getString(2));
        department.setPhoneNumber(resultSet.getLong(3));
        department.setEmail(resultSet.getString(4));
        return department;
    }
}
