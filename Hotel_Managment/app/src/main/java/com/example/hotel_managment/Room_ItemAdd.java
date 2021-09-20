package com.example.hotel_managment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.ProxyFileDescriptorCallback;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Room_ItemAdd extends AppCompatActivity {
    private EditText editText_roomNo,editText_roomType,editText_badType,editText_price;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private TextView textViewDate;
    private Uri uri_image;
    private StorageReference storageReference;
    private static int requestImageCode=1;
    private ImageView imageView;
    private  Room_addModel getItem;
      private  ProgressDialog progressDialog;
      private  Room_addModel get_item;
      private static String add_room_id=null;
      private static  SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room__item_add);
        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/
       databaseReference=FirebaseDatabase.getInstance().getReference("roomAdd");
       storageReference= FirebaseStorage.getInstance().getReference("roomAdd");
        editText_roomNo=(EditText)findViewById(R.id.room_no);
        editText_roomType=(EditText)findViewById(R.id.room_type);
        editText_badType=(EditText)findViewById(R.id.bad_type);
        editText_price=(EditText)findViewById(R.id.room_price);
        imageView=(ImageView)findViewById(R.id.room_addImage);
       //date and time
       textViewDate=(TextView)findViewById(R.id.text_data);

        Date date_checkOut=new Date();
        SimpleDateFormat sm=new SimpleDateFormat("YYYY-MM-dd");
        String currentData= sm.format(date_checkOut.getTime());
       textViewDate.setText(currentData);

         progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Lodding....");
        progressDialog.setCancelable(false);

 sharedPreferences=getApplicationContext().
 getSharedPreferences("com.example.hotel_managment", Context.MODE_PRIVATE);
    }

/*    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {  //action bar backButton add mathod
        if(item.getItemId()==android.R.id.home){
         this.finish();
        }
        return super.onOptionsItemSelected(item);
    }*/

    public void roomAdd_chooseImag(View view) {
        chooseImage();
    }
    private void chooseImage(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,requestImageCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestImageCode==requestCode && resultCode==RESULT_OK && data!=null && data.getData()!=null){
          uri_image= data.getData();
          Picasso.get().load(uri_image).into(imageView);
        }
    }


    @Override
    protected void onStart() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    get_item=dataSnapshot1.getValue(Room_addModel.class);
                    get_item.setData_Key(dataSnapshot1.getKey());

                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("match_room_id",get_item.getRoomId()).apply();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(Room_ItemAdd.this, "Not Match:"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
      super.onStart();
    }

    public void room_seveData(View view) {

       // Match_roomId();


        progressDialog.show();
        final String roomid = editText_roomNo.getText().toString().trim();
        final String roomType = editText_roomType.getText().toString().trim();
        final String badtype = editText_badType.getText().toString().trim();
        final String price = editText_price.getText().toString();
       // int price = Integer.parseInt(editText_price.getText().toString());
        final String date_current=textViewDate.getText().toString().trim();

        if (editText_roomNo.getText().toString().isEmpty()) {
             editText_roomNo.setError("Room ID is Empty");
            editText_roomNo.requestFocus();
            progressDialog.dismiss();
        } else if (editText_roomType.getText().toString().isEmpty()) {
             editText_roomType.setError("Room Type is Empty..");
            editText_roomType.requestFocus();
            progressDialog.dismiss();
        } else if (editText_badType.getText().toString().isEmpty()) {

            editText_badType.setError("Bad Type is Empty..");
            editText_badType.requestFocus();
            progressDialog.dismiss();
        } else if (editText_price.getText().toString().isEmpty()) {
            editText_price.setError("Room price is Empty..");
            editText_price.requestFocus();
            progressDialog.dismiss();
        } else if (imageView.getDrawable()==null) {
            Toast.makeText(this, "Your Room Imgae is Empty...", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            imageView.requestFocus();
        }
        else {
            progressDialog.show();
            String rm_id = sharedPreferences.getString("match_room_id", null);

            if (rm_id.contains(editText_roomNo.getText().toString())) {
                Toast.makeText(this, "Your Room No Alrady Add...", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }else {

                StorageReference reference = storageReference.child(System.currentTimeMillis() + ".png");
                /// StorageReference reference=storageReference.child(System.currentTimeMillis()+""+getFileExtention(uri_image));

                reference.putFile(uri_image)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!uriTask.isComplete()) ;
                                final Uri uridowanload = uriTask.getResult();


                                final Room_addModel itemData = new Room_addModel(roomid, roomType, badtype, price, uridowanload.toString(), date_current);
                                final String key = databaseReference.push().getKey();
                                databaseReference.child(key).setValue(itemData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Room_ItemAdd.this, "Your Room Add SuccessFull..", Toast.LENGTH_SHORT).show();
                                        editText_roomNo.setText("");
                                        editText_roomType.setText("");
                                        editText_price.setText("");
                                        editText_badType.setText("");
                                        imageView.setImageResource(0);
                                        editText_roomNo.requestFocus();
                                        progressDialog.dismiss();
                                    }
                                });
                                progressDialog.dismiss();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                                Toast.makeText(Room_ItemAdd.this, "Your Room Add UnSuccessFull..", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        float percent = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        progressDialog.setMessage("please wait..." + (int) percent + "%");
                    }
                });
            }
        }
    }
}