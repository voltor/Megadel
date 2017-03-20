package com.voltor.ui.tabs.selling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.bean.Role;
import com.voltor.bean.Seller;
import com.voltor.bean.Selling;
import com.voltor.bean.SellingPosition;
import com.voltor.bean.SellingType;
import com.voltor.services.ExchangeRateService;
import com.voltor.services.SecurityService;
import com.voltor.services.SellingService;
import com.voltor.util.UIComponentUtils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

@Component
public class SellingModel {

	@Autowired
	private SellingService sellingService;
	@Autowired
	private UIComponentUtils componentUtils;
	
	private Selling sellingValue;

	private TableView<SellingPosition> table;
	private Label sum;
	private TextField received;
	private Label remainder;
	private ObservableList<SellingPosition> masterData = FXCollections.observableArrayList();
	private Button credit;
	private Button sell;

	public void init() {
		sellingValue = new Selling();
		initViewComponents();
	}

	private void initViewComponents() {
		initListenersComponent();
		createTable();
		updateValues();
	}

	private void initListenersComponent() {
		sum.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				onChangeSum();
			}
		});
		received.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				onChangeSum();
			}
		});
	}

	private Double onChangeSum() {
		Double t = 0.0;
		try {
			t = Double.valueOf(received.getText());
		} catch (Exception e) {
		} finally {
			t -= sellingValue.getSum();
			remainder.setText(t.toString());
		}
		if( t >= 0 ){
			credit.setDisable(true);
			sell.setDisable(false);
		} else {
			credit.setDisable(false);
			sell.setDisable(true);
		}
		return t;
	}

	private void createTable() {
		componentUtils.addTableColumn(table, "назва", 152.0, SellingPosition.class, "productName");
		componentUtils.addTableColumn(table, "код товару", 152.0, SellingPosition.class, "productCode");
		componentUtils.addTableColumn(table, "штрих код", 122.0, SellingPosition.class, "shtrihCode");
		componentUtils.addTableColumn(table, "ціна", 102.0, SellingPosition.class, "price");
		componentUtils.addTableColumn(table, "кількість", 102.0, SellingPosition.class, "Кількість");
		componentUtils.addTableColumn(table, "сума", 102.0, SellingPosition.class, "sum");
		componentUtils.addTableColumnWithButton(table, "", 30.0, SellingPosition.class, "this", "-", this::removeItem );
	}

	public void updateValues() {
		table.setItems(masterData);
		sellingValue = new Selling();
		Double sum = 0.0;
		for (SellingPosition postition : masterData) {
			postition.setExchangeRate( ExchangeRateService.CURRENT_EXCHAGE_RATE );
			sellingValue.getCollection().add(postition);
			sum += postition.getSum();
		}
		sellingValue.setSum(sum);
		if( onChangeSum() >= 0 ){
			sellingValue.setForSeller(0.0);
		} else {
			sellingValue.setForSeller( onChangeSum() );
		}
		this.sum.setText(sum.toString());
		if( masterData.isEmpty() ){
			sell.setDisable(true);
		} else { 
			onChangeSum();
		}

	}

	public void addItem(SellingPosition postition) {
		masterData.add(postition);
		updateValues();
	}
	
	public void removeItem(SellingPosition postition) {
		masterData.remove(postition);
		updateValues();
	}

	void clearFields() {
		masterData.clear();
		received.clear();
		updateValues();
	}

	void setTable(TableView<SellingPosition> table) {
		this.table = table;
	}

	void setSum(Label sum) {
		this.sum = sum;
	}

	boolean save(Seller seller) {
		if(Double.valueOf( remainder.getText())<0 && Role.SELLER.equals( SecurityService.getCurrentUser().getRole())){
			componentUtils.showMessage("Ви не можите продати в борг!");
			return false;
		}
		save(seller,SellingType.NORMAL);
		return true;
	}
	
	boolean saveAsCredit(Seller seller) {
		save(seller,SellingType.CREDIT);
		return true;
	}
	
	private void save( Seller seller, SellingType type ){
		updateValues();
		sellingValue.setSeller(seller);
		sellingValue.setType(type);
		sellingService.save(sellingValue);
		clearFields();
	}
	

	public void setReceived(TextField received) {
		this.received = received;
	}

	public void setRemainder(Label remainder) {
		this.remainder = remainder;
	}

	public Button getCredit() {
		return credit;
	}

	public void setCredit(Button credit) {
		this.credit = credit;
	}

	public Button getSell() {
		return sell;
	}

	public void setSell(Button sell) {
		this.sell = sell;
	}
}
