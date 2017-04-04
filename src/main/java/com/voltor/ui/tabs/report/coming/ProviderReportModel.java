package com.voltor.ui.tabs.report.coming;

import java.util.Collection;

import com.voltor.util.UIProviderModelsUtils;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.bean.Provider;
import com.voltor.services.ProviderService;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

@Component
public class ProviderReportModel {

	@Autowired
	private ProviderService providerService;

	private Provider editedValue;

	private TableView<Provider> table;
	private TextField name;
	private TextField firmName;
	private TextField email;
	private TextField phone;

	private ComingReportControler controler;
	
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
		addValueChangeListenerToTextField(name);
		addValueChangeListenerToTextField(firmName);
		addValueChangeListenerToTextField(email);
		addValueChangeListenerToTextField(phone);
	}
	
	private void addValueChangeListenerToTextField(TextField field){
		field.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				updateFilteredData();
			}
		});
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void createTable() {
		TableColumn nameColl = new TableColumn("Ім'я");
		nameColl.setMinWidth(168);
		nameColl.setCellValueFactory(new PropertyValueFactory<Provider, Provider>("name"));

		TableColumn firmNameColl = new TableColumn("Фірма");
		firmNameColl.setMinWidth(168);
		firmNameColl.setCellValueFactory(new PropertyValueFactory<Provider, Provider>("firmName"));

		TableColumn emailColl = new TableColumn("email");
		emailColl.setMinWidth(138);
		emailColl.setCellValueFactory(new PropertyValueFactory<Provider, Provider>("email"));
		TableColumn phoneColl = new TableColumn("телефон");
		phoneColl.setMinWidth(138);
		phoneColl.setCellValueFactory(new PropertyValueFactory<Provider, Provider>("phone"));

		table.getColumns().addAll(nameColl, firmNameColl, emailColl, phoneColl);
		table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
				if (table.getSelectionModel().getSelectedItem() != null) {
					TableViewSelectionModel selectionModel = table.getSelectionModel();
					ObservableList selectedCells = selectionModel.getSelectedItems();
					editedValue = (Provider) selectedCells.get(0);
					writeFields();
					controler.updateComingByProvider( editedValue);
				}
			}
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
			showMessage("Будь-ласка, постачальника!");
			return false;
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

	public void setContoler(ComingReportControler comingReportControler) {
		this.controler = comingReportControler;
	}
}
