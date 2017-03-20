package com.voltor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SpringBootApplication
@ComponentScan(value = "com.voltor")
public class MegadelApplication extends Application {

	public static String PROGECT_NAME = "Megadel";
	public static ConfigurableApplicationContext springContext;
	private Parent rootNode;

	public static void main(final String[] args) {
		Application.launch(args);
	}

	@Override
	public void init() throws Exception {
		springContext = SpringApplication.run(MegadelApplication.class);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
		fxmlLoader.setControllerFactory(springContext::getBean);
		rootNode = fxmlLoader.load();
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setScene(new Scene(rootNode));
		stage.setTitle(MegadelApplication.PROGECT_NAME);
		stage.show();
	}

	@Override
	public void stop() throws Exception {
		springContext.close();
	}

}
