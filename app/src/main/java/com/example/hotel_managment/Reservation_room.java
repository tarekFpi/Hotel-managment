package com.example.hotel_managment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import com.squareup.picasso.Target;

import java.io.File;
import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Reservation_room extends AppCompatActivity {
 private DatePickerDialog.OnDateSetListener mDateListener;
    private List<Room_addModel> room_remove=new ArrayList<>();
  private   TextView textViewDate,textViewRoom_no, textViewRoom_tp,textViewBad_ty,textViewRoom_rate;
  private TextView textViewCheeckIn;
  private ImageView imageView;
  private EditText editTextCustomarName,editTextAddress,editTextNadCard,editTextPhon;
  private DatabaseReference databaseReference;
  private DatabaseReference databaseRemove;
  private StorageReference storageRef_imageSave;
  private StorageReference storageReferemove;

  private FirebaseStorage firebaseStorage;
 private String listPosition;
  private Room_addModel item;
  private static int requestCord=1;
   private Uri uri_image;
   private ProgressDialog progressDialog;
private int room_Price;
private DatabaseReference databaseReference_match_user;
     String cust_phon;
private  check_out_rmModel getItem;

private String user_gmail;
private SharedPreferences SharedPrefer_user_gmali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_room);
        databaseReference_match_user= FirebaseDatabase.getInstance().getReference("Room_check_Out");

        databaseReference= FirebaseDatabase.getInstance().getReference("Reservation_roomCheek_In");
       storageRef_imageSave= FirebaseStorage.getInstance().getReference("Reservation_roomCheek_In");

        storageReferemove= FirebaseStorage.getInstance().getReference("roomAdd");
        databaseRemove= FirebaseDatabase.getInstance().getReference("roomAdd");
        firebaseStorage=FirebaseStorage.getInstance();
        imageView=(ImageView)findViewById(R.id.reservition_image);
        textViewRoom_no=(TextView)findViewById(R.id.room_id);
        textViewRoom_tp=(TextView)findViewById(R.id.room_tpId);
        textViewBad_ty=(TextView)findViewById(R.id.bad_tpId);
        textViewRoom_rate=(TextView)findViewById(R.id.price_rm);

        editTextCustomarName=(EditText)findViewById(R.id.edCut_name);
        editTextAddress=(EditText)findViewById(R.id.edcut_address);
        editTextNadCard=(EditText)findViewById(R.id.edcut_NadCard);
        editTextPhon=(EditText)findViewById(R.id.edcut_phonNumber);
        textViewCheeckIn=(TextView)findViewById(R.id.text_cheeckIn);
        //imageViewClock=(ImageView)findViewById(R.id.clock_imageId);
        textViewDate=(TextView)findViewById(R.id.text_date);
     //for Date
        Date date_checkOut=new Date();
        SimpleDateFormat sm=new SimpleDateFormat("YYYY-MM-dd");
        String currentData= sm.format(date_checkOut.getTime());
         textViewDate.setText(currentData);

       getData();

     /* editTextPhon.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              databaseReference_match_user.addValueEventListener(new ValueEventListener() {
                  @Override
                  public void onDataChange(DataSnapshot dataSnapshot) {

                      for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                          getItem = dataSnapshot1.getValue(check_out_rmModel.class);
                          getItem.setUser_data_key(dataSnapshot1.getKey());

                         *//* String test=getItem.getCust_phon();
                      Toast.makeText(Reservation_room.this, "Number:"+test, Toast.LENGTH_SHORT).show();*//*
                  }

                     if(getItem.getCust_phon().contains(cust_phon)){
                           editTextCustomarName.setText(""+getItem.getCustom_name());
                          Picasso.get().load(getItem.getCustom_image()).into(imageView);
                           editTextAddress.setText(""+getItem.getCustom_deatils());
                            editTextNadCard.setText(""+getItem.getNid_card());

                      }else{
                          Toast.makeText(Reservation_room.this, "Your Data Rong..", Toast.LENGTH_SHORT).show();
                      }
                  }

                  @Override
                  public void onCancelled(DatabaseError error) {
                      Toast.makeText(Reservation_room.this, "User Deatils Failde"+error.getMessage(), Toast.LENGTH_SHORT).show();
                  }
              });*/



    }

