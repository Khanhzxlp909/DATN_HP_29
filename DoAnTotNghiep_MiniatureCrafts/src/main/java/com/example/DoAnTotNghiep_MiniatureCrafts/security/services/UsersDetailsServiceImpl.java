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
    EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account user = accountRepository.findByUsername(username);
        System.out.println("username: " + user.getUsername());
        System.out.println("password: " + user.getPassword());
        System.out.println("email: " + user.getEmail());
        System.out.println("Role: "+user.getAccountRole());
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found with username: " + username);
        }

        if (user.getAccountRole().equals("ADMIN")|| user.getAccountRole().equals("USERS")) {
            List<Employee> employees = employeeRepository.findEmployeeByUsers(user.getUsersID());

            return UserDetailsImpl.build(user, employees);
        } else if (user.getAccountRole().equals("CUSTOMER")) {
            List<Customer> customers = customerRepository.findByUsers(user.getUsersID());

            return UserDetailsImpl.build(user, customers);
        }

        // Kiểm tra vai trò và lấy thông tin người dùng tương ứng



        // Trả về UserDetailsImpl với cả Customer và Employee
         throw new UsernameNotFoundException("User Not Found with username: " + username);
    }
}
