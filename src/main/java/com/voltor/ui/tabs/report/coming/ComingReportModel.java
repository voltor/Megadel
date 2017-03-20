package com.voltor.ui.tabs.report.coming;

import java.util.Collection;

import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.bean.Coming;
import com.voltor.bean.Provider;
import com.voltor.services.ComingService;

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
public class ComingReportModel {

	@Autowired
	private ComingService comingService;

	private Coming editedValue;

	private TableView<Coming> table;
	private TextField date;
	private TextField providerName;
	private TextField providerFirm;
	private TextField user;
	private TextField sum;
	private ComingReportControler controler;
	
	private boolean isWriting;

	private Collection<Coming> masterData = FXCollections.observableArrayList();
	private ObservableList<Coming> filteredData = FXCollections.observableArrayList();

	public void init() {
		initViewComponents();
		initListenersComponent();
	}

	private void initListenersComponent() {
		addValueChangeListenerToTextField(date);
		addValueChangeListenerToTextField(providerName);
		addValueChangeListenerToTextField(providerFirm);
		addValueChangeListenerToTextField(user);
		addValueChangeListenerToTextField(sum);
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

        for (Coming p : masterData) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }
        updateTable();

	}

	private boolean matchesFilter(Coming p) {
		if( !Strings.isNullOrEmpty(providerName.getText()) ){
			if(!p.getProvider().getName().toLowerCase().contains(providerName.getText().toLowerCase())){
				return false;
			}
		}
		if( !Strings.isNullOrEmpty(providerFirm.getText()) && !Strings.isNullOrEmpty(p.getProvider().getFirmName())){
			if(!p.getProvider().getFirmName().toLowerCase().contains(providerFirm.getText().toLowerCase())){
				return false;
			}
		}
		if( !Strings.isNullOrEmpty( date.getText() )){
			if(!p.getDate().toString().toLowerCase().contains(date.getText().toLowerCase())){
				return false;
			}
		}
		if( !Strings.isNullOrEmpty(sum.getText()) ){
			if( !p.getSum().toString().contains(sum.getText()) ){
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
		TableColumn dateColl = new TableColumn("дата");
		dateColl.setMinWidth(168);
		dateColl.setCellValueFactory(new PropertyValueFactory<Coming, Coming>("date"));

		TableColumn providerNameColl = new TableColumn("ім'я");
		providerNameColl.setMinWidth(168);
		providerNameColl.setCellValueFactory(new PropertyValueFactory<Coming, Coming>("name"));

		TableColumn providerFirmNameColl = new TableColumn("фірма");
		providerFirmNameColl.setMinWidth(138);
		providerFirmNameColl.setCellValueFactory(new PropertyValueFactory<Coming, Coming>("firmName"));
		TableColumn userNameColl = new TableColumn("користувач");
		userNameColl.setMinWidth(138);
		userNameColl.setCellValueFactory(new PropertyValueFactory<Coming, Coming>("userName"));
		TableColumn sumColl = new TableColumn("сума");
		sumColl.setMinWidth(138);
		sumColl.setCellValueFactory(new PropertyValueFactory<Coming, Coming>("sum"));

		table.getColumns().addAll(dateColl, providerNameColl, providerFirmNameColl, userNameColl, sumColl);
		table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
				if (table.getSelectionModel().getSelectedItem() != null) {
					TableViewSelectionModel selectionModel = table.getSelectionModel();
					ObservableList selectedCells = selectionModel.getSelectedItems();
					editedValue = (Coming) selectedCells.get(0);
					writeFields();
					controler.updateComingPositionByComing(editedValue);
				}
			}
		});
	}

	public void updateTable() {
		table.setItems( filteredData );
		editedValue = new Coming();
	}

	void clearFields() {
		providerFirm.clear();
		providerName.clear();
		date.clear();
		sum.clear();
		user.clear();
		masterData.clear();
		updateFilteredData();
	}

	private void writeFields() {
		isWriting = true;
		sum.setText(editedValue.getSum().toString());
		user.setText(editedValue.getUserName());
		providerName.setText(editedValue.getName());
		providerFirm.setText(editedValue.getFirmName());
		date.setText(editedValue.getDate().toString());
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

	public void setTable(TableView<Coming> table) {
		this.table = table;
	}

	public void setProviderName(TextField providerName) {
		this.providerName = providerName;
	}

	public void setProviderFirm(TextField providerFirm) {
		this.providerFirm = providerFirm;
	}

	public void setUser(TextField user) {
		this.user = user;
	}

	public void setSum(TextField sum) {
		this.sum = sum;
	}

	public void setDate(TextField date) {
		this.date = date;
	}

	void updateByProviser( Provider provider ) {
		clearFields();
		masterData = comingService.getByProvider(provider);
		updateFilteredData();
	}

	public void setContoler(ComingReportControler comingReportControler) {
		this.controler = comingReportControler;
	}

}
