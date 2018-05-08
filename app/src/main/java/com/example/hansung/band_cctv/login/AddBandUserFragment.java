package com.example.hansung.band_cctv.login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.hansung.band_cctv.R;

public class AddBandUserFragment extends Fragment {

    private static AddAppUserFragment instance;

    EditText editText_id;
    EditText editText_phone;
    EditText editText_birthday;
    EditText editText_address;
    EditText editText_name;
    RadioButton radioButton_m;
    RadioButton radioButton_w;

    public String band_user_id;
    public String band_user_phone;
    public String band_user_birthday;
    public String band_user_address;
    public String band_user_name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_band_user,container,false);

        editText_name = (EditText)view.findViewById(R.id.bandUser_name_tv);
        editText_phone = (EditText)view.findViewById(R.id.bandUser_phone_tv);
        editText_birthday = (EditText)view.findViewById(R.id.bandUser_birth_tv);
        editText_address = (EditText)view.findViewById(R.id.adress_et);
        radioButton_m = (RadioButton) view.findViewById(R.id.radioButton);
        radioButton_w = (RadioButton)view.findViewById(R.id.radioButton2);

        editText_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                band_user_name = editText_name.getText().toString();
            }
        });

        editText_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                band_user_address = editText_address.getText().toString();
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
                band_user_birthday = editText_birthday.getText().toString();
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
                band_user_phone = editText_phone.getText().toString();
            }
        });
        return view;
    }
    public String getBand_user_name(){
        return band_user_name;
    }
    public String getBand_user_phone(){
        return band_user_phone;
    }
    public String getBand_user_birthday(){
        return band_user_birthday;
    }
    public String getBand_user_address(){
        return band_user_address;
    }

}
