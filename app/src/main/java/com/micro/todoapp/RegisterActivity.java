package com.micro.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.micro.todoapp.utlisService.UtilService;

public class RegisterActivity extends AppCompatActivity {

    MaterialButton loginBtn,registerBtn;
    EditText username_et,email_et,password_et;
    ProgressBar progressBar;
    UtilService utilService;

    private String username,email,password;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loginBtn =(MaterialButton)findViewById(R.id.login_bt);
        registerBtn =(MaterialButton)findViewById(R.id.reg_register_bt);
        username_et = (EditText) findViewById(R.id.reg_username_et);
        email_et = (EditText)findViewById(R.id.reg_email_et);
        password_et =(EditText)findViewById(R.id.reg_password_et);

        utilService = new UtilService();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                utilService.hideKeyboard(view,RegisterActivity.this);
                username = username_et.getText().toString();
                email = email_et.getText().toString();
                password = password_et.getText().toString();
            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }
}