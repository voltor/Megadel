package com.voltor.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.entity.OrganizationEntity;
import com.voltor.entity.TickEntity;
import com.voltor.repositories.TickRepository;

@Component
public class TickService {

	@Autowired
	private TickRepository tickRepository;
	@Autowired
	private TickHistoryService historyService;
	@Autowired
	private OrganizationService organizationService;

	public TickEntity save(TickEntity tick) {
		return tickRepository.save(getTickEntity(tick));
	}
	
	public TickEntity add(TickEntity tick) {
		TickEntity entity = tickRepository.save( getTickEntity(tick));
		historyService.create( entity );
		return entity;
	}

	public TickEntity getTickEntity(TickEntity tick) {
		if (tick == null) {
			return new TickEntity();
		}
		TickEntity entity = null;
		if (tick.getId() == 0L) {
			entity = new TickEntity();
		} else {
			entity = tickRepository.findOneById(tick.getId());
		}
		
		entity.setValueUA( tick.getValueUA() );
		entity.setValueUSA( tick.getValueUSA() );		
		return entity;
	}
	
	@Transactional
	public void addUSA(TickEntity tick, Double sum) {
		tickRepository.addValueUSA(sum, tick.getId());
		historyService.addUSAValue( tick, sum);
	}

	@Transactional
	public void addUA(TickEntity tick, Double sum) {
		tickRepository.addValueUA(sum, tick.getId());
		historyService.addValue( tick, sum);
	}
	
	@Transactional
	public void addUAForSeller(TickEntity tick, Double sum) {
		tickRepository.addValueUA(sum, tick.getId());
		historyService.addValueForSeller( tick, sum);
	}
	
	@Transactional
	public void coming( Double sum ) {
		OrganizationEntity organizationEntity = organizationService.getOrganization(); 
		historyService.toComing( organizationEntity.getTick(), sum);
		tickRepository.addValueUSA( -sum, organizationEntity.getTick().getId());
	}

	@Transactional
	public void change( Double sumFrom, Double sumTo ) {
		OrganizationEntity organizationEntity = organizationService.getOrganization(); 
		historyService.toChange( organizationEntity.getTick(), sumFrom);
		tickRepository.addValueUA( -sumFrom, organizationEntity.getTick().getId());
		addUSA(organizationEntity.getTick(), sumTo);
	}

	public TickEntity findOneById(long id) { 
		return tickRepository.findOneById( id );
	}
}
