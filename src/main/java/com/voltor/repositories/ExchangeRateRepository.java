package com.voltor.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.voltor.entity.ExchangeRateEntity;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRateEntity, Integer> {
	ExchangeRateEntity findOneById(Long id);
	List<ExchangeRateEntity> findAllByOrderByDateTimeDesc();
	ExchangeRateEntity findTop1ByOrderByDateTimeDesc();
}
