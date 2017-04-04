package com.voltor.ui.popup;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.voltor.SpringFXMLloader;
import com.voltor.bean.User;
import com.voltor.ui.popup.usertick.UserTickPopupControler;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@Component
public class PopupView {

	public void showTickPoputForUser( User user ) { 
		Parent rootNode;
		try {
			FXMLLoader loader = new SpringFXMLloader().getLoaderByPath("/fxml/popups/TickPopup.fxml");
			rootNode = loader.load();
		
			Stage mainStage = new Stage();
			UserTickPopupControler controller = loader.getController();
			controller.setUser( user );
			Scene scene = new Scene( rootNode );
			mainStage.setScene( scene );
			mainStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
