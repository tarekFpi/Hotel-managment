package com.example.my_creating_firestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;

import java.util.HashMap;

public class add_itemShow extends AppCompatActivity {
  private EditText editTextName,editTextAge;
  private DocumentReference documentReference;
  CollectionReference collectionReference;
  private TextView textViewShow;
  ItemModel itemModel=new ItemModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_show);
      documentReference= FirebaseFirestore.getInstance().collection("MyTest").document();
        collectionReference=FirebaseFirestore.getInstance().collection("MyTest");
        editTextName=(EditText)findViewById(R.id.edNameId);
        editTextAge=(EditText)findViewById(R.id.edAgeid);
        textViewShow=(TextView)findViewById(R.id.text_dataShowId);

    }

  /*  @Override
    protected void onStart() {
        super.onStart();
        collectionReference.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                  if(error !=null){
                   return;
                  }
                StringBuffer stringBuffer=new StringBuffer();
                for(QueryDocumentSnapshot queryDocumentSnapshots1 :queryDocumentSnapshots){
                    ItemModel item=queryDocumentSnapshots1.toObject(ItemModel.class);
                    item.setDocumontId(queryDocumentSnapshots1.getId());
                    String name=item.getName();
                    String age=item.getAge();
                    String duId=item.getDocumontId();
                    stringBuffer.append("id :"+duId+" \nName :"+name+"\n"+"Age :"+age+"\n \n");
                }
                textViewShow.setText(stringBuffer);
            }
        });
    }*/

    public void saveAllData(View view) {
     String name= editTextName.getText().toString().trim();
     String age=editTextAge.getText().toString().trim();
     ItemModel item=new ItemModel(name,age);
      documentReference.set(item).addOnSuccessListener(new OnSuccessListener<Void>() {
          @Override
          public void onSuccess(Void aVoid) {
              Toast.makeText(add_itemShow.this, "Data Add SuccessFull...", Toast.LENGTH_SHORT).show();
              editTextName.setText("");
              editTextAge.setText("");
          }
      }).addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
         Toast.makeText(add_itemShow.this, "Data Failde...", Toast.LENGTH_SHORT).show();
          }
      });
    }

    public void Loding_Data(View view) {
   collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
       @Override
       public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
           StringBuffer stringBuffer=new StringBuffer();
           for(QueryDocumentSnapshot queryDocumentSnapshots1 :queryDocumentSnapshots){
               ItemModel item=queryDocumentSnapshots1.toObject(ItemModel.class);
               item.setDocumontId(queryDocumentSnapshots1.getId());
               String name=item.getName();
               String age=item.getAge();
               String duId=item.getDocumontId();
               stringBuffer.append("id :"+duId+" \nName :"+name+"\n"+"Age :"+age+"\n \n");
           }
           textViewShow.setText(stringBuffer);
       }
   }).addOnFailureListener(new OnFailureListener() {
       @Override
       public void onFailure(@NonNull Exception e) {
    Toast.makeText(add_itemShow.this, "Data Loadding Failde...", Toast.LENGTH_SHORT).show();
       }
   });
    }

    public void UpdateData(View view) {
     collectionReference .get()
             .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                 @Override
                 public void onComplete(@NonNull Task<QuerySnapshot> task) {
   