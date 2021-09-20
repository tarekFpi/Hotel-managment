package com.example.hotel_managment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Recipe_payment_ShowList extends AppCompatActivity {
 private RecyclerView recyclerView;
 private Recipe_payment_Adapter adapter;
 private List<Recipe_payment_Modle>payment_modleList=new ArrayList<>();
 private DatabaseReference databaseRefer_Recipe_payment_Show;
 private EditText editText_search;
 private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_payment__show_list);
        this.setTitle("Recipe Payment Show");
    databaseRefer_Recipe_payment_Show= FirebaseDatabase.getInstance().getReference("recipe_payment_Data");
    recyclerView=(RecyclerView)findViewById(R.id.recipe_payment_recycler_id);
    editText_search=(EditText)findViewById(R.id.recipe_payment_search);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
     progressDialog=new ProgressDialog(this);
     progressDialog.setMessage("Plesase wite...");
     progressDialog.setCancelable(false);
     progressDialog.show();
        // Read from the database
        databaseRefer_Recipe_payment_Show.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                payment_modleList.clear();
          for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
             Recipe_payment_Modle  Item=dataSnapshot1.getValue(Recipe_payment_Modle.class);
              Item.setRecipe_payment_Key(dataSnapshot1.getKey());

              payment_modleList.add(Item);
           }
              adapter=new Recipe_payment_Adapter(getApplicationContext(),payment_modleList);
             recyclerView.setAdapter(adapter);
             progressDialog.dismiss();

             adapter.setOnItemClickListener(new Recipe_payment_Adapter.ItemClickListener() {
                 @Override
                 public void OnItemClickListener(int position) {
                     Recipe_payment_Modle item=payment_modleList.get(position);
                     String name=payment_modleList.get(position).getCustom_name();
  Toast.makeText(Recipe_payment_ShowList.this, "Customar Name:"+name, Toast.LENGTH_SHORT).show();
                 }

                 @Override
                 public void OnDelete(int position) {
                     Recipe_payment_Modle item=payment_modleList.get(position);
                     databaseRefer_Recipe_payment_Show.child(item.getRecipe_payment_Key()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                         @Override
                         public void onSuccess(Void aVoid) {
             Toast.makeText(Recipe_payment_ShowList.this, "User Information Delete..", Toast.LENGTH_SHORT).show();
                         }
                     }).addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception e) {
                             Toast.makeText(Recipe_payment_ShowList.this, "User Information Delete Faild..", Toast.LENGTH_SHORT).show();
                         }
                     });

                 }
             });
            }

            @Override
            public void onCancelled(DatabaseError error) {
       Toast.makeText(Recipe_payment_ShowList.this, "Sorry Your Recipe payment Upload Failde.!!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
        editText_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
             fileList(editable.toString());

            }
        });
    }

    private void fileList(String textData) {
        List<Recipe_payment_Modle>filter_list=new ArrayList<>();

        for(Recipe_payment_Modle item:payment_modleList){
            if(item.getRoom_id().toLowerCase().contains(textData.toLowerCase())){
                filter_list.add(item);
            }
            adapter.filter_setData(filter_list);
        }
    }

    public void recipe_payment_floating(View view) {
        Intent intent=new Intent(Recipe_payment_ShowList.this,Main_RestaurantPage.class);
        startActivity(intent);
        finish();
    }
}