package com.example.hotel_managment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class User_ReservationLeval extends AppCompatActivity{
    private RecyclerView recyclerView;
    private List<Room_addModel> User_room_ModleList=new ArrayList<>();
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private User_level_roomRateAdapter adapter;
    private FirebaseStorage firebaseStorage;
    private EditText editText_Usr_Search;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__reservation_leval);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Lodding wite....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        databaseReference= FirebaseDatabase.getInstance().getReference("roomAdd");
        //  storageReference= FirebaseStorage.getInstance().getReference("room_RateImage");
        firebaseStorage=FirebaseStorage.getInstance();
        recyclerView=(RecyclerView)findViewById(R.id.User_recycler_room);
        editText_Usr_Search=(EditText)findViewById(R.id.User_edit_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User_room_ModleList.clear();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Room_addModel roomItem=dataSnapshot1.getValue(Room_addModel.class);
                    roomItem.setData_Key(dataSnapshot1.getKey());
                    User_room_ModleList.add(roomItem);
                }

                adapter= new User_level_roomRateAdapter(getApplicationContext(),User_room_ModleList);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();

                adapter.setOnItemClickListener(new User_level_roomRateAdapter.OnItemClickListener() {
                    @Override
                    public void onClickListener(int position) {
                        Room_addModel Item_position=User_room_ModleList.get(position);

                        String badName=User_room_ModleList.get(position).getBadType();
                        Toast.makeText(User_ReservationLeval.this, "Bad Type :"+badName, Toast.LENGTH_SHORT).show();
                        Intent putdata=new Intent(User_ReservationLeval.this,Reservation_room.class);
                        putdata.putExtra("RoomId",User_room_ModleList.get(position).getRoomId());
                        putdata.putExtra("Room_typ",User_room_ModleList.get(position).getRoomType());
                        putdata.putExtra("badType",User_room_ModleList.get(position).getBadType());
                        putdata.putExtra("roomRate",User_room_ModleList.get(position).getRoom_price());
                        putdata.putExtra("image",User_room_ModleList.get(position).getRoom_image());

                        putdata.putExtra("list_Delet_position",Item_position.getData_Key());  //getItem position putExtra
                        startActivity(putdata);
                    }
                });



           /*         @Override
                    public void onUpdate(int position) {
                        // final Room_addModel item_position=room_ModleList.get(position);

                 *//*       String badName=User_room_ModleList.get(position).getBadType();
                        Toast.makeText(Room_RateShow.this, "Bad Type :"+badName, Toast.LENGTH_SHORT).show();
                        Intent putdata=new Intent(Room_RateShow.this,Room_Data_Update.class);
                        putdata.putExtra("RoomId",User_room_ModleList.get(position).getRoomId());
                        putdata.putExtra("Room_typ",User_room_ModleList.get(position).getRoomType());
                        putdata.putExtra("badType",User_room_ModleList.get(position).getBadType());
                        putdata.putExtra("roomRate",User_room_ModleList.get(position).getRoom_price());
                        putdata.putExtra("image",User_room_ModleList.get(position).getRoom_image());
                        putdata.putExtra("room_date",User_room_ModleList.get(position).getDate());

                        Room_addModel Item=User_room_ModleList.get(position);
                        putdata.putExtra("list_position",Item.getData_Key());  //getItem position putExtra
                        startActivity(putdata);*//*
                    }*/
                }


            @Override
            public void onCancelled(DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(User_ReservationLeval.this, "Error"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}