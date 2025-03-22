package com.example.testfoodapp.repository;

import com.example.testfoodapp.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface FoodRepository extends JpaRepository<Food, UUID> {

    List<Food> getAllByCreatedAtAndUserId(LocalDate date, UUID userId);
}
