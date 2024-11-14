package com.example.DoAnTotNghiep_MiniatureCrafts.security.services;

import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Account;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Customer;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Employee;
import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.User.AccountRepository;
import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.User.CustomerRepository;
import com.example.DoAnTotNghiep_MiniatureCrafts.Repository.User.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsersDetailsServiceImpl implements UserDetailsService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EmployeeRepository employeeRepository; // Giả sử bạn có repository cho nhân viên

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account user = accountRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found with username: " + username);
        }

        // Kiểm tra xem user có phải là khách hàng hay nhân viên
        if (user.getRoles().equals("CUSTOMER")) {
            // Nếu là khách hàng, trả về CustomerDetailsImpl
            List<Customer> customers = customerRepository.findByUsers(user.getUsers());
            return CustomerDetailsImpl.build(user, customers);
        } else if (user.getRoles().equals("ADMIN") || user.getRoles().equals("USERS")) {
            // Nếu là nhân viên, trả về EmployeeDetailsImpl
            List<Employee> employees = employeeRepository.findEmployeeByUsers(user.getUsers());
            return EmployeeDetailsImpl.build(user, employees);
        } else {
            throw new UsernameNotFoundException("Invalid role for user: " + username);
        }
    }


//  @Override
//  @Transactional
//  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//    Account user = accountRepository.findByUsername(username);
//    System.out.println("ID :"+ user.getID());
//    System.out.println("ID_Customer :"+ user.getUsers());
//    System.out.println("Username :"+ user.getUsername());
//    System.out.println("Password :"+ user.getPassword());
//    if (user == null) {
//      throw new UsernameNotFoundException("User Not Found with username: " + username);
//    }
//
//    // Lấy danh sách nhân viên từ cơ sở dữ liệu
//    List<Customer> customers = customerRepository.findByUsers(user.getUsers());
//
//    for (Customer customer : customers) {
//      System.out.println(customer.getName());
//    }
//    // Trả về đối tượng EmployeeDetailsImpl với danh sách nhân viên
//    return CustomerDetailsImpl.build(user, customers);
//  }
}
