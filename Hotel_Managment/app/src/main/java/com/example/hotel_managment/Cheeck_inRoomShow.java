package com.example.hotel_managment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Cheeck_inRoomShow extends AppCompatActivity {
private RecyclerView recyclerViewCheck_in;
private Check_RoomAdapter adapter;
private DatabaseReference databaseReference;
//private ImageView imageView;
private LayoutInflater layoutInflater;
private View view;
private AlertDialog builder;
private List<Reservation_customarInfo>roomModleList=new ArrayList<Reservation_customarInfo>();
private EditText editText_deatils_Update,editText_nidCard_up,editText_phon_update;
private Button button_update,button_exit;
private  Reservation_customarInfo select_item;
private Context context;
private ProgressDialog progressDialog;
private EditText editText_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheeck_in_room_show);
        this.setTitle("Check In Deatils");
        databaseReference= FirebaseDatabase.getInstance().getReference("Reservation_roomCheek_In");

        recyclerViewCheck_in=(RecyclerView)findViewById(R.id.recycler_check_inroom);
        editText_search=(EditText)findViewById(R.id.Check_in_edit_search);
        recyclerViewCheck_in.setHasFixedSize(true);
        recyclerViewCheck_in.setLayoutManager(new LinearLayoutManager(this));

       progressDialog=new ProgressDialog(this);
       progressDialog.setCancelable(false);
       progressDialog.setMessage("Please wite....");
       progressDialog.show();
        // Read from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                roomModleList.clear();
              for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                  Reservation_customarInfo item=dataSnapshot1.getValue(Reservation_customarInfo.class);
                  item.setReservation_data_Key(dataSnapshot1.getKey());
                  roomModleList.add(item);
              }
              adapter=new Check_RoomAdapter(getApplicationContext(),roomModleList);
                progressDialog.dismiss();
              recyclerViewCheck_in.setAdapter(adapter);
              adapter.setOnItemClickListenter(new Check_RoomAdapter.onItemClickListener() {
                  @Override
                  public void onItemClickListener(int position) {
                      String value= roomModleList.get(position).getCut_name();
            Toast.makeText(Cheeck_inRoomShow.this, "Customar Name:"+value, Toast.LENGTH_SHORT).show();
                  }

                  @Override
                  public void onDelete(int position) {
                      progressDialog.show();
                      Reservation_customarInfo select_item=roomModleList.get(position);
                      String remove_key=select_item.getReservation_data_Key();
                      databaseReference.child(remove_key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                          @Override
                          public void onSuccess(Void aVoid) {
                     Toast.makeText(Cheeck_inRoomShow.this, "Your Data Update SuccessFull..", Toast.LENGTH_SHORT).show();
                       progressDialog.dismiss();
                          }
                      });
                  }

                  @Override
                  public void onUpdate(int position) {
                      Reservation_customarInfo get_item=roomModleList.get(position);

            builder=new AlertDialog.Builder(Cheeck_inRoomShow.this).create();
                      new AlertDialog.Builder(Cheeck_inRoomShow.this);
              select_item=roomModleList.get(position);
                  layoutInflater=getLayoutInflater();
                 view= layoutInflater.inflate(R.layout.update_item,null);
                 builder.setView(view);
          editText_deatils_Update= view.findViewById(R.id.cust_deatils_update);
          editText_nidCard_up= view.findViewById(R.id.cust_nid_card_update);
            editText_phon_update= view.findViewById(R.id.cust_pon_update);
            button_update= view.findViewById(R.id.Update_dataSave);
            button_exit= view.findViewById(R.id.Update_exit);

                      editText_deatils_Update.setText( get_item.getCut_address());
                      editText_nidCard_up.setText( get_item.getCut_Nad());
                      editText_phon_update.setText(get_item.getCut_phon());
                      builder.show();

                 button_update.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         progressDialog.show();
                     HashMap<String,Object>hashMap=new HashMap<>();
                     hashMap.put("cut_address",editText_deatils_Update.getText().toString());
                     hashMap.put("cut_Nad",editText_nidCard_up.getText().toString());
                     hashMap.put("cut_phon",editText_phon_update.getText().toString());

                      if(editText_deatils_Update.getText().toString().isEmpty()){
                             editText_deatils_Update.setError("Customar Details Empty..");
                             editText_deatils_Update.requestFocus();
                             progressDialog.dismiss();
                         }
                       if(editText_nidCard_up.length()<10){
                           editText_nidCard_up.setError("Customar NID Number Invlide..");
                           editText_nidCard_up.requestFocus();
                             progressDialog.dismiss();
                         }else if(editText_phon_update.length()<11){
                           editText_phon_update.setError("Customar phon Number Invlide..");
                           editText_phon_update.requestFocus();
                             progressDialog.dismiss();
                         }
                     databaseReference.child(select_item.getReservation_data_Key()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                         @Override
                         public void onSuccess(Void aVoid) {
              Toast.makeText(Cheeck_inRoomShow.this, "Your Data Update SuccessFull..", Toast.LENGTH_SHORT).show();
              editText_deatils_Update.setText("");
              editText_nidCard_up.setText("");
              editText_phon_update.setText("");
              builder.dismiss();
              progressDialog.dismiss();
                         }
                     }).addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Cheeck_inRoomShow.this, "Your Data Falide..", Toast.LENGTH_SHORT).show();
                             builder.dismiss();
                             progressDialog.dismiss();
                         }
                     });
                     }
                  });
                 button_exit.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                      builder.dismiss();
                    progressDialog.dismiss();
                     }
                 });

                  }
              });
            }

            @Override
            public void onCancelled(DatabaseError error) {
          Toast.makeText(Cheeck_inRoomShow.this, "Your Check in Data Load Failde..", Toast.LENGTH_SHORT).show();
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
      List<Reservation_customarInfo>filterDataList=new ArrayList<>();

      for(Reservation_customarInfo get_item : roomModleList){
         if(get_item.getCut_phon().toLowerCase().contains(textData.toLowerCase())){
            filterDataList.add(get_item);
         }
      }
      adapter.filterList(filterDataList);
    }


}