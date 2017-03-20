package com.voltor.ui.popup.usertick;

import java.util.Collection;

import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.entity.TickEntity;
import com.voltor.entity.TickHistoryEntity;
import com.voltor.services.TickHistoryService;
import com.voltor.util.UIComponentUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

@Component
public class UserTickPopupUSATickHistoryModel {
	
	@Autowired
	private TickHistoryService tickHistoryService;
	@Autowired
	private UIComponentUtils componentUrils;

	private TableView<TickHistoryEntity> table;
	private TextField date;
	private TextField value;
	private TextField type;
	
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
		componentUrils.addValueChangeListenerToTextField(date, this::updateFilteredData );
		componentUrils.addValueChangeListenerToTextField(value, this::updateFilteredData );
		componentUrils.addValueChangeListenerToTextField(type, this::updateFilteredData );
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

	private void createTable() {
		componentUrils.addTableColumn(table, "дата", 150.0, TickHistoryEntity.class, "date");
		componentUrils.addTableColumn(table, "значення", 80.0, TickHistoryEntity.class, "value");
		componentUrils.addTableColumn(table, "тип", 80.0, TickHistoryEntity.class, "type");
		componentUrils.addTableColumn(table, "користувач", 102.0, TickHistoryEntity.class, "userName");
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
		masterData = tickHistoryService.getByTickForUSA( editedValue ) ;
		updateFilteredData();
	}

}
