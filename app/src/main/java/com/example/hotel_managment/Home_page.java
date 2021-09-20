package com.example.hotel_managment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.*;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.hotel_managment.R.string.close;

public class Home_page extends AppCompatActivity {
private CardView user_cardView_restaurant,user_cardViewRoom_show,user_cardViewcheck_in,Card_Admin_level,
        cardView_location,cardView_exit,cardView_upload,cardViewRecipe_Made;

private ImageSlider imageSlider;
private ProgressBar progressBar;
   static  int  myStutas=0;
private DrawerLayout drawerLayout;
private NavigationView navigationView;
private ActionBarDrawerToggle toggle;
private int prog=0;
private TextView textView_status;
 private String status;
 private CircleImageView circleImageView_profile;
 private TextView textView_profile_emile,textView_userName;
 private LayoutInflater layoutInflater;
 private View view;
 private SharedPreferences sharedPreferences;
 private SharedPreferences SharedPrefer_user_gmali;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

           drawerLayout=(DrawerLayout)findViewById(R.id.home_drawable_id);
          navigationView=(NavigationView)findViewById(R.id.home_navigation_id);
          textView_status=(TextView)findViewById(R.id.status_id);
        view=navigationView.inflateHeaderView(R.layout.hader_layout);
        circleImageView_profile=view.findViewById(R.id.profile_imageId);
        textView_profile_emile=view.findViewById(R.id.profile_email_id);

      //  Toolbar toolbar;
      Toolbar toolbar=(Toolbar)findViewById(R.id.home_toolbar);
       setSupportActionBar(toolbar);


        SharedPrefer_user_gmali = getApplicationContext().getSharedPreferences("com.example.hotel_managment", Context.MODE_PRIVATE);
         toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open, close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.my_hom_id:
                        Toast.makeText(Home_page.this, "Home", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.my_order_Id:
                        Intent intent_data=new Intent(getApplicationContext(),User_leve_cheeck_inRoomShow.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(intent_data);
                        break;

                    case R.id.settin_id:
                        Toast.makeText(Home_page.this, "Setting.", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.my_profile_id:
                        Toast.makeText(Home_page.this, "Profile.", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.my_signOut_id:
                        //Toast.makeText(Home_page.this, "Profile.", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(Home_page.this,Log_inPage.class);
                        startActivity(intent);
                        SharedPrefer_user_gmali.edit().clear().commit();
                        SharedPrefer_user_gmali.edit().remove("remember_email").commit();
                        SharedPrefer_user_gmali.edit().remove("remember_pass").commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                         finish();
                        break;
                }
              return true;
            }
        });

        imageSlider=(ImageSlider)findViewById(R.id.home_imageSlider);
        user_cardViewRoom_show=(CardView)findViewById(R.id.User_Room_showId);
       // Amdin_cardView_restaurant=(CardView)findViewById(R.id.card_reservation);

        user_cardViewcheck_in=(CardView)findViewById(R.id.Use_card_check_in);
       // user_cardView_restaurant=(CardView)findViewById(R.id.User_restaurant_id_ho);
        Card_Admin_level=(CardView)findViewById(R.id.admin_level_id);

        cardView_location=(CardView)findViewById(R.id.card_MapId);
        progressBar=(ProgressBar)findViewById(R.id.home_progressId);
        //cardView_exit=(CardView)findViewById(R.id.exit_id);

        cardViewRecipe_Made=(CardView)findViewById(R.id.User_card_recipe_mede_h);
        cardView_upload=(CardView)findViewById(R.id.User_restaurant_id_ho);

         Admin_information();

        List<SlideModel>slideModels=new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.romm,"Room", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.room2,"Room", ScaleTypes.FIT));
        //slideModels.add(new SlideModel(R.drawable.pedro_sousa,"Room", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.pexels_vecislavas_pop,"Room", ScaleTypes.FIT));
       slideModels.add(new SlideModel(R.drawable.vecislavas_popa,"Room", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.francesca_tosolini_unsplash,"Room", ScaleTypes.FIT));
         slideModels.add(new SlideModel(R.drawable.chastity_mqm_unsplash,"Room", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.pedro_sousa,"Cooked Food", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.test4,"Round Cake", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.test2,"Bowl of Cooked Food", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.pizza,"Pizza", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.test5,"Chocolate cake", ScaleTypes.FIT));
      // slideModels.add(new SlideModel(R.drawable.ro,"Room", ScaleTypes.FIT));
        imageSlider.setImageList(slideModels,ScaleTypes.FIT);



        cardView_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home_page.this,Maps_restaurant.class);
                startActivity(intent);
                Toast.makeText(Home_page.this, "Current Location", Toast.LENGTH_SHORT).show();
                // finish();
            }
        });

        user_cardViewRoom_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home_page.this,User_ReservationLeval.class);
                startActivity(intent);
                Toast.makeText(Home_page.this, "Room Rate Show", Toast.LENGTH_SHORT).show();
                // finish();
            }
        });

        user_cardViewcheck_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home_page.this,User_leve_cheeck_inRoomShow.class);
                startActivity(intent);
            }
        });

/*        user_cardView_restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                Thread thread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        show_progeress();
                    }
                });thread.start();

            }
        });*/

        Card_Admin_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home_page.this,Amdin_deatils.class);
                startActivity(intent);
            }
        });

        cardViewRecipe_Made.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home_page.this,Recipe_Made_details.class);
                startActivity(intent);
            }
        });

        cardView_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home_page.this,User_level_recipeShow.class);
                startActivity(intent);
            }
        });


      /*  cardView_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//             sharedPreferences = getApplicationContext().getSharedPreferences("com.example.hotel_managment", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("remember_status","1");
//                sharedPreferences.edit().commit();

                String data="";
              //  myStutas=1;

*//*

               finish();
            }
        });*/
    }

    void Admin_information(){

        Bundle bundle=getIntent().getExtras();
      String mystats= bundle.getString("mystuts");
    textView_status.setText(""+bundle.getString("mystuts"));

     String gm=SharedPrefer_user_gmali.getString("user_gmail","gmail not save..");
        Toast.makeText(this, "gmail:"+gm, Toast.LENGTH_SHORT).show();

/*        layoutInflater=getLayoutInflater();
        view=layoutInflater.inflate(R.layout.hader_layout,null);*/
        circleImageView_profile=view.findViewById(R.id.profile_imageId);
        textView_profile_emile=view.findViewById(R.id.profile_email_id);

        textView_profile_emile.setText(""+gm);

        if(textView_status.getText().toString().contains("Admin")){
            Card_Admin_level.setVisibility(View.VISIBLE);
            toggle.setDrawerIndicatorEnabled(false);

        }else if(textView_status.getText().toString().contains("User")){
            Card_Admin_level.setVisibility(View.INVISIBLE);
            toggle.setDrawerIndicatorEnabled(true);
        }


      //  String value=bundle.getString("my_emil").toString();
       // Toast.makeText(this, "Email:"+value, Toast.LENGTH_SHORT).show();

        //  textView_userName.setText(""+bundle.getString("my_name"));*/
    }

   /* void show_progeress(){
    for (prog=10; prog <= 100; prog=prog+10){
        try {
            Thread.sleep(300);
          progressBar.setProgress(prog);
          if(prog==100){
            Intent intent=new Intent(Home_page.this,Main_RestaurantPage.class);
              intent.putExtra("homepage_stuts",textView_status.getText().toString());

             startActivity(intent);
           finish();
          }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    }*/


}