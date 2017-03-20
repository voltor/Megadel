package com.voltor.services;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.bean.Selling;
import com.voltor.bean.SellingPosition;
import com.voltor.entity.SellingEntity;
import com.voltor.entity.SellingPositionEntity;
import com.voltor.repositories.SellingPositionRepository;

@Component
public class SellingPositionService {

	@Autowired
	private SellingPositionRepository sellingPositionRepository;
	@Autowired
	private ProductService productService;
	@Autowired
	private ExchangeRateService exchangeRateService;
	@Autowired
	private SellingService sellingService;


	public SellingPosition getSellingPosition(SellingPositionEntity entity) {
		if (entity == null) {
			return null;
		}
		SellingPosition bean = new SellingPosition();
		bean.setCount(entity.getCount());
		bean.setPrice(entity.getPrice());
		bean.setProduct(productService.getProduct(entity.getProductEntity()));
		bean.setSum( entity.getSum() );
		bean.setExchangeRate(exchangeRateService.getExchangeRate(entity.getExchangeRateEntity()));
		return bean;
	}

	public SellingPositionEntity getSellingPositionEntity(SellingPosition position) {
		if (position == null) {
			return null;
		}
		SellingPositionEntity entity = null;
		if (position.getId() == 0L) {
			entity = new SellingPositionEntity();
		} else {
			entity = sellingPositionRepository.findOneById(position.getId());
		}
		 
		entity.setCount(position.getCount());
		entity.setPrice(position.getPrice());
		entity.setProductEntity(productService.getProductEntity(position.getProduct()));
		entity.setSum( position.getSum() );
		entity.setSellingEntity( sellingService.getSellingEntity(position.getSelling()));
		entity.setExchangeRateEntity(exchangeRateService.getExchangeRateEntity(position.getExchangeRate()));
		return entity;
	}
	
	public void write( Selling Selling ){
		for(SellingPosition position : Selling.getCollection() ){
			position.setSelling(Selling);
			sellingPositionRepository.save(getSellingPositionEntity(position));
			productService.addCount(position.getProduct(), (-1)*position.getCount());
		}
	}
	
	public Collection<SellingPosition> getBySellingEntity( SellingEntity sellingEntity ){
		return sellingPositionRepository.findBySellingEntityOrderByIdDesc(sellingEntity).stream().map(this::getSellingPosition).collect(Collectors.toList());
	}
}
