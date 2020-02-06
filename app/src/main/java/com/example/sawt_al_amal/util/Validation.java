package com.example.sawt_al_amal.util;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputEditText;

/*AZALMAD Ilham*/
public class Validation {
    private Context context;
    public Validation(Context context) {
        this.context = context;
    }
    //vérifier si l'input est vide ou non
    public boolean isInputEditText(EditText editText, String msg) {
        String value = editText.getText().toString().trim();
        if (value.isEmpty()) {
            editText.setError(msg);
            return false;
        } else {
            editText.setError(null);
        }

        return true;
    }

    // vérifier l'email
    public boolean isInputEmail(EditText editText, String msg) {
        String value = editText.getText().toString().trim();
        if (value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            editText.setError(msg);
            return false;
        } else {
            editText.setError(null);
        }
        return true;
    }

    // vérifier les données d'un utlisateur dans la base de données (login et mot de passe)
    public boolean isInputMatches(EditText editText1,EditText editText2, String msg) {
        String value1 = editText1.getText().toString().trim();
        String value2 = editText2.getText().toString().trim();
        if (!value1.contentEquals(value2)) {
            editText2.setError(msg);
            return false;
        } else {
            editText2.setError(null);
        }
        return true;
    }


}

