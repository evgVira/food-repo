package com.example.testfoodapp.repository;

import com.example.testfoodapp.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {

    boolean existsByEmail(String email);

    boolean existsById(UUID id);

    @Query(value = "select daily_calories from user_sc.user_t as u where u.id = :userId", nativeQuery = true)
    String getUserDailyCaloriesNorm(@Param("userId") UUID userId);

    @Query(value = "select name from user_sc.user_t as u where u.id = :userId", nativeQuery = true)
    String getUserNameByUserId(@Param("userId") UUID userId);

}
