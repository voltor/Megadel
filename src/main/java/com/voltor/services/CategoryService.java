package com.voltor.services;

import java.util.Collection;
import java.util.stream.Collectors;

import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.bean.Category;
import com.voltor.entity.CategoryEntity;
import com.voltor.repositories.CategoryRepository;

@Component
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	public Collection<Category> getAll() {
		return categoryRepository.findAll().stream().map(this::getCategory).collect(Collectors.toList());
	}

	public Category save(Category bean) {
		return getCategory(categoryRepository.save(getCategoryEntity(bean)));
	}

	public void delete(Category product) {
		categoryRepository.delete(getCategoryEntity(product));
	}

	public Category getCategory(CategoryEntity entity) {
		if (entity == null) {
			return null;
		}

		Category bean = new Category();
 
		bean.setId(entity.getId());
		bean.setName(entity.getName()); 
		bean.setCode(entity.getCode());
		return bean;
	}

	public CategoryEntity getCategoryEntity(Category bean) {
		if (bean == null) {
			return null;
		}
		CategoryEntity entity = null;
		if (bean.getId() == 0L) {
			entity = new CategoryEntity();
		} else {
			entity = categoryRepository.findOneById(bean.getId());
		}
		if (!Strings.isNullOrEmpty(bean.getName())) {
			entity.setName(bean.getName());
		} 
 
		entity.setCode(bean.getCode()); 

		entity.setId(bean.getId());

		return entity;
	} 

}
