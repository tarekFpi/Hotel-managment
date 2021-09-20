package com.example.hotel_managment;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


import java.text.SimpleDateFormat;
import java.time.LocalDate;


import java.time.temporal.ChronoUnit;
import java.util.*;

import de.hdodenhof.circleimageview.CircleImageView;

public class Custom_checkOut extends AppCompatActivity {
   private TextView  Text_totalPrice,textView_Room_price;
   private TextView textView_checkOut_rmType,
           textView_checkOut_bdType,textView_check_in_rmPrice,textView_checkOut_custName,textView_checkOut_custPhon
           ,textView_checkOut_custNid,textView_checkOut_cust_Deatils,textView_checkIn_status,textView_checkIn_date;
 private  TextView textView_check_out_date;
 private  TextView textView_total_Day;
 private DatabaseReference databaseReference_check_out;
 private DatabaseReference databaseRefer_check_In_Image;
 private CircleImageView circleImageView;
 private Spinner spinner_Room_id;
 ArrayAdapter<String>arrayAdapter;
 ArrayList<String>array_list_roomId=new ArrayList<>();
 private Reservation_customarInfo getItem;
 private String imageUrl=null;
 private  int room_price;
private String Spinner_item;
private  int Total_Price_room=0;
String custom_name,custm_address,custom_nid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_check_out);

        databaseReference_check_out= FirebaseDatabase.getInstance().getReference("Room_check_Out");
        databaseRefer_check_In_Image= FirebaseDatabase.getInstance().getReference("Reservation_roomCheek_In");

        spinner_Room_id=(Spinner) findViewById(R.id.cust_check_outId);
        //textView_checkOut_rmId=(TextView)findViewById(R.id.checkOut_Roomtype);
        textView_checkOut_rmType=(TextView)findViewById(R.id.checkOut_Roomtype);
        textView_checkOut_bdType=(TextView)findViewById(R.id.checkOut_Badtype);
        textView_check_in_rmPrice=(TextView)findViewById(R.id.checkOut_price);

        textView_checkOut_custName=(TextView)findViewById(R.id.checkOut_Cust_name);
        textView_checkOut_cust_Deatils=(TextView)findViewById(R.id.CheckOut_Cust_Deatils);
        textView_checkOut_custNid=(TextView)findViewById(R.id.checkOut_Nid);
        textView_checkOut_custPhon=(TextView)findViewById(R.id.check_Out_phon);

        textView_checkIn_status=(TextView)findViewById(R.id.text_check_in);
        textView_checkIn_date=(TextView)findViewById(R.id.check_in_Date);
        textView_check_out_date=(TextView)findViewById(R.id.check_outDate);
        Text_totalPrice=(TextView) findViewById(R.id.check_outTotal_Price);

        textView_total_Day=(TextView) findViewById(R.id.check_out_totalDay);

        circleImageView=(CircleImageView) findViewById(R.id.CheckOut_cutom_Image);
        Check_outDate();

        // Read from the database
        databaseRefer_check_In_Image.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                array_list_roomId.clear();
            for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
          getItem=dataSnapshot1.getValue(Reservation_customarInfo.class);
          getItem.setReservation_data_Key(dataSnapshot1.getKey());
          array_list_roomId.add("Select Room Id");
          array_list_roomId.add(getItem.getRes_RoomNo());
            }
            arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,array_list_roomId);
            spinner_Room_id.setAdapter(arrayAdapter);

            spinner_Room_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
             public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Spinner_item=array_list_roomId.get(position).toString();

                    // Read from the database
                    databaseRefer_check_In_Image.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            array_list_roomId.clear();
                            array_list_roomId.add("Select Room Id");
                            for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                                Reservation_customarInfo getItem=dataSnapshot1.getValue(Reservation_customarInfo.class);
                                getItem.setReservation_data_Key(dataSnapshot1.getKey());

                                array_list_roomId.add(getItem.getRes_RoomNo());
                                if (getItem.getRes_RoomNo().contains(Spinner_item)) {
                                    imageUrl=getItem.getRes_RoomImage();
                                    Picasso.get().load(imageUrl).into(circleImageView);
                                    //textView_checkOut_rmId.setText(getItem.getRes_RoomNo());
                                    textView_checkOut_rmType.setText(getItem.getRes_RoomType());
                                    textView_checkOut_bdType.setText(getItem.getRes_BadType());

                                     room_price=getItem.getRes_RoomPrice();

                                    custom_name=getItem.getCut_name();
                                    custm_address=getItem.getCut_address();
                                    custom_nid=getItem.getCut_Nad();

                                    textView_check_in_rmPrice.setText("Room price:"+room_price);
                                    textView_checkOut_custName.setText("Customar Name:"+custom_name);
                                    textView_checkOut_cust_Deatils.setText("Address:"+custm_address);
                                    textView_checkOut_custNid.setText("NID Number:"+custom_nid);
                                    textView_checkIn_status.setText(getItem.getRoomCheekIn());
                                    textView_checkIn_date.setText(""+getItem.getDate());
                                    textView_checkOut_custPhon.setText("Phon Number:"+getItem.getCut_phon());

                                    arrayAdapter.notifyDataSetChanged();

                                if(textView_checkIn_date !=null && textView_check_out_date!=null){
                                    setDate();
                                  }else{
                                Toast.makeText(Custom_checkOut.this, "your Date Check..", Toast.LENGTH_SHORT).show();
                               arrayAdapter.notifyDataSetChanged();
                                  }
                                }

                                if(Spinner_item.equals("Select Room Id")){
                                    circleImageView.setImageResource(0);
                                    textView_checkOut_rmType.setText("");
                                    textView_checkOut_bdType.setText("");
                                    textView_check_in_rmPrice.setText("");
                                    textView_checkOut_custName.setText("");
                                    textView_checkOut_cust_Deatils.setText("");
                                    textView_checkOut_custNid.setText("");
                                    textView_checkIn_status.setText("");
                                    textView_checkIn_date.setText("");
                                    textView_checkOut_custPhon.setText("");
                                    textView_total_Day.setText("");

                                    arrayAdapter.notifyDataSetChanged();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
       Toast.makeText(Custom_checkOut.this, "Your Image Loding Failde...", Toast.LENGTH_SHORT).show();
                    }
                });
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            }
            @Override
            public void onCancelled(DatabaseError error) {
      Toast.makeText(Custom_checkOut.this, "Your Room id Lodding Failde..."+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Check_outData(View view) {
        String status_Data="Check Out";

       String room_id=Spinner_item.toString();
      String room_type= textView_checkOut_rmType.getText().toString();
      String bad_type= textView_checkOut_bdType.getText().toString();
      int rm_price=room_price;
      //String cust_name= textView_checkOut_custName.getText().toString();
      String cust_name= custom_name;
      String cust_image= imageUrl.toString();
      /// String cust_deatils= textView_checkOut_cust_Deatils.getText().toString();
       String cust_deatils=custm_address;
      String nid_card= custom_nid;
      String stats=status_Data;
       String check_inDate=  textView_checkIn_date.getText().toString();
       String cust_phon= textView_checkOut_custPhon.getText().toString();
       String check_out= textView_check_out_date.getText().toString();
       String totalDay= textView_total_Day.getText().toString();

     //  int total_price= Integer.parseInt(Text_totalPrice.getText().toString());

   /*if(TextUtils.isEmpty(room_id)){
        Toast.makeText(this, "Your Room NO is Empty..", Toast.LENGTH_SHORT).show();
        spinner_Room_id.requestFocus();
       return;
    } if(textView_checkOut_rmType.getText().toString().isEmpty()){
        Toast.makeText(this, "Your Room Type is Empty..", Toast.LENGTH_SHORT).show();
        textView_checkOut_rmType.requestFocus();

    }else if(textView_checkOut_bdType.getText().toString().isEmpty()){
       Toast.makeText(this, "Your Bad is Empty..", Toast.LENGTH_SHORT).show();
       textView_checkOut_bdType.requestFocus();

   }
   else if(rm_price==0){
        Toast.makeText(this, "Your Room Price is Empty..", Toast.LENGTH_SHORT).show();
        textView_check_in_rmPrice.requestFocus();

    }else if(textView_checkOut_custName.getText().toString().isEmpty()){
        Toast.makeText(this, "Your Customar Name is Empty..", Toast.LENGTH_SHORT).show();
        textView_checkOut_custName.requestFocus();
            return;
    }else if(cust_image.isEmpty()){
        Toast.makeText(this, "Your Customar Image is Empty..", Toast.LENGTH_SHORT).show();
        circleImageView.requestFocus();

    }else if(textView_checkOut_cust_Deatils.getText().toString().isEmpty()){
        Toast.makeText(this, "Your Customar Deatils is Empty..", Toast.LENGTH_SHORT).show();
        textView_checkOut_cust_Deatils.requestFocus();

    }else if(textView_checkOut_custNid.getText().toString().isEmpty()){
        Toast.makeText(this, "Your Customar NiD is Empty..", Toast.LENGTH_SHORT).show();
        textView_checkOut_custNid.requestFocus();

    }else if(textView_checkIn_date.getText().toString().isEmpty()){
        Toast.makeText(this, "Your Check In Date is Empty..", Toast.LENGTH_SHORT).show();
       textView_checkIn_date.requestFocus();

    }else if(textView_checkOut_custPhon.getText().toString().isEmpty()){
        Toast.makeText(this, "Your Customar phon Number is Empty..", Toast.LENGTH_SHORT).show();
        textView_checkIn_date.requestFocus();

    }*/ if(textView_total_Day.getText().toString().isEmpty()){
        Toast.makeText(this, "Your  Total Day is Empty..", Toast.LENGTH_SHORT).show();
        textView_total_Day.requestFocus();

    }else if(Text_totalPrice.getText().toString().isEmpty()){
        Toast.makeText(this, "Your  Room Total Price is Empty..", Toast.LENGTH_SHORT).show();
        Text_totalPrice.requestFocus();

    }else {
        String key = databaseReference_check_out.push().getKey();
        check_out_rmModel Add_item = new check_out_rmModel(room_id, room_type, bad_type, rm_price, cust_name, cust_deatils,
                cust_image, nid_card, cust_phon, stats, check_inDate, check_out, totalDay, Total_Price_room);

        databaseReference_check_out.child(key).setValue(Add_item).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Read from the database
                databaseRefer_check_In_Image.addValueEventListener(new ValueEventListener() { //check Room Delete
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            Reservation_customarInfo firebase_data = dataSnapshot1.getValue(Reservation_customarInfo.class);
                            firebase_data.setReservation_data_Key(dataSnapshot1.getKey());

                            if (firebase_data.getRes_RoomNo().contains(Spinner_item)) {
                                String item_remove = firebase_data.getReservation_data_Key();
                                databaseRefer_check_In_Image.child(item_remove).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                      public void onSuccess(Void aVoid) {
                    Toast.makeText(Custom_checkOut.this, "Your Check Out SuccessFull...", Toast.LENGTH_SHORT).show();
                          }
                        });
                       }
                      }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
               Toast.makeText(Custom_checkOut.this, "Your Not Match ..." + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                circleImageView.setImageResource(0);
                textView_checkOut_rmType.setText("");
                textView_checkOut_bdType.setText("");
                textView_check_in_rmPrice.setText(":");
                textView_checkOut_custName.setText("");
                textView_checkOut_cust_Deatils.setText("");
                textView_checkOut_custNid.setText("");
                textView_checkIn_status.setText("");
                textView_checkIn_date.setText("");
                textView_checkOut_custPhon.setText("");
                textView_total_Day.setText("");
                Text_totalPrice.setText("");
                //spinner_Room_id.clearAnimation();
                spinner_Room_id.requestFocus();

                arrayAdapter.notifyDataSetChanged();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Custom_checkOut.this, "Your Check Out UnsuccessFull..." + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    }
    void Check_outDate(){

        Date date_checkOut=new Date();
        SimpleDateFormat sm=new SimpleDateFormat("yyyy-MM-dd");
        String current_date= sm.format(date_checkOut.getTime());
        textView_check_out_date.setText(current_date);
    }

    @SuppressLint("NewApi")
    void setDate(){

     long noOfDaysBetween = 0;

            String dateBeforeString= textView_checkIn_date.getText().toString();
            String dateAfterString= textView_check_out_date.getText().toString();


         // String price=textView_check_in_rmPrice.getText().toString();
      //room_price=Integer.parseInt(textView_check_in_rmPrice.getText().toString());
        //Parsing the date
        LocalDate dateAfter = null;
        LocalDate dateBefore=null;
          dateAfter = LocalDate.parse(dateAfterString);
            dateBefore = LocalDate.parse(dateBeforeString);
        noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore,dateAfter);

        Toast.makeText(this, "total Day:"+noOfDaysBetween, Toast.LENGTH_LONG).show();
        textView_total_Day.setText("Total Day:"+noOfDaysBetween);
        Total_Price_room= (int) (room_price*noOfDaysBetween);
       Text_totalPrice.setText("Total:"+Total_Price_room);
    }
}