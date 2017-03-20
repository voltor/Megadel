package com.voltor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.voltor.entity.ProviderEntity;

public interface ProviderRepository extends JpaRepository<ProviderEntity, Integer> {
	ProviderEntity findOneById(Long id);
}
