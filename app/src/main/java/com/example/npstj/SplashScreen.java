package com.example.npstj;

import android.content.Intent;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.npstj.Common.UserLocalStore;
import com.example.npstj.loginframe.Login_Page;
import com.example.npstj.mainframe.Home_Page;
import com.example.npstj.test.Test;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.splash_src_clr, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.splash_src_clr));
        }

        UserLocalStore userLocalStore = new UserLocalStore(this);
        if (userLocalStore.get_kid_list().isEmpty()){
            Intent intent = new Intent(getApplicationContext(), Login_Page.class);
            //Intent intent = new Intent(getApplicationContext(), Home_Page.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(getApplicationContext(), Home_Page.class);
            if (!userLocalStore.get_theme().isEmpty()){
                if (userLocalStore.get_theme().equals("dark")){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
            }
           // Intent intent = new Intent(getApplicationContext(), Test.class);
            overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
            startActivity(intent);
        }
    }


}
