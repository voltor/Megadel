package com.voltor.services;

import java.util.Collection;
import java.util.stream.Collectors;

import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.bean.Product;
import com.voltor.entity.ProductEntity;
import com.voltor.repositories.ProductRepository;

@Component
public class ProductService {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private SubCategoryService subCategoryService;

	public Collection<Product> getAll() {
		return productRepository.findAll().stream().map(this::getProduct).collect(Collectors.toList());
	}
	
	public Collection<Product> getAllForSelling() {
		return productRepository.getForSelling().stream().map(this::getProduct).collect(Collectors.toList());
	}

	public Product save(Product product) {
		return getProduct(productRepository.save(getProductEntity(product)));
	}

	public void delete(Product product) {
		productRepository.delete(getProductEntity(product));
	}

	public Product getProduct(ProductEntity entity) {
		if (entity == null) {
			return null;
		}

		Product product = new Product();

		product.setCount(entity.getCount());
		product.setId(entity.getId());
		product.setName(entity.getName());
		product.setPrice(entity.getPrice());
		product.setPrice1(entity.getPrice1());
		product.setShtrihCode(entity.getShtrihCode());
		product.setSubCategory(subCategoryService.getSubCategory(entity.getSubCategory()));
		product.setCode(entity.getCode());
		product.setPriceOpt(entity.getPriceOpt());
		product.setPriceOpt1(entity.getPriceOpt1());
		product.setComingPrice( entity.getComingPrice() );
		return product;
	}
	
	public ProductEntity getProductEntity(Product bean) {
		if (bean == null) {
			return null;
		}
		ProductEntity entity = null;
		if (bean.getId() == 0L) {
			entity = new ProductEntity();
		} else {
			entity = productRepository.findOneById( bean.getId() );
		}
		entity.setName(bean.getName());
		entity.setSubCategory(subCategoryService.getSubCategoryEntity(bean.getSubCategory()));
		entity.setCount( bean.getCount() == null ? 0 : bean.getCount());
		entity.setPrice(bean.getPrice());
		entity.setPriceOpt(bean.getPriceOpt());
		entity.setId(bean.getId());
		entity.setPrice1(bean.getPrice1());
		entity.setPriceOpt1(bean.getPriceOpt1());
		entity.setComingPrice( bean.getComingPrice() );
		if( Strings.isNullOrEmpty(bean.getCode())){
			entity.setCode(null);
		} else {
			entity.setCode(bean.getCode());
		}
		if( Strings.isNullOrEmpty(bean.getShtrihCode())){
			entity.setShtrihCode(null);
		} else {
			entity.setShtrihCode(bean.getShtrihCode());
		}
		return entity;
	}
	
	public Product getById(long id) {
		return getProduct( productRepository.findOneById( id ) );
	}
	
	public boolean existName( String code, Long id ){
		return productRepository.existName(code, id);
	}
	
	public boolean existCode( String code, Long id ){
		return productRepository.existCode(code, id);
	}
	
	public boolean existShtrihCode( String shtrihCode, Long id ){
		return productRepository.existShtrihCode(shtrihCode, id);
	}
	
	public void addCountAndPrice( Product product, Integer count ){
		if (product == null || product.getId() == 0L) {
			return;
		}
		productRepository.addCount( count, Long.valueOf( product.getId() ) );
		productRepository.setPrice( product.getComingPrice(), Long.valueOf( product.getId() ) );
		
	}
	
	public void addCount( Product product, Integer count ){
		if (product == null || product.getId() == 0L) {
			return;
		}
		productRepository.addCount( count, Long.valueOf( product.getId() ) );
		
	}

	public Integer getCount( Product product ){
		if (product == null || product.getId() == 0L) {
			return 0;
		}
		return productRepository.getCount( Long.valueOf( product.getId() ) );
	}
}
