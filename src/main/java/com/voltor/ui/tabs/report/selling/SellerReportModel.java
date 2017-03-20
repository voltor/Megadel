package com.voltor.ui.tabs.report.selling;

import java.util.Collection;

import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.bean.Seller;
import com.voltor.services.SellerService;

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
public class SellerReportModel {

	@Autowired
	private SellerService providerService;

	private Seller editedValue;

	private TableView<Seller> table;
	private TextField name;
	private TextField email;
	private TextField phone;

	private SellingReportControler controler;
	
	private boolean isWriting;

	private Collection<Seller> masterData = null;
	private ObservableList<Seller> filteredData = FXCollections.observableArrayList();

	public void init() {
		masterData = providerService.getAll();
		editedValue = new Seller();
		initViewComponents();
		initListenersComponent();
	}

	private void initListenersComponent() {
		addValueChangeListenerToTextField(name);
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

        for (Seller p : masterData) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }
        updateTable();

	}

	private boolean matchesFilter(Seller p) {
		if( !Strings.isNullOrEmpty(name.getText()) ){
			if(!p.getName().toLowerCase().contains(name.getText().toLowerCase())){
				return false;
			}
		}
		if( !Strings.isNullOrEmpty(phone.getText()) && !Strings.isNullOrEmpty(p.getPhone()) ){
			if(!p.getPhone().toLowerCase().contains(phone.getText().toLowerCase())){
				return false;
			}
		}
		if( !Strings.isNullOrEmpty(email.getText()) && !Strings.isNullOrEmpty(p.getEmail()) ){
			if(!p.getEmail().toLowerCase().contains(email.getText().toLowerCase())){
				return false;
			}
		}
		return true;
	}

	private void initViewComponents() {
		createTable();
		updateFilteredData();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void createTable() {
		TableColumn nameColl = new TableColumn("Ім'я");
		nameColl.setMinWidth(168);
		nameColl.setCellValueFactory(new PropertyValueFactory<Seller, Seller>("name"));
//
//		TableColumn firmNameColl = new TableColumn("Фірма");
//		firmNameColl.setMinWidth(168);
//		firmNameColl.setCellValueFactory(new PropertyValueFactory<Provider, Provider>("firmName"));

		TableColumn emailColl = new TableColumn("email");
		emailColl.setMinWidth(138);
		emailColl.setCellValueFactory(new PropertyValueFactory<Seller, Seller>("email"));
		TableColumn phoneColl = new TableColumn("телефон");
		phoneColl.setMinWidth(138);
		phoneColl.setCellValueFactory(new PropertyValueFactory<Seller, Seller>("phone"));

		table.getColumns().addAll(nameColl, emailColl, phoneColl);
		table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
				if (table.getSelectionModel().getSelectedItem() != null) {
					TableViewSelectionModel selectionModel = table.getSelectionModel();
					ObservableList selectedCells = selectionModel.getSelectedItems();
					editedValue = (Seller) selectedCells.get(0);
					writeFields();
					controler.updateSellingByProvider( editedValue);
				}
			}
		});
	}

	public void updateTable() {
		table.setItems( filteredData );
		editedValue = new Seller();
	}

	void clearFields() {
		name.clear();
		email.clear();
		phone.clear();
		updateFilteredData();
	}

	private void writeFields() {
		isWriting = true;
		name.setText(editedValue.getName());
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

	void setTable(TableView<Seller> table) {
		this.table = table;
	}

	void setName(TextField name) {
		this.name = name;
	}

	void setEmail(TextField email) {
		this.email = email;
	}

	void setPhone(TextField phone) {
		this.phone = phone;
	}

	public void setContoler(SellingReportControler comingReportControler) {
		this.controler = comingReportControler;
	}
}
