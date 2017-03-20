package com.voltor.ui.tabs.coming;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.bean.Category;
import com.voltor.bean.ComingPosition;
import com.voltor.bean.Product;
import com.voltor.bean.Provider;
import com.voltor.bean.SubCategory;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

@Component
public class ComingControler {

	@Autowired
	private ProductModel productModel;
	@Autowired
	private ProviderModel providerModel;
	@Autowired
	private ComingModel comingModel;

	@FXML
	private TableView<Product> productTable;
	@FXML
	private ComboBox<Category> productCategory;
	@FXML
	private ComboBox<SubCategory> productSubCategory;
	@FXML
	private TextField productName;
	@FXML
	private TextField productShtrihCode;
	@FXML
	private TextField productCode;

	@FXML
	private TableView<Provider> providerTable;
	@FXML
	private TextField providerName;
	@FXML
	private TextField providerFirmName;
	@FXML
	private TextField providerEmail;
	@FXML
	private TextField providerPhone;
	@FXML
	private TextField count;
	@FXML
	private TextField price;

	@FXML
	private TableView<ComingPosition> comingTable;
	@FXML
	private Label sum;
	@FXML
	private Label points;

	public void initialize() {
		initProducts();
		initProviders();
		initComing();
	}

	private void initProducts() {
		productModel.setTable(productTable);
		productModel.setName(productName);
		productModel.setShtrihCode(productShtrihCode);
		productModel.setCode(productCode);
		productModel.setSubCategory(productSubCategory);
		productModel.setCategory(productCategory);
		productModel.setCount(count);
		productModel.setPrice(price);
		productModel.init();
	}

	private void initProviders() {
		providerModel.setTable(providerTable);
		providerModel.setName(providerName);
		providerModel.setFirmName(providerFirmName);
		providerModel.setPhone(providerPhone);
		providerModel.setEmail(providerEmail);
		providerModel.init();
	}

	private void initComing() {
		comingModel.setTable(comingTable);
		comingModel.setSum(sum);
		comingModel.setPoints(points);
		comingModel.init();
	}

	public void add() {
		if (productModel.isValidateFields()) {
			ComingPosition comingPostition = productModel.getComingPostition();
			comingModel.addItem(comingPostition);
			productModel.clearFields();
		}
	}
	
	public void save() {
		if (providerModel.isValidateFields()) {
			Provider provider = providerModel.getEditedValue();
			comingModel.save(provider);
			clean();
		}
	}
	
	public void clean() {
		providerModel.clearFields();
		productModel.clearFields();
		comingModel.clearFields();
	}

}
