package com.example.hotel_managment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class Main_RestaurantPage extends AppCompatActivity {
  private ImageSlider imageSlider;
  private CardView cardView_upload,cardViewRecipe_Made,go_room_bookingPage;
//  String pname= "Users";
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private ProgressBar progressBar;
    private int prog=0;
    private TextView textView_status;
    private String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__restaurant_page);
        this.setTitle("Recipe Home page");

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_recipe);
        drawerLayout=(DrawerLayout)findViewById(R.id.recipe_drawer_id);
        navigationView=(NavigationView)findViewById(R.id.recipe_navigation_id);
        textView_status=(TextView)findViewById(R.id.status_recipe_id);

        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.my_hom_id:
                        Toast.makeText(Main_RestaurantPage.this, "Home", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(Main_RestaurantPage.this,Recipe_Upload.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.my_order_Id:
                        Toast.makeText(Main_RestaurantPage.this, "Customar Order", Toast.LENGTH_SHORT).show();
                        Intent intent1=new Intent(Main_RestaurantPage.this,Recipe_payment_ShowList.class);
                        startActivity(intent1);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });

        imageSlider=(ImageSlider)findViewById(R.id.imageSlider_recip_homePage);

        cardViewRecipe_Made=(CardView)findViewById(R.id.User_card_recipe_mede);
       cardView_upload=(CardView)findViewById(R.id.User_level_recipe_Show);

        go_room_bookingPage=(CardView)findViewById(R.id.User_card_home);
        progressBar=(ProgressBar) findViewById(R.id.recipe_progressId);

       Bundle bundle=getIntent().getExtras();
        textView_status.setText(""+bundle.getString("homepage_stuts"));
        if(textView_status.getText().toString().contains("Admin")){
            toggle.setDrawerIndicatorEnabled(true);
        }else if(textView_status.getText().toString().contains("User")){
            toggle.setDrawerIndicatorEnabled(false);
        }

        List<SlideModel>slideModels=new ArrayList<>();
      slideModels.add(new SlideModel(R.drawable.pedro_sousa,"Cooked Food", ScaleTypes.FIT));
      slideModels.add(new SlideModel(R.drawable.test4,"Round Cake", ScaleTypes.FIT));
      slideModels.add(new SlideModel(R.drawable.test2,"Bowl of Cooked Food", ScaleTypes.CENTER_CROP));
      slideModels.add(new SlideModel(R.drawable.pizza,"Pizza", ScaleTypes.CENTER_CROP));
      slideModels.add(new SlideModel(R.drawable.test5,"Chocolate cake", ScaleTypes.FIT));
      imageSlider.setImageList(slideModels,ScaleTypes.FIT);


        cardViewRecipe_Made.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Main_RestaurantPage.this,Recipe_Made_details.class);
                startActivity(intent);
            }
        });

        cardView_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent=new Intent(Main_RestaurantPage.this,User_level_recipeShow.class);
                startActivity(intent);
            }
        });

        go_room_bookingPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                Thread thread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                       show_progeress();
                    }
                }); thread.start();
            }
        });
    }
    void show_progeress(){
        for (prog=10; prog <= 100; prog=prog+10){
            try {
                Thread.sleep(300);
                progressBar.setProgress(prog);
                if(prog==100){
                    Intent intent=new Intent(Main_RestaurantPage.this,Home_page.class);
                    intent.putExtra("mystuts",textView_status.getText().toString());
                    startActivity(intent);
                     finish();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}