package com.example.DoAnTotNghiep_MiniatureCrafts.Repository.User;

import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<Users, Long> {

    @Query("SELECT u FROM Users u WHERE u.Username = :username AND u.Password = :password")
    Users loginByUsername(@Param("username") String username, @Param("password") String password);

    @Query("SELECT u FROM Users u WHERE u.Username = :username")
    Users findByUsername(@Param("username") String username);

    @Query("SELECT COUNT(u) > 0 FROM Users u WHERE u.Username = :username")
    boolean existsByUsername(@Param("username") String username);

    @Query("SELECT COUNT(u) > 0 FROM Users u WHERE u.Email = :email")
    boolean existsByEmail(@Param("email") String email);
}
