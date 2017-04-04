package com.voltor.ui.tabs.coming;

import java.util.Collection;

import com.voltor.util.UIProviderModelsUtils;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.bean.Provider;
import com.voltor.services.ProviderService;
import com.voltor.util.UIComponentUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

@Component
public class ProviderModel {

	@Autowired
	private ProviderService providerService;
	@Autowired
	private UIComponentUtils componentUtils;

	private Provider editedValue;

	private TableView<Provider> table;
	private TextField name;
	private TextField firmName;
	private TextField email;
	private TextField phone;
	
	private boolean isWriting;

	private Collection<Provider> masterData = null;
	private ObservableList<Provider> filteredData = FXCollections.observableArrayList();

	public void init() {
		masterData = providerService.getAll();
		editedValue = new Provider();
		initViewComponents();
		initListenersComponent();
	}

	private void initListenersComponent() {
		componentUtils.addValueChangeListenerToTextField(name, this::updateFilteredData);
		componentUtils.addValueChangeListenerToTextField(firmName, this::updateFilteredData);
		componentUtils.addValueChangeListenerToTextField(email, this::updateFilteredData);
		componentUtils.addValueChangeListenerToTextField(phone, this::updateFilteredData);
	}

	private void updateFilteredData() {
		if(isWriting){
			return;
		}
        filteredData.clear();

        for (Provider p : masterData) {
            if (UIProviderModelsUtils.matchesFilter(p,name,firmName,email,phone)) {
                filteredData.add(p);
            }
        }
        updateTable();

	}

	private void initViewComponents() {
		createTable();
		updateFilteredData();
	}

	private void createTable() {
		componentUtils.addTableColumn(table, "ім'я", 168.0, Provider.class, "name");
		componentUtils.addTableColumn(table, "фірма", 168.0, Provider.class, "firmName");
		componentUtils.addTableColumn(table, "email", 138.0, Provider.class, "email");
		componentUtils.addTableColumn(table, "телефон", 138.0, Provider.class, "phone");
		componentUtils.addSelectionEventToTable(table, Provider.class, e -> {
			editedValue = e;
			writeFields();
		}); 
	}

	public void updateTable() {
		table.setItems( filteredData );
		editedValue = new Provider();
	}

	void clearFields() {
		name.clear();
		firmName.clear();
		email.clear();
		phone.clear();
		updateFilteredData();
	}

	private void writeFields() {
		isWriting = true;
		name.setText(editedValue.getName());
		firmName.setText(editedValue.getFirmName());
		email.setText(editedValue.getEmail());
		phone.setText(editedValue.getPhone());
		isWriting = false;
	}

	boolean isValidateFields() {
		if (editedValue == null || editedValue.getId()==0L) {
			componentUtils.showMessage("Будь-ласка, виберіть постачальника!");
			return false;
		}
		return true;
	}

	Provider getEditedValue() {
		return editedValue;
	}

	void setTable(TableView<Provider> table) {
		this.table = table;
	}

	void setName(TextField name) {
		this.name = name;
	}

	void setFirmName(TextField firmName) {
		this.firmName = firmName;
	}

	void setEmail(TextField email) {
		this.email = email;
	}

	void setPhone(TextField phone) {
		this.phone = phone;
	}
}
