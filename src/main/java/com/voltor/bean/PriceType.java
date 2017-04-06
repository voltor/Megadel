package com.voltor.bean;

public enum PriceType {
	PRICE_1 ("Ціна1"),
	PRICE_OPT_1  ("Опт. ціна1"),
	PRICE_2  ("Ціна2"),
	PRICE_OPT_2  ("Опт. ціна2");
	
	private String name;

	//FIXME: Why did you removed 'private'? This enum only for use, not for create!!!
	PriceType(String name) {
		this.name = name;
	}
	
	@Override
	public String toString(){
		return this.name;
	}
}
