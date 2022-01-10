package com.example.springblog.repositories;

import com.example.springblog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findByTitle(String title);
    void deleteByTitle(String title);

}
