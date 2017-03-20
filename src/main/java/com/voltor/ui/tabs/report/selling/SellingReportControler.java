package com.voltor.ui.tabs.report.selling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.bean.Seller;
import com.voltor.bean.Selling;
import com.voltor.bean.SellingPosition;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

@Component
public class SellingReportControler {

	@Autowired
	private SellerReportModel sellerModel;
	@Autowired
	private SellingReportModel sellingModel;
	@Autowired
	private SellingPositionReportModel sellerPositionModel;


	@FXML
	private TableView<Seller> sellerTable;
	@FXML
	private TextField sellerName;
	@FXML
	private TextField sellerEmail;
	@FXML
	private TextField sellerPhone;
	
	@FXML
	private TableView<Selling> sellingTable;
	@FXML
	private TextField sellingDate;
	@FXML
	private TextField sellingSellerName;
	@FXML
	private TextField sellingUser;
	@FXML
	private TextField sellingSum;

	@FXML
	private TableView<SellingPosition> sellingPositionTable;

	public void initialize() {
		initSellers();
		initSelling();
		initSellingPosition();
		clean();
	}

	private void initSellers() {
		sellerModel.setTable(sellerTable);
		sellerModel.setName(sellerName);
		sellerModel.setPhone(sellerPhone);
		sellerModel.setEmail(sellerEmail);
		sellerModel.setContoler( this);
		sellerModel.init();
	}
	
	private void initSelling() {
		sellingModel.setTable(sellingTable);
		sellingModel.setName(sellingSellerName);
		sellingModel.setSum(sellingSum);
		sellingModel.setUser(sellingUser);
		sellingModel.setDate(sellingDate);
		sellingModel.setContoler( this);
		sellingModel.init();
	}

	private void initSellingPosition() {
		sellerPositionModel.setTable(sellingPositionTable);
		sellerPositionModel.init();
	}
	
	public void clean() {
		sellerModel.clearFields();
		sellerPositionModel.clearFields();
		sellingModel.clearFields();
	}

	void updateSellingByProvider( Seller provider ) {
		sellingModel.updateBySeller(provider);
	}
	
	void updateSellingPositionByComing( Selling coming ) {
		sellerPositionModel.updateBySelling(coming);
	}
}
