package com.voltor.ui.tabs.selling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.bean.Category;
import com.voltor.bean.Product;
import com.voltor.bean.Role;
import com.voltor.bean.Seller;
import com.voltor.bean.SellingPosition;
import com.voltor.bean.SubCategory;
import com.voltor.services.SecurityService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

@Component
public class SellingControler {

	@Autowired
	private ProductForSellingModel productModel;
	@Autowired
	private SellerModel sellerModel;
	@Autowired
	private SellingModel sellingModel;

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
	private TableView<Seller> sellerTable;
	@FXML
	private TextField sellerName;
	@FXML
	private TextField sellerEmail;
	@FXML
	private TextField sellerPhone;
	@FXML
	private TextField count;
	@FXML
	private TextField price;

	@FXML
	private TableView<SellingPosition> comingTable;
	@FXML
	private Label sum;
	@FXML
	private Label points;
	
	@FXML
	private TextField received;
	@FXML
	private Label remainder;
	
	@FXML
	private Button credit;
	@FXML
	private Button sell;

	public void initialize() {
		price.setEditable(false);
		if( Role.MANAGER.equals(SecurityService.getCurrentUser().getRole()) || 
				 Role.ADMIN.equals(SecurityService.getCurrentUser().getRole())){
			credit.setVisible(true);
			price.setEditable(true);
		}
		initProducts();
		initSellers();
		initSelling();
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

	private void initSellers() {
		sellerModel.setTable(sellerTable);
		sellerModel.setName(sellerName);
		sellerModel.setPhone(sellerPhone);
		sellerModel.setEmail(sellerEmail);
		sellerModel.init( this );
	}

	private void initSelling() {
		sellingModel.setTable(comingTable);
		sellingModel.setSum(sum);
		sellingModel.setReceived(received);
		sellingModel.setRemainder(remainder);
		sellingModel.setCredit(credit);
		sellingModel.setSell(sell);
		sellingModel.init();
	}

	public void add() {
		if (productModel.isValidateFields()) {
			SellingPosition comingPostition = productModel.getSellingPostition();
			sellingModel.addItem(comingPostition);
			productModel.clearFields();
		}
	}
	
	public void save() {
		if (sellerModel.isValidateFields()) {
			Seller seller = sellerModel.getEditedValue();
			if( sellingModel.save(seller) ){
				clean();
			}
		}
	}
	
	public void saveAsCredit() {
		if (sellerModel.isValidateFields()) {
			Seller seller = sellerModel.getEditedValue();
			if( sellingModel.saveAsCredit(seller) ){
				clean();
			}
		}
	}
	
	public void clean() {
		sellerModel.clearFields();
		productModel.clearFields();
		productModel.setSeller(null);
		sellingModel.clearFields();
	}
	
	public void setSeller( Seller seller) {
		productModel.setSeller(seller);
	}

}
