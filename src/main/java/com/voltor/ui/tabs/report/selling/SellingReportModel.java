package com.voltor.ui.tabs.report.selling;

import java.util.Collection;

import com.voltor.util.UISellingReportModelUtils;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.bean.Coming;
import com.voltor.bean.Seller;
import com.voltor.bean.Selling;
import com.voltor.services.SellingService;

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
public class SellingReportModel {

	@Autowired
	private SellingService sellingService;

	private Selling editedValue;

	private TableView<Selling> table;
	private TextField date;
	private TextField name;
	private TextField user;
	private TextField sum;
	private SellingReportControler controler;
	
	private boolean isWriting;

	private Collection<Selling> masterData = FXCollections.observableArrayList();
	private ObservableList<Selling> filteredData = FXCollections.observableArrayList();

	public void init() {
		initViewComponents();
		initListenersComponent();
	}

	private void initListenersComponent() {
		addValueChangeListenerToTextField(date);
		addValueChangeListenerToTextField(name);
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

        for (Selling p : masterData) {
            if (UISellingReportModelUtils.matchesFilter(p,date,name,sum)) {
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
	private void createTable() {UISellingReportModelUtils.createTable(table);
		table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
				if (table.getSelectionModel().getSelectedItem() != null) {
					TableViewSelectionModel selectionModel = table.getSelectionModel();
					ObservableList selectedCells = selectionModel.getSelectedItems();
					editedValue = (Selling) selectedCells.get(0);
					writeFields();
					controler.updateSellingPositionByComing(editedValue);
				}
			}
		});
	}

	public void updateTable() {
		table.setItems( filteredData );
		editedValue = new Selling();
	}

	void clearFields() {
		name.clear();
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
		name.setText(editedValue.getName());
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

	public void setTable(TableView<Selling> table) {
		this.table = table;
	}

	public void setName(TextField name) {
		this.name = name;
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

	void updateBySeller( Seller seller ) {
		clearFields();
		masterData = sellingService.getBySeller(seller);
		updateFilteredData();
	}

	public void setContoler(SellingReportControler comingReportControler) {
		this.controler = comingReportControler;
	}

}
