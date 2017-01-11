package br.com.monitoratec.treinamentomonitoraretrofit;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;

/**
 * Created by lucasfranco on 11/01/17.
 */
public class AppUtils {
    public static boolean checkFilledFields(Context context , TextInputLayout... fields) {
        boolean isValid = true;
        for (TextInputLayout field: fields){
            if(field.getEditText() != null) {
                if (TextUtils.isEmpty(field.getEditText().getText())) {
                    field.setError("O campo está vazio");
                    field.setErrorEnabled(true);
                }else{
                    field.setError(null);
                    field.setErrorEnabled(false);
                    return true;
                }
            }else{
                throw new RuntimeException("o EditText do TextInputLayout está nulo");
            }
        }
        return false;
    }

}
