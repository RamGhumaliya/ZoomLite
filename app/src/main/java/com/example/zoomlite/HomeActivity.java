package com.example.zoomlite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.zoomlite.R;
import com.example.zoomlite.call.activities.CallActivity;
import com.example.zoomlite.call.services.LoginService;
import com.example.zoomlite.call.utils.SharedPrefsHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private SharedPrefsHelper sharedPrefsHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_meetchat, R.id.navigation_meetings, R.id.navigation_contacts, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        sharedPrefsHelper = SharedPrefsHelper.getInstance();
        startLoginService();
    }

    private void startLoginService() {
        if (sharedPrefsHelper.hasQbUser()){
            LoginService.start(this,sharedPrefsHelper.getQbUser());
        }
    }

}