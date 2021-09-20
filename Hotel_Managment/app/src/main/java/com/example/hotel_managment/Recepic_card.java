package com.example.hotel_managment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Recepic_card extends AppCompatActivity {
   private TextView Room_textView_recipeNo,textView_recip_allorder,textView_Totalprice,
           textView_customName,textView_customPhon,textView_date;

    private DatabaseReference databaseRef_RoomCheckIn_image;
    private CircleImageView circleImageView;
    Recipe_cardModel model_item;
    private DatabaseReference database_order_paymentShow;

    private ArrayAdapter<String>adapter;

    private String imageUrl=null;
    private Spinner spinnerId;
    Reservation_customarInfo get_item;
    ArrayList<String> ar;
    private String Spinner_item;
  private int total_price=0;
  private String order_date;
  private String Coustomar_name;
  private String Coustomar_phon;
  private String list_position_Data;
   private DatabaseReference databaseReference_category_remove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepic_card);
        this.setTitle("Recipe Card");
        ar= new ArrayList<String>();

        databaseReference_category_remove = FirebaseDatabase.getInstance().getReference("recipe_category");
        //database_order_Show= FirebaseDatabase.getInstance().getReference("Recipe_orderCard");
        databaseRef_RoomCheckIn_image= FirebaseDatabase.getInstance().getReference("Reservation_roomCheek_In");
        database_order_paymentShow= FirebaseDatabase.getInstance().getReference("recipe_payment_Data");
        //editText_roomId=(EditText)findViewById(R.id.pay_rm_id);
        textView_customName=(TextView) findViewById(R.id.pay_recipeCustName);
        textView_customPhon=(TextView) findViewById(R.id.pay_recipeCust_phon);

        Room_textView_recipeNo=(TextView) findViewById(R.id.pay_recipe_Id);
        textView_recip_allorder=(TextView) findViewById(R.id.pay_recipe_allOrder);
        textView_Totalprice=(TextView) findViewById(R.id.pay_recipe_total);
       // textView_recipe_quantity=(TextView) findViewById(R.id.pay_recipe_quantity);
        circleImageView=(CircleImageView)findViewById(R.id.pay_custImage);
       spinnerId=(Spinner)findViewById(R.id.auto_spnner_id);
         textView_date=(TextView)findViewById(R.id.recipe_Delivary_date);
      //   textView_roomId=(TextView)findViewById(R.id.pay_roomId);
             circleImageView.setImageResource(0);

