package com.voltor.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.voltor.entity.ComingEntity;
import com.voltor.entity.ComingPositionEntity;
import com.voltor.entity.ProductEntity;

public interface ComingPositionRepository extends JpaRepository<ComingPositionEntity, Integer> {
	ComingPositionEntity findOneById(Long id);
	
	ComingPositionEntity findTop1ByProductEntityOrderByIdDesc(ProductEntity productEntity);
	
	List<ComingPositionEntity> findByComingEntityOrderByIdDesc(ComingEntity comingEntity);
}