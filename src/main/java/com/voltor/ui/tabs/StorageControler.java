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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

@Component
public class StorageControler {

	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private SubCategoryService subCategoryService;

	@FXML
	private TableView<Product> table;
	@FXML
	private ComboBox<Category> category;
	@FXML
	private ComboBox<SubCategory> subCategory;
	@FXML
	private TextField count;
	@FXML
	private TextField name;
	@FXML
	private TextField shtrihCode;
	@FXML
	private TextField code;

	 private Collection<Product> masterData = null;
	 private ObservableList<Product> filteredData = FXCollections.observableArrayList();
	 
	public void initialize() {
		initViewComponents();
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
					subCategory
							.setItems(FXCollections.observableArrayList(subCategoryService.getAllByCategory(newValue)));
				}
			}
		});
		updateFilteredData();
		initListenersComponent();
	}
	
	private void initListenersComponent(){
		category.valueProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> observable,
            		Object oldValue, Object newValue) {
                updateFilteredData();
            }
		});
		subCategory.valueProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> observable,
            		Object oldValue, Object newValue) {
                updateFilteredData();
            }
		});
		name.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                updateFilteredData();
            }
        });
		shtrihCode.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                updateFilteredData();
            }
		});
		code.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                updateFilteredData();
            }
		});
		count.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                updateFilteredData();
            }
		});
	}

	private void updateFilteredData() {
        filteredData.clear();

        for (Product p : masterData) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }
        updateTable();
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
		if( !Strings.isNullOrEmpty(count.getText()) ){
			if(!p.getCount().toString().toLowerCase().contains(count.getText().toLowerCase())){
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
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void createTable() {
		TableColumn categoryColl = new TableColumn("Група");
		categoryColl.setMinWidth(159);
		categoryColl.setCellValueFactory(new PropertyValueFactory<Product, Product>("category"));

		TableColumn subCategoryColl = new TableColumn("Бренд");
		subCategoryColl.setMinWidth(159);
		subCategoryColl.setCellValueFactory(new PropertyValueFactory<Product, Product>("subCategory"));

		TableColumn nameColl = new TableColumn("Назва");
		nameColl.setMinWidth(159);
		nameColl.setCellValueFactory(new PropertyValueFactory<Product, Product>("name"));

		TableColumn countColl = new TableColumn("Кількість");
		countColl.setMinWidth(80);
		countColl.setCellValueFactory(new PropertyValueFactory<Product, Product>("count"));

		table.getColumns().addAll(categoryColl, subCategoryColl, nameColl, countColl);
		TableColumn codeColl = new TableColumn("Код товару");
		codeColl.setMinWidth(159);
		codeColl.setCellValueFactory(new PropertyValueFactory<Product, Product>("code"));
		TableColumn shtrihCodeColl = new TableColumn("Штрих код");
		shtrihCodeColl.setMinWidth(159);
		shtrihCodeColl.setCellValueFactory(new PropertyValueFactory<Product, Product>("shtrihCode"));
		table.getColumns().addAll(codeColl, shtrihCodeColl);
	}


	public void updateTable() {
		table.setItems(filteredData);
	}

	public void deleteRow(Product bean) {
		productService.delete(bean);
		updateTable();
	}

	public void saveValue() {
		clearFields();
			clearFields();
			updateTable();
		
	}

	private void clearFields() {
		category.setValue(null);
		subCategory.setValue(null);
		name.clear();
		count.clear();
		code.clear();
		shtrihCode.clear();
	}
}
