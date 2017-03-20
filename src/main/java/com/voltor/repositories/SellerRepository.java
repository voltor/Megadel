package com.voltor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.voltor.entity.SellerEntity;

public interface SellerRepository extends JpaRepository<SellerEntity, Integer> {
	SellerEntity findOneById(Long id);
}