/*    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
          this.finish();
        }
        return super.onOptionsItemSelected(item);
    }*/

    public void resrvation_custom_image(View view) {
        fileChooser();
    }

   private void fileChooser(){
     Intent intent=new Intent();
     intent.setAction(Intent.ACTION_GET_CONTENT);
     intent.setType("image/*");
     startActivityForResult(intent,requestCord);
   }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
          if(requestCode==requestCord && resultCode==RESULT_OK && data!=null && data.getData()!=null) {
           uri_image= data.getData();
           Picasso.get().load(uri_image).into(imageView);
          }
    }

     @Override
    protected void onStart() {
          final String  cust_phon=editTextPhon.getText().toString();

       editTextPhon.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               databaseReference_match_user.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {

                       for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            getItem = dataSnapshot1.getValue(check_out_rmModel.class);
                           getItem.setUser_data_key(dataSnapshot1.getKey());
                       }
                       if(getItem.getCust_phon().contains(cust_phon)){
                          editTextCustomarName.setText(""+getItem.getCustom_name());
                           Picasso.get().load(getItem.getCustom_image()).into(imageView);
                            editTextAddress.setText(""+getItem.getCustom_deatils());
                            editTextNadCard.setText(""+getItem.getNid_card());

                            editTextAddress.setEnabled(false);
                            editTextCustomarName.setEnabled(false);
                            editTextNadCard.setEnabled(false);
                       }else{
                     Toast.makeText(Reservation_room.this, "Your Data Rong..", Toast.LENGTH_SHORT).show();
                       }
                   }

                   @Override
                   public void onCancelled(DatabaseError error) {
       Toast.makeText(Reservation_room.this, "User Deatils Failde"+error.getMessage(), Toast.LENGTH_SHORT).show();
                   }
               });
           }
       });

        super.onStart();
    }

    public void resrvation_save(View view) {
     SharedPrefer_user_gmali = getApplicationContext().getSharedPreferences("com.example.hotel_managment", Context.MODE_PRIVATE);
     user_gmail= SharedPrefer_user_gmali.getString("user_gmail","User Gmail Not Found..");

          progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("please wite...");
        progressDialog.show();
        final String cust_name = editTextCustomarName.getText().toString().trim();
        final String cust_address = editTextAddress.getText().toString().trim();
        final String cust_nad = editTextNadCard.getText().toString().trim();
          cust_phon = editTextPhon.getText().toString().trim();
        final String roomCheekIn = textViewCheeckIn.getText().toString().trim();

        final String roomId = textViewRoom_no.getText().toString().trim();
        final String roomtype = textViewRoom_tp.getText().toString().trim();
        final String badtype = textViewBad_ty.getText().toString().trim();
      //  final int roomPrice = Integer.parseInt(textViewRoom_rate.getText().toString());
        final String current_date = textViewDate.getText().toString().trim();
        // final String current_date= textView.getText().toString().trim();
        progressDialog.show();
       if(editTextCustomarName.getText().toString().isEmpty()){
         editTextCustomarName.setError("Custom Name is Empty...");
         editTextCustomarName.requestFocus();
           progressDialog.dismiss();
        }else if(editTextAddress.getText().toString().isEmpty()){
           editTextAddress.setError("Custom Address is Empty...");
           editTextAddress.requestFocus();
           progressDialog.dismiss();
        }else if(editTextPhon.getText().toString().isEmpty()){
           editTextPhon.setError("Custom phon is Empty...");
           editTextPhon.requestFocus();
           progressDialog.dismiss();
       }else if(editTextNadCard.getText().toString().isEmpty()){
           editTextNadCard.setError("Custom phon is Empty...");
           editTextNadCard.requestFocus();
           progressDialog.dismiss();
       }else if(editTextNadCard.length()<10){
           editTextNadCard.setError("Customar NID Number Invlide..");
           editTextNadCard.requestFocus();
           progressDialog.dismiss();
       }else if(editTextPhon.length()>11){
           editTextPhon.setError("Customar phon Number Invlide..");
           editTextPhon.requestFocus();
           progressDialog.dismiss();
       }
       else if(imageView.getDrawable()==null){
           Toast.makeText(this, "Customar Image is Empty..", Toast.LENGTH_SHORT).show();
           imageView.requestFocus();
           progressDialog.dismiss();
       }
       else {
           progressDialog.show();


           StorageReference imageRefer = storageRef_imageSave.child(System.currentTimeMillis() +".png");
          // StorageReference imageRefer = storageRef_imageSave.child(System.currentTimeMillis() +);

           imageRefer.putFile(uri_image)
                   .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           Task<Uri> taskUri = taskSnapshot.getStorage().getDownloadUrl();
                           while (!taskUri.isComplete());
                           Uri DowanloadImage = taskUri.getResult();

 Reservation_customarInfo customarInfo = new Reservation_customarInfo(roomId, roomtype, badtype, room_Price, DowanloadImage.toString(), cust_name,
      cust_address, cust_nad, cust_phon, current_date, roomCheekIn,user_gmail);

                           String key = databaseReference.push().getKey();
                           databaseReference.child(key).setValue(customarInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                               @Override
                               public void onSuccess(Void aVoid) {
                                   // Read from the database
                                   databaseRemove.addValueEventListener(new ValueEventListener() {
                                       @Override
                                       public void onDataChange(DataSnapshot dataSnapshot) {

                                   for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                         item = dataSnapshot1.getValue(Room_addModel.class);
                                         item.setData_Key(dataSnapshot1.getKey());
                                      }
                                           StorageReference imageRef = firebaseStorage.getReferenceFromUrl(item.getRoom_image());
                                           imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                               @Override
                                               public void onSuccess(Void aVoid) {
                                            //String dataKey = item.getData_Key();   // Old Data Delete
                                              databaseRemove.child(listPosition).removeValue();

                           Toast.makeText(Reservation_room.this, "Your Cheeck In SuccessFull..", Toast.LENGTH_SHORT).show();
                                                  editTextAddress.setText("");
                                                  editTextCustomarName.setText("");
                                                  editTextPhon.setText("");
                                                  editTextNadCard.setText("");
                                                  textViewRoom_no.setText("");
                                                  textViewRoom_tp.setText("");
                                                  textViewBad_ty.setText("");
                                                  textViewRoom_rate.setText("");
                                                  textViewDate.setText("");
                                                  imageView.setImageResource(0);
                                                   editTextCustomarName.requestFocus();
                                                     progressDialog.dismiss();
                                                  if(imageView!=null){
                                                   imageView.setImageResource(R.drawable.rm);
                                                  }
                                               }
                                           });
                                       }

                                       @Override
                                       public void onCancelled(DatabaseError error) {
                                           progressDialog.dismiss();
                            Toast.makeText(Reservation_room.this, "Your Cheeck In UnSuccessFull.." + error.getMessage(), Toast.LENGTH_SHORT).show();
                                       }
                                   });
                               }
                           });
                       }
                   })
                   .addOnFailureListener(new OnFailureListener() {  // stroge image Failde
                       @Override
                       public void onFailure(@NonNull Exception exception) {
                  Toast.makeText(Reservation_room.this, "Stroage Upload Failde.." + exception.getMessage(), Toast.LENGTH_SHORT).show();
                  progressDialog.dismiss();
                       }
                   }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
               @Override
               public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                   float percent =(100* taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                   progressDialog.setMessage("please wait..."+(int)percent+"%");
                   progressDialog.dismiss();
               }
           });
       }
    }

    public void getData(){

      Bundle bundle=getIntent().getExtras();

      String room_no=bundle.getString("RoomId").toString();
      String room_type=bundle.getString("Room_typ").toString();
      String bad_ty=bundle.getString("badType").toString();
    //  int roomPrice=bundle.getInt("roomRate");
        String roomPrice =bundle.getString("roomRate");
        room_Price=Integer.parseInt(roomPrice);
      //String roomimage=bundle.getString("image").toString();
      textViewRoom_no.setText("Room No:"+room_no);
      textViewRoom_tp.setText("Room Type:"+room_type);
      textViewBad_ty.setText("Bad Type:"+bad_ty);
      textViewRoom_rate.setText("Room price:"+room_Price);
      //Picasso.get().load(bundle.getString("image")).into(imageView);
     // Image_url=bundle.getString("image").trim();

        listPosition=bundle.getString("list_Delet_position");
      // Toast.makeText(this, "price:"+value, Toast.LENGTH_SHORT).show();
        textViewCheeckIn.setText("Cheeck_In");
    }


}
