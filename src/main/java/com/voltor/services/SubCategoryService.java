package com.voltor.services;

import java.util.Collection;
import java.util.stream.Collectors;

import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.bean.Category;
import com.voltor.bean.SubCategory;
import com.voltor.entity.SubCategoryEntity;
import com.voltor.repositories.SubCategoryRepository;

@Component
public class SubCategoryService {
	@Autowired
	private SubCategoryRepository subCategoryRepository;
	@Autowired
	private CategoryService categoryService;

	public Collection<SubCategory> getAll() {
		return subCategoryRepository.findAll().stream().map(this::getSubCategory).collect(Collectors.toList());
	}
	
	public Collection<SubCategory> getAllByCategory( Category category ) {
		return subCategoryRepository.findByCategoryEntity( 
				categoryService.getCategoryEntity(category) 
				).stream().map(this::getSubCategory).collect(Collectors.toList());
	}

	public SubCategory save(SubCategory bean) {
		return getSubCategory(subCategoryRepository.save(getSubCategoryEntity(bean)));
	}

	public void delete(SubCategory product) {
		subCategoryRepository.delete(getSubCategoryEntity(product));
	}

	public SubCategory getSubCategory(SubCategoryEntity entity) {
		if (entity == null) {
			return null;
		}

		SubCategory bean = new SubCategory();
 
		bean.setId(entity.getId());
		bean.setName(entity.getName()); 
		bean.setCode(entity.getCode());
		bean.setCategory( categoryService.getCategory( entity.getCategoryEntity() ) );
		return bean;
	}

	public SubCategoryEntity getSubCategoryEntity(SubCategory bean) {
		if (bean == null) {
			return null;
		}
		SubCategoryEntity entity = null;
		if (bean.getId() == 0L) {
			entity = new SubCategoryEntity();
		} else {
			entity = subCategoryRepository.findOneById(bean.getId());
		}
		if (!Strings.isNullOrEmpty(bean.getName())) {
			entity.setName(bean.getName());
		} 
 
		entity.setCode(bean.getCode()); 
		entity.setCategoryEntity( categoryService.getCategoryEntity(bean.getCategory()));
		entity.setId(bean.getId());

		return entity;
	}

	public boolean existCategory(Category bean) { 
		return subCategoryRepository.existCategory( categoryService.getCategoryEntity( bean ));
	}

}
