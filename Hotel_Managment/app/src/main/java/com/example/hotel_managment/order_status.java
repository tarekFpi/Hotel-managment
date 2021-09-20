package com.example.hotel_managment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class order_status extends AppCompatActivity {
 private RecyclerView recyclerView;
 private List<Recipe_categoryModel>recipe_categoryModelList=new ArrayList<>();

 private DatabaseReference databaseReference;
 private DatabaseReference database_quantitySet;
 private DatabaseReference orderCard_databaseReference;
 private Recipe_categoryAdapter adapter;

 private TextView textView;
   int Total_price=0;
  static int qu=0;

private static int  quantity_new;
  private  RecipeModel recipe_Item;
    Recipe_categoryModel listget_Data ;
    private ProgressDialog progressDialog;
    private LayoutInflater layoutInflater;
    private View view;
    private CheckBox checkBox;
    private SharedPreferences SharedPrefer_user_gmali;
    private String user_gmail;

    //private List<RecipeModel>recipeModelList=new ArrayList<RecipeModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        this.setTitle("Recipe Home page");
        databaseReference = FirebaseDatabase.getInstance().getReference("recipe_category");
        database_quantitySet = FirebaseDatabase.getInstance().getReference("Recipe_upload");

      //  orderCard_databaseReference = FirebaseDatabase.getInstance().getReference("Recipe_orderCard");
        recyclerView = (RecyclerView) findViewById(R.id.order_show_recycler);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

     /*   layoutInflater = LayoutInflater.from(this);
        view = layoutInflater.inflate(R.layout.recipe_category_item, null);
        checkBox = view.findViewById(R.id.recipe_item_check);
*/
        SharedPrefer_user_gmali = getApplicationContext().getSharedPreferences("com.example.hotel_managment", Context.MODE_PRIVATE);
        user_gmail= SharedPrefer_user_gmali.getString("user_gmail","User Gmail Not Found..");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Read from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                recipe_categoryModelList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Recipe_categoryModel item = dataSnapshot1.getValue(Recipe_categoryModel.class);
                    item.setFirebase_dataKey(dataSnapshot1.getKey());
                    ///Total_price = item.getRecipe_totalPrice()+item.getRecipe_quantity()+Total_price;
                   /// qu = item.getRecipe_quantity() +qu;
                    recipe_categoryModelList.add(item);
                }
               /// textView.setText("Total: "+Total_price);
                adapter = new Recipe_categoryAdapter(getApplicationContext(), recipe_categoryModelList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
                adapter.setOnItemClickListener(new Recipe_categoryAdapter.ItemClickListener() {
                    @Override
                    public void OnItemClickListener(int Position) {
                        StringBuffer stringBuffer=new StringBuffer();
                        Recipe_categoryModel getItem=recipe_categoryModelList.get(Position);

                        String value = recipe_categoryModelList.get(Position).getRecipe_Name();
                    Toast.makeText(order_status.this, value, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(order_status.this, Recepic_card.class);

                        stringBuffer.append("Id ="+getItem.getRecipe_id()+",");
                        stringBuffer.append("Name="+getItem.getRecipe_Name()+",");
                        stringBuffer.append("Quantity="+getItem.getRecipe_quantity()+"/");
                        intent.putExtra("äll_order", (Serializable) stringBuffer);
                        intent.putExtra("Total_price",getItem.getRecipe_totalPrice());
                        intent.putExtra("Room_Id",getItem.getUser_room_id());
                        intent.putExtra("list_position",recipe_categoryModelList.get(Position).getFirebase_dataKey());
                        startActivity(intent);
           //  stringBuffer.append("Discount="+recipe_categoryModelList.getCategory_Shose_discount()+"/");
                    }

                    @Override
                    public void OnDelete(int position) {

                      final Recipe_categoryModel item_position = recipe_categoryModelList.get(position);
                          //  final int  set_quantity=item_position.setRecipe_quantity(quantity_new);
                        // Read from the database
                        database_quantitySet.child(item_position.getFirebase_dataKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
             Toast.makeText(order_status.this, "Your Order Delete SuccessFull..", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {
            Toast.makeText(getApplicationContext(), "Category Failde.."+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

     public void place_order_data(View view) {
      // Read from the database
     if (recipe_categoryModelList.isEmpty()) {
       Toast.makeText(this, "Recipe Not Order", Toast.LENGTH_SHORT).show();
        } else {
       Recipe_order_pay();
        }
     }


    void Recipe_order_pay() {

/*
                  final ProgressDialog progressDialog = new ProgressDialog(this);
                  progressDialog.setMessage("please wait...");
                  progressDialog.setCancelable(false);
                  progressDialog.show();

                  databaseReference.addValueEventListener(new ValueEventListener() {
                      @Override
                      public void onDataChange(DataSnapshot dataSnapshot) {
                          // recipe_categoryModelList.clear();
                        recipe_categoryModelList.clear();
                          for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                              listget_Data = dataSnapshot1.getValue(Recipe_categoryModel.class);
                              listget_Data.setFirebase_dataKey(dataSnapshot1.getKey());
                          }
                          // Recipe_categoryModel categoryModel=new Recipe_categoryModel();
                          final String id = listget_Data.getRecipe_id();
                          final String name = listget_Data.getRecipe_Name();
                          //  final int quan = listget_Data.getRecipe_quantity();
                          //int item_pise = listget_Data.getRecipe_totalPrice();
                          int total_price =Total_price;
                          // item_pise=p+item_pise;
                          if (TextUtils.isEmpty(id)) {
                              Toast.makeText(order_status.this, "Recipe  Id  Empty..", Toast.LENGTH_SHORT).show();
                          } else {


                              Recipe_cardModel cardModel = new Recipe_cardModel(id, name, qu, total_price);
                              String key = orderCard_databaseReference.push().getKey();
                              Toast.makeText(order_status.this, "quantity:"+qu+"totaPrice:"+total_price, Toast.LENGTH_SHORT).show();
                              orderCard_databaseReference.child(key).setValue(cardModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                  @Override
                                  public void onSuccess(Void aVoid) {
                                      //array list clear
                                      // Read from the database
                                      databaseReference.addValueEventListener(new ValueEventListener() {
                                          @Override
                                          public void onDataChange(DataSnapshot dataSnapshot) {
                                              recipe_categoryModelList.clear();
                                              for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                   listget_Data = dataSnapshot1.getValue(Recipe_categoryModel.class);
                                                  listget_Data.setFirebase_dataKey(dataSnapshot1.getKey());

                                         *//* recipe_categoryModelList.remove(item.getFirebase_dataKey());
              databaseReference.child(item.getFirebase_dataKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                      public void onSuccess(Void aVoid) {
                                         Toast.makeText(order_status.this, "Data is Clear..", Toast.LENGTH_SHORT).show();
                                           textView.setText("Total:0 ");
                                                      }
                                                  }); *//*
                                              }


                            databaseReference.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                  public void onSuccess(Void aVoid) {
                                        progressDialog.dismiss();
                            Toast.makeText(order_status.this, "Order Card SuccessFull..", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                             progressDialog.dismiss();
                                              Intent intent = new Intent(order_status.this, Recepic_card.class);
                                              startActivity(intent);
                                          }

                                          @Override
                                          public void onCancelled(DatabaseError error) {
                                              progressDialog.dismiss();
                                              Toast.makeText(order_status.this, "Error:" + error, Toast.LENGTH_SHORT).show();
                                          }
                                      });

                                  }
                              }).addOnFailureListener(new OnFailureListener() {
                                  @Override
                                  public void onFailure(@NonNull Exception e) {
                                      progressDialog.dismiss();
                                      Toast.makeText(order_status.this, "Order Card UnSuccessFull..", Toast.LENGTH_SHORT).show();

                                  }
                              });
                          }
                      }

                      @Override
                      public void onCancelled(DatabaseError error) {
                          progressDialog.dismiss();
                          Toast.makeText(order_status.this, "Order Transaction Failde..!!" + error.getMessage(), Toast.LENGTH_SHORT).show();
                      }
                  });*/
              }

            }

