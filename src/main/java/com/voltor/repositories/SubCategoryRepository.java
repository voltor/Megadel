package com.voltor.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.voltor.entity.CategoryEntity;
import com.voltor.entity.SubCategoryEntity;

public interface SubCategoryRepository extends JpaRepository<SubCategoryEntity, Integer> {
	SubCategoryEntity findOneById(Long id);
	List< SubCategoryEntity > findByCategoryEntity( CategoryEntity categoryEntity );
	
	@Query("SELECT count(sb)>0 FROM SubCategory sb WHERE sb.categoryEntity = :categoryEntity")
	boolean existCategory( @Param("categoryEntity") CategoryEntity categoryEntity );
}
