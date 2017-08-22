package com.example.a700_15isk.justtalk.tools;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a700_15isk.justtalk.bean.User;

/**
 * Created by 700-15isk on 2017/8/20.
 */

public class TextUtil {
    public static  int CHANGE_AGE=0;
    public static  int CHANGE_NICK=1;
    public static  int CHANGE_EMAIL=2;

    public static void banEnter(EditText editText){
         editText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return(event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });
    }

    public static void editTextTool(final EditText editText, final int Type, final User user, final Context context){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                   if (Type==CHANGE_AGE&&!editText.getText().toString().equals("")){

                       user.setAge(Integer.valueOf(editText.getText().toString()));
                       UserTool.getInstance().update(user,context);
                   }
                   else if(Type==CHANGE_EMAIL&&!editText.getText().toString().equals("")){
                       user.setEmail(editText.getText().toString());
                       UserTool.getInstance().update(user,context);
                   }
                   else if(Type==CHANGE_NICK&&!editText.getText().toString().equals("")){
                       user.setNick(editText.getText().toString());
                       UserTool.getInstance().update(user,context);
                   }
            }
        });

    }
}
