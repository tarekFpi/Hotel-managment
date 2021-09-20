package com.example.hotel_managment;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Recipe_RateShow extends AppCompatActivity {
  private RecyclerView recyclerView;
  private List<RecipeModel>recipeModelList=new ArrayList<RecipeModel>();
  private Recipe_Adapter adapter;
  private DatabaseReference databaseReference;
  private FirebaseStorage firebaseStorage;
  private EditText editText_Search;
  private LayoutInflater layoutInflater;
  private View view;
   private AlertDialog builder;
   private EditText editText_recipeName,editText_recipe_quantity,editText_price;
   private Button button_up,button_exit;
   private ProgressDialog progressDialog;
  private int quantit,price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe__rate_show);

        databaseReference= FirebaseDatabase.getInstance().getReference("Recipe_upload");
        firebaseStorage=FirebaseStorage.getInstance();
        recyclerView=(RecyclerView)findViewById(R.id.recip_rateShow);
        editText_Search=(EditText)findViewById(R.id.recipe_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

         progressDialog=new ProgressDialog(this);
          progressDialog.setMessage("Please Wite...");
          progressDialog.setCancelable(false);
          progressDialog.show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recipeModelList.clear();
                RecipeModel Item;
              for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                  Item = dataSnapshot1.getValue(RecipeModel.class);
                  Item.setRecipe_data_key(dataSnapshot1.getKey());
                  recipeModelList.add(Item);
              }
              adapter=new Recipe_Adapter(getApplicationContext(),recipeModelList);
              recyclerView.setAdapter(adapter);

               progressDialog.dismiss();
              adapter.setOnItemClickListener(new Recipe_Adapter.onItemClickListener() {
                  @Override
                  public void onItemClick(int position) {
          String item_name=recipeModelList.get(position).getRecipe_Name();
             Toast.makeText(Recipe_RateShow.this, "Select :"+item_name, Toast.LENGTH_SHORT).show();
                      Intent putdata=new Intent(Recipe_RateShow.this,Recipe_category.class);
                     RecipeModel item_position=recipeModelList.get(position);
                     putdata.putExtra("recipe_id",recipeModelList.get(position).getRecipe_id());
                      putdata.putExtra("recipe_name",recipeModelList.get(position).getRecipe_Name());
                      putdata.putExtra("recipe_datils",recipeModelList.get(position).getRecipe_Details());
                      putdata.putExtra("recipe_price",recipeModelList.get(position).getRecipe_price());
                      putdata.putExtra("recipe_quantity",recipeModelList.get(position).getRecipe_quantity());
                      putdata.putExtra("recipe_image",recipeModelList.get(position).getRecipe_image());
                      putdata.putExtra("recipe_date",recipeModelList.get(position).getRecipe_date());
                    putdata.putExtra("list_position",item_position);
                    startActivity(putdata);
                  }

                  @Override
                  public void onUpdate(int position) {
               final RecipeModel imagePosition=recipeModelList.get(position);

                   builder= new AlertDialog.Builder(Recipe_RateShow.this).create();
                  layoutInflater=getLayoutInflater();
                   view= layoutInflater.inflate(R.layout.recipe_update,null);
                   builder.setView(view);
               editText_recipeName= view.findViewById(R.id.recipe_name_update);
                editText_recipe_quantity=view.findViewById(R.id.recipe_quantity_update);
                editText_price=view.findViewById(R.id.recipe_price_update);

                button_up=view.findViewById(R.id.recipe_Update_dataSave);
                button_exit=view.findViewById(R.id.recipe_alert_exit);

                      editText_recipeName.setText(imagePosition.getRecipe_Name());
                      editText_recipe_quantity.setText(""+imagePosition.getRecipe_quantity());
                      editText_price.setText(""+imagePosition.getRecipe_price());

                   builder.show();
                   builder.setCancelable(false);
                      button_up.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                              progressDialog.show();
                              if(editText_recipeName.getText().toString().isEmpty()){
                                editText_recipeName.setError("Your Recipe Name is Empty...!!");
                                  editText_recipeName.requestFocus();
                                  progressDialog.dismiss();
                              }else if(editText_recipe_quantity.getText().toString().isEmpty()){
                                  editText_recipe_quantity.setError("Your Recipe Quantity is Empty...!!");
                                  editText_recipe_quantity.requestFocus();
                                  progressDialog.dismiss();
                              }else if(editText_price.getText().toString().isEmpty()){
                                  editText_price.setError("Your Recipe price is Empty...!!");
                                  editText_price.requestFocus();
                                  progressDialog.dismiss();
                              }else{
                                  progressDialog.show();
                              HashMap<String,Object>hashMap=new HashMap<>();
                                price=Integer.parseInt(editText_price.getText().toString());
                             quantit=Integer.parseInt(editText_recipe_quantity.getText().toString());
                              hashMap.put("recipe_Name",editText_recipeName.getText().toString());
                              hashMap.put("recipe_price",price);
                              hashMap.put("recipe_quantity",quantit);
                              databaseReference.child(imagePosition.getRecipe_data_key()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                  @Override
                                  public void onSuccess(Void aVoid) {
                             Toast.makeText(Recipe_RateShow.this, "Your Data Update SuccessFull..", Toast.LENGTH_SHORT).show();

                                     editText_price.setText("");
                                     editText_recipe_quantity.setText("");
                                     editText_recipeName.setText("");
                                     builder.dismiss();
                                     progressDialog.dismiss();
                                  }
                              }).addOnFailureListener(new OnFailureListener() {
                                  @Override
                                  public void onFailure(@NonNull Exception e) {
                                      Toast.makeText(Recipe_RateShow.this, "Your Data Update Failde..", Toast.LENGTH_SHORT).show();
                                      builder.dismiss();
                                  }
                              });
                          }
                          }
                      });
                      button_exit.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                          builder.dismiss();
                          progressDialog.dismiss();
                          }
                      });
                  }

                  @Override
                  public void onDelete(int position) {
                  final RecipeModel imagePosition=recipeModelList.get(position);
                  StorageReference storageRefeImage=firebaseStorage.getReferenceFromUrl(imagePosition.getRecipe_image());
                  storageRefeImage.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                  @Override
                  public void onSuccess(Void aVoid) {
                          String key=imagePosition.getRecipe_data_key();
                          databaseReference.child(key).removeValue();
                          //Recipe_RateShow.this.notify();
             Toast.makeText(Recipe_RateShow.this, "Your Data Delete SuccessFull...", Toast.LENGTH_SHORT).show();
                      }
                  });
                  }

              });
            }

            @Override
            public void onCancelled(DatabaseError error) {
       Toast.makeText(Recipe_RateShow.this, "Your Data Failde..."+error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

        editText_Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
             filter(editable.toString());
            }
        });
    }



    private void filter(String textData) {
     List<RecipeModel>filterList=new ArrayList<>();
     for(RecipeModel item: recipeModelList){
       if(item.getRecipe_Name().toLowerCase().contains(textData.toLowerCase())){
           filterList.add(item);
       }
     }
     adapter.filterdList(filterList);
    }

}