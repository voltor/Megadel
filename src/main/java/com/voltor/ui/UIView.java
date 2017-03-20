package com.voltor.ui;

public enum UIView {
	USERS("Користувачі", "/fxml/tabs/Users.fxml"),
	SELLING("Продаж", "/fxml/tabs/Selling.fxml"),
	PRODUCTS("Товари", "/fxml/tabs/Products.fxml"),
	PROVIDER("Постачальники", "/fxml/tabs/Provider.fxml"),
	SELLER("Покупці", "/fxml/tabs/Seller.fxml"),
	CATEGORY("Категорії", "/fxml/tabs/Category.fxml"),
	EXCHANGE_RATE("Курс валюти", "/fxml/tabs/ExchangeRate.fxml"),
	COMING("Прихід", "/fxml/tabs/Coming.fxml"),
	STORAGE("Склад", "/fxml/tabs/Storage.fxml"),
	COMING_REPORT("Історія приходу", "/fxml/tabs/ComingReport.fxml"),
	SELLING_REPORT("Історія продажу", "/fxml/tabs/SellingReport.fxml"),
	USER_CASH("Каса продавця", "/fxml/tabs/UserSellingCash.fxml"),
	CASHIER_CASH("Каса касира", "/fxml/tabs/ManagerSellingCash.fxml"),
	ORGANIZATION("Рахунок", "/fxml/tabs/Organization.fxml"),
	NONE("", "");
	
	private String name;
	private String path;
	
	private UIView(String name, String path) {
		this.name = name;
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public String getName() {
		return name;
	}
	
	public static UIView getUIViewByName(String name){
		for(UIView view : values()){
			if(view.getName().equals(name)){
				return view;
			}
		}
		return UIView.NONE;
	}
}
