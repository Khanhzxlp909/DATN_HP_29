package com.example.hp_29_MiniatureCrafts.security.services;

import com.example.hp_29_MiniatureCrafts.entity.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsImpl.class);

    private final Long id;
    private final Object users;  // Lưu danh sách Employee hoặc Customer
    private final String username;
    private final String email;
    private final String accountRole;  // Vai trò tài khoản
    @JsonIgnore
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    // Constructor nhận danh sách employees hoặc customers
    public UserDetailsImpl(Long id, Object users, String username, String email, String password, String accountRole,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.users = users;
        this.username = username;
        this.email = email;
        this.password = password;
        this.accountRole = accountRole;
        this.authorities = authorities;
    }

    // Xây dựng UserDetailsImpl cho cả Employee và Customer
    public static UserDetailsImpl build(Account user, Object userList) {
        logger.info("Building UserDetailsImpl for user: {}", user.getUsername());
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().toString()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getUsersID(),
                userList,
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getAccountRole(),
                authorities);
    }

    public Long getId() {
        return id;
    }

    public String getAccountRole() {
        return accountRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Object getUsers() {
        return users;
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
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
