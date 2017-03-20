package com.voltor.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.voltor.entity.SellingEntity;
import com.voltor.entity.SellingPositionEntity;

public interface SellingPositionRepository extends JpaRepository<SellingPositionEntity, Integer> {
	SellingPositionEntity findOneById(Long id);
	Collection<SellingPositionEntity> findBySellingEntityOrderByIdDesc(SellingEntity sellingEntity);
}
