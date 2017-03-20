package com.voltor.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.bean.TickHistoryType;
import com.voltor.entity.TickEntity;
import com.voltor.entity.TickHistoryEntity;
import com.voltor.entity.TransferCashEntity;
import com.voltor.repositories.TickHistoryRepository;

@Component
public class TickHistoryService {

	@Autowired
	private TickHistoryRepository tickRepository;
	@Autowired
	private UserService userService;
	
	public Collection<TickHistoryEntity> getByTick( TickEntity entity) {
		return tickRepository.findAllByTickEntityOrderByDateDesc(entity);
	}
	public Collection<TickHistoryEntity> getForUserCash() {
		return tickRepository.findForUserCash( userService.getUserEntityByAuthName( SecurityService.getCurrentUser().getAuthName() ), TickHistoryType.ADDED_FOR_USER );
	}
	public Collection<TickHistoryEntity> getByTickForUSA( TickEntity entity) {
		Collection<TickHistoryEntity> result = new ArrayList<>();
		for(TickHistoryEntity historyEntity : tickRepository.findAllByTickEntityOrderByDateDesc(entity)){
			if( isValidUSA(historyEntity) ){
				result.add(historyEntity);
			}
		}
		return result;
	}
	
	public Collection<TickHistoryEntity> getByTickForUA( TickEntity entity) {
		Collection<TickHistoryEntity> result = new ArrayList<>();
		for(TickHistoryEntity historyEntity : tickRepository.findAllByTickEntityOrderByDateDesc(entity)){
			if( !isValidUSA(historyEntity) ){
				result.add(historyEntity);
			}
		}
		return result;
	}
	
	@SuppressWarnings("incomplete-switch")
	private boolean isValidUSA( TickHistoryEntity entity) {
			switch (entity.getType()) {
			case ADDED_USA:
				return true; 
			case COMING:
				return true; 
			}
		return false;
	}

	public void addValue(TickEntity tick, Double sum) {
		TickHistoryEntity entity = getEntityFoAction(tick);
		entity.setValue(sum);
		entity.setType( TickHistoryType.ADDED );
		tickRepository.save(entity);
	}
	
	public void addValueForSeller(TickEntity tick, Double sum) {
		TickHistoryEntity entity = getEntityFoAction(tick);
		entity.setValue(sum);
		entity.setType( TickHistoryType.ADDED_FOR_USER );
		tickRepository.save(entity);
	}
	public void addUSAValue(TickEntity tick, Double sum) {
		TickHistoryEntity entity = getEntityFoAction(tick);
		entity.setValue(sum);
		entity.setType( TickHistoryType.ADDED_USA );
		tickRepository.save(entity);
	}

	public void create(TickEntity tick) {
		TickHistoryEntity entity = getEntityFoAction(tick);
		entity.setValue( 0.0 );
		entity.setType( TickHistoryType.CREATED );
		tickRepository.save(entity);
	}
	
	public void toComing(TickEntity tick, Double sum) {
		TickHistoryEntity entity = getEntityFoAction(tick);
		entity.setValue( -sum );
		entity.setType( TickHistoryType.COMING );
		tickRepository.save(entity);	
	}
	public void toSelling(TickEntity tick, Double sum) {
		TickHistoryEntity entity = getEntityFoAction(tick);
		entity.setValue( sum );
		entity.setType( TickHistoryType.SELLING );
		tickRepository.save(entity);	
	}
	
	public void toChange(TickEntity tick, Double sum) {
		TickHistoryEntity entity = getEntityFoAction(tick);
		entity.setValue( -sum );
		entity.setType( TickHistoryType.EXCHAGE );
		tickRepository.save(entity);	
	}
	
	private TickHistoryEntity getEntityFoAction( TickEntity tick ){
		TickHistoryEntity entity = new TickHistoryEntity();
		entity.setTickEntity(tick);
		entity.setDate( new Date() );
		entity.setUserEntity( userService.getUserEntityByAuthName( SecurityService.getCurrentUser().getAuthName() ) );
		return entity;
	}

	public void cofirmTickHistory(TickHistoryEntity entity, TransferCashEntity transferCash) {
		entity.setTransferCashEntity(transferCash);
		tickRepository.save(entity);
	}
}
