package com.voltor.ui.tabs.report.coming;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.bean.Coming;
import com.voltor.bean.ComingPosition;
import com.voltor.bean.Provider;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

@Component
public class ComingReportControler {

	@Autowired
	private ProviderReportModel providerModel;
	@Autowired
	private ComingReportModel comingModel;
	@Autowired
	private ComingPositionReportModel comingPositionModel;


	@FXML
	private TableView<Provider> providerTable;
	@FXML
	private TextField providerName;
	@FXML
	private TextField providerFirmName;
	@FXML
	private TextField providerEmail;
	@FXML
	private TextField providerPhone;
	
	@FXML
	private TableView<Coming> comingTable;
	@FXML
	private TextField comingDate;
	@FXML
	private TextField comingProviderName;
	@FXML
	private TextField comingProviderFirm;
	@FXML
	private TextField comingUser;
	@FXML
	private TextField comingSum;

	@FXML
	private TableView<ComingPosition> comingPositionTable;

	public void initialize() {
		initProviders();
		initComing();
		initComingPosition();
		clean();
	}

	private void initProviders() {
		providerModel.setTable(providerTable);
		providerModel.setName(providerName);
		providerModel.setFirmName(providerFirmName);
		providerModel.setPhone(providerPhone);
		providerModel.setEmail(providerEmail);
		providerModel.setContoler( this);
		providerModel.init();
	}
	
	private void initComing() {
		comingModel.setTable(comingTable);
		comingModel.setProviderFirm(comingProviderFirm);
		comingModel.setProviderName(comingProviderName);
		comingModel.setSum(comingSum);
		comingModel.setUser(comingUser);
		comingModel.setDate(comingDate);
		comingModel.setContoler( this);
		comingModel.init();
	}

	private void initComingPosition() {
		comingPositionModel.setTable(comingPositionTable);
		comingPositionModel.init();
	}
	
	public void clean() {
		providerModel.clearFields();
		comingPositionModel.clearFields();
		comingModel.clearFields();
	}

	void updateComingByProvider( Provider provider ) {
		comingModel.updateByProviser(provider);
	}
	
	void updateComingPositionByComing( Coming coming ) {
		comingPositionModel.updateByComing(coming);
	}
}
