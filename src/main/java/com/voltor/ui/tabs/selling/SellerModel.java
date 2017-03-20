package com.voltor.ui.tabs.selling;

import java.util.Collection;

import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.bean.Seller;
import com.voltor.services.SellerService;
import com.voltor.util.UIComponentUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

@Component
public class SellerModel {

	@Autowired
	private SellerService sellerService;
	@Autowired
	private UIComponentUtils componentUtils;
	
	private SellingControler controler;

	private Seller editedValue;

	private TableView<Seller> table;
	private TextField name;
	private TextField email;
	private TextField phone;
	
	private boolean isWriting;

	private Collection<Seller> masterData = null;
	private ObservableList<Seller> filteredData = FXCollections.observableArrayList();

	public void init( SellingControler controler) {
		this.controler = controler;
		masterData = sellerService.getAll();
		editedValue = new Seller();
		initViewComponents();
		initListenersComponent();
	}

	private void initListenersComponent() {
		componentUtils.addValueChangeListenerToTextField(name, this::updateFilteredData);
		componentUtils.addValueChangeListenerToTextField(email, this::updateFilteredData);
		componentUtils.addValueChangeListenerToTextField(phone, this::updateFilteredData);
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
		if( !Strings.isNullOrEmpty(phone.getText()) ){
			if( Strings.isNullOrEmpty(p.getPhone()) || !p.getPhone().toLowerCase().contains(phone.getText().toLowerCase())){
				return false;
			}
		}
		if( !Strings.isNullOrEmpty(email.getText()) ){
			if( Strings.isNullOrEmpty(p.getEmail()) || !p.getEmail().toLowerCase().contains(email.getText().toLowerCase())){
				return false;
			}
		}
		return true;
	}

	private void initViewComponents() {
		createTable();
		updateFilteredData();
	}

	private void createTable() {
		componentUtils.addTableColumn(table, "ім'я", 168.0, Seller.class, "name");
		componentUtils.addTableColumn(table, "email", 138.0, Seller.class, "email");
		componentUtils.addTableColumn(table, "телефон", 138.0, Seller.class, "phone");
		componentUtils.addSelectionEventToTable(table, Seller.class, e -> {
			editedValue = e;
			writeFields();
			controler.setSeller(editedValue);
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
			componentUtils.showMessage("Будь-ласка, виберіть покупця!");
			return false;
		}
		return true;
	}

	Seller getEditedValue() {
		return editedValue;
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
}
