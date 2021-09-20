package com.example.hotel_managment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
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

public class Room_Data_Update extends AppCompatActivity {
private TextView textView_roomId,textView_roomType,textView_bad_type,textView_price,textView_bookingDate;
private ImageSlider imageSlider;
private DatabaseReference databaseRefe_update;
private StorageReference storageReference_update;
 private Room_addModel room_addModel;
 private EditText editText_roomType,editText_badType,editText_price;
 private ProgressDialog progressDialog;
    //int total_price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room__data__update);
        imageSlider=(ImageSlider)findViewById(R.id.room_up_Image);
        databaseRefe_update= FirebaseDatabase.getInstance().getReference("roomAdd");
       // storageReference_update= FirebaseStorage.getInstance().getReference("roomAdd");
        textView_roomId=(TextView)findViewById(R.id.room_update_id);
        textView_roomType=(TextView)findViewById(R.id.room_type_update);
        textView_bad_type=(TextView)findViewById(R.id.bad_type_update);
        textView_price=(TextView)findViewById(R.id.room_rate_update);
        textView_bookingDate=(TextView)findViewById(R.id.booking_date);

        editText_roomType=(EditText)findViewById(R.id.edi__rmType);
        editText_badType=(EditText)findViewById(R.id.edit_badType);
        editText_price=(EditText)findViewById(R.id.edit_room_price);

        getData_intent();
    }
    void getData_intent(){
      //  progressDialog.show();
        Bundle intent=getIntent().getExtras();
        String id= intent.getString("RoomId");
        String room_typ= intent.getString("Room_typ");
        String badType= intent.getString("badType");
        String date= intent.getString("room_date");
        String image= intent.getString("image");
         String room_price=intent.getString("roomRate");

        textView_roomId.setText("Room No:"+id);
        textView_roomType.setText("Room Type:"+room_typ);
        textView_price.setText("Price:"+room_price);
         //total_price.setText("price:"+price);
        textView_bookingDate.setText("Date:"+date);
        textView_bad_type.setText("Bad Type:"+badType);

        List<SlideModel> slideModels=new ArrayList<>();

        slideModels.add(new SlideModel(image,badType, ScaleTypes.FIT));
        slideModels.add(new SlideModel(image,badType, ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModels,ScaleTypes.FIT);
    }
    public void floating_recipe_rateShowPage(View view) {
        Intent intent=new Intent(Room_Data_Update.this,Room_RateShow.class);
        startActivity(intent);
        finish();
    }

    public void Update_dataSet(View view) {
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wite....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        // Read from the database
        databaseRefe_update.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               String room_type= editText_roomType.getText().toString().toString();
                String bad_type=  editText_badType.getText().toString().toString();
                String room_price= editText_price.getText().toString().toString();

                if(editText_roomType.getText().toString().isEmpty()){
                    editText_roomType.setError("Please Your Room Type Empty..");
                    editText_roomType.requestFocus();
                    progressDialog.dismiss();
                }else if(editText_badType.getText().toString().isEmpty()) {
                    editText_badType.setError("Please Your Bad Type Empty..");
                    editText_badType.requestFocus();
                    progressDialog.dismiss();
                }else if(editText_price.getText().toString().isEmpty()) {
                    editText_price.setError("Please Your Bad Type Empty..");
                    editText_price.requestFocus();
                    progressDialog.dismiss();
                }else {
                    progressDialog.show();
                    HashMap<String, Object> hashMap = new HashMap<>();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        room_addModel = dataSnapshot1.getValue(Room_addModel.class);
                        room_addModel.setData_Key(dataSnapshot1.getKey());

                        hashMap.put("roomType", editText_roomType.getText().toString());
                        hashMap.put("badType", editText_badType.getText().toString());
                        hashMap.put("room_price", editText_price.getText().toString());
                        databaseRefe_update.child(room_addModel.getData_Key()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
               Toast.makeText(Room_Data_Update.this, "Your Data Update SuccessFull..", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
              Toast.makeText(Room_Data_Update.this, "Your Data Update USuccessFull....:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    textView_bad_type.setText("Room Bad:" + editText_badType.getText().toString());
                    textView_roomType.setText("Room Type:" + editText_roomType.getText().toString());
                    textView_price.setText("Price:" + editText_price.getText().toString());
                    editText_price.setText("");
                    editText_roomType.setText("");
                    editText_badType.setText("");
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
       Toast.makeText(Room_Data_Update.this, "your Data Update Failde.."+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}