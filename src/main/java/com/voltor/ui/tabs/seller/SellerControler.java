package com.voltor.ui.tabs.seller;

import java.util.Collection;

import com.voltor.bean.Selling;
import com.voltor.util.UISellerModelUtils;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.bean.PriceType;
import com.voltor.bean.Seller;
import com.voltor.entity.TickEntity;
import com.voltor.entity.TickHistoryEntity;
import com.voltor.services.SellerService;
import com.voltor.util.UIComponentUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

@Component
public class SellerControler {

	@Autowired
	private SellerService sellerService;
	@Autowired
	private SellerTickHistoryModel historyModel;
	@Autowired
	private UIComponentUtils componentUrils;

	private Seller editedValue;

	@FXML
	private TableView<Seller> table;
	@FXML
	private TextField name;
	@FXML
	private TextField email;
	@FXML
	private TextField phone;

	@FXML
	private TableView<TickHistoryEntity> tickHistoryTable;
	@FXML
	private TextField tickHistoryDate;
	@FXML
	private TextField tickHistoryValue;
	@FXML
	private TextField tickHistoryType;
	@FXML
	private TextField tickHistoryNewValue;
	@FXML
	private ComboBox<PriceType> priceType;
	
	private boolean isWriting;

	private Collection<Seller> masterData = null;
	private ObservableList<Seller> filteredData = FXCollections.observableArrayList();

	public void initialize() {
		masterData = sellerService.getAll();
		initViewComponents();
		initHistoryModel();
	}

	private void initHistoryModel() {
		historyModel.setTable(tickHistoryTable);
		historyModel.setDate(tickHistoryDate);
		historyModel.setValue(tickHistoryValue);
		historyModel.setAddValue(tickHistoryNewValue);
		historyModel.setType(tickHistoryType);
		historyModel.init();
	}
	private void initViewComponents() {
		initListenersComponent();
		createTable();
		updateFilteredData();
		priceType.setItems(FXCollections.observableArrayList(PriceType.values()));
	}
	
	private void initListenersComponent() {
		componentUrils.addValueChangeListenerToComboBox(priceType, this::updateFilteredData );
		componentUrils.addValueChangeListenerToTextField(name, this::updateFilteredData );
		componentUrils.addValueChangeListenerToTextField(email, this::updateFilteredData );
		componentUrils.addValueChangeListenerToTextField(phone, this::updateFilteredData );
	}
	
	private void updateFilteredData() {
		if(isWriting){
			return;
		}
        filteredData.clear();

        for (Seller p : masterData) {
            if (UISellerModelUtils.matchesFilter(p,name,email,phone)) {
                filteredData.add(p);
            }
        }
        updateTable();

	}

	private void createTable() {
		componentUrils.addTableColumn(table, "ім'я", 168.0, Seller.class, "name");
		componentUrils.addTableColumn(table, "email", 138.0, Seller.class, "email");
		componentUrils.addTableColumn(table, "телефон", 138.0, Seller.class, "phone");
		componentUrils.addTableColumn(table, "nип ціни", 110.0, Seller.class, "priceType");
		componentUrils.addTableColumn(table, "рахунок", 80.0, Seller.class, "tickUA");
		componentUrils.addSelectionEventToTable(table, Seller.class, e -> {
			editedValue = e;
			writeFields();
		});
	}

	public void updateTable() {
		table.setItems( filteredData );
	}

	public void saveValue() {
		if (isValidateFields()) {
			if (editedValue == null || editedValue.getId() == 0L) {
				editedValue = new Seller();
			}
			editedValue.setPriceType(priceType.getValue());
			editedValue.setName(name.getText());
			editedValue.setEmail(email.getText());
			editedValue.setPhone(phone.getText());
			sellerService.save(editedValue);
			editedValue = new Seller();
			clearFields();
			masterData = sellerService.getAll();
			updateFilteredData();
		}
	}

	private void clearFields() {
		name.clear();
		email.clear();
		phone.clear();
	}

	private void writeFields() {
		isWriting = true;
		priceType.getSelectionModel().select(editedValue.getPriceType());
		name.setText(editedValue.getName());
		email.setText(editedValue.getEmail());
		phone.setText(editedValue.getPhone());
		updateTickHistory( editedValue.getTick() );
		isWriting = false;
	}

	private boolean isValidateFields() {
		if (priceType.getValue() == null) {
			componentUrils.showMessage("Будь-ласка, тип ціни!");
			return false;
		}
		if (Strings.isNullOrEmpty(name.getText())) {
			componentUrils.showMessage("Будь-ласка, введіть назву!");
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
 
	private void updateTickHistory(TickEntity tick) {
		historyModel.updateByTick(tick);
	}
	
	public void refillAccount(){
		if (editedValue == null || editedValue.getId() == 0L) {
			return;
		}
		historyModel.refillAccount();
		masterData = sellerService.getAll();
		updateFilteredData();
	}
}
