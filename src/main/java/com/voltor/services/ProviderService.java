package com.voltor.services;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.bean.Provider;
import com.voltor.entity.ProviderEntity;
import com.voltor.entity.TickEntity;
import com.voltor.repositories.ProviderRepository;
import com.voltor.repositories.TickRepository;

@Component
public class ProviderService {

	@Autowired
	private ProviderRepository providerRepository;
	@Autowired
	private TickRepository tickRepository;
	@Autowired
	private TickService tickService;
	
	public Collection<Provider> getAll() {
		return providerRepository.findAll().stream().map(this::getProvider).collect(Collectors.toList());
	}

	public Provider save(Provider bean) {
		return getProvider(providerRepository.save(getProviderEntity(bean)));
	}

	public Provider getProvider(ProviderEntity entity) {
		if (entity == null) {
			return null;
		}

		Provider bean = new Provider();
		bean.setId(entity.getId());
		bean.setEmail(entity.getEmail());
		bean.setPhone(entity.getPhone());
		bean.setFirmName(entity.getFirmName());
		bean.setName(entity.getName());
		bean.setTick(entity.getTick());

		return bean;
	}

	public ProviderEntity getProviderEntity(Provider bean) {
		if (bean == null) {
			return null;
		}
		ProviderEntity entity = null;
		if (bean.getId() == 0L) {
			entity = new ProviderEntity();
		} else {
			entity = providerRepository.findOneById(bean.getId());
		}
		TickEntity tickEntity = null;
		if (bean.getTick() == null || bean.getTick().getId() == 0L) {
			tickEntity = new TickEntity();
			tickEntity.setValueUA(0.0);
			tickEntity.setValueUSA(0.0);
			tickEntity = tickService.add(tickEntity);
		} else {
			tickEntity = tickRepository.findOneById(bean.getTick().getId());
			tickEntity.setValueUA(bean.getTick().getValueUA());
			tickEntity.setValueUSA(bean.getTick().getValueUSA());
		}

		entity.setEmail(bean.getEmail());
		entity.setFirmName(bean.getFirmName());
		entity.setName(bean.getName());
		entity.setPhone(bean.getPhone());
		entity.setTick(tickEntity);

		return entity;
	}

}
