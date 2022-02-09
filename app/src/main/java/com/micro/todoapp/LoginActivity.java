package com.micro.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.micro.todoapp.utliservice.SharedPreference;
import com.micro.todoapp.utliservice.UtilService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    MaterialButton registerBtn,loginBtn;
    EditText email_et,password_et;
    ProgressBar progressBar;
    UtilService utilService;
    private String email;
    private String password;
    SharedPreference sharedPreference;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        registerBtn = (MaterialButton)findViewById(R.id.log_register_bt);
        loginBtn =(MaterialButton)findViewById(R.id.log_login_bt);
        email_et = (EditText)findViewById(R.id.log_email_et);
        password_et =(EditText)findViewById(R.id.log_password_et);

        utilService = new UtilService();
        progressBar =(ProgressBar)findViewById(R.id.log_progress_bar);
        sharedPreference = new SharedPreference(this);
        progressBar.setVisibility(View.GONE);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view) {
                email = email_et.getText().toString();
                password = password_et.getText().toString();
                Toast.makeText(LoginActivity.this, "Logging .....", Toast.LENGTH_SHORT).show();
            loginUser(view);
            }
        });




    }

    private void loginUser (View view) {
        progressBar.setVisibility(View.VISIBLE);

        HashMap<String,String> params = new HashMap<>();
        params.put("email",email);
        params.put("password",password);

        String apikey ="https://todo-application231.herokuapp.com/api/todo/auth/login";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, apikey, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse (JSONObject response) {
                try {
                    if(response.getBoolean("success")){
                        String token = response.getString("token");
                        sharedPreference.setValueString("token",token);
                        Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                    }
                    progressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if(error instanceof ServerError && response!= null){
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers,"utf-8"));
                        JSONObject obj = new JSONObject(res);
                        Toast.makeText(LoginActivity.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();
                        Log.d("taggg",obj.getString("msg"));
                        progressBar.setVisibility(View.GONE);
                    }catch (JSONException | UnsupportedEncodingException js){
                        js.printStackTrace();
                        progressBar.setVisibility(View.GONE);
                    }
                }

            }
        }){
            @Override
            public Map<String, String> getHeaders () throws AuthFailureError {
                HashMap<String,String> headers = new HashMap<>();
                headers.put("Content-Type","application/json");
                return params;
            }
        };

        //set retry policy
        int socketTime = 3000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTime,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        jsonObjectRequest.setRetryPolicy(retryPolicy);

            //request add
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    protected void onStart () {
        super.onStart();
        SharedPreferences userPref= getSharedPreferences("todo_user", MODE_PRIVATE);
        if(userPref.contains("token")){
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
            finish();
        }
    }
}
