package com.example.DoAnTotNghiep_MiniatureCrafts.Repository.User;

import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Users;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {
    //    List<Users> findByUsername(String username, String password);

     @Query("SELECT u FROM Users u WHERE u.Username = :username AND u.Password = :password")
    List<Users> loginByUsername(@Param("username") String username, @Param("password") String password);


}
