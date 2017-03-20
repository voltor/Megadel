package com.voltor.ui.tabs;

import java.util.Collection;

import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.bean.Category;
import com.voltor.bean.SubCategory;
import com.voltor.services.CategoryService;
import com.voltor.services.SubCategoryService;
import com.voltor.util.UIComponentUtils;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

@Component
public class CategoryControler {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private SubCategoryService subCategoryService;
	@Autowired
	private UIComponentUtils componentUrils;

	@FXML
	private TableView<Category> tableCategories;

	@FXML
	private TableView<SubCategory> tableSubCategories;

	@FXML
	private TextField nameCategory;

	@FXML
	private TextField codeCategory;

	@FXML
	private ComboBox<Category> categories;

	@FXML
	private TextField nameSubCategory;

	@FXML
	private TextField codeSubCategory;

	private Category editedCategoryValue;
	private SubCategory editedSubCategoryValue;

	public void initialize() {
		initViewComponents();
	}

	private void initViewComponents() {
		createCategoryTable();
		createSubCategoryTable();
		updateCategoryTable();
		updateSubCategoryTable();
	}

	private void createCategoryTable() {
		componentUrils.addTableColumn(tableCategories, "назва", 148.0, Category.class, "name");
		componentUrils.addTableColumn(tableCategories, "rод", 149.0, Category.class, "code");
		componentUrils.addSelectionEventToTable(tableCategories, Category.class, e -> {
			editedCategoryValue = e;
			writeCategoryFields();
		});
	}

	private void createSubCategoryTable() {
		componentUrils.addTableColumn(tableSubCategories, "група", 150.0, SubCategory.class, "categoryName");
		componentUrils.addTableColumn(tableSubCategories, "назва", 148.0, SubCategory.class, "name");
		componentUrils.addTableColumn(tableSubCategories, "код", 148.0, SubCategory.class, "code");
		componentUrils.addSelectionEventToTable(tableSubCategories, SubCategory.class, e -> {
			editedSubCategoryValue = e;
			writeSubCategoryFields();
		});
	}

	public void updateCategoryTable() {
		Collection<Category> all = categoryService.getAll();
		tableCategories.setItems(FXCollections.observableArrayList(all));
		categories.setItems(FXCollections.observableArrayList(all));
	}

	public void updateSubCategoryTable() {
		tableSubCategories.setItems(FXCollections.observableArrayList(subCategoryService.getAll()));
	}

	public void saveCategoryValue() {
		if (isValidateCategoryFields()) {
			if (editedCategoryValue == null || editedCategoryValue.getId() == 0L) {
				editedCategoryValue = new Category();
			}
			editedCategoryValue.setName(nameCategory.getText().trim());
			editedCategoryValue.setCode(codeCategory.getText().trim());
			categoryService.save(editedCategoryValue);
			cleanCategoryFields();
			updateCategoryTable();
			editedCategoryValue = new Category();
		}
	}

	public void saveSubCategoryValue() {
		if (isValidateSubCategoryFields()) {
			if (editedSubCategoryValue == null || editedSubCategoryValue.getId() == 0L) {
				editedSubCategoryValue = new SubCategory();
			}
			editedSubCategoryValue.setName(nameSubCategory.getText().trim());
			editedSubCategoryValue.setCode(codeSubCategory.getText().trim());
			editedSubCategoryValue.setCategory(categories.getValue());
			subCategoryService.save(editedSubCategoryValue);
			cleanSubCategoryFields();
			updateSubCategoryTable();
			editedSubCategoryValue = new SubCategory();
		}
	}

	private void cleanSubCategoryFields() {
		categories.getSelectionModel().select(null);
		nameSubCategory.clear();
		codeSubCategory.clear();
	}

	private void cleanCategoryFields() {
		nameCategory.clear();
		codeCategory.clear();
	}

	private void writeCategoryFields() {
		nameCategory.setText(editedCategoryValue.getName());
		codeCategory.setText(editedCategoryValue.getCode());
	}

	private void writeSubCategoryFields() {
		nameSubCategory.setText(editedSubCategoryValue.getName());
		codeSubCategory.setText(editedSubCategoryValue.getCode());
		categories.getSelectionModel().select(editedSubCategoryValue.getCategory());
	}

	private boolean isValidateCategoryFields() {
		if (Strings.isNullOrEmpty(nameCategory.getText())) {
			componentUrils.showMessage("Будь-ласка, введіть назву групи!");
			return false;
		}
		return true;
	}

	private boolean isValidateSubCategoryFields() {
		if (categories.getValue() == null) {
			componentUrils.showMessage("Будь-ласка, виберіть групу!");
			return false;
		}
		if ( Strings.isNullOrEmpty( nameSubCategory.getText() ) ) {
			componentUrils.showMessage("Будь-ласка, введіть назву бренду!");
			return false;
		}
		return true;
	}
}
