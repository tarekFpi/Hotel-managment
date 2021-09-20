package com.example.hotel_managment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User_informatio_show extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Sign_upModel> UserSignUp_ModleList=new ArrayList<>();
    private User_inform_Adapter adapter;
    private EditText editText_Search;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference_sign_up;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_informatio_show);
        recyclerView=(RecyclerView)findViewById(R.id.rcycler_user_infor);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wite...");
        progressDialog.setCancelable(false);
       // progressDialog.show();
        databaseReference_sign_up= FirebaseDatabase.getInstance().getReference("Sign_upData");

        // Read from the database
        databaseReference_sign_up.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              UserSignUp_ModleList.clear();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Sign_upModel user_item=dataSnapshot1.getValue(Sign_upModel.class);
                user_item.setUser_key(dataSnapshot1.getKey());
                  UserSignUp_ModleList.add(user_item);
                }
                adapter=new User_inform_Adapter(getApplicationContext(),UserSignUp_ModleList);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();
                adapter.setOnItemClickListener(new User_inform_Adapter.onItemClickItem() {
                    @Override
                    public void onClick(int position) {
                    String UserName=UserSignUp_ModleList.get(position).getUser_name();
                 Toast.makeText(User_informatio_show.this, "User:"+UserName, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDelete(int position) {
                        Sign_upModel item_position=UserSignUp_ModleList.get(position);
                      String remove_item= item_position.getUser_key();
                      databaseReference_sign_up.child(remove_item).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                          @Override
                          public void onSuccess(Void aVoid) {
                      Toast.makeText(User_informatio_show.this, "your Data Delete SuccessFull..", Toast.LENGTH_SHORT).show();
                          }
                      }).addOnFailureListener(new OnFailureListener() {
                          @Override
                          public void onFailure(@NonNull Exception e) {
                              progressDialog.dismiss();
                    Toast.makeText(User_informatio_show.this, "your Data Delete UnSuccessFull..", Toast.LENGTH_SHORT).show();
                          }
                      });

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {
    Toast.makeText(User_informatio_show.this, "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}