package com.voltor.ui.tabs.report.selling;

import org.springframework.stereotype.Component;

import com.voltor.bean.Selling;
import com.voltor.bean.SellingPosition;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

@Component
public class SellingPositionReportModel {

	private TableView<SellingPosition> table;

	private ObservableList<SellingPosition> masterData = FXCollections.observableArrayList();

	public void init() {
		initViewComponents();
	}

	private void initViewComponents() {
		createTable();
		updateTable();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void createTable() {
		TableColumn productNameColl = new TableColumn("Назва");
		productNameColl.setMinWidth(152);
		productNameColl.setCellValueFactory(new PropertyValueFactory<SellingPosition, SellingPosition>("productName"));

		TableColumn codeColl = new TableColumn("Код товару");
		codeColl.setMinWidth(152);
		codeColl.setCellValueFactory(new PropertyValueFactory<SellingPosition, SellingPosition>("productCode"));

		TableColumn shtrihCodeColl = new TableColumn("Штрих код");
		shtrihCodeColl.setMinWidth(122);
		shtrihCodeColl.setCellValueFactory(new PropertyValueFactory<SellingPosition, SellingPosition>("shtrihCode"));

		table.getColumns().addAll(productNameColl, codeColl, shtrihCodeColl);

		TableColumn priceColl = new TableColumn("Ціна");
		priceColl.setMinWidth(102);
		priceColl.setCellValueFactory(new PropertyValueFactory<SellingPosition, SellingPosition>("price"));

		TableColumn countColl = new TableColumn("Кількість");
		countColl.setMinWidth(102);
		countColl.setCellValueFactory(new PropertyValueFactory<SellingPosition, SellingPosition>("count"));

		TableColumn sumColl = new TableColumn("Сума");
		sumColl.setMinWidth(102);
		sumColl.setCellValueFactory(new PropertyValueFactory<SellingPosition, SellingPosition>("sum"));

		table.getColumns().addAll(priceColl, countColl, sumColl);
		
	}

	public void updateTable() {
		table.setItems( masterData );
	}

	void clearFields() {
		masterData.clear();
		updateTable();
	}


	void setTable(TableView<SellingPosition> table) {
		this.table = table;
	}
	
	void updateBySelling( Selling selling ) {
		clearFields();
		masterData.addAll( selling.getCollection() );
		updateTable();
	}
}
