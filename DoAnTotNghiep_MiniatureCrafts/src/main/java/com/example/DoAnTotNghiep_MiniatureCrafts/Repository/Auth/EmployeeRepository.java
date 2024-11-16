package com.example.DoAnTotNghiep_MiniatureCrafts.Repository.Auth;


import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(value = "Select e from Employee e where e.ID = :userID")
    List<Employee> findEmployeeByUsers(@Param("userID") Long usersID);

}