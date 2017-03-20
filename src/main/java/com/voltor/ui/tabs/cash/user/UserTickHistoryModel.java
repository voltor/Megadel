package com.voltor.ui.tabs.cash.user;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.entity.TickHistoryEntity;
import com.voltor.services.TickHistoryService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

@Component
public class UserTickHistoryModel {
	
	@Autowired
	private TickHistoryService tickHistoryService;

	private TableView<TickHistoryEntity> table;
	
	private boolean isWriting;
	
	 private Collection<TickHistoryEntity> masterData = null;
	 private ObservableList<TickHistoryEntity> filteredData = FXCollections.observableArrayList();

	public void init() {
		masterData = FXCollections.observableArrayList();
		initViewComponents();
	}

	private void initViewComponents() {
		createTable();
		updateFilteredData();
	}
	

	private void updateFilteredData() {
		if(isWriting){
			return;
		}
		masterData = tickHistoryService.getForUserCash();
        filteredData.clear();

        for (TickHistoryEntity p : masterData) {
                filteredData.add(p);
        }
        updateTable();
    }

	Double getSumm() {
        Double summ =0.0;
        for (TickHistoryEntity p : masterData) {
            summ+=p.getValue();
        }
        return summ;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void createTable() {
		TableColumn dateColl = new TableColumn("дата");
		dateColl.setMinWidth(150);
		dateColl.setCellValueFactory(new PropertyValueFactory<TickHistoryEntity, TickHistoryEntity>("date"));

		TableColumn valueColl = new TableColumn("значення");
		valueColl.setMinWidth(100);
		valueColl.setCellValueFactory(new PropertyValueFactory<TickHistoryEntity, TickHistoryEntity>("value"));

		TableColumn typeColl = new TableColumn("тип");
		typeColl.setMinWidth(100);
		typeColl.setCellValueFactory(new PropertyValueFactory<TickHistoryEntity, TickHistoryEntity>("type"));

		TableColumn userColl = new TableColumn("користувач");
		userColl.setMinWidth(150);
		userColl.setCellValueFactory(new PropertyValueFactory<TickHistoryEntity, TickHistoryEntity>("userName"));
		
		table.getColumns().addAll(dateColl, valueColl, typeColl,userColl) ;
	}

	public void updateTable() {
		table.setItems( filteredData );
	}

	public void setTable(TableView<TickHistoryEntity> table) {
		this.table = table;
	}

	public Collection<TickHistoryEntity> getData() {
		return masterData;
	}

	public void clean() {
		masterData.clear();
		updateFilteredData();
	}
}
