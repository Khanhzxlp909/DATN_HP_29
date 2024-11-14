package com.example.DoAnTotNghiep_MiniatureCrafts.security.services;

import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Account;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Employee;
import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.User.AccountRepository;
import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.User.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeDetailsServiceImpl implements UserDetailsService {
  @Autowired
  AccountRepository accountRepository;

  @Autowired
  EmployeeRepository employeeRepository; // Giả sử bạn có repository cho nhân viên

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Account user = accountRepository.findByUsername(username);
    System.out.println("ID :"+ user.getID());
    System.out.println("ID_Employee :"+ user.getUsers());
    System.out.println("Username :"+ user.getUsername());
    System.out.println("Password :"+ user.getPassword());
    if (user == null) {
      throw new UsernameNotFoundException("User Not Found with username: " + username);
    }

    // Lấy danh sách nhân viên từ cơ sở dữ liệu
    List<Employee> employees = employeeRepository.findEmployeeByUsers(user.getUsers());

    // Trả về đối tượng EmployeeDetailsImpl với danh sách nhân viên
    return EmployeeDetailsImpl.build(user, employees);
  }
}
