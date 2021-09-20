package com.example.hotel_managment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.List;

public class User_leve_cheeck_inRoomShow extends AppCompatActivity {
    private RecyclerView recyclerViewCheck_in;
    private User_level_checkIn_roomAdapter adapter;
    private DatabaseReference databaseReference;
    private ImageView imageView;
    private List<Reservation_customarInfo> User_roomModleList=new ArrayList<Reservation_customarInfo>();
    private ProgressDialog progressDialog;
    private EditText User_editText_search;
    private SharedPreferences SharedPrefer_user_gmali;
    private String user_gmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_leve_cheeck_in_room_show);

        databaseReference= FirebaseDatabase.getInstance().getReference("Reservation_roomCheek_In");
        recyclerViewCheck_in=(RecyclerView)findViewById(R.id.Userlevel_recycler_check_inroom);
        User_editText_search=(EditText)findViewById(R.id.User_Check_in_search);
        recyclerViewCheck_in.setHasFixedSize(true);
        recyclerViewCheck_in.setLayoutManager(new LinearLayoutManager(this));

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Loding wite....");
        progressDialog.show();

        SharedPrefer_user_gmali = getApplicationContext().getSharedPreferences("com.example.hotel_managment", Context.MODE_PRIVATE);
        user_gmail= SharedPrefer_user_gmali.getString("user_gmail","User Gmail Not Found..");
        adapter=new User_level_checkIn_roomAdapter(getApplicationContext(),User_roomModleList);
        progressDialog.dismiss();
        // Read from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User_roomModleList.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Reservation_customarInfo item=dataSnapshot1.getValue(Reservation_customarInfo.class);
                    item.setReservation_data_Key(dataSnapshot1.getKey());

                    if(user_gmail.contains(item.getUser_Gmail())){
                        User_roomModleList.add(item);
                        recyclerViewCheck_in.setAdapter(adapter);

                        adapter.setOnItemClickListener(new User_level_checkIn_roomAdapter.onItemClickListener() {
                            @Override
                            public void onClickListener(int position) {
                     String CustomaName=User_roomModleList.get(position).getCut_name();
                 Toast.makeText(User_leve_cheeck_inRoomShow.this, "Name:"+CustomaName, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
            Toast.makeText(User_leve_cheeck_inRoomShow.this, "Please Your Room Booking ..", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
       Toast.makeText(User_leve_cheeck_inRoomShow.this, "Your Data Loding Failde.."+error.getMessage(),Toast.LENGTH_SHORT).show();
       progressDialog.dismiss();
            }
        });


        User_editText_search.addTextChangedListener(new TextWatcher() {
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
        List<Reservation_customarInfo>filtesr_data=new ArrayList<>();

        for(Reservation_customarInfo getItem : User_roomModleList){
            if(getItem.getCut_name().toLowerCase().contains(textData.toLowerCase())){

             filtesr_data.add(getItem);
            }
            adapter.filterList(filtesr_data);
        }

    }




}