package com.voltor.util;

import com.voltor.bean.Coming;
import com.voltor.bean.Selling;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.assertj.core.util.Strings;

/**
 * Created by Petro on 04.04.2017.
 */
public class UISellingReportModelUtils {
    public static boolean matchesFilter(Selling p,
                                        TextField date,
                                        TextField name,
                                        TextField sum) {
        if( !Strings.isNullOrEmpty(name.getText()) ){
            if(!p.getSeller().getName().toLowerCase().contains(name.getText().toLowerCase())){
                return false;
            }
        }
        if( !Strings.isNullOrEmpty( date.getText() )){
            if(!p.getDate().toString().toLowerCase().contains(date.getText().toLowerCase())){
                return false;
            }
        }
        if( !Strings.isNullOrEmpty(sum.getText()) ){
            if( !p.getSum().toString().contains(sum.getText()) ){
                return false;
            }
        }
        return true;
    }

    public static void createTable(TableView<Selling> table) {
        TableColumn dateColl = new TableColumn("дата");
        dateColl.setMinWidth(168);
        dateColl.setCellValueFactory(new PropertyValueFactory<Coming, Coming>("date"));

        TableColumn providerNameColl = new TableColumn("ім'я");
        providerNameColl.setMinWidth(168);
        providerNameColl.setCellValueFactory(new PropertyValueFactory<Coming, Coming>("name"));

        TableColumn userNameColl = new TableColumn("користувач");
        userNameColl.setMinWidth(138);
        userNameColl.setCellValueFactory(new PropertyValueFactory<Coming, Coming>("userName"));

        TableColumn sumColl = new TableColumn("сума");
        sumColl.setMinWidth(138);
        sumColl.setCellValueFactory(new PropertyValueFactory<Coming, Coming>("sum"));

        table.getColumns().addAll(dateColl, providerNameColl, userNameColl, sumColl);
    }
}
