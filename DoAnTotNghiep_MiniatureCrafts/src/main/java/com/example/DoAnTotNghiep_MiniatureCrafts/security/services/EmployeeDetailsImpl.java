package com.example.DoAnTotNghiep_MiniatureCrafts.security.services;

import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Account;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Employee; // Thêm import nếu cần
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class EmployeeDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private List<Employee> employees; // Lưu trữ danh sách Employee

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    // Constructor mới nhận vào danh sách employees
    public EmployeeDetailsImpl(List<Employee> employees, String username, String email, String password,
                               Collection<? extends GrantedAuthority> authorities) {
        this.employees = employees;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    // Phương thức build đã sửa lại để trả về EmployeeDetailsImpl với danh sách employees
    public static EmployeeDetailsImpl build(Account user, List<Employee> employees) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().toString()))
                .collect(Collectors.toList());

        return new EmployeeDetailsImpl(
                employees, // Truyền vào danh sách employees
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public List<Employee> getEmployees() {  // Trả về danh sách employees
        return employees;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        EmployeeDetailsImpl user = (EmployeeDetailsImpl) o;
        return Objects.equals(employees, user.employees);  // So sánh danh sách employees
    }
}
