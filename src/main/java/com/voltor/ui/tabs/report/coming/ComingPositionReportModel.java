package com.voltor.ui.tabs.report.coming;

import org.springframework.stereotype.Component;

import com.voltor.bean.Coming;
import com.voltor.bean.ComingPosition;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

@Component
public class ComingPositionReportModel {

	private TableView<ComingPosition> table;

	private ObservableList<ComingPosition> masterData = FXCollections.observableArrayList();

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
		productNameColl.setCellValueFactory(new PropertyValueFactory<ComingPosition, ComingPosition>("productName"));

		TableColumn codeColl = new TableColumn("Код товару");
		codeColl.setMinWidth(152);
		codeColl.setCellValueFactory(new PropertyValueFactory<ComingPosition, ComingPosition>("productCode"));

		TableColumn shtrihCodeColl = new TableColumn("Штрих код");
		shtrihCodeColl.setMinWidth(122);
		shtrihCodeColl.setCellValueFactory(new PropertyValueFactory<ComingPosition, ComingPosition>("shtrihCode"));


		table.getColumns().addAll(productNameColl, codeColl, shtrihCodeColl) ;

		TableColumn priceColl = new TableColumn("Ціна");
		priceColl.setMinWidth(102);
		priceColl.setCellValueFactory(new PropertyValueFactory<ComingPosition, ComingPosition>("price"));
		

		TableColumn countColl = new TableColumn("Кількість");
		countColl.setMinWidth(102);
		countColl.setCellValueFactory(new PropertyValueFactory<ComingPosition, ComingPosition>("count"));
		

		TableColumn sumColl = new TableColumn("Сума");
		sumColl.setMinWidth(102);
		sumColl.setCellValueFactory(new PropertyValueFactory<ComingPosition, ComingPosition>("sum"));
		
		table.getColumns().addAll( priceColl, countColl, sumColl);
		
	}

	public void updateTable() {
		table.setItems( masterData );
	}

	void clearFields() {
		masterData.clear();
		updateTable();
	}


	void setTable(TableView<ComingPosition> table) {
		this.table = table;
	}
	
	void updateByComing( Coming coming ) {
		clearFields();
		masterData.addAll( coming.getCollection() );
		updateTable();
	}
}
