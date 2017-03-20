package com.voltor.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.bean.Seller;
import com.voltor.bean.Selling;
import com.voltor.bean.SellingType;
import com.voltor.entity.SellerEntity;
import com.voltor.entity.SellingEntity;
import com.voltor.entity.TickHistoryEntity;
import com.voltor.entity.TransferCashEntity;
import com.voltor.repositories.SellingRepository;

@Component
public class SellingService {
	@Autowired
	private SellingRepository sellingRepository;
	@Autowired
	private SellerService sellerService;
	@Autowired
	private SellingPositionService  sellingPositionService;
	@Autowired
	private UserService userService;
	@Autowired
	private TickHistoryService tickHistoryService;
	@Autowired
	private TransferCashService transferCashService;
	
	@Transactional
	public void save( Selling selling ) {
		selling.setUser( SecurityService.getCurrentUser() );
		selling.setDate( new Date() );
		selling.getSeller().getTick().plusUA(selling.getForSeller());
		tickHistoryService.toSelling( selling.getSeller().getTick(), selling.getForSeller());
		selling.setId( sellingRepository.save(getSellingEntity(selling)).getId());
		sellingPositionService.write(selling);
	}
	
	public Selling getSelling( SellingEntity entity ){
		
		if (entity == null) {
			return null;
		}
		Selling bean = new Selling();
	
		bean.getCollection().addAll( sellingPositionService.getBySellingEntity(entity));
		
		bean.setUser( userService.getUser( entity.getUserEntity() ) );
		bean.setDate( entity.getDate() );
		bean.setSeller( sellerService.getSeller( entity.getSellerEntity() ) );
		bean.setSum( entity.getSum() );
		bean.setId( entity.getId() );
		bean.setType( entity.getType() );
		return bean;
	}
	
	public SellingEntity getSellingEntity( Selling selling ){
		
		if (selling == null) {
			return null;
		}
		SellingEntity entity = null;
		if (selling.getId() == 0L) {
			entity = new SellingEntity();
		} else {
			entity = sellingRepository.findOneById(selling.getId());
		}
		entity.setUserEntity( userService.getUserEntityByAuthName( selling.getUser().getAuthName() ) );
		entity.setDate( selling.getDate() );
		entity.setSellerEntity( sellerService.getSellerEntity( selling.getSeller() ) );
		entity.setSum( selling.getSum() );
		entity.setType( selling.getType() );
		return entity;
	}

	public Collection<Selling> getBySeller(Seller seller) {
		SellerEntity sellerEntity = sellerService.getSellerEntity( seller );
		return sellingRepository.findBySellerEntityOrderByDateDesc(sellerEntity).stream().map(this::getSelling).collect(Collectors.toList());
	}
	
	public Collection<Selling> getForUserCash() {
		return sellingRepository.findByUserEntityAndTransferCashEntityAndTypeOrderByDateDesc(
				userService.getUserEntityByAuthName(SecurityService.getCurrentUser().getAuthName() ), 
				null,
				SellingType.NORMAL)
				.stream().map(this::getSelling).collect(Collectors.toList());
	}
	
	public Collection<Selling> getForManagerCash() {
		Collection<Selling> all = new ArrayList<>();
		for(TransferCashEntity cashEntity : transferCashService.getForManagerCashes()){
			all.addAll(getForManagerCash(cashEntity));
		} 
		return all;
	}
	
	public Collection<Selling> getForManagerCash( TransferCashEntity transferCashEntity ) {
		return sellingRepository.findByTransferCashEntityOrderByDateDesc(transferCashEntity).stream().map(this::getSelling).collect(Collectors.toList());
	}

	@Transactional
	public void cofirmSellings(Collection<Selling> sellings, Collection<TickHistoryEntity> tickHistories, Double sum) {
		TransferCashEntity transferCash = transferCashService.create();
		transferCash.setSum(sum);
		sellings.forEach(item -> this.cofirmSelling(item,transferCash));
		tickHistories.forEach(item -> tickHistoryService.cofirmTickHistory(item,transferCash));
	}
	
	private void cofirmSelling(Selling selling, TransferCashEntity transferCash) {
		SellingEntity entity = sellingRepository.findOneById(selling.getId());
		entity.setTransferCashEntity(transferCash);
		sellingRepository.save(entity);
	}
}
