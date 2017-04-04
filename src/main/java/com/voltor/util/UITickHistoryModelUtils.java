package com.voltor.util;

import com.voltor.entity.TickHistoryEntity;
import javafx.scene.control.TextField;
import org.assertj.core.util.Strings;

/**
 * Created by Petro on 04.04.2017.
 */
public class UITickHistoryModelUtils {
    public static boolean matchesFilter(TickHistoryEntity p,
                                        TextField date,
                                        TextField value,
                                        TextField type) {

        if( !Strings.isNullOrEmpty( date.getText() )){
            if(!p.getDate().toString().toLowerCase().contains(date.getText().toLowerCase())){
                return false;
            }
        }
        if( !Strings.isNullOrEmpty( type.getText() )){
            if(!p.getType().toString().toLowerCase().contains(type.getText().toLowerCase())){
                return false;
            }
        }
        if( !Strings.isNullOrEmpty( value.getText() ) ){
            if( !p.getValue().toString().toLowerCase().contains(value.getText().toLowerCase()) ){
                return false;
            }
        }
        return true;
    }
}
