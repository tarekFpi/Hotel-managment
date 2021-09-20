package com.example.hotel_managment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

public class Amdin_deatils extends AppCompatActivity {
    private CardView Admin_room_bookingCarview,Admin_cardViewRoom_show, Admin_cardViewCheckOut,Admin_cardViewcheck_in,Admin_Card_roomPayment_show,
            Admin_careView_recipeAdd,cardView_recipe_priceUdate,cardView_recipe_order,Card_recipe_paymentShow,cardView_userData;

     private ImageSlider imageSlider;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amdin_deatils);

       Admin_room_bookingCarview=(CardView)findViewById(R.id.admin_Room_bookId);
        //Amdin_cardView_restaurant=(CardView)findViewById(R.id.card_reservation);
        Admin_cardViewCheckOut=(CardView)findViewById(R.id.admin_card_checkOut);
        Admin_cardViewRoom_show=(CardView)findViewById(R.id.Admin_Room_rateShow);
        Admin_cardViewcheck_in=(CardView)findViewById(R.id.admin_cheeck_in);

        Admin_careView_recipeAdd=(CardView)findViewById(R.id.card_recipe_add);
        cardView_recipe_priceUdate=(CardView)findViewById(R.id.card_recipe_upload);
        cardView_recipe_order=(CardView)findViewById(R.id.admin_recipe_order);
        Admin_Card_roomPayment_show=(CardView)findViewById(R.id.admin_room_CheckOut_payment_Id);
        cardView_userData=(CardView)findViewById(R.id.User_id);
        Card_recipe_paymentShow=(CardView)findViewById(R.id.recipe_payment);
          imageSlider=(ImageSlider)findViewById(R.id.admin_imageSlider);
        List<SlideModel>slideModels=new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.romm,"Room", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.room2,"Room", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.pexels_vecislavas_pop,"Room", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.vecislavas_popa,"Room", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.test4,"Round Cake", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.test2,"Bowl of Cooked Food", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.pedro_sousa,"Pizza", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.test5,"Chocolate cake", ScaleTypes.FIT));
        imageSlider.setImageList(slideModels,ScaleTypes.FIT);

        Admin_room_bookingCarview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Amdin_deatils.this,Room_ItemAdd.class);
                startActivity(intent);
                Toast.makeText(Amdin_deatils.this, "Room Add Page", Toast.LENGTH_SHORT).show();
                // finish();
            }
        });

        Admin_cardViewCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Amdin_deatils.this,Custom_checkOut.class);
                startActivity(intent);
                Toast.makeText(Amdin_deatils.this, "Room Check Out", Toast.LENGTH_SHORT).show();
                // finish();
            }
        });

        Admin_cardViewcheck_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Amdin_deatils.this,Cheeck_inRoomShow.class);
                startActivity(intent);
                Toast.makeText(Amdin_deatils.this, "Room Check Out", Toast.LENGTH_SHORT).show();
            }
        });

        Admin_cardViewRoom_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Amdin_deatils.this,Room_RateShow.class);
              //  intent.putExtra("admin_test",textView_status.getText().toString());
                startActivity(intent);

            }
        });

        Admin_Card_roomPayment_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Amdin_deatils.this,payment_Customar_checkOut.class);
                startActivity(intent);
            }
        });

        Admin_Card_roomPayment_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Amdin_deatils.this,payment_Customar_checkOut.class);
                startActivity(intent);
            }
        });

        Admin_careView_recipeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Amdin_deatils.this,Recipe_Upload.class);
                startActivity(intent);
            }
        });

        cardView_recipe_priceUdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        Intent intent=new Intent(Amdin_deatils.this,Recipe_RateShow.class);
       startActivity(intent);
            }
        });

        cardView_recipe_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Amdin_deatils.this,order_status.class);
                startActivity(intent);
            }
        });

        Card_recipe_paymentShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Amdin_deatils.this,Recipe_payment_ShowList.class);
                startActivity(intent);
            }
        });

        cardView_userData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Amdin_deatils.this,User_informatio_show.class);
                startActivity(intent);
            }
        });
    }
}