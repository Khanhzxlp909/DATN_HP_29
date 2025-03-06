package com.example.hp_29_MiniatureCrafts.repository.auth;

import com.example.hp_29_MiniatureCrafts.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE a.Username = :username AND a.Password = :password")
    Account loginByUsername(@Param("username") String username, @Param("password") String password);

    @Query("SELECT a FROM Account a WHERE a.Username = :username")
    Account findByUsername(@Param("username") String username);

    @Query("SELECT COUNT(a) > 0 FROM Account a WHERE a.Username = :username")
    boolean existsByUsername(@Param("username") String username);

    @Query("SELECT COUNT(a) > 0 FROM Account a WHERE a.Email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT a FROM Account a WHERE a.AccountRole = 'ADMIN'")
    List<Account> findByRolesAdmin();

}
