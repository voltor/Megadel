package com.voltor.util;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

@Component
public class UIComponentUtils {
	
	public <T> void addTableColumn(TableView<T> table, String name, Double wight, Class<T> type , String source ){
		table.getColumns().add( getTableColumn(name, wight, type, source) );
	}
	
	public <T> TableColumn<T, T> getTableColumn( String name, Double wight, Class<T> type , String source ){
		TableColumn<T, T> coll = new TableColumn<T, T>( name );
		coll.setMinWidth(wight);
		coll.setCellValueFactory(new PropertyValueFactory<T, T>(source));
		return coll;
	}

	public <T> void addTableColumnWithButton(TableView<T> table, String name, Double wight, Class<T> type , String source, String buttonName, Consumer<T> runnable ){
		TableColumn<T, T> coll  = getTableColumn(name, wight, type, source);
		coll.setCellFactory(col -> {
			Button editButton = new Button(buttonName);
			editButton.setPrefWidth(wight);
			TableCell<T, T> cell = new TableCell<T, T>() {
				@Override
				public void updateItem(T bean, boolean empty) {
					super.updateItem(bean, empty);
					if (empty) {
						setGraphic(null);
					} else {
						setGraphic(editButton);
					}
				}
			};
			editButton.setOnAction(e -> { try {
				runnable.accept( cell.getItem() );
			} catch ( Exception e1) {
				e1.printStackTrace();
			} });

			return cell;
		});
		table.getColumns().add( coll );
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> void addSelectionEventToTable(TableView<T> table, Class<T> clazz,Consumer<T> runnable) {
		table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
				if (table.getSelectionModel().getSelectedItem() != null) {
					TableViewSelectionModel selectionModel = table.getSelectionModel();
					ObservableList selectedCells = selectionModel.getSelectedItems();
					runnable.accept( (T) selectedCells.get(0) );
				}
			}
		});
		
	}

	public <T> void addValueChangeListenerToTextField(TextField field, Runnable runnable){
		field.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				runnable.run();
			}
		});
	}
	
	public <T> void addValueChangeListenerToComboBox(ComboBox<? extends Object>  field, Runnable runnable){ 
		field.valueProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> observable,
            		Object oldValue, Object newValue) {
            	runnable.run();
            }
		});
	}
	
	public void showMessage(String contentText) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Увага!");
		alert.setHeaderText(null);
		alert.setContentText(contentText);
		alert.showAndWait();
	}
}
