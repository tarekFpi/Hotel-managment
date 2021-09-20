package com.example.hotel_managment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

import java.util.ArrayList;
import java.util.List;

public class payment_Customar_checkOut extends AppCompatActivity {
  private RecyclerView recyclerView;
  private check_out_rmAdapter adapter;
  private List<check_out_rmModel>check_out_rmModelList=new ArrayList<>();
  private DatabaseReference databaseRef_customar_payment_show;
    check_out_rmModel model_item;
    private ProgressDialog progressDialog;
    private EditText editText_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_customar_checkout);

        databaseRef_customar_payment_show= FirebaseDatabase.getInstance().getReference("Room_check_Out");
        recyclerView=(RecyclerView)findViewById(R.id.recycler_payment_show);
        editText_search=(EditText)findViewById(R.id.edit_payment_checkOut);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        progressDialog=new ProgressDialog(this);
         progressDialog.setCancelable(false);
         progressDialog.setMessage("Please Loding wite...");
         progressDialog.show();
        // Read from the database
        databaseRef_customar_payment_show.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                check_out_rmModelList.clear();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                    model_item=dataSnapshot1.getValue(check_out_rmModel.class);
                    model_item.setUser_data_key(dataSnapshot1.getKey());

                  check_out_rmModelList.add(model_item);
                }
                adapter=new check_out_rmAdapter(getApplicationContext(),check_out_rmModelList);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();
              adapter.setOnItemClickListener(new check_out_rmAdapter.onItemClickListener() {
                  @Override
                  public void onItemClick(int position) {
                String customar_name=check_out_rmModelList.get(position).getCustom_name();
             Toast.makeText(payment_Customar_checkOut.this, customar_name, Toast.LENGTH_SHORT).show();
                  }

                  @Override
                  public void OnDelete(int position) {
                      check_out_rmModel item_position=check_out_rmModelList.get(position);
                      String revome_key=item_position.getUser_data_key();
                      databaseRef_customar_payment_show.child(revome_key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                          @Override
                          public void onSuccess(Void aVoid) {
                Toast.makeText(payment_Customar_checkOut.this, "Customar Information Delete SuccessFull..", Toast.LENGTH_LONG).show();
                          }
                      }).addOnFailureListener(new OnFailureListener() {
                          @Override
                          public void onFailure(@NonNull Exception e) {
      Toast.makeText(payment_Customar_checkOut.this, "Customar Information Remove Failde..", Toast.LENGTH_SHORT).show();
                          }
                      });
                  }

                  @Override
                  public void OnUpdate(int position) {

                  }
              });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
         Toast.makeText(payment_Customar_checkOut.this, "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void fileList(String text) {

        List<check_out_rmModel>filterList_data=new ArrayList<>();
       for(check_out_rmModel item: check_out_rmModelList){

         if(item.getCust_phon().toLowerCase().contains(text.toLowerCase())){
             filterList_data.add(item);
         }
       }
       adapter.filter_chingData(filterList_data);
    }
}