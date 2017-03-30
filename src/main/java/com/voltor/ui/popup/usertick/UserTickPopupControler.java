package com.voltor.ui.popup.usertick;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.bean.User;
import com.voltor.entity.TickHistoryEntity;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import com.voltor.util.UserTickUtils;

@Component
public class UserTickPopupControler {

	@Autowired
	private UserTickPopupUATickHistoryModel uaTickHistoryModel;
	@Autowired
	private UserTickPopupUSATickHistoryModel usaTickHistoryModel;
//	@Autowired
//	private TickService tickService;
//	@Autowired
//	private UIComponentUrils componentUrils;
	
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
	
//	@FXML
//	private TextField usaNewValue; 
//	@FXML
//	private TextField usaNewExchange;
	@FXML
	private Label usa;
    @Autowired
    private UserTickUtils userTickUtils;

	private User user;

	public void initialize() {
        userTickUtils.initTickHistory(uaTickHistoryModel,uaTable,uaDate,uaValue,uaType,
                usaTickHistoryModel,usaTable,usaDate,usaValue,usaType);
	}

	private void updateData(){
		uaTickHistoryModel.updateByTick(user.getTick());
		usaTickHistoryModel.updateByTick(user.getTick());
		ua.setText(user.getTick().getValueUA().toString());
		usa.setText(user.getTick().getValueUSA().toString());
	}

	
//	public void exchage() {
//		if( isValidateFields() ){
//			Double sumTo = Double.valueOf(usaNewValue.getText());
//			Double rate = Double.valueOf(usaNewExchange.getText());
//			tickService.change(sumTo*rate, sumTo);
//			clean();
//		}
//	}
	
	public void clean() {
		uaTickHistoryModel.clearFields();
		usaTickHistoryModel.clearFields();
//		usaNewValue.clear();
//		usaNewExchange.clear();
		updateData();

	}
	
//	boolean isValidateFields() {
//		
//		if (!Strings.isNullOrEmpty(usaNewValue.getText())) {
//			try {
//				Double.parseDouble(usaNewValue.getText().trim());
//			} catch (NumberFormatException e) {
//				componentUrils.showMessage("Ви неправельно ввели суму поповнення! Приклад 1253.25");
//				return false;
//			}
//		}
//		if (!Strings.isNullOrEmpty(usaNewExchange.getText())) {
//			try {
//				Double.parseDouble(usaNewExchange.getText().trim());
//			} catch (NumberFormatException e) {
//				componentUrils.showMessage("Ви неправельно ввели курс валюти! Приклад 24.25");
//				return false;
//			}
//		}
//		return true;
//	}

	public void setUser(User user) {
		this.user = user;
		clean();
	}
	
	
}
