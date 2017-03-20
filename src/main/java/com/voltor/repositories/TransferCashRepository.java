package com.voltor.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.voltor.bean.TransferCashStatus;
import com.voltor.entity.TransferCashEntity;

public interface TransferCashRepository extends JpaRepository<TransferCashEntity, Integer> {
	List<TransferCashEntity> findByStatusOrderByDateDesc( TransferCashStatus status );
}
