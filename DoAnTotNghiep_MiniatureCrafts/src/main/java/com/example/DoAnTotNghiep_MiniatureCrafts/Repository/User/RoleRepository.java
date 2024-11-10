package com.example.DoAnTotNghiep_MiniatureCrafts.Repository.User;

import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.ERole;
import com.example.DoAnTotNghiep_MiniatureCrafts.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
