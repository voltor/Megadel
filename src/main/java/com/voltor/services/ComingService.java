package com.voltor.services;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.bean.Coming;
import com.voltor.bean.Provider;
import com.voltor.entity.ComingEntity;
import com.voltor.entity.ProviderEntity;
import com.voltor.repositories.ComingRepository;

@Component
public class ComingService {
	@Autowired
	private ComingRepository comingRepository;
	@Autowired
	private ProviderService providerService;
	@Autowired
	private ComingPositionService  comingPositionService;
	@Autowired
	private UserService userService;
	@Autowired
	private TickHistoryService tickHistoryService;
	
	@Transactional
	public void save( Coming coming ) {
		coming.setUser( SecurityService.getCurrentUser() );
		coming.setDate( new Date() );
		coming.getProvider().getTick().minusUSA( coming.getSum());
		tickHistoryService.toComing( coming.getProvider().getTick(), coming.getSum());
		coming.setId( comingRepository.save(getComingEntity(coming)).getId());
		comingPositionService.write(coming);
	}
	
	public Coming getComing( ComingEntity entity ){
		
		if (entity == null) {
			return null;
		}
		Coming bean = new Coming();
		bean.getCollection().addAll( comingPositionService.getByComingEntity(entity));
		bean.setUser( userService.getUser( entity.getUserEntity() ) );
		bean.setDate( entity.getDate() );
		bean.setProvider( providerService.getProvider( entity.getProviderEntity() ) );
		bean.setId( entity.getId() );
		bean.setSum( entity.getSum() );
		
		return bean;
	}
	
	public ComingEntity getComingEntity( Coming coming ){
		
		if (coming == null) {
			return null;
		}
		ComingEntity entity = null;
		if (coming.getId() == 0L) {
			entity = new ComingEntity();
		} else {
			entity = comingRepository.findOneById(coming.getId());
		}
		entity.setUserEntity( userService.getUserEntityByAuthName( coming.getUser().getAuthName() ) );
		entity.setDate( coming.getDate() );
		entity.setProviderEntity( providerService.getProviderEntity( coming.getProvider() ) );
		entity.setSum( coming.getSum() );
		return entity;
	}
	
	public Collection<Coming> getByProvider( Provider provider ) {
		ProviderEntity providerEntity = providerService.getProviderEntity(provider);
		return comingRepository.findByProviderEntityOrderByDateDesc(providerEntity).stream().map(this::getComing).collect(Collectors.toList());
	}
}
