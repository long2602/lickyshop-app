package com.tlong.test.ultil;

import android.content.Context;
import android.content.SharedPreferences;
import com.tlong.test.model.*;

import java.util.Arrays;

public class UserLocalStore {
    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context){
        userLocalDatabase=context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
    }
    public void storeUserData(user user) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putString("username",user.name);
        userLocalDatabaseEditor.putString("pass",user.pass);
        userLocalDatabaseEditor.putString("phone",user.phone);
        userLocalDatabaseEditor.putInt("id",user.id);
        userLocalDatabaseEditor.commit();
    }
    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putBoolean("loggedIn", loggedIn);
        userLocalDatabaseEditor.commit();
    }
    public void clearUserData() {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.clear();
        userLocalDatabaseEditor.commit();
    }
    public user getLoggedInUser() {
        if (userLocalDatabase.getBoolean("loggedIn", false) == false) {
            return null;
        }
        String username = userLocalDatabase.getString("username", "");
        String pass = userLocalDatabase.getString("pass", "");
        String phone = userLocalDatabase.getString("phone", "");
        int id=userLocalDatabase.getInt("id",-1);
        user user = new user(phone,username,pass,id);
        return user;
    }
}
