package com.example.hansung.band_cctv.login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.hansung.band_cctv.R;


public class AddAppUserFragment extends Fragment {

    EditText editText_id;
    EditText editText_password;
    EditText editText_phone;
    EditText editText_birthday;

    String user_password;
    String user_id;
    String user_phone;
    String user_birthday;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_app_user, container, false);

        editText_birthday = view.findViewById(R.id.birthday_et);
        editText_phone = view.findViewById(R.id.phone_et);
        editText_password = view.findViewById(R.id.pw_et);
        editText_id = view.findViewById(R.id.id_et);

        editText_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                user_password = editText_password.getText().toString();
            }
        });

        editText_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                user_id = editText_id.getText().toString();
            }
        });

        editText_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                user_phone = editText_phone.getText().toString();
            }
        });

        editText_birthday.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                user_birthday = editText_birthday.getText().toString();
            }
        });
        return view;
    }

    public String getuserPassword(){
        return user_password;
    }

    public String getuserPhone(){
        return user_phone;
    }

    public String getuserBirthday(){
        return user_birthday;
    }

    public String getuserId(){
        return user_id;
    }

}
