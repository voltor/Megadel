package com.voltor.services;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.bean.Seller;
import com.voltor.entity.SellerEntity;
import com.voltor.entity.TickEntity;
import com.voltor.repositories.SellerRepository;

@Component
public class SellerService {

	@Autowired
	private SellerRepository sellerRepository;
	@Autowired
	private TickService tickService;
	
	public Collection<Seller> getAll() {
		return sellerRepository.findAll().stream().map(this::getSeller).collect(Collectors.toList());
	}

	public Seller save(Seller bean) {
		return getSeller(sellerRepository.save(getSellerEntity(bean)));
	}

	public Seller getSeller(SellerEntity entity) {
		if (entity == null) {
			return null;
		}

		Seller bean = new Seller();
		bean.setId(entity.getId());
		bean.setEmail(entity.getEmail());
		bean.setPriceType(entity.getPriceType());
		bean.setPhone(entity.getPhone());
		bean.setCanGiveALoan(entity.isCanGiveALoan());
		bean.setName(entity.getName());
		bean.setTick(entity.getTick());

		return bean;
	}

	public SellerEntity getSellerEntity(Seller bean) {
		if (bean == null) {
			return null;
		}
		SellerEntity entity = null;
		if (bean.getId() == 0L) {
			entity = new SellerEntity();
		} else {
			entity = sellerRepository.findOneById(bean.getId());
		}
		TickEntity tickEntity = null;
		if (bean.getTick() == null || bean.getTick().getId() == 0L) {
			tickEntity = new TickEntity();
			tickEntity.setValueUA(0.0);
			tickEntity.setValueUSA(0.0);
			tickEntity = tickService.add(tickEntity);
		} else {
			tickEntity = tickService.findOneById(bean.getTick().getId());
			tickEntity.setValueUA(bean.getTick().getValueUA());
			tickEntity.setValueUSA(bean.getTick().getValueUSA());
		}

		entity.setEmail(bean.getEmail());
		entity.setPriceType(bean.getPriceType());
		entity.setCanGiveALoan(bean.isCanGiveALoan());
		entity.setName(bean.getName());
		entity.setPhone(bean.getPhone());
		entity.setTick(tickEntity);

		return entity;
	}

}
