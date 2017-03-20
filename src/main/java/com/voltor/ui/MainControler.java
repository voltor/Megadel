package com.voltor.ui;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.MegadelApplication;
import com.voltor.SpringFXMLloader;
import com.voltor.services.ExchangeRateService;
import com.voltor.services.SecurityService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

@Component
public class MainControler {
	@Autowired private ExchangeRateService exchangeRateService;
	@FXML
	private ListView<String> leftMenu;
	@FXML
	private Pane content;
	@FXML
	private Pane info;
	@FXML
	private Label carrency;

	public void initialize() {
		leftMenu.setItems(getLeftMenuForUser());
		updateExchangeCarrency();
	}
	
	public void updateExchangeCarrency(){ 
		carrency.setText( exchangeRateService.getCurrentExchangeRate().getCurrency().toString());
	}

	private ObservableList<String> getLeftMenuForUser() {
		switch (SecurityService.getCurrentUser().getRole()) {
		case SELLER:
			return getLeftMenuForSELLER();
		case CASHIER:
			return getLeftMenuForCASHIER();
		case MANAGER:
			return getLeftMenuForMANAGER();
		case ADMIN:
			return getLeftMenuForADMINISTRATOR();
		default: 
			return FXCollections.observableArrayList();
		}
	}
	
	private ObservableList<String> getLeftMenuForSELLER() {
		ObservableList<String> leftMenuList = FXCollections.observableArrayList();
		leftMenuList.add(UIView.SELLING.getName());
		leftMenuList.add(UIView.STORAGE.getName());
		leftMenuList.add(UIView.SELLER.getName());
		leftMenuList.add(UIView.SELLING_REPORT.getName());
		leftMenuList.add(UIView.USER_CASH.getName());
		return leftMenuList;
	}

	private ObservableList<String> getLeftMenuForCASHIER() {
		ObservableList<String> leftMenuList = FXCollections.observableArrayList();
		leftMenuList.add(UIView.STORAGE.getName());
		leftMenuList.add(UIView.SELLER.getName());
		leftMenuList.add(UIView.USER_CASH.getName());
		leftMenuList.add(UIView.CASHIER_CASH.getName());
		leftMenuList.add(UIView.ORGANIZATION.getName());
		return leftMenuList;
	}
	
	private ObservableList<String> getLeftMenuForMANAGER() {
		ObservableList<String> leftMenuList = FXCollections.observableArrayList();
		leftMenuList.add(UIView.PRODUCTS.getName());
		leftMenuList.add(UIView.COMING.getName());
		leftMenuList.add(UIView.SELLING.getName());
		leftMenuList.add(UIView.STORAGE.getName());
		leftMenuList.add(UIView.CATEGORY.getName());
		leftMenuList.add(UIView.EXCHANGE_RATE.getName());
		leftMenuList.add(UIView.PROVIDER.getName()); 
		leftMenuList.add(UIView.SELLER.getName());
		leftMenuList.add(UIView.COMING_REPORT.getName());
		leftMenuList.add(UIView.SELLING_REPORT.getName());
		leftMenuList.add(UIView.USER_CASH.getName());
		return leftMenuList;
	}
	
	private ObservableList<String> getLeftMenuForADMINISTRATOR() {
		ObservableList<String> leftMenuList = FXCollections.observableArrayList();
		leftMenuList.add(UIView.USERS.getName());
		leftMenuList.add(UIView.PRODUCTS.getName());
		leftMenuList.add(UIView.COMING.getName());
		leftMenuList.add(UIView.SELLING.getName());
		leftMenuList.add(UIView.STORAGE.getName());
		leftMenuList.add(UIView.CATEGORY.getName());
		leftMenuList.add(UIView.EXCHANGE_RATE.getName());
		leftMenuList.add(UIView.PROVIDER.getName()); 
		leftMenuList.add(UIView.SELLER.getName());
		leftMenuList.add(UIView.COMING_REPORT.getName());
		leftMenuList.add(UIView.SELLING_REPORT.getName());
		leftMenuList.add(UIView.USER_CASH.getName());
		leftMenuList.add(UIView.CASHIER_CASH.getName());
		leftMenuList.add(UIView.ORGANIZATION.getName());
		return leftMenuList;
	}
	
	@FXML
	private void onMouseClicked(MouseEvent event) throws IOException {
		updateExchangeCarrency();
		String selectItem = leftMenu.getSelectionModel().getSelectedItem();
		Parent parent = new SpringFXMLloader().getParrentByPath( UIView.getUIViewByName(selectItem).getPath() );
		content.getChildren().setAll(parent.getChildrenUnmodifiable());
	}

	public void logOut() throws IOException { 
		Parent rootNode = new SpringFXMLloader().getParrentByPath("/fxml/Login.fxml");
		Stage mainStage = new Stage();
		Scene scene = new Scene( rootNode );
		mainStage.setScene( scene );
		mainStage.setTitle(MegadelApplication.PROGECT_NAME);
		mainStage.show();
		
        Stage stage = (Stage) leftMenu.getScene().getWindow();
		stage.close();
	}
}
