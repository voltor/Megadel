package com.voltor.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.voltor.bean.TransferCashStatus;

@Entity(name = "TransferCash")
@Table(name = "transfer_cash", schema = "public")
public class TransferCashEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn(name = "autor", nullable = false )
	private UserEntity autor;
	
	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn(name = "receiver")
	private UserEntity receiver;
	 
	@Column(name = "status")
	private TransferCashStatus status;
	
	@Column(name = "date")
	private Date date;
	
	@Column(name = "sum")
	private Double sum;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserEntity getAutor() {
		return autor;
	}

	public void setAutor(UserEntity autor) {
		this.autor = autor;
	}

	public UserEntity getReceiver() {
		return receiver;
	}

	public void setReceiver(UserEntity receiver) {
		this.receiver = receiver;
	}

	public TransferCashStatus getStatus() {
		return status;
	}

	public void setStatus(TransferCashStatus status) {
		this.status = status;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	} 
	public String getName() {
		return autor.getFirstName() + " " + autor.getLastName();
	}
	
	public TransferCashEntity getThis() {
		return this;
	}
}