/*        Date date=new Date();
        SimpleDateFormat sm=new SimpleDateFormat("YYYY-MM-dd");
        order_date= sm.format(date.getTime());
        textView_date.setText("Date:"+order_date);*/

         Current_Date();
        Bundle bundle=getIntent().getExtras();

         String room_Id=  bundle.getString("Room_Id");
        String user_allOrder=bundle.getString("äll_order");
        total_price=bundle.getInt("Total_price");
        Room_textView_recipeNo.setText(room_Id);
        textView_Totalprice.setText("Total Price:"+total_price);
        list_position_Data=bundle.getString("list_position");

        String m[]=user_allOrder.split("/");
        StringBuilder sb=new StringBuilder();
        for (int i = 0,j=0; i <m.length ; i++) {j++;
       sb.append(j+".Order No:"+m[i]+".\n");
        }
        textView_recip_allorder.setText("AllOrder"+sb);
       //String room_Id=  bundle.get("",);
                databaseRef_RoomCheckIn_image.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                       // ar.clear();
                        for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                            get_item =dataSnapshot1.getValue(Reservation_customarInfo.class);
                            get_item.setReservation_data_Key(dataSnapshot1.getKey());
                            ar.add("Select  Room Id");
                            ar.add(get_item.getRes_RoomNo());
                           // ar.add("Select");
                        }

                adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,ar);
                spinnerId.setAdapter(adapter);

                        //spinner itme click Aciton
                        spinnerId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(final AdapterView<?> adapterView, View view, int position, long l) {
                          //final String Select_Spnner=String.valueOf(position);
                                Spinner_item= ar.get(position).toString();

                                // Read from the database
                                databaseRef_RoomCheckIn_image.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        ar.clear();
                                        ar.add("Select Room Id");
                                        for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                                            get_item =dataSnapshot1.getValue(Reservation_customarInfo.class);
                                            get_item.setReservation_data_Key(dataSnapshot1.getKey());

                                           ar.add(get_item.getRes_RoomNo());

                                          //  String room_position=textView_roomId.getText().toString();
                                            if (get_item.getRes_RoomNo().contains(Spinner_item)) {
                                                imageUrl = get_item.getRes_RoomImage();
                                                Coustomar_name=get_item.getCut_name();
                                                Coustomar_phon=get_item.getCut_phon();
                                                Picasso.get().load(imageUrl).into(circleImageView);
                                                textView_customName.setText("Customar Name:" + get_item.getCut_name());
                                                textView_customPhon.setText("phon Number:" + get_item.getCut_phon());
                                            }

                                         if(Spinner_item.equals("Select Room Id")){
                                                circleImageView.setImageResource(0);
                                               textView_customName.setText("");
                                               textView_customPhon.setText("");
                                              // textView_recipeNo.setText("");
                                              //  textView_recipName.setText("");
                                               // textView_Totalprice.setText("");
                                               // textView_recipe_quantity.setText("");
                                                //textView_date.setText("");

                                               adapter.notifyDataSetChanged();
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError error) {
                            Toast.makeText(Recepic_card.this, "Error:"+error.getMessage(), Toast.LENGTH_SHORT).show();
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
                 Toast.makeText(Recepic_card.this, "Customar image Lodding Failde..", Toast.LENGTH_SHORT).show();
                    }
                });
    }

   /* @Override
    protected void onStart() {
        // Read from the database
        database_order_Show.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Recipe_cardModel item=null;
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                    model_item =dataSnapshot1.getValue(Recipe_cardModel.class);
                    model_item.setReci_key(dataSnapshot1.getKey());
                }
                String reci_id=model_item.getRec_id();
                String reci_name=model_item.getRec_Name();
                int reci_quant=model_item.getRec_quantity();
                int reci_total=model_item.getRec_totalPrice();

                textView_recipeNo.setText(reci_id);
                textView_recipName.setText(reci_name);
                textView_recipe_quantity.setText("quantity:"+reci_quant);
                textView_Totalprice.setText("total Price:"+reci_total);
           // Toast.makeText(Recepic_card.this, "data"+item.getRec_Name()+"\nId:"+item.getRec_id()+"\nquantity:"+item.getRec_quantity(), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCancelled(DatabaseError error) {
          Toast.makeText(Recepic_card.this, "Recipe Order Data Not Found..", Toast.LENGTH_SHORT).show();
            }
        });

        Current_Date();
        super.onStart();
    }*/

