package com.voltor.ui.tabs;

import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.bean.Role;
import com.voltor.bean.User;
import com.voltor.services.UserService;
import com.voltor.util.UIComponentUtils;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

@Component
public class UsersControler {

	@Autowired
	private UserService userService;
//	@Autowired
//	private PopupView popupView;
	@Autowired
	private UIComponentUtils componentUrils;
	

	private User editedValue;

	@FXML
	private TableView<User> table;
	@FXML
	private TextField lastName;
	@FXML
	private TextField firstName;
	@FXML
	private TextField email;
	@FXML
	private TextField phone;
	@FXML
	private TextField authName;
	@FXML
	private TextField authPassword;
	@FXML
	private ComboBox<Role> role;

	public void initialize() {
		initViewComponents();
	}

	private void initViewComponents() {
		createTable();
		role.setItems(FXCollections.observableArrayList(Role.values()));
		role.getSelectionModel().select(Role.SELLER);
		updateTable();
	}

	private void createTable() {
		componentUrils.addTableColumn(table, "Прізвище", 148.0, User.class, "lastName");
		componentUrils.addTableColumn(table, "Ім'я", 149.0, User.class, "firstName");
		componentUrils.addTableColumn(table, "Email", 149.0, User.class, "email");
		componentUrils.addTableColumn(table, "Номер тел.", 112.0, User.class, "phone");
		componentUrils.addTableColumn(table, "Логін", 149.0, User.class, "authName");
		componentUrils.addTableColumn(table, "Пароль", 149.0, User.class, "authPassword");
		componentUrils.addTableColumn(table, "Роль", 94.0, User.class, "role");
//		componentUrils.addTableColumnWithButton(table, "", 20.0, User.class, "this", "Рахунок", e -> {
//			popupView.showTickPoputForUser( e );
//		});
		componentUrils.addSelectionEventToTable(table, User.class, e -> {
			editedValue = e;
			writeFields();
		});
	}

	public void updateTable() {
		table.setItems(FXCollections.observableArrayList(userService.getAll()));
		editedValue = new User();
	}

	public void saveValue() {
		if (isValidateFields()) {
			if (editedValue == null || editedValue.getId() == 0L) {
				editedValue = new User();
			}
			if (authName.getText() != null) {
				editedValue.setAuthName(authName.getText().trim());
			}
			if (authPassword.getText() != null) {
				editedValue.setAuthPassword(authPassword.getText());
			}
			if (email.getText() != null) {
				editedValue.setEmail(email.getText().trim());
			}
			editedValue.setFirstName(firstName.getText().trim());
			editedValue.setLastName(lastName.getText().trim());
			if (phone.getText() != null) {
				editedValue.setPhone(phone.getText().trim());
			}
			editedValue.setRole(role.getValue());
			userService.save(editedValue);
			clearFields();
			updateTable();
			editedValue = new User();
		}
	}

	private void clearFields() {
		role.getSelectionModel().select(Role.SELLER);
		authName.clear();
		authPassword.clear();
		email.clear();
		firstName.clear();
		lastName.clear();
		phone.clear();
	}

	private void writeFields() {
		role.getSelectionModel().select(editedValue.getRole());
		authName.setText(editedValue.getAuthName());
		authPassword.setText(editedValue.getAuthPassword());
		email.setText(editedValue.getEmail());
		firstName.setText(editedValue.getFirstName());
		lastName.setText(editedValue.getLastName());
		phone.setText(editedValue.getPhone());
	}

	private boolean isValidateFields() {
		if (role.getValue() == null) {
			componentUrils.showMessage("Будь-ласка, виберіть роль!");
			return false;
		}
		if (Strings.isNullOrEmpty(firstName.getText())) {
			componentUrils.showMessage("Будь-ласка, введіть ім'я!");
			return false;
		}
		if (Strings.isNullOrEmpty(lastName.getText())) {
			componentUrils.showMessage("Будь-ласка, введіть прізвище!");
			return false;
		}

		if (!Strings.isNullOrEmpty(authName.getText())) {
			if (userService.isExistAuthName(authName.getText(), editedValue.getId())) {
				componentUrils.showMessage("Такий логін вже існує. Будт-ласка, введіть інший логін!");
				return false;
			}
		}
		return true;
	}
}
