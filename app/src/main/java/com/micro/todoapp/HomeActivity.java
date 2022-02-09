package com.micro.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;
import com.micro.todoapp.utliservice.SharedPreference;

public class HomeActivity extends AppCompatActivity {
    Button logoutBtn;
    SharedPreference sharedPreference;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        logoutBtn = findViewById(R.id.home_logout_btn);
        sharedPreference = new SharedPreference(this);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
            sharedPreference.clear();
            startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            finish();
            }
        });

    }
}