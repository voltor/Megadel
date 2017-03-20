package com.voltor.ui.tabs.cash.manager;

import java.util.Collection;

import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.entity.TransferCashEntity;
import com.voltor.services.TransferCashService;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

@Component
public class ManagerTransferCashModel {

	@Autowired
	private TransferCashService cashService;

	private TransferCashEntity editedValue;

	private TableView<TransferCashEntity> table;
	private TextField date;
	private TextField autor;
	private TextField sum;
	private ManagerCashControler controler;
	
	private boolean isWriting;

	private Collection<TransferCashEntity> masterData = FXCollections.observableArrayList();
	private ObservableList<TransferCashEntity> filteredData = FXCollections.observableArrayList();

	public void init() {
		initViewComponents();
		initListenersComponent();
	}

	private void initListenersComponent() {
		addValueChangeListenerToTextField(date);
		addValueChangeListenerToTextField(autor);
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
		masterData = cashService.getForManagerCashes();
        for (TransferCashEntity p : masterData) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }
        updateTable();

	}

	private boolean matchesFilter(TransferCashEntity p) {
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
		dateColl.setCellValueFactory(new PropertyValueFactory<TransferCashEntity, TransferCashEntity>("date"));

		TableColumn providerNameColl = new TableColumn("ім'я");
		providerNameColl.setMinWidth(168);
		providerNameColl.setCellValueFactory(new PropertyValueFactory<TransferCashEntity, TransferCashEntity>("name"));
		TableColumn sumColl = new TableColumn("сума");
		sumColl.setMinWidth(138);
		sumColl.setCellValueFactory(new PropertyValueFactory<TransferCashEntity, TransferCashEntity>("sum"));

		TableColumn confirm = new TableColumn<>("");
		confirm.setMinWidth(30);
		confirm.setCellValueFactory(new PropertyValueFactory<TransferCashEntity, TransferCashEntity>("this"));

		confirm.setCellFactory(col -> {
			Button editButton = new Button("Підтвердити");
			TableCell<TransferCashEntity, TransferCashEntity> cell = new TableCell<TransferCashEntity, TransferCashEntity>() {
				@Override
				public void updateItem(TransferCashEntity bean, boolean empty) {
					super.updateItem(bean, empty);
					if (empty) {
						setGraphic(null);
					} else {
						setGraphic(editButton);
					}
				}
			};

			editButton.setOnAction(e -> confirm(cell.getItem()));

			return cell;
		});
		
		
		table.getColumns().addAll(dateColl, providerNameColl, sumColl, confirm);
		table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
				if (table.getSelectionModel().getSelectedItem() != null) {
					TableViewSelectionModel selectionModel = table.getSelectionModel();
					ObservableList selectedCells = selectionModel.getSelectedItems();
					editedValue = (TransferCashEntity) selectedCells.get(0);
					writeFields();
					controler.updateSellingByCash(editedValue);
				}
			}
		});
	} 

	private void confirm(TransferCashEntity item) {
		cashService.cofirm(item);
		controler.clean();
	}

	public void updateTable() {
		table.setItems( filteredData );
	}

	void clearFields() { 
		date.clear();
		sum.clear();
		autor.clear();
		masterData.clear();
		updateFilteredData();
	}

	private void writeFields() {
		isWriting = true;
		sum.setText(editedValue.getSum().toString()); 
		autor.setText(editedValue.getAutor().getFirstName());
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

	public void setTable(TableView<TransferCashEntity> table) {
		this.table = table;
	}


	public void setAutor(TextField user) {
		this.autor = user;
	}

	public void setSum(TextField sum) {
		this.sum = sum;
	}

	public void setDate(TextField date) {
		this.date = date;
	} 
	
	public void setContoler(ManagerCashControler comingReportControler) {
		this.controler = comingReportControler;
	}

}
