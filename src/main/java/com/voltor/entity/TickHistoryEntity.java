package com.voltor.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.voltor.bean.TickHistoryType;


@Entity(name = "TickHistory")
@Table(name = "tick_history", schema = "public")
public class TickHistoryEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "date")
	private Date date;
	
	@Column(name = "value")
	private Double value;
	
	@Column(name = "type")
	private TickHistoryType type;
	
	@ManyToOne
	@JoinColumn(name = "tick_id", nullable = false )
	private TickEntity tickEntity;

	@ManyToOne 
	@JoinColumn(name = "user_id", nullable = false )
	private UserEntity userEntity;
	
	@ManyToOne
	@JoinColumn(name = "transfer_cash_id")
	private TransferCashEntity transferCashEntity;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public TickHistoryType getType() {
		return type;
	}

	public void setType(TickHistoryType type) {
		this.type = type;
	}

	public TickEntity getTickEntity() {
		return tickEntity;
	}

	public void setTickEntity(TickEntity tickEntity) {
		this.tickEntity = tickEntity;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public String getUserName() {
		return userEntity.getAuthName();
	}
	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public TransferCashEntity getTransferCashEntity() {
		return transferCashEntity;
	}

	public void setTransferCashEntity(TransferCashEntity transferCashEntity) {
		this.transferCashEntity = transferCashEntity;
	}
	
}
