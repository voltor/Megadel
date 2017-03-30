package com.voltor.util;

import com.voltor.entity.TickHistoryEntity;
import com.voltor.ui.popup.usertick.UserTickPopupUATickHistoryModel;
import com.voltor.ui.popup.usertick.UserTickPopupUSATickHistoryModel;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

/**
 * Created by Petro on 29.03.2017.
 */
@Component
public class UserTickUtils {

    public void initTickHistory(UserTickPopupUATickHistoryModel uaTickHistoryModel, TableView<TickHistoryEntity> uaTable,
                                TextField uaDate, TextField uaValue, TextField uaType,
                                UserTickPopupUSATickHistoryModel usaTickHistoryModel, TableView<TickHistoryEntity> usaTable,
                                TextField usaDate, TextField usaValue, TextField usaType) {
        uaTickHistoryModel.setTable(uaTable);
        uaTickHistoryModel.setDate(uaDate);
        uaTickHistoryModel.setValue(uaValue);
        uaTickHistoryModel.setType(uaType);
        uaTickHistoryModel.init();

        usaTickHistoryModel.setTable(usaTable);
        usaTickHistoryModel.setDate(usaDate);
        usaTickHistoryModel.setValue(usaValue);
        usaTickHistoryModel.setType(usaType);
        usaTickHistoryModel.init();
    }

}