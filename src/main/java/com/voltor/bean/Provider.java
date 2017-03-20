package com.voltor.bean;

import com.voltor.entity.TickEntity;

public class Provider {
	
	private long id;
	private String name;
	private String firmName;
	private String phone;
	private String email;
	private TickEntity tick;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public TickEntity getTick() {
		return tick;
	}

	public void setTick(TickEntity tick) {
		this.tick = tick;
	}
	
	public Double getTickUA() {
		return this.tick.getValueUA();
	}
	
	public Double getTickUSA() {
		return this.tick.getValueUSA();
	}


}
