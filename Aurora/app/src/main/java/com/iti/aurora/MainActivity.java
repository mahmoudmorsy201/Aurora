package com.iti.aurora;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.iti.aurora.addmedicine.view.AddMedicineActivity;
import com.iti.aurora.databinding.ActivityMainHomeBinding;
import com.iti.aurora.utils.permissons.PermissionUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavigationUI.setupWithNavController(bottomNavigationView, Navigation.findNavController(this, R.id.nav_host_fragment));
        bottomNavigationView.setOnNavigationItemReselectedListener(menuItem -> {
        });

//        PermissionUtil.requestIgnorePowerOptimize(getApplicationContext());
//        PermissionUtil.checkOverlayPermission(getApplicationContext());
    }

}