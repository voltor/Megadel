package com.voltor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.voltor.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
	CategoryEntity findOneById(Long id);
}
