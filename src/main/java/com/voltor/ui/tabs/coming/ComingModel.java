package com.voltor.ui.tabs.coming;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.bean.Coming;
import com.voltor.bean.ComingPosition;
import com.voltor.bean.Provider;
import com.voltor.services.ComingService;
import com.voltor.util.UIComponentUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

@Component
public class ComingModel {

	@Autowired
	private ComingService comingService;
	@Autowired
	private UIComponentUtils componentUtils;
	
	private Coming comingValue;

	private TableView<ComingPosition> table;
	private Label sum;
	private Label points;

	private ObservableList<ComingPosition> masterData = FXCollections.observableArrayList();

	public void init() {
		comingValue = new Coming();
		initViewComponents();
	}

	private void initViewComponents() {
		createTable();
		updateValues();
	}

	private void createTable() {
		componentUtils.addTableColumn(table, "назва", 152.0, ComingPosition.class, "productName");
		componentUtils.addTableColumn(table, "код товару", 152.0, ComingPosition.class, "productCode");
		componentUtils.addTableColumn(table, "штрих код", 122.0, ComingPosition.class, "shtrihCode");
		componentUtils.addTableColumn(table, "ціна", 102.0, ComingPosition.class, "price"); 
		componentUtils.addTableColumn(table, "кількість", 102.0, ComingPosition.class, "count"); 
		componentUtils.addTableColumn(table, "сума", 102.0, ComingPosition.class, "sum");
		componentUtils.addTableColumnWithButton(table, "", 30.0, ComingPosition.class, "this", "-", this::removeItem );
	}

	public void updateValues() {
		table.setItems( masterData );
		comingValue = new Coming();
		Double sum = 0.0;
		for( ComingPosition postition : masterData ){
			comingValue.getCollection().add(postition);
			sum+=postition.getSum();
		}
		comingValue.setSum(sum);
		this.sum.setText(sum.toString());
		this.points.setText("USD");
		
	}
	
	public void addItem( ComingPosition postition ) {
		masterData.add(postition);
		updateValues();
	}
	
	public void removeItem( ComingPosition postition ) {
		masterData.remove(postition);
		updateValues();
	}


	void clearFields() {
		masterData.clear();
		updateValues();
	}


	void setTable(TableView<ComingPosition> table) {
		this.table = table;
	}

	void setSum(Label sum) {
		this.sum = sum;
	}

	void setPoints(Label points) {
		this.points = points;
	}
	
	void save( Provider provider ) {
		comingValue.setProvider(provider);
		comingService.save(comingValue);
		clearFields();
	}
}
