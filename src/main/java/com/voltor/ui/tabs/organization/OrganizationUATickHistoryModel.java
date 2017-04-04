package com.voltor.ui.tabs.organization;

import java.util.Collection;

import com.voltor.util.UITickHistoryModelUtils;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.entity.TickEntity;
import com.voltor.entity.TickHistoryEntity;
import com.voltor.services.TickHistoryService;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

@Component
public class OrganizationUATickHistoryModel {
	
	@Autowired
	private TickHistoryService tickHistoryService;

	private TableView<TickHistoryEntity> table;
	private TextField date;
	private TextField value;
	private TextField type;
	
	private boolean isWriting;
	
	 private Collection<TickHistoryEntity> masterData = null;
	 private ObservableList<TickHistoryEntity> filteredData = FXCollections.observableArrayList();

	public void init() {
		masterData = FXCollections.observableArrayList(); //tickHistoryService.getAll() ;
		initViewComponents();
		initListenersComponent();
	}

	private void initViewComponents() {
		createTable();
		updateFilteredData();
	}
	
	private void initListenersComponent(){
		date.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                updateFilteredData();
            }
        });
		value.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                updateFilteredData();
            }
		});
		type.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                updateFilteredData();
            }
		});
	}

	private void updateFilteredData() {
		if(isWriting){
			return;
		}
        filteredData.clear();

        for (TickHistoryEntity p : masterData) {
            if (UITickHistoryModelUtils.matchesFilter(p,date,value,type)) {
                filteredData.add(p);
            }
        }
        updateTable();
    }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void createTable() {
		TableColumn dateColl = new TableColumn("дата");
		dateColl.setMinWidth(150);
		dateColl.setCellValueFactory(new PropertyValueFactory<TickHistoryEntity, TickHistoryEntity>("date"));

		TableColumn valueColl = new TableColumn("значення");
		valueColl.setMinWidth(80);
		valueColl.setCellValueFactory(new PropertyValueFactory<TickHistoryEntity, TickHistoryEntity>("value"));

		TableColumn typeColl = new TableColumn("тип");
		typeColl.setMinWidth(80);
		typeColl.setCellValueFactory(new PropertyValueFactory<TickHistoryEntity, TickHistoryEntity>("type"));

		TableColumn userColl = new TableColumn("користувач");
		userColl.setMinWidth(102);
		userColl.setCellValueFactory(new PropertyValueFactory<TickHistoryEntity, TickHistoryEntity>("userName"));
		table.getColumns().addAll(dateColl, valueColl, typeColl, userColl) ;
	}

	public void updateTable() {
		table.setItems( filteredData );
	}

	void clearFields() {
		date.clear();
		type.clear();
		value.clear();
	}

	public void setTable(TableView<TickHistoryEntity> table) {
		this.table = table;
	}

	public void setDate(TextField date) {
		this.date = date;
	}

	public void setValue(TextField value) {
		this.value = value;
	}

	public void setType(TextField type) {
		this.type = type;
	}

	public void updateByTick(TickEntity editedValue) {
		masterData = tickHistoryService.getByTickForUA( editedValue ) ;
		updateFilteredData();
	}

}
