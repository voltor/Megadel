package com.voltor.ui.tabs.cash.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.bean.Selling;
import com.voltor.bean.SellingPosition;
import com.voltor.entity.TransferCashEntity;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

@Component
public class ManagerCashControler {
	@Autowired
	private ManagerCashSellingModel sellingModel;
	@Autowired
	private ManagerCashSellingPositionModel sellerPositionModel;
	@Autowired
	private ManagerTransferCashModel managerTransferCashModel;
	
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
	
	@FXML
	private TableView<TransferCashEntity> cashTable;
	@FXML
	private TextField cashDate;
	@FXML
	private TextField cashAuthor;
	@FXML
	private TextField cashSum;

	public void initialize() {
		initCashModel();
		initSelling();
		initSellingPosition();
		clean();
	}

	
	private void initCashModel() {
		managerTransferCashModel.setTable(cashTable); 
		managerTransferCashModel.setSum(cashSum);
		managerTransferCashModel.setAutor(cashAuthor);
		managerTransferCashModel.setDate(cashDate);
		managerTransferCashModel.setContoler( this);
		managerTransferCashModel.init();
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
		sellerPositionModel.clearFields();
		sellingModel.clearFields();
		managerTransferCashModel.clearFields();
	} 
	
	void updateSellingPositionByComing( Selling coming ) {
		sellerPositionModel.updateBySelling(coming);
	}


	public void updateSellingByCash(TransferCashEntity editedValue) {
		sellingModel.updateByCash( editedValue );
	}
}
