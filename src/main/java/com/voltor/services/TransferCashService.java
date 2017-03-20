package com.voltor.services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.bean.TransferCashStatus;
import com.voltor.entity.OrganizationEntity;
import com.voltor.entity.TransferCashEntity;
import com.voltor.repositories.TransferCashRepository;

@Component
public class TransferCashService {

	@Autowired
	private TransferCashRepository transferCashRepository;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private UserService userService; 
	@Autowired
	private TickService tickService;


	public TransferCashEntity create(){
		TransferCashEntity cashEntity = new TransferCashEntity();
		cashEntity.setStatus( TransferCashStatus.CRETED );
		cashEntity.setDate(new Date());
		cashEntity.setAutor(userService.getUserEntityByAuthName(SecurityService.getCurrentUser().getAuthName()));
		return transferCashRepository.save(cashEntity);
	}
	
	@Transactional
	public void cofirm(TransferCashEntity cashEntity){
		OrganizationEntity organizationEntity = organizationService.getOrganization();
		cashEntity.setStatus( TransferCashStatus.CONFIRMED );
		cashEntity.setReceiver(userService.getUserEntityByAuthName(SecurityService.getCurrentUser().getAuthName()));
		transferCashRepository.save(cashEntity);
		tickService.addUA(organizationEntity.getTick(), cashEntity.getSum());
	}

	public Collection<TransferCashEntity> getForManagerCashes() {
		return transferCashRepository.findByStatusOrderByDateDesc( TransferCashStatus.CRETED );
	}
	
	 
}
