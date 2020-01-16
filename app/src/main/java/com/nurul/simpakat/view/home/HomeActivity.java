package com.nurul.simpakat.view.home;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nurul.simpakat.R;
import com.nurul.simpakat.common.Constanta;
import com.nurul.simpakat.common.util.PreferenceUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

//        PreferenceUtils preferenceUtils = new PreferenceUtils(getApplicationContext(), Constanta.APPLICATION_PREFERENCE);
        AppBarConfiguration appBarConfiguration = null;
//        String jabatan = preferenceUtils.getString(Constanta.PREF_JABATAN, null);
//        if(jabatan != null) {
//            if(jabatan.equals("Unit Kerja")) {
//                appBarConfiguration = new AppBarConfiguration.Builder(
//                        R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                        .build();
//            } else if(jabatan.equals("Dekan")) {
//                appBarConfiguration = new AppBarConfiguration.Builder(
//                        R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_list_pengajuan, R.id.navigation_notifications)
//                        .build();
//            }
//        } else {
//            appBarConfiguration = new AppBarConfiguration.Builder(
//                    R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                    .build();
//        }

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.container);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

}
