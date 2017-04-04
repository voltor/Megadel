package com.voltor.util;

import com.voltor.bean.Provider;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.assertj.core.util.Strings;

/**
 * Created by Petro on 04.04.2017.
 */
public class UIProviderModelsUtils {
    public static boolean matchesFilter(Provider p,
                                        TextField name,
                                        TextField firmName,
                                        TextField email,
                                        TextField phone) {
        if( !Strings.isNullOrEmpty(name.getText()) ){
            if(!p.getName().toLowerCase().contains(name.getText().toLowerCase())){
                return false;
            }
        }
        if( !Strings.isNullOrEmpty(firmName.getText()) && !Strings.isNullOrEmpty(p.getFirmName())){
            if(!p.getFirmName().toLowerCase().contains(firmName.getText().toLowerCase())){
                return false;
            }
        }
        if( !Strings.isNullOrEmpty(phone.getText()) && !Strings.isNullOrEmpty(p.getPhone()) ){
            if(!p.getPhone().toLowerCase().contains(phone.getText().toLowerCase())){
                return false;
            }
        }
        if( !Strings.isNullOrEmpty(email.getText()) && !Strings.isNullOrEmpty(p.getEmail()) ){
            if(!p.getEmail().toLowerCase().contains(email.getText().toLowerCase())){
                return false;
            }
        }
        return true;
    }
}
