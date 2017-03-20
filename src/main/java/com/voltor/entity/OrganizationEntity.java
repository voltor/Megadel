package com.voltor.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "Organization")
@Table(name = "organization", schema = "public")
public class OrganizationEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "tick_id", nullable = false )
	private TickEntity tick;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TickEntity getTick() {
		return tick;
	}

	public void setTick(TickEntity tick) {
		this.tick = tick;
	}
}
