package com.voltor.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.voltor.bean.SellingType;
import com.voltor.entity.SellerEntity;
import com.voltor.entity.SellingEntity;
import com.voltor.entity.TransferCashEntity;
import com.voltor.entity.UserEntity;

public interface SellingRepository extends JpaRepository<SellingEntity, Integer> {
	SellingEntity findOneById(Long id);
	List<SellingEntity> findBySellerEntityOrderByDateDesc(SellerEntity sellerEntity);
	List<SellingEntity> findByUserEntityAndTransferCashEntityAndTypeOrderByDateDesc(UserEntity userEntity, TransferCashEntity transferCashEntity, SellingType type);
	List<SellingEntity> findByTransferCashEntityOrderByDateDesc( TransferCashEntity transferCashEntity);
}
