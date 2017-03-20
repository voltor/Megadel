package com.voltor.ui.tabs.organization;

import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.entity.OrganizationEntity;
import com.voltor.entity.TickHistoryEntity;
import com.voltor.services.OrganizationService;
import com.voltor.services.TickService;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

@Component
public class OrganizationControler {

	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private OrganizationUATickHistoryModel uaTickHistoryModel;
	@Autowired
	private OrganizationUSATickHistoryModel usaTickHistoryModel;
	@Autowired
	private TickService tickService;
	
	@FXML
	private TableView<TickHistoryEntity> uaTable;
	@FXML
	private TextField uaDate;
	@FXML
	private TextField uaValue;
	@FXML
	private TextField uaType;
	@FXML
	private Label ua;
	
	@FXML
	private TableView<TickHistoryEntity> usaTable;
	@FXML
	private TextField usaDate;
	@FXML
	private TextField usaValue;
	@FXML
	private TextField usaType;
	
	@FXML
	private TextField usaNewValue; 
	@FXML
	private TextField usaNewExchange;
	@FXML
	private Label usa;
	
	private OrganizationEntity organizationEntity;
	
	public void initialize() {  
		initUATickHistory();
		initUSATickHistory();
		updateData();
	}

	private void updateData(){
		organizationEntity = organizationService.getOrganization();
		uaTickHistoryModel.updateByTick(organizationEntity.getTick());
		usaTickHistoryModel.updateByTick(organizationEntity.getTick());
		ua.setText(organizationEntity.getTick().getValueUA().toString());
		usa.setText(organizationEntity.getTick().getValueUSA().toString());
	}
	private void initUATickHistory() {
		uaTickHistoryModel.setTable(uaTable);
		uaTickHistoryModel.setDate(uaDate);
		uaTickHistoryModel.setValue(uaValue);
		uaTickHistoryModel.setType(uaType);
		uaTickHistoryModel.init();
	}
	
	private void initUSATickHistory() {
		usaTickHistoryModel.setTable(usaTable);
		usaTickHistoryModel.setDate(usaDate);
		usaTickHistoryModel.setValue(usaValue);
		usaTickHistoryModel.setType(usaType);
		usaTickHistoryModel.init();
	}
	
	public void exchage() {
		if( isValidateFields() ){
			Double sumTo = Double.valueOf(usaNewValue.getText());
			Double rate = Double.valueOf(usaNewExchange.getText());
			tickService.change(sumTo*rate, sumTo);
			clean();
		}
	}
	
	public void clean() {
		uaTickHistoryModel.clearFields();
		usaTickHistoryModel.clearFields();
		usaNewValue.clear();
		usaNewExchange.clear();
		updateData();

	}
	
	boolean isValidateFields() {
		
		if (!Strings.isNullOrEmpty(usaNewValue.getText())) {
			try {
				Double.parseDouble(usaNewValue.getText().trim());
			} catch (NumberFormatException e) {
				showMessage("Ви неправельно ввели суму поповнення! Приклад 1253.25");
				return false;
			}
		}
		if (!Strings.isNullOrEmpty(usaNewExchange.getText())) {
			try {
				Double.parseDouble(usaNewExchange.getText().trim());
			} catch (NumberFormatException e) {
				showMessage("Ви неправельно ввели курс валюти! Приклад 24.25");
				return false;
			}
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