void Current_Date(){
    Calendar calendar=Calendar.getInstance();
    String date= DateFormat.getDateTimeInstance().format(calendar.getTime());
    textView_date.setText("Date:"+date);
}

    public void Selling_dataSave(View view) {
      database_order_paymentShow=FirebaseDatabase.getInstance().getReference("recipe_payment_Data");

      //String room_id=autoCompleteTextView.getText().toString();
/*      String Custom_Name=textView_customName.getText().toString();
      String Custom_phon=textView_customPhon.getText().toString();
      String recipe_id=textView_recipeNo.getText().toString();*/

        Coustomar_name=get_item.getCut_name();
        Coustomar_phon=get_item.getCut_phon();

        Bundle bundle=getIntent().getExtras();
        final String room_Id=  bundle.getString("Room_Id");
        String user_allOrder=bundle.getString("äll_order");
        total_price=bundle.getInt("Total_price");


        String m[]=user_allOrder.split("/");
        StringBuilder sb=new StringBuilder();
        for (int i = 0,j=0; i <m.length ; i++) {j++;
            sb.append(j+".Order No:"+m[i]+".\n");
        }
     // String recipe_Name=textView_recipName.getText().toString();
    /*  int recipe_quantity= Integer.parseInt(textView_recipe_quantity.getText().toString());
      int recipe_total= Integer.parseInt(textView_Totalprice.getText().toString());*/

        /*CharSequence qu=textView_recipe_quantity.getText();
        CharSequence tot=textView_Totalprice.getText();*/

      String recipe_date=textView_date.getText().toString();
       if(circleImageView.getDrawable()==null){
      Toast.makeText(this, "Please Image Select..", Toast.LENGTH_SHORT).show();
           circleImageView.requestFocus();
       }else if(Coustomar_name.isEmpty()){
           Toast.makeText(this, "YOur Coustomar Name is Empty..", Toast.LENGTH_SHORT).show();
       }else if(Coustomar_phon.isEmpty()){
           Toast.makeText(this, "YOur Coustomar Phone is Empty", Toast.LENGTH_SHORT).show();

       } else if(sb.toString().isEmpty()){
           Toast.makeText(this, "Your Order Is Empty..", Toast.LENGTH_SHORT).show();

       }else if(textView_Totalprice.getText().toString().isEmpty()){
           Toast.makeText(this, "Your Total Price is Empty..", Toast.LENGTH_SHORT).show();
       }else{

      String key= database_order_paymentShow.push().getKey();
    Recipe_payment_Modle addItem=new Recipe_payment_Modle(spinnerId.getSelectedItem().toString(),Coustomar_name,Coustomar_phon,sb.toString() ,total_price,imageUrl,recipe_date);


 database_order_paymentShow.child(key).setValue(addItem).addOnSuccessListener(new OnSuccessListener<Void>() {
   @Override
 public void onSuccess(Void aVoid) {
                 //old order delete
   databaseReference_category_remove.child(list_position_Data).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
       @Override
       public void onSuccess(Void aVoid) {

           Toast.makeText(Recepic_card.this, "Your Data Add SuccessFull...", Toast.LENGTH_SHORT).show();

           textView_customName.setText("");
           textView_customPhon.setText("");
           textView_Totalprice.setText("");
           textView_recip_allorder.setText("");
           Room_textView_recipeNo.setText("");
           circleImageView.setImageResource(0);
           if(circleImageView!=null) {
 circleImageView.setImageResource(R.drawable.ic_image_search);
           }
       }
   });


  }
  }).addOnFailureListener(new OnFailureListener() {
     @Override
     public void onFailure(@NonNull Exception e) {
         Toast.makeText(Recepic_card.this, "Your Data Save Faild..", Toast.LENGTH_SHORT).show();
     }
 });
    }
    }

}




               /*  databaseReference_category_remove.addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(DataSnapshot dataSnapshot) {
                         // Recipe_cardModel item=null;
                         for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
        //  Reservation_customarInfo   model_item =dataSnapshot1.getValue(Reservation_customarInfo.class);
                             Recipe_categoryModel item = dataSnapshot1.getValue(Recipe_categoryModel.class);


                              databaseReference_category_remove.child(model_item.getReci_key()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                  @Override
                                  public void onSuccess(Void aVoid) {
                                      Toast.makeText(Recepic_card.this, "Your Data Add SuccessFull...", Toast.LENGTH_SHORT).show();
                                      textView_customName.setText("");
                                      textView_customPhon.setText("");
                                      textView_recipe_quantity.setText("");
                                      textView_recipeNo.setText("");

                                      textView_recipe_quantity.setText("");
                                      textView_Totalprice.setText("");
                                      textView_Totalprice.setText("Total price:0");
                                      circleImageView.setImageResource(0);
                                      if(circleImageView!=null){
                                       circleImageView.setImageResource(R.drawable.ic_image_search);
                                      }
                                  }
                              });

                         }

                     }
                     @Override
                     public void onCancelled(DatabaseError error) {
  Toast.makeText(Recepic_card.this, "Recipe Order Data Not Found..", Toast.LENGTH_SHORT).show();
                     }
                 });
*/
