package com.ems.employeems.repository;

import com.ems.employeems.model.Department;
import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Long> {

    @Query(value = "select * from departments where name = ?1", nativeQuery = true)
    List<Department> findByName(String name);

    @Query(value = "select * from departments where name like %:keyword%", nativeQuery = true)
    List<Department> findByKeyword(@Param("keyword") String keyword);
}
