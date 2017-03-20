package com.voltor.ui.tabs;

import java.util.Collection;

import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.bean.Category;
import com.voltor.bean.Product;
import com.voltor.bean.SubCategory;
import com.voltor.services.CategoryService;
import com.voltor.services.ProductService;
import com.voltor.services.SubCategoryService;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;

@Component
public class ProductsControler {

	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private SubCategoryService subCategoryService;

	private Product editedValue;
	
	@FXML
	private TableView<Product> table;
	@FXML
	private ComboBox<Category> category;
	@FXML
	private ComboBox<SubCategory> subCategory;
	@FXML
	private TextField name;
	@FXML
	private TextField shtrihCode;
	@FXML
	private TextField code;
	@FXML
	private TextField price;
	@FXML
	private TextField priceOpt;
	@FXML
	private TextField price1;
	@FXML
	private TextField priceOpt1;
	@FXML
	private TextField commingPrice;

	 private Collection<Product> masterData = null;
	 private ObservableList<Product> filteredData = FXCollections.observableArrayList();
	 
	 private boolean isWriting;
	 
	public void initialize() {
		initViewComponents();
		editedValue = new Product();
	}

	private void initViewComponents() {
		masterData = productService.getAll();
		createTable();
		category.setItems(FXCollections.observableArrayList(categoryService.getAll()));
		category.valueProperty().addListener(new ChangeListener<Category>() {
			@SuppressWarnings("rawtypes")
			@Override
			public void changed(ObservableValue observableValue, Category oldValue, Category newValue) {
				subCategory.getItems().clear();
				if (newValue != null) {
					subCategory.setItems(FXCollections.observableArrayList(subCategoryService.getAllByCategory(newValue)));
				}
			}
		});
		updateFilteredData();
		initListenersComponent();
	}
	
