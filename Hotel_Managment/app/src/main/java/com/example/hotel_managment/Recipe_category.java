package com.example.hotel_managment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Recipe_category extends AppCompatActivity {
  private ImageSlider imageSlider;
  private TextView textViewId,textViewRe_name,textView_deatils,textView_quantity,textView_price,textView_Date;
  private DatabaseReference databaseReference,user_romCheck_databaseReference;
  private StorageReference storageRefere_image;
    private DatabaseReference MyFava_databaseReference;
  private DatabaseReference database_quantitySet;
  private ElegantNumberButton numberButton;
   // private EditText editText_quantity,editText_bill;
    int quantity;
    int price;
    int total_price;
private  RecipeModel recipe_Item;
private  int cunt;
private String list_upId;
private ProgressDialog progressDialog;
private SharedPreferences SharedPrefer_user_gmali;
private String user_gmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_category);
        databaseReference= FirebaseDatabase.getInstance().getReference("recipe_category");
        storageRefere_image= FirebaseStorage.getInstance().getReference("recipe_category");
        //MyFava_databaseReference=FirebaseDatabase.getInstance().getReference("");
        user_romCheck_databaseReference= FirebaseDatabase.getInstance().getReference("Reservation_roomCheek_In");

      database_quantitySet= FirebaseDatabase.getInstance().getReference("Recipe_upload"); // new selling Quantity Set
        imageSlider=(ImageSlider)findViewById(R.id.recip_category_Image);
        textViewId=(TextView)findViewById(R.id.recipe_category_id);
        textViewRe_name=(TextView)findViewById(R.id.recipe_category_name);
        textView_deatils=(TextView)findViewById(R.id.recipe_category_deatils);
        textView_quantity=(TextView)findViewById(R.id.recipe_category_quantity);
        textView_price=(TextView)findViewById(R.id.recipe_category_price);
        textView_Date=(TextView)findViewById(R.id.recipe_category_date);
        numberButton=(ElegantNumberButton) findViewById(R.id.recipe_Quantity);
       // button=(Button)findViewById(R.id.test_itemId);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        getData_intent();
    }

    void getData_intent(){
        progressDialog.show();
        Bundle intent=getIntent().getExtras();
       String id= intent.getString("recipe_id");
       String name= intent.getString("recipe_name");
        quantity= intent.getInt("recipe_quantity");
         price= intent.getInt("recipe_price");
       String deatils= intent.getString("recipe_datils");
       String date= intent.getString("recipe_date");
       String image= intent.getString("recipe_image");

       textViewId.setText("Recipe No:"+id);
       textViewRe_name.setText("Name:"+name);
     // textView_deatils.setText("Deatils:"+deatils);
       textView_price.setText("price:"+price);
       textView_deatils.setText("Deatils:"+deatils);
       textView_quantity.setText("Quantity:"+quantity);
       textView_Date.setText("Expired Date:"+date);
        List<SlideModel>slideModels=new ArrayList<>();
        slideModels.add(new SlideModel(image,name, ScaleTypes.FIT));
        slideModels.add(new SlideModel(image,name, ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModels,ScaleTypes.FIT);
        progressDialog.dismiss();
    }

    public void Selling_datasave(View view) {
        SharedPrefer_user_gmali = getApplicationContext().getSharedPreferences("com.example.hotel_managment", Context.MODE_PRIVATE);
        user_gmail= SharedPrefer_user_gmali.getString("user_gmail","User Gmail Not Found..");

        final int num_Quantity = Integer.parseInt(numberButton.getNumber().toString());
        final int total_quan;

        progressDialog.show();
        Bundle bundle=getIntent().getExtras();
        final String re_id=textViewId.getText().toString();
        final String name=textViewRe_name.getText().toString();
        final String date=textView_Date.getText().toString();
        final String image= bundle.getString("recipe_image");

        if(quantity<num_Quantity){
            Toast.makeText(Recipe_category.this, "Your Quantity is Over...", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }else {
            quantity = (quantity - num_Quantity);
            total_price = (price * num_Quantity);
            textView_quantity.setText("quantity :" + quantity);
            progressDialog.show();

// Read from the database
            user_romCheck_databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        Reservation_customarInfo roomItem=dataSnapshot1.getValue(Reservation_customarInfo.class);
                        //    roomItem.setData_Key(dataSnapshot1.getKey());
                        if(user_gmail.contains(roomItem.getUser_Gmail())){
                            String user_room_Id= roomItem.getRes_RoomNo();
                            // roomItem.getCut_phon();
                            Recipe_categoryModel item = new Recipe_categoryModel(re_id, name, date, num_Quantity, total_price,user_gmail,user_room_Id);
                            String key = databaseReference.push().getKey();
                            databaseReference.child(key).setValue(item)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // Read from the database selling data
                                            // Toast.makeText(Recipe_category.this, ".", Toast.LENGTH_SHORT).show();
                                            database_quantitySet.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    HashMap<String, Object> hashMap = new HashMap<>();
                                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                        recipe_Item = dataSnapshot1.getValue(RecipeModel.class);

                                                        recipe_Item.setRecipe_data_key(dataSnapshot1.getKey());
                                                        hashMap.put("recipe_quantity", quantity);
                                                    }
                                                    recipe_Item.setRecipe_quantity(quantity);
                                                    database_quantitySet.child(recipe_Item.getRecipe_data_key()).updateChildren(hashMap);
                                                    Toast.makeText(Recipe_category.this, "Your Recipe Order Category Select SuccessFull..", Toast.LENGTH_LONG).show();

                                                    progressDialog.dismiss();
                                                }
                                                @Override
                                                public void onCancelled(DatabaseError error) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(Recipe_category.this, "Your Recipe Order Failde..." + error.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }
                                    });
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(Recipe_category.this, "please Your Room check In", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(Recipe_category.this, "Your Data Not Found...", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }



    public void Add_to_card(View view) {
        SharedPrefer_user_gmali = getApplicationContext().getSharedPreferences("com.example.hotel_managment", Context.MODE_PRIVATE);
        user_gmail= SharedPrefer_user_gmali.getString("user_gmail","User Gmail Not Found..");

        final int num_Quantity = Integer.parseInt(numberButton.getNumber().toString());
        final int total_quan;

       progressDialog.show();
      Bundle bundle=getIntent().getExtras();
      final String re_id=textViewId.getText().toString();
       final String name=textViewRe_name.getText().toString();
       final String date=textView_Date.getText().toString();
      final String image= bundle.getString("recipe_image");

        if(quantity<num_Quantity){
            Toast.makeText(Recipe_category.this, "Your Quantity is Over...", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }else {
            quantity = (quantity - num_Quantity);
            total_price = (price * num_Quantity);
            textView_quantity.setText("quantity :" + quantity);
            progressDialog.show();

// Read from the database
       user_romCheck_databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        Reservation_customarInfo roomItem=dataSnapshot1.getValue(Reservation_customarInfo.class);
                    //    roomItem.setData_Key(dataSnapshot1.getKey());
                      if(user_gmail.contains(roomItem.getUser_Gmail())){
                         String user_room_Id= roomItem.getRes_RoomNo();
                        // roomItem.getCut_phon();
                        Recipe_categoryModel item = new Recipe_categoryModel(re_id, name, date, num_Quantity, total_price,user_gmail,user_room_Id);
                        String key = databaseReference.push().getKey();
                     databaseReference.child(key).setValue(item)
                                     .addOnSuccessListener(new OnSuccessListener<Void>() {
                                         @Override
                                         public void onSuccess(Void aVoid) {
                                             // Read from the database selling data
                                             // Toast.makeText(Recipe_category.this, ".", Toast.LENGTH_SHORT).show();
                                             database_quantitySet.addValueEventListener(new ValueEventListener() {
                                                 @Override
                                                 public void onDataChange(DataSnapshot dataSnapshot) {
                                                     HashMap<String, Object> hashMap = new HashMap<>();
                                                     for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                         recipe_Item = dataSnapshot1.getValue(RecipeModel.class);

                                                         recipe_Item.setRecipe_data_key(dataSnapshot1.getKey());
                                                         hashMap.put("recipe_quantity", quantity);
                                                     }
                                                     recipe_Item.setRecipe_quantity(quantity);
                                                     database_quantitySet.child(recipe_Item.getRecipe_data_key()).updateChildren(hashMap);
                                                     Toast.makeText(Recipe_category.this, "Your Recipe Order Category Select SuccessFull..", Toast.LENGTH_LONG).show();

                                                     progressDialog.dismiss();
                                                 }
                                       @Override
                                         public void onCancelled(DatabaseError error) {
                                         progressDialog.dismiss();
                       Toast.makeText(Recipe_category.this, "Your Recipe Order Failde..." + error.getMessage(), Toast.LENGTH_SHORT).show();
                                                 }
                                             });

                                         }
                                     });
                         }else{
                             progressDialog.dismiss();
                 Toast.makeText(Recipe_category.this, "please Your Room check In", Toast.LENGTH_SHORT).show();
                 }
               }
                }
                @Override
                public void onCancelled(DatabaseError error) {
     Toast.makeText(Recipe_category.this, "Your Data Not Found...", Toast.LENGTH_SHORT).show();
                }
            });

             }
             }

    }
