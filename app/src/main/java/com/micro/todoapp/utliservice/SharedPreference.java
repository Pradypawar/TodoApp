package com.micro.todoapp.utliservice;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {

    private static final String USER_PREF = "todo_user";
    private SharedPreferences appShared;
    private SharedPreferences.Editor prefEditor;


    public SharedPreference(Context context){

        appShared = context.getSharedPreferences(USER_PREF, Activity.MODE_PRIVATE);
        this.prefEditor =appShared.edit();


    }

    public int getint(String key){
        return appShared.getInt(key,0);
    }
    public String getString(String key){
        return appShared.getString(key,null);
    }
    public boolean getboolean(String key){
        return appShared.getBoolean(key,false);}

    public void setValueString(String key ,String value){
        prefEditor.putString(key, value).apply();
    }
    public void setValueInt(String key ,int value){
        prefEditor.putInt(key, value).apply();
    }
    public void setValueBoolean(String key ,Boolean value){
        prefEditor.putBoolean(key, value).apply();
    }

    public void clear(){
        prefEditor.clear().apply();
    }

}
