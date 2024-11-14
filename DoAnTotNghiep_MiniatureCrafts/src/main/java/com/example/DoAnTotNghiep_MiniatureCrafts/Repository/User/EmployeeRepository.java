package com.example.DoAnTotNghiep_MiniatureCrafts.Repository.User;


import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
