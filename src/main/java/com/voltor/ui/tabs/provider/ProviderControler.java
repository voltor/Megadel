package com.voltor.ui.tabs.provider;

import java.util.Collection;

import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.bean.Provider;
import com.voltor.entity.TickEntity;
import com.voltor.entity.TickHistoryEntity;
import com.voltor.services.ProviderService;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

@Component
public class ProviderControler {

	@Autowired
	private ProviderService providerService;
	@Autowired
	private ProviderTickHistoryModel historyModel;

	private Provider editedValue;

	@FXML
	private TableView<Provider> table;
	@FXML
	private TextField name;
	@FXML
	private TextField firmName;
	@FXML
	private TextField email;
	@FXML
	private TextField phone;

	@FXML
	private TableView<TickHistoryEntity>  tickHistoryTable;
	@FXML
	private TextField tickHistoryDate;
	@FXML
	private TextField tickHistoryValue;
	@FXML
	private TextField tickHistoryType;
	@FXML
	private TextField tickHistoryNewValue;
	
	private boolean isWriting = false;
	private Collection<Provider> masterData = null;
	private ObservableList<Provider> filteredData = FXCollections.observableArrayList();
	
	public void initialize() {
		initViewComponents();
		initHistoryModel();
	}
	
	private void initHistoryModel() {
		historyModel.setTable( tickHistoryTable );
		historyModel.setDate( tickHistoryDate );
		historyModel.setValue( tickHistoryValue );
		historyModel.setAddValue( tickHistoryNewValue );
		historyModel.setType( tickHistoryType );
		historyModel.init();
	}

	private void initListenersComponent() {
		addValueChangeListenerToTextField(name);
		addValueChangeListenerToTextField(firmName);
		addValueChangeListenerToTextField(email);
		addValueChangeListenerToTextField(phone);
	}
	
	private void initViewComponents() {
		masterData = providerService.getAll();
		createTable();
		updateFilteredData();
		initListenersComponent();
	}
	
	private void updateFilteredData() {
		if(isWriting){
			return;
		}
        filteredData.clear();

        for (Provider p : masterData) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }
        updateTable();
    }
	private boolean matchesFilter(Provider p) {
		if( !Strings.isNullOrEmpty(name.getText()) ){
			if(!p.getName().toLowerCase().contains(name.getText().toLowerCase())){
				return false;
			}
		}
		if( !Strings.isNullOrEmpty(firmName.getText()) && !Strings.isNullOrEmpty(p.getFirmName())){
			if(!p.getFirmName().toLowerCase().contains(firmName.getText().toLowerCase())){
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void createTable() {
		TableColumn nameColl = new TableColumn("ім'я");
		nameColl.setMinWidth(168);
		nameColl.setCellValueFactory(new PropertyValueFactory<Provider, Provider>("name"));

		TableColumn firmNameColl = new TableColumn("фірма");
		firmNameColl.setMinWidth(168);
		firmNameColl.setCellValueFactory(new PropertyValueFactory<Provider, Provider>("firmName"));

		TableColumn emailColl = new TableColumn("email");
		emailColl.setMinWidth(138);
		emailColl.setCellValueFactory(new PropertyValueFactory<Provider, Provider>("email"));
		TableColumn phoneColl = new TableColumn("телефон");
		phoneColl.setMinWidth(100);
		phoneColl.setCellValueFactory(new PropertyValueFactory<Provider, Provider>("phone"));

		table.getColumns().addAll(nameColl, firmNameColl, emailColl, phoneColl);
		TableColumn tickUSAColl = new TableColumn("рахунок");
		tickUSAColl.setCellValueFactory(new PropertyValueFactory<Provider, Provider>("tickUSA"));
		tickUSAColl.setMinWidth(80);
		table.getColumns().addAll( tickUSAColl);

		table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
				if (table.getSelectionModel().getSelectedItem() != null) {
					TableViewSelectionModel selectionModel = table.getSelectionModel();
					ObservableList selectedCells = selectionModel.getSelectedItems();
					editedValue = (Provider) selectedCells.get(0);
					writeFields();
				}
			}
		});
	}

	public void updateTable() {
		table.setItems( filteredData );
	}

	public void saveValue() {
		if (isValidateFields()) {
			if (editedValue == null || editedValue.getId() == 0L) {
				editedValue = new Provider();
			}
			editedValue.setName(name.getText());
			editedValue.setFirmName(firmName.getText());
			editedValue.setEmail(email.getText());
			editedValue.setPhone(phone.getText());
			providerService.save(editedValue);
			editedValue = new Provider();
			masterData = providerService.getAll();
			clearFields();
			updateFilteredData();
		}
	}

	private void clearFields() {
		name.clear();
		firmName.clear();
		email.clear();
		phone.clear();
	}

	private void writeFields() {
		isWriting = true;
		name.setText(editedValue.getName());
		firmName.setText(editedValue.getFirmName());
		email.setText(editedValue.getEmail());
		phone.setText(editedValue.getPhone());
		updateTickHistory( editedValue.getTick() );
		isWriting = false;
	}

	private boolean isValidateFields() {
		if (Strings.isNullOrEmpty(name.getText())) {
			showMessage("Будь-ласка, введіть назву!");
			return false;
		}
		if (Strings.isNullOrEmpty(firmName.getText())) {
			showMessage("Будь-ласка, введіть назву фірми!");
			return false;
		}
		if (Strings.isNullOrEmpty(email.getText())) {
			email.setText("");
		}
		if (Strings.isNullOrEmpty(phone.getText())) {
			phone.setText("");
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
	
	private void addValueChangeListenerToTextField(TextField field){
		field.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				updateFilteredData();
			}
		});
	}
	
	private void updateTickHistory(TickEntity tick) {
		historyModel.updateByTick(tick);
	}
	
	public void refillAccount(){
		if (editedValue == null || editedValue.getId() == 0L) {
			return;
		}
		historyModel.refillAccount();
		masterData = providerService.getAll();
		updateFilteredData();
	}
}
