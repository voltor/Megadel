package com.voltor.bean;

public enum TickHistoryType {
	CREATED ("Створ..."),
	COMING  ("Прихід"),
	ADDED  ("Попов..."),
	EXCHAGE  ("Поміняв"),
	SELLING  ("Продаж"),
	ADDED_USA  ("Попов..."),
	ADDED_FOR_USER  ("Попов...");
	
	private String name;
	//FIXME: Why did you removed 'private'? This enum only for use, not for create!!!
	TickHistoryType(String name) {
		this.name = name;
	}
	
	@Override
	public String toString(){
		return this.name;
	}
}
