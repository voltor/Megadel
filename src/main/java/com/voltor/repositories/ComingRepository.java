package com.voltor.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.voltor.entity.ComingEntity;
import com.voltor.entity.ProviderEntity;

public interface ComingRepository extends JpaRepository<ComingEntity, Integer> {
	ComingEntity findOneById(Long id);
	
	List<ComingEntity> findByProviderEntityOrderByDateDesc(ProviderEntity providerEntity);
}
