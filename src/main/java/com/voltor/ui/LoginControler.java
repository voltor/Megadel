package com.voltor.ui;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voltor.MegadelApplication;
import com.voltor.SpringFXMLloader;
import com.voltor.bean.User;
import com.voltor.services.SecurityService;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

@Component
public class LoginControler {

	private static String ON_LOGIG_FAILED_TEXT = "Ви ввели невірний логін або пароль!";

	@Autowired
	SecurityService securityService;

	@FXML
	private Label inputInfo;
	@FXML
	private Button login;
	@FXML
	private TextField loginField;
	@FXML
	private PasswordField passwordField;

	@FXML
	private void onButtonClicked(MouseEvent event) throws IOException {
		User user = new User(loginField.getText(), passwordField.getText());
		if (securityService.checkUserAndLogin(user)) {
			onUserPassed();
		} else {
			onUserFailed();
		}
	}

	private void onUserPassed() throws IOException {
		inputInfo.setVisible(false);
        Parent rootNode = new SpringFXMLloader().getParrentByPath("/fxml/MainView.fxml");
        
        
        Stage stage = (Stage) login.getScene().getWindow();
		stage.close();

		Stage mainStage = new Stage();
		Scene scene = new Scene( rootNode );
		mainStage.setScene( scene );
		mainStage.setTitle(MegadelApplication.PROGECT_NAME);
		mainStage.show();
	}

	private void onUserFailed() {
		inputInfo.setText(ON_LOGIG_FAILED_TEXT);
		inputInfo.setVisible(true);
	}

}
