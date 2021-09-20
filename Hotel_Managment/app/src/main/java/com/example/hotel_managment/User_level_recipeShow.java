package com.example.hotel_managment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class User_level_recipeShow extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<RecipeModel> User_recipeModelList=new ArrayList<RecipeModel>();
    private User_level_recipe_Adapter adapter;

    private DatabaseReference databaseReference;
    private EditText editText_Search;
    private EditText editText_recipeName,editText_recipe_quantity,editText_price;
    private ProgressDialog progressDialog;
    private int quantit,price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_level_recipe_show);

        databaseReference= FirebaseDatabase.getInstance().getReference("Recipe_upload");

        recyclerView=(RecyclerView)findViewById(R.id.User_recip_rateShow);
        editText_Search=(EditText)findViewById(R.id.User_recipe_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wite...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Read from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User_recipeModelList.clear();
                RecipeModel Item;

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Item = dataSnapshot1.getValue(RecipeModel.class);
                    Item.setRecipe_data_key(dataSnapshot1.getKey());
                    User_recipeModelList.add(Item);
                }
          adapter=new User_level_recipe_Adapter(getApplicationContext(),User_recipeModelList);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();

                adapter.setOnItemClickListener(new User_level_recipe_Adapter.onItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        String item_name=User_recipeModelList.get(position).getRecipe_Name();
                        Toast.makeText(User_level_recipeShow.this, "Select :"+item_name, Toast.LENGTH_SHORT).show();
                        Intent putdata=new Intent(User_level_recipeShow.this,Recipe_category.class);
                        RecipeModel item_position=User_recipeModelList.get(position);
                        putdata.putExtra("recipe_id",User_recipeModelList.get(position).getRecipe_id());
                        putdata.putExtra("recipe_name",User_recipeModelList.get(position).getRecipe_Name());
                        putdata.putExtra("recipe_datils",User_recipeModelList.get(position).getRecipe_Details());
                        putdata.putExtra("recipe_price",User_recipeModelList.get(position).getRecipe_price());
                        putdata.putExtra("recipe_quantity",User_recipeModelList.get(position).getRecipe_quantity());
                        putdata.putExtra("recipe_image",User_recipeModelList.get(position).getRecipe_image());
                        putdata.putExtra("recipe_date",User_recipeModelList.get(position).getRecipe_date());
                        putdata.putExtra("list_position",item_position);
                        startActivity(putdata);
                    }
                });

            }
            @Override
            public void onCancelled(DatabaseError error) {
     Toast.makeText(User_level_recipeShow.this, "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
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
        for(RecipeModel item: User_recipeModelList){
            if(item.getRecipe_Name().toLowerCase().contains(textData.toLowerCase())){
                filterList.add(item);
            }
        }
        adapter.filterdList(filterList);
    }
}