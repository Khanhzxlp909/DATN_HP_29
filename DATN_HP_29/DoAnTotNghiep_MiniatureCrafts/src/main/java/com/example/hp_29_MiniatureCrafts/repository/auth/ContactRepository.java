package com.example.hp_29_MiniatureCrafts.repository.auth;

import com.example.hp_29_MiniatureCrafts.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

    @Query("SELECT c FROM Contact c order by c.Id desc")
    List<Contact> findAll();
}
