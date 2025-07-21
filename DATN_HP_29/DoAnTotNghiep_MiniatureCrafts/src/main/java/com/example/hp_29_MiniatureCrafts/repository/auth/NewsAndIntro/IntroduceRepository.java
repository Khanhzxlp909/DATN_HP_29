package com.example.hp_29_MiniatureCrafts.repository.auth.NewsAndIntro;

import com.example.hp_29_MiniatureCrafts.entity.introduce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntroduceRepository extends JpaRepository<introduce, Integer> {
}