	private void initListenersComponent(){
		addListenerToComboBox(category);
		addListenerToComboBox(subCategory);
		addListenerToTextField(name);
		addListenerToTextField(shtrihCode);
		addListenerToTextField(code);
		addListenerToTextField(commingPrice);
	}
	private void addListenerToTextField( TextField field ){
		field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                updateFilteredData();
            }
		});
	}
	private void addListenerToComboBox( ComboBox<? extends Object> field ){
		field.valueProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> observable,
            		Object oldValue, Object newValue) {
                updateFilteredData();
            }
		});
	}
	
	private void updateFilteredData() {
		if(isWriting){
			return;
		}
        filteredData.clear();

        for (Product p : masterData) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }
    	table.setItems(filteredData);
    }
	private boolean matchesFilter(Product p) {
		if(category.getValue()!=null){
			if(!p.getCategory().equals(category.getValue())){
				return false;
			}
		}
		if(subCategory.getValue()!=null){
			if(!p.getSubCategory().equals(subCategory.getValue())){
				return false;
			}
		}
		if( !Strings.isNullOrEmpty(name.getText()) ){
			if(!p.getName().toLowerCase().contains(name.getText().toLowerCase())){
				return false;
			}
		}
		if( !Strings.isNullOrEmpty(code.getText())){
			if( Strings.isNullOrEmpty(p.getCode()) || !p.getCode().toLowerCase().contains(code.getText().toLowerCase())){
				return false;
			}
		}
		if( !Strings.isNullOrEmpty(shtrihCode.getText()) ){
			if( Strings.isNullOrEmpty(p.getShtrihCode()) || !p.getShtrihCode().toLowerCase().contains(shtrihCode.getText().toLowerCase())){
				return false;
			}
		}
		if( !Strings.isNullOrEmpty(commingPrice.getText()) ){
			if( p.getComingPrice()==null || !p.getComingPrice().toString().toLowerCase().contains(commingPrice.getText().toLowerCase())){
				return false;
			}
		}
		return true;
	}
	
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void createTable() {
		TableColumn categoryColl = new TableColumn("Група");
		categoryColl.setMinWidth(138);
		categoryColl.setCellValueFactory(new PropertyValueFactory<Product, Product>("category"));

		TableColumn subCategoryColl = new TableColumn("Бренд");
		subCategoryColl.setMinWidth(138);
		subCategoryColl.setCellValueFactory(new PropertyValueFactory<Product, Product>("subCategory"));

		TableColumn nameColl = new TableColumn("Назва");
		nameColl.setMinWidth(138);
		nameColl.setCellValueFactory(new PropertyValueFactory<Product, Product>("name"));
		TableColumn priceColl = new TableColumn("Ціна1");
		priceColl.setMinWidth(70);
		priceColl.setCellValueFactory(new PropertyValueFactory<Product, Product>("price"));
		TableColumn price1Coll = new TableColumn("Ціна2");
		price1Coll.setMinWidth(70);
		TableColumn priceOptColl = new TableColumn("Опт. ціна1");
		priceOptColl.setCellValueFactory(new PropertyValueFactory<Product, Product>("priceOpt"));
		priceOptColl.setMinWidth(80);
		TableColumn priceOpt1Coll = new TableColumn("Опт. ціна2");
		priceOpt1Coll.setCellValueFactory(new PropertyValueFactory<Product, Product>("priceOpt1"));
		priceOpt1Coll.setMinWidth(80);
		price1Coll.setCellValueFactory(new PropertyValueFactory<Product, Product>("price1"));
		table.getColumns().addAll(categoryColl, subCategoryColl, nameColl);
		table.getColumns().addAll(priceColl, price1Coll,priceOptColl, priceOpt1Coll);
		TableColumn codeColl = new TableColumn("Код товару");
		codeColl.setMinWidth(123);
		codeColl.setCellValueFactory(new PropertyValueFactory<Product, Product>("code"));
		TableColumn shtrihCodeColl = new TableColumn("Штрих код");
		shtrihCodeColl.setMinWidth(123);
		shtrihCodeColl.setCellValueFactory(new PropertyValueFactory<Product, Product>("shtrihCode"));
		
		TableColumn comingPriceColl = new TableColumn("Прих. ціна");
		comingPriceColl.setMinWidth(97);
		comingPriceColl.setCellValueFactory(new PropertyValueFactory<Product, Product>("comingPrice"));
		TableColumn idColl = new TableColumn("");
		idColl.setMinWidth(50);
		idColl.setCellValueFactory(new PropertyValueFactory<Product, Product>("id"));
		table.getColumns().addAll(codeColl, shtrihCodeColl,comingPriceColl,idColl);
		
		table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
				if (table.getSelectionModel().getSelectedItem() != null) {
					TableViewSelectionModel selectionModel = table.getSelectionModel();
					ObservableList selectedCells = selectionModel.getSelectedItems();
					editedValue = (Product) selectedCells.get(0);
					writeFields();
				}
			}
		});
		
		
	}


	public void updateTable() {
		masterData = productService.getAll();
		updateFilteredData();
	
	}

	private void writeFields() {
		isWriting=true;
		category.getSelectionModel().select(editedValue.getCategory());
		subCategory.getSelectionModel().select(editedValue.getSubCategory());
		if (editedValue.getPrice() != null) {
			price.setText(editedValue.getPrice().toString());
		}
		if (editedValue.getPriceOpt() != null) {
			priceOpt.setText(editedValue.getPriceOpt().toString());
		}
		if (editedValue.getPrice1() != null) {
			price1.setText(editedValue.getPrice1().toString());
		}
		if (editedValue.getPriceOpt1() != null) {
			priceOpt1.setText(editedValue.getPriceOpt1().toString());
		}
		if (editedValue.getCode() != null) {
			code.setText(editedValue.getCode());
		}
		if (editedValue.getShtrihCode() != null) {
			shtrihCode.setText(editedValue.getShtrihCode());
		}
		name.setText(editedValue.getName());
		isWriting=false;
	}
	public void saveValue() {
		if (editedValue == null || editedValue.getId() == 0L) {
			editedValue = new Product();
		}
		if (isValidateFields()) {
			editedValue.setSubCategory(subCategory.getValue());
			editedValue.setName(name.getText());
			if (!Strings.isNullOrEmpty(price.getText())) {
				editedValue.setPrice(Double.parseDouble(price.getText().trim()));
			}
			if (!Strings.isNullOrEmpty(priceOpt.getText())) {
				editedValue.setPriceOpt(Double.parseDouble(priceOpt.getText().trim()));
			}
			if (!Strings.isNullOrEmpty(price1.getText())) {
				editedValue.setPrice1(Double.parseDouble(price1.getText().trim()));
			}
			if (!Strings.isNullOrEmpty(priceOpt1.getText())) {
				editedValue.setPriceOpt1(Double.parseDouble(priceOpt1.getText().trim()));
			}
			if( !Strings.isNullOrEmpty(shtrihCode.getText().trim()) ){
				editedValue.setShtrihCode(shtrihCode.getText().trim());
			} else {
				editedValue.setShtrihCode( null );
			}
			if( !Strings.isNullOrEmpty(code.getText().trim()) ){
				editedValue.setCode(code.getText().trim());
			} else {
				editedValue.setCode( null );
			}
			productService.save(editedValue);
			editedValue = null;
			clearFields();
			updateTable();
		}
	}
	
	private boolean isValidateFields() {
		if (category.getValue() == null) {
			showMessage("Будь-ласка, виберіть групу!");
			return false;
		}
		if (subCategory.getValue() == null) {
			showMessage("Будь-ласка, виберіть бренд!");
			return false;
		}
		
		if (!Strings.isNullOrEmpty(price.getText())) {
			try {
				Double.parseDouble(price.getText().trim());
			} catch (NumberFormatException e) {
				showMessage("Ви неправельно ввели ціну1! Приклад 1253.25");
				return false;
			}
		}
		if (!Strings.isNullOrEmpty(priceOpt.getText())) {
			try {
				Double.parseDouble(priceOpt.getText().trim());
			} catch (NumberFormatException e) {
				showMessage("Ви неправельно ввели оптову ціну1! Приклад 1253.25");
				return false;
			}
		}
		if (!Strings.isNullOrEmpty(price1.getText())) {
			try {
				Double.parseDouble(price1.getText().trim());
			} catch (NumberFormatException e) {
				showMessage("Ви неправельно ввели ціну2! Приклад 1253.25");
				return false;
			}
		}
		if (!Strings.isNullOrEmpty(priceOpt1.getText())) {
			try {
				Double.parseDouble(priceOpt1.getText().trim());
			} catch (NumberFormatException e) {
				showMessage("Ви неправельно ввели оптову ціну2! Приклад 1253.25");
				return false;
			}
		}
		if (Strings.isNullOrEmpty(name.getText())) {
			showMessage("Будь-ласка, введіть назву!");
			return false;
		}
		if (!Strings.isNullOrEmpty(name.getText().trim())) {
			if ( productService.existName(name.getText().trim(), editedValue.getId())) {
				showMessage("Дана назва вже використовується. Будь-ласка, введіть іншу назву!");
				return false;
			}
		}
		if (!Strings.isNullOrEmpty(code.getText().trim())) {
			if ( productService.existCode(code.getText().trim(), editedValue.getId())) {
				showMessage("Даний код вже використовується. Будь-ласка, введіть інший код!");
				return false;
			}
		}
		if (!Strings.isNullOrEmpty(shtrihCode.getText().trim())) {
			if ( productService.existShtrihCode( shtrihCode.getText().trim(), editedValue.getId())) {
				showMessage("Даний штрих код вже використовується. Будь-ласка, введіть інший штрих код!");
				return false; 
			}
		}
		return true;
	}

	public void clearFields() {
		category.setValue(null);
		subCategory.setValue(null);
		name.clear();
		code.clear();
		shtrihCode.clear();
		price.clear();
		priceOpt.clear();
		price1.clear();
		priceOpt1.clear();
		updateFilteredData();
	}
	
	private void showMessage(String contentText) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Увага!");
		alert.setHeaderText(null);
		alert.setContentText(contentText);
		alert.showAndWait();
	}
}
