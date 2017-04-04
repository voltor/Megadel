package com.voltor.ui.tabs.coming;

import java.util.Collection;

import com.voltor.util.UIProductModelsUtils;
import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.bean.Category;
import com.voltor.bean.ComingPosition;
import com.voltor.bean.Product;
import com.voltor.bean.SubCategory;
import com.voltor.services.CategoryService;
import com.voltor.services.ComingPositionService;
import com.voltor.services.ProductService;
import com.voltor.services.SubCategoryService;
import com.voltor.util.UIComponentUtils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

@Component
public class ProductModel {

	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private SubCategoryService subCategoryService;
	@Autowired
	private ComingPositionService comingPositionService;
	@Autowired
	private UIComponentUtils componentUtils;

	private Product editedValue;

	private TableView<Product> table;
	private ComboBox<Category> category;
	private ComboBox<SubCategory> subCategory;
	private TextField name;
	private TextField shtrihCode;
	private TextField code;
	private TextField count;
	private TextField price;
	
	private boolean isWriting;
	
	 private Collection<Product> masterData = null;
	 private ObservableList<Product> filteredData = FXCollections.observableArrayList();

	public void init() {
		masterData = productService.getAll() ;
		editedValue = new Product();
		initViewComponents();
		initListenersComponent();
	}

	private void initViewComponents() {
		createTable();
		ObservableList<Category> categoryData = FXCollections.observableArrayList(categoryService.getAll());
		category.setItems(categoryData);
		category.valueProperty().addListener(new ChangeListener<Category>() {
			@SuppressWarnings("rawtypes")
			@Override
			public void changed(ObservableValue observableValue, Category oldValue, Category newValue) {
				subCategory.getItems().clear();
				ObservableList<SubCategory> subCategoryData = null;
				if (newValue != null) {
					subCategoryData = FXCollections.observableArrayList(subCategoryService.getAllByCategory(newValue));
					subCategory.setItems(subCategoryData);
				} 
			}
		});
		updateFilteredData();
	}
	
	private void initListenersComponent(){
		componentUtils.addValueChangeListenerToComboBox(category, this::updateFilteredData);
		componentUtils.addValueChangeListenerToComboBox(subCategory, this::updateFilteredData);
		componentUtils.addValueChangeListenerToTextField(name, this::updateFilteredData);
		componentUtils.addValueChangeListenerToTextField(shtrihCode, this::updateFilteredData);
		componentUtils.addValueChangeListenerToTextField(code, this::updateFilteredData);
	}

	boolean isValidateFields() {
		if (editedValue == null || editedValue.getId()==0L) {
			componentUtils.showMessage("Будь-ласка, виберіть товар!");
			return false;
		}
		if (!Strings.isNullOrEmpty(price.getText())) {
			try {
				Double.parseDouble(price.getText().trim());
			} catch (NumberFormatException e) {
				componentUtils.showMessage("Ви неправельно ввели ціну! Приклад 1253.25");
				return false;
			}
		} else {
			componentUtils.showMessage("Ви не ввели ціну!");
			return false;
		}

		if (!Strings.isNullOrEmpty(count.getText())) {
			try {
				Integer.valueOf(count.getText().trim());
			} catch (NumberFormatException e) {
				componentUtils.showMessage("Ви неправельно ввели кількість! Приклад 55");
				return false;
			}
		} else {
			componentUtils.showMessage("Ви не ввели кількість!");
			return false;
		}
		return true;
	}

	private void updateFilteredData() {
		if(isWriting){
			return;
		}
        filteredData.clear();

        for (Product p : masterData) {
            if (UIProductModelsUtils.matchesFilter(p,table,category,subCategory,name,shtrihCode,code)) {
                filteredData.add(p);
            }
        }
        updateTable();
    }

	private void createTable() {
		componentUtils.addTableColumn(table, "група", 172.0, Product.class, "category");
		componentUtils.addTableColumn(table, "бренд", 172.0, Product.class, "subCategory");
		componentUtils.addTableColumn(table, "назва", 172.0, Product.class, "name");
		componentUtils.addTableColumn(table, "код товару", 172.0, Product.class, "code");
		componentUtils.addTableColumn(table, "штрих код", 172.0, Product.class, "shtrihCode");
		componentUtils.addTableColumn(table, "ціна", 100.0, Product.class, "priceByType");
		componentUtils.addSelectionEventToTable(table, Product.class, e -> {
			editedValue = e;
			writeFields();
		});
	}

	public void updateTable() {
		table.setItems( filteredData );
		editedValue = new Product();
	}

	void clearFields() {
		category.setValue(null);
		subCategory.setValue(null);
		name.clear();
		code.clear();
		shtrihCode.clear();
		price.clear();
		count.clear();
		editedValue = new Product();
	}

	private void writeFields() {
		isWriting=true;
		category.getSelectionModel().select(editedValue.getCategory());
		subCategory.getSelectionModel().select(editedValue.getSubCategory());
		shtrihCode.setText(editedValue.getShtrihCode());
		name.setText(editedValue.getName());
		code.setText(editedValue.getCode());
		Double lastPrice = comingPositionService.getLastPriceByProductId(editedValue);
		if( lastPrice != null ){
			price.setText( lastPrice.toString() );
		} else {
			price.clear();
		}
		isWriting=false;
	}

	void setTable(TableView<Product> table) {
		this.table = table;
	}

	void setSubCategory(ComboBox<SubCategory> subCategory) {
		this.subCategory = subCategory;
	}
	void setName(TextField name) {
		this.name = name;
	}

	void setShtrihCode(TextField shtrihCode) {
		this.shtrihCode = shtrihCode;
	}
	void setCode(TextField code) {
		this.code = code;
	}
	void setCategory(ComboBox<Category> category) {
		this.category = category;
	}

	public void setCount(TextField count) {
		this.count = count;
	}

	public void setPrice(TextField price) {
		this.price = price;
	}
	
	public ComingPosition getComingPostition(){
		ComingPosition comingPostition = new ComingPosition();
		comingPostition.setProduct( editedValue );
		comingPostition.setPrice( Double.valueOf( price.getText() ));
		comingPostition.setCount( Integer.valueOf( count.getText() ));
		comingPostition.calculateSum();
		return comingPostition;
	}
}
