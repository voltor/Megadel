package com.voltor.ui.tabs.cash.user;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.bean.Selling;
import com.voltor.bean.SellingPosition;
import com.voltor.entity.TickHistoryEntity;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

@Component
public class UserCashControler {
	@Autowired
	private UserCashSellingModel sellingModel;
	@Autowired
	private UserCashSellingPositionModel sellerPositionModel;
	@Autowired
	private UserTickHistoryModel historyModel;
	
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
	private Label sum;

	@FXML
	private TableView<TickHistoryEntity> tickHistoryTable;
	
	@FXML
	private TableView<SellingPosition> sellingPositionTable;

	public void initialize() {
		initSelling();
		initSellingPosition();
		initHistoryModel();
		clean();
		calculate();
	}

	
	private void initSelling() {
		sellingModel.setSumAll(sum);
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
	} 
	
	void updateSellingPositionByComing( Selling coming ) {
		sellerPositionModel.updateBySelling(coming);
	}
	
	private void initHistoryModel() {
		historyModel.setTable(tickHistoryTable);
		historyModel.init();
	}
	
	Collection<TickHistoryEntity> getTickHistoryData() {
		return historyModel.getData();
	}

	public void save() {
		sellingModel.save();
		historyModel.clean();
		clean();
		calculate();
	}
	
	public void calculate() {
		Double tickHistorySum = historyModel.getSumm(); 
		Double sellingSum = sellingModel.getSumm(); 
		Double summ = sellingSum + tickHistorySum;
        if(sum!=null){
        	sum.setText( summ.toString() );
        }
	}
}
