package com.example.hotel_managment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

public class Naviation_test extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naviation_test);

        drawerLayout=(DrawerLayout)findViewById(R.id.test_drawer_search);
        Toolbar toolbar=(Toolbar) findViewById(R.id.test_toolbar);
        setSupportActionBar(toolbar);
       navigationView=(NavigationView)findViewById(R.id.test_navigation_id);
       toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
       drawerLayout.addDrawerListener(toggle);
       toggle.syncState();

       navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

               switch (menuItem.getItemId()){
                   case R.id.my_hom_id:
                Toast.makeText(Naviation_test.this, "Home ", Toast.LENGTH_SHORT).show();
                   drawerLayout.closeDrawer(GravityCompat.START);
                  break;

               }
               return true;
           }
       });
    }
}