package com.voltor.util;

import com.voltor.bean.Seller;
import javafx.scene.control.TextField;
import org.assertj.core.util.Strings;

/**
 * Created by Petro on 04.04.2017.
 */
public class UISellerModelUtils {
    public static boolean matchesFilter(Seller p,
                                  TextField name,
                                  TextField email,
                                  TextField phone) {
        if( !Strings.isNullOrEmpty(name.getText()) ){
            if(!p.getName().toLowerCase().contains(name.getText().toLowerCase())){
                return false;
            }
        }
        if( !Strings.isNullOrEmpty(phone.getText()) ){
            if( Strings.isNullOrEmpty(p.getPhone()) || !p.getPhone().toLowerCase().contains(phone.getText().toLowerCase())){
                return false;
            }
        }
        if( !Strings.isNullOrEmpty(email.getText()) ){
            if( Strings.isNullOrEmpty(p.getEmail()) || !p.getEmail().toLowerCase().contains(email.getText().toLowerCase())){
                return false;
            }
        }
        return true;
    }
}
