package com.voltor.ui.tabs;

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
		TableColumn dateColl = new TableColumn("Дата");
		dateColl.setMinWidth(148);
		dateColl.setCellValueFactory(new PropertyValueFactory<ExchangeRate, ExchangeRate>("dateTime"));

		TableColumn currencyColl = new TableColumn("Курс валюти");
		currencyColl.setMinWidth(148);
		currencyColl.setCellValueFactory(new PropertyValueFactory<ExchangeRate, ExchangeRate>("currency"));

		table.getColumns().addAll( dateColl, currencyColl );
 
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
			showMessage("Будь-ласка, введіть курс валюти!");
			return false;
		}
		try{
			Double.parseDouble(currency.getText().trim());
		} catch (NumberFormatException e) {
			showMessage("Ви неправельно ввели курс валюти! Приклад 1253.25");
			return false;
		} 
		return true;
	}

	private void showMessage(String contentText) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Увага!");
		alert.setHeaderText(null);
		alert.setContentText(contentText);
		alert.showAndWait();
	}
}
