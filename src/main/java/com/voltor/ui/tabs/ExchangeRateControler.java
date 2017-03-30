package com.voltor.ui.tabs;

import com.voltor.util.UIComponentUtils;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.bean.ExchangeRate;
import com.voltor.services.ExchangeRateService;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

@Component
public class ExchangeRateControler {
 
	@Autowired
	private ExchangeRateService exchangeRateService;

	@Autowired
	private UIComponentUtils componentUtils;
	
	private ExchangeRate editedValue;

	@FXML
	private TableView<ExchangeRate> table;
	@FXML
	private TextField currency;

	public void initialize() {
		initViewComponents();
	}

	private void initViewComponents() {
		createTable();
		updateTable();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void createTable() {
		componentUtils.addTableColumn(table, "дата", 148.0, ExchangeRate.class, "dateTime");
		componentUtils.addTableColumn(table, "курс валюти", 148.0, ExchangeRate.class, "currency");
	}

	public void updateTable() {
		table.setItems(FXCollections.observableArrayList(exchangeRateService.getAll()));
		editedValue = new ExchangeRate();
	}

	public void saveValue() {
		if( isValidateFields() ){
			if (editedValue == null || editedValue.getId() == 0L) {
				editedValue = new ExchangeRate();
			} 
			editedValue.setCurrency(Double.parseDouble(currency.getText().trim()));
			exchangeRateService.save(editedValue);
			exchangeRateService.getCurrentExchangeRate();
			editedValue = new ExchangeRate();
			clearFields();
			updateTable();
		}
	}

	private void clearFields() {
		currency.clear(); 
	} 

	private boolean isValidateFields() { 
		if (Strings.isNullOrEmpty(currency.getText())) {
			componentUtils.showMessage("Будь-ласка, введіть курс валюти!");
			return false;
		}
		try{
			Double.parseDouble(currency.getText().trim());
		} catch (NumberFormatException e) {
			componentUtils.showMessage("Ви неправельно ввели курс валюти! Приклад 1253.25");
			return false;
		} 
		return true;
	}

}
