package com.example.hotel_managment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
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

public class Room_RateShow extends AppCompatActivity {
private RecyclerView recyclerView;
private List<Room_addModel>room_ModleList=new ArrayList<>();
 private DatabaseReference databaseReference;
 private StorageReference storageReference;
 private Room_rateAdapter adapter;
 private FirebaseStorage firebaseStorage;
 private EditText editText_Search;
 private ProgressDialog progressDialog;
 private static String Mystatus=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room__rate_show);
       /* getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
*/      progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Lodding wite....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        databaseReference= FirebaseDatabase.getInstance().getReference("roomAdd");
      //  storageReference= FirebaseStorage.getInstance().getReference("room_RateImage");
        firebaseStorage=FirebaseStorage.getInstance();
        recyclerView=(RecyclerView)findViewById(R.id.recycler_room);
        editText_Search=(EditText)findViewById(R.id.edit_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                room_ModleList.clear();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Room_addModel roomItem=dataSnapshot1.getValue(Room_addModel.class);
                   roomItem.setData_Key(dataSnapshot1.getKey());
                    room_ModleList.add(roomItem);
                }
                adapter=new Room_rateAdapter(getApplicationContext(),room_ModleList);
               recyclerView.setAdapter(adapter);
                progressDialog.dismiss();

               adapter.setOnItemClickListener(new Room_rateAdapter.OnItemClickListener() {
                   @Override
               public void onItemClickListener(int position) {
                // Room_addModel Item_position=room_ModleList.get(position);
                 String badName=room_ModleList.get(position).getBadType();
                  Toast.makeText(Room_RateShow.this, "Bad Type :"+badName, Toast.LENGTH_SHORT).show();
                   Intent putdata=new Intent(Room_RateShow.this,Reservation_room.class);
                    putdata.putExtra("RoomId",room_ModleList.get(position).getRoomId());
                    putdata.putExtra("Room_typ",room_ModleList.get(position).getRoomType());
                    putdata.putExtra("badType",room_ModleList.get(position).getBadType());
                    putdata.putExtra("roomRate",room_ModleList.get(position).getRoom_price());
                    putdata.putExtra("image",room_ModleList.get(position).getRoom_image());
                       Room_addModel Item_position=room_ModleList.get(position);
                    putdata.putExtra("list_Delet_position",Item_position.getData_Key());  //getItem position putExtra
                     startActivity(putdata);
                   }

                   @Override
                   public void doAnytask(int position) {
              Toast.makeText(Room_RateShow.this, "Do Any Action", Toast.LENGTH_SHORT).show();
                   }

                   @Override
                   public void OnDelete(int position) {

                     final Room_addModel imagePosition=room_ModleList.get(position);
                   StorageReference imageRef=firebaseStorage.getReferenceFromUrl(imagePosition.getRoom_image());
                   imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void aVoid) {
                       String dataKey=imagePosition.getData_Key();
                       databaseReference.removeValue();
                           room_ModleList.remove(dataKey);
                  Toast.makeText(Room_RateShow.this, "your Data Delete SuccessFull..", Toast.LENGTH_SHORT).show();
                    }
                   });
                   }

                   @Override
                   public void onUpdate(int position) {
                  // final Room_addModel item_position=room_ModleList.get(position);

                   String badName=room_ModleList.get(position).getBadType();
               Toast.makeText(Room_RateShow.this, "Bad Type :"+badName, Toast.LENGTH_SHORT).show();
                      Intent putdata=new Intent(Room_RateShow.this,Room_Data_Update.class);
                       putdata.putExtra("RoomId",room_ModleList.get(position).getRoomId());
                       putdata.putExtra("Room_typ",room_ModleList.get(position).getRoomType());
                       putdata.putExtra("badType",room_ModleList.get(position).getBadType());
                       putdata.putExtra("roomRate",room_ModleList.get(position).getRoom_price());
                       putdata.putExtra("image",room_ModleList.get(position).getRoom_image());
                       putdata.putExtra("room_date",room_ModleList.get(position).getDate());

                       Room_addModel Item=room_ModleList.get(position);
                       putdata.putExtra("list_position",Item.getData_Key());  //getItem position putExtra
                       startActivity(putdata);
                   }
               });
            }

            @Override
            public void onCancelled(DatabaseError error) {
                progressDialog.dismiss();
        Toast.makeText(Room_RateShow.this, "Data Not Upload..", Toast.LENGTH_SHORT).show();
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

    private void filter(String text) {
        List<Room_addModel>filterList=new ArrayList<>();

        for(Room_addModel item: room_ModleList){
      if(item.getBadType().toLowerCase().contains(text.toLowerCase())){
          filterList.add(item);
        }
        }
        adapter.filterdList(filterList);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            this.finish();
        }
      return super.onOptionsItemSelected(item);
    }
}