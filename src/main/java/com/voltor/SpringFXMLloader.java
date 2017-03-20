package com.voltor;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class SpringFXMLloader {

	public Parent getParrentByPath(String path) throws IOException {
		return getLoaderByPath(path).load(); 
	}
	
	public FXMLLoader getLoaderByPath(String path) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader( getClass().getResource(path) );
		fxmlLoader.setControllerFactory(MegadelApplication.springContext::getBean);
		return fxmlLoader; 
	}
}
