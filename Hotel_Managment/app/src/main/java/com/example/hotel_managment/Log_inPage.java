package com.example.hotel_managment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Log_inPage extends AppCompatActivity {
private TextView textView_log_sign_up;
private EditText editText_email,editText_password;
 private int prog=0;
  private FirebaseAuth mAuth;
  private ProgressBar progressBar;
  private String admin_gmail="tarekhasan@gmail.com";
   private String admin_password="tarek@22";
   private   String admin_status="Admin";
   private CheckBox checkBox_remember;
   private SharedPreferences sharedPreferences,SharedPrefer_user_gmali;


static int iki=0;

   private DatabaseReference databaseReference_singUp;
 //  private static String admin_name="Tarek";
  // private static String user_status="User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_inpage);
        mAuth = FirebaseAuth.getInstance();

        databaseReference_singUp = FirebaseDatabase.getInstance().getReference("Sign_upData");
        editText_email = (EditText) findViewById(R.id.logIn_gmail);
        editText_password = (EditText) findViewById(R.id.logIn_pass);
        textView_log_sign_up = (TextView) findViewById(R.id.log_to_signUP);
        progressBar = (ProgressBar) findViewById(R.id.log_progrss_id);
        checkBox_remember = (CheckBox) findViewById(R.id.check_id);

   sharedPreferences = getApplicationContext().getSharedPreferences("com.example.hotel_managment", Context.MODE_PRIVATE);
 SharedPrefer_user_gmali = getApplicationContext().getSharedPreferences("com.example.hotel_managment", Context.MODE_PRIVATE);
   Home_page hp=new Home_page();
    //iki=hp.myStutas+1;
       // sharedPreferences.edit().clear().commit();
        String em=sharedPreferences.getString("remember_email", "Data Not found");
        String pp=sharedPreferences.getString("remember_pass", "Data Not Found");


  if (!(em.contains("Data Not found") && pp.contains("Data Not Found"))){

             SharedPrefer_user_gmali.edit().putString("user_gmail",em).commit();
                //Toast.makeText(this, "em:"+em+"pass:"+pp, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.VISIBLE);
                admin_status = "User";
                Toast.makeText(Log_inPage.this, " User Log in SuccessFull...", Toast.LENGTH_SHORT).show();

               Intent intent = new Intent(Log_inPage.this, Home_page.class);
                intent.putExtra("mystuts", admin_status);
                startActivity(intent);

                finish();
                progressBar.setVisibility(View.INVISIBLE);
            }


       textView_log_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Log_inPage.this, Sign_UpPage.class);
                startActivity(intent);
            }
        });

        admin_status.equals("");

    }

    public void log_in_data(View view) {
        admin_status="Admin";

        if(editText_email.getText().toString().isEmpty()){
          editText_email.setError("Your Email is Empty....");
          editText_email.requestFocus();

      }else if(editText_password.getText().toString().isEmpty()){
        editText_password.setError("Your Password is Empty....");
        editText_password.requestFocus();
    }else if(editText_password.length()<6){
          editText_password.setError("Your password Only 6 Character..");
          editText_password.setText("");
          editText_password.requestFocus();
      }
      else {
            sharedPreferences = getApplicationContext().getSharedPreferences("com.example.hotel_managment", Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = sharedPreferences.edit();

          if(checkBox_remember.isChecked()){
            databaseReference_singUp.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String em, pass;

                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            Sign_upModel upModel = dataSnapshot1.getValue(Sign_upModel.class);
                            upModel.setUser_key(dataSnapshot1.getKey());

                            em = upModel.getEmail();
                            pass = upModel.getPassword();

  if (em.contains(editText_email.getText().toString().toLowerCase()) && pass.contains(editText_password.getText().toString())) {
                                editor.putString("remember_email", upModel.getEmail()).apply();
                                editor.putString("remember_pass", upModel.getPassword()).apply();
                               // editor.putString("remember_status", "0").apply();
                      SharedPrefer_user_gmali.edit().putString("user_gmail",upModel.getEmail()).commit();
                                Toast.makeText(Log_inPage.this, "Save email:" + em + "pass:" + pass, Toast.LENGTH_LONG).show();
                            }else{
      editor.putString("remember_email", "Data Not found").apply();
      editor.putString("remember_pass", "Data Not found").apply();
                Toast.makeText(Log_inPage.this, "Your Email password Not Remember..", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
             @Override
            public void onCancelled(DatabaseError error) {
           Toast.makeText(Log_inPage.this, "Your Sign UP load Data Failed.." + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
          } else if (!checkBox_remember.isChecked()) {
                Toast.makeText(Log_inPage.this, "UnCheck", Toast.LENGTH_SHORT).show();
                editor.clear().apply();
            }

         // Intent intent=new Intent();
          final String emai=editText_email.getText().toString();
          final String passwor=editText_password.getText().toString();

          progressBar.setVisibility(View.VISIBLE);
          Thread thread=new Thread(new Runnable() {
              @Override
              public void run() {
                  setPorgesBar();
              }
          });thread.start();


          if(admin_gmail.contains(emai) && admin_password.contains(passwor)){
          Toast.makeText(Log_inPage.this, "Admin Login SuccessFull..", Toast.LENGTH_SHORT).show();
            setPorgesBar_admin();
              SharedPrefer_user_gmali.edit().putString("user_gmail",admin_gmail).commit();
          }else {
              mAuth.signInWithEmailAndPassword(emai, passwor)
                      .addOnCompleteListener(Log_inPage.this, new OnCompleteListener<AuthResult>() {
                          @Override
                          public void onComplete(@NonNull Task<AuthResult> task) {
                              if(task.isSuccessful()) {
                                  admin_status="User";
                                  Toast.makeText(Log_inPage.this, " User Log in SuccessFull...", Toast.LENGTH_SHORT).show();
                                  Intent intent=new Intent(Log_inPage.this,Home_page.class);
                                  intent.putExtra("mystuts",admin_status);
                                  startActivity(intent);
                                  finish();
                                  progressBar.setVisibility(View.INVISIBLE);
                                  editText_password.setText("");
                                  editText_email.setText("");

                                  SharedPrefer_user_gmali.edit().putString("user_gmail",emai).commit();
                              }else {
                                  editText_password.setText("");
                                  editText_email.setText("");
                                  editText_email.requestFocus();
                                  progressBar.setVisibility(View.INVISIBLE);
                                  Toast.makeText(Log_inPage.this, "Your Email OR password invlide..", Toast.LENGTH_LONG).show();
                              }
                          }
                 });
          }

      }
    }

    private void setPorgesBar(){

        for (prog=10; prog <= 100; prog=prog+10){
            try {
                Thread.sleep(400);
                progressBar.setProgress(prog);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void setPorgesBar_admin(){

        for (prog=10; prog <= 100; prog=prog+10){
            try {
                Thread.sleep(400);
                progressBar.setProgress(prog);
                if(prog==100){
                    Intent intent=new Intent(Log_inPage.this,Home_page.class);
                    /*editText_password.setText("");
                    editText_email.setText("");*/
                  intent.putExtra("mystuts",admin_status);

                  startActivity(intent);
                    finish();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}