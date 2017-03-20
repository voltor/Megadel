package com.voltor.ui.tabs.seller;

import java.util.Collection;

import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.entity.TickEntity;
import com.voltor.entity.TickHistoryEntity;
import com.voltor.services.TickHistoryService;
import com.voltor.services.TickService;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

@Component
public class SellerTickHistoryModel {
	
	@Autowired
	private TickHistoryService tickHistoryService;
	@Autowired
	private TickService tickService;

	private TickEntity editedValue;

	private TableView<TickHistoryEntity> table;
	private TextField date;
	private TextField value;
	private TextField type;
	private TextField addValue;
	
	private boolean isWriting;
	
	 private Collection<TickHistoryEntity> masterData = null;
	 private ObservableList<TickHistoryEntity> filteredData = FXCollections.observableArrayList();

	public void init() {
		masterData = FXCollections.observableArrayList();
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
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }
        updateTable();
    }
	private boolean matchesFilter(TickHistoryEntity p) {
		
		if( !Strings.isNullOrEmpty( date.getText() )){
			if(!p.getDate().toString().toLowerCase().contains(date.getText().toLowerCase())){
				return false;
			}
		}
		if( !Strings.isNullOrEmpty( type.getText() )){
			if(!p.getType().toString().toLowerCase().contains(type.getText().toLowerCase())){
				return false;
			}
		}
		if( !Strings.isNullOrEmpty( value.getText() ) ){
			if( !p.getValue().toString().toLowerCase().contains(value.getText().toLowerCase()) ){
				return false;
			}
		}
		return true;
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

		table.getColumns().addAll(dateColl, valueColl, typeColl) ;
	}

	public void updateTable() {
		table.setItems( filteredData );
	}

	void clearFields() {
		date.clear();
		type.clear();
		value.clear();
		addValue.clear();
	}

	boolean isValidateFields() {
		if (editedValue == null || editedValue.getId()==0L) {
			showMessage("Будь-ласка, виберіть почтачальника!");
			return false;
		}
		if ( Strings.isNullOrEmpty(addValue.getText())) {
				showMessage("Ви не ввели суму поповнення! Приклад 1253.25");
				return false;
		}
		
		if (!Strings.isNullOrEmpty(addValue.getText())) {
			try {
				Double.parseDouble(addValue.getText().trim());
			} catch (NumberFormatException e) {
				showMessage("Ви неправельно ввели суму поповнення! Приклад 1253.25");
				return false;
			}
		}
		return true;
	}

	private void showMessage(String contentText) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Увага!");
		alert.setHeaderText(null);
		alert.setContentText(contentText);
		alert.showAndWait();
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

	public void setAddValue(TextField addValue) {
		this.addValue = addValue;
	}

	public void updateByTick(TickEntity editedValue) {
		this.editedValue = editedValue;
		masterData = tickHistoryService.getByTick( editedValue ) ;
		updateFilteredData();
	}
	
	public void refillAccount(){
		if(isValidateFields()){
			tickService.addUAForSeller( editedValue, Double.valueOf( addValue.getText().trim() ) ); 
		}
		masterData = tickHistoryService.getByTick( editedValue ) ;
		updateFilteredData();
	}

}
