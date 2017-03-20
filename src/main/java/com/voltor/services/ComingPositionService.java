package com.voltor.services;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.bean.Coming;
import com.voltor.bean.ComingPosition;
import com.voltor.bean.Product;
import com.voltor.entity.ComingEntity;
import com.voltor.entity.ComingPositionEntity;
import com.voltor.entity.ProductEntity;
import com.voltor.repositories.ComingPositionRepository;

@Component
public class ComingPositionService {

	@Autowired
	private ComingPositionRepository comingPositionRepository;
	@Autowired
	private ProductService productService;
	@Autowired
	private ComingService comingService;


	public ComingPosition getComingPosition(ComingPositionEntity entity) {
		if (entity == null) {
			return null;
		}
		ComingPosition bean = new ComingPosition();
		 
		bean.setCount(entity.getCount());
		bean.setPrice(entity.getPrice());
		bean.setProduct(  productService.getProduct( entity.getProductEntity()));
		bean.setSum( entity.getSum() );
		return bean;
	}

	public ComingPositionEntity getComingPositionEntity(ComingPosition position) {
		if (position == null) {
			return null;
		}
		ComingPositionEntity entity = null;
		if (position.getId() == 0L) {
			entity = new ComingPositionEntity();
		} else {
			entity = comingPositionRepository.findOneById(position.getId());
		}
		 
		entity.setCount(position.getCount());
		entity.setPrice(position.getPrice());
		entity.setProductEntity(productService.getProductEntity(position.getProduct()));
		entity.setSum( position.getSum() );
		entity.setComingEntity( comingService.getComingEntity(position.getComing()));
		return entity;
	}
	
	public void write( Coming coming ){
		for(ComingPosition position : coming.getCollection() ){
			position.setComing(coming);
			comingPositionRepository.save( getComingPositionEntity( position ) );
			position.getProduct().setComingPrice(position.getPrice());
			productService.addCountAndPrice( position.getProduct(), position.getCount() );
		}
	}
	
	public Double getLastPriceByProductId( Product product ){
		ProductEntity productEntity = productService.getProductEntity(product);
		ComingPositionEntity entity = comingPositionRepository.findTop1ByProductEntityOrderByIdDesc(productEntity);
		return entity == null ? null : entity.getPrice() ;
	}
	
	public Collection<ComingPosition> getByComingEntity( ComingEntity comingEntity ){
		return comingPositionRepository.findByComingEntityOrderByIdDesc(comingEntity).stream().map(this::getComingPosition).collect(Collectors.toList());
	}
}
