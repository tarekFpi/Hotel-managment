package com.example.hotel_managment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class Sign_UpPage extends AppCompatActivity {
  private EditText editText_userName,editText_email,editText_password,editText_comf_pass,editText_phon;
   private RadioGroup radioGroup;
   private RadioButton radioButton_male,radioButton_female;
   private TextView textView_sign_Up;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private DatabaseReference databaseReference_sign_up;
     private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up_page);
        mAuth = FirebaseAuth.getInstance();
        databaseReference_sign_up= FirebaseDatabase.getInstance().getReference("Sign_upData");
        editText_userName=(EditText)findViewById(R.id.user_name_id);
        editText_email=(EditText)findViewById(R.id.email_id);
        editText_password=(EditText)findViewById(R.id.password_id);
        editText_comf_pass=(EditText)findViewById(R.id.comf_password);
        editText_phon=(EditText)findViewById(R.id.user_phon);
        radioGroup=(RadioGroup)findViewById(R.id.radi_groupId);
        textView_sign_Up=(TextView)findViewById(R.id.text_sign_id);
        radioGroup=(RadioGroup)findViewById(R.id.radi_groupId);

        radioButton_male=(RadioButton)findViewById(R.id.male_id);
        radioButton_female =(RadioButton)findViewById(R.id.female_id);

        progressBar=(ProgressBar) findViewById(R.id.sign_upProgressbar);

        textView_sign_Up.setOnClickListener(new View.OnClickListener() {
            @Override
         public void onClick(View view) {
         Intent intent=new Intent(Sign_UpPage.this,Log_inPage.class);
         startActivity(intent);
         finish();
          }
        });


    }

    public void Sign_upData_Save(View view) {

        final int r_grop=radioGroup.getCheckedRadioButtonId();
        //radioButton=(RadioButton) findViewById(r_grop);
     //  String value=radioButton.getText().toString();

    //Toast.makeText(this, ""+value, Toast.LENGTH_SHORT).show();
      final String user_name= editText_userName.getText().toString();
      final String user_email= editText_email.getText().toString();
      final String pass= editText_password.getText().toString();
      final String com_pass= editText_comf_pass.getText().toString();
      final String phon= editText_phon.getText().toString();

         if(TextUtils.isEmpty(user_name)){
            editText_userName.setError("Your Name is Empty..");
            editText_userName.requestFocus();
            return;

        }else if(TextUtils.isEmpty(user_email)){
            editText_email.setError("Your Email is Empty..");
            editText_email.requestFocus();
            return;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(user_email).matches()){
            editText_email.setError("Your Email is Invalid..!!");
            editText_email.requestFocus();
            return;
        }else if(pass.length()<6){
            editText_password.setError("Your password Only 6 Character..");
            editText_password.requestFocus();
            return;
        }
        else if(TextUtils.isEmpty(pass)){
            editText_password.setError("Your password is Empty..");
            editText_password.requestFocus();
            return;
        }
        else if(TextUtils.isEmpty(com_pass)){
            editText_comf_pass.setError("Your ComFrom password is Empty..");
            editText_comf_pass.requestFocus();
            return;
        }
        else if(TextUtils.isEmpty(phon)){
            editText_phon.setError("Your phon Number is Empty..");
            editText_phon.requestFocus();
            return;
        }else if(r_grop==-1){
            Toast.makeText(this, "please your Gender Select", Toast.LENGTH_SHORT).show();
        }/* else if(!radioButton.getText().toString().isEmpty()){
            gender=radioButton.getText().toString();
         Toast.makeText(this, "please your Gender :"+gender, Toast.LENGTH_SHORT).show();
        }*/
        else {
           if(radioButton_male.isChecked()){
               gender=  radioButton_male.getText().toString();
            }else if(radioButton_female.isChecked()){
               gender= radioButton_male.getText().toString();
            }
             if(com_pass.contains(pass)==false){
                 editText_comf_pass.setError("Your password Not Match Empty..");
                 editText_password.setText("");
                 editText_comf_pass.setText("");
                 editText_password.requestFocus();
                 return;
             }
             else{

                 progressBar.setVisibility(View.VISIBLE);
                 mAuth.createUserWithEmailAndPassword(user_email, com_pass)
                         .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                             @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()) {

        Sign_upModel sign_upModel_item=new Sign_upModel(user_name,user_email,pass,phon, gender);
                   String myKey= databaseReference_sign_up.push().getKey();
                databaseReference_sign_up.child(myKey).setValue(sign_upModel_item).addOnSuccessListener(new OnSuccessListener<Void>() {
                          @Override
                          public void onSuccess(Void aVoid) {
                              Toast.makeText(Sign_UpPage.this, "Your Sign Up SuccessFull..", Toast.LENGTH_SHORT).show();
                              editText_comf_pass.setText("");
                              editText_email.setText("");
                              editText_password.setText("");
                              editText_phon.setText("");
                              editText_userName.setText("");
                              radioGroup.clearCheck();
                              editText_userName.requestFocus();
                              progressBar.setVisibility(View.INVISIBLE);

                          }
                      }).addOnFailureListener(new OnFailureListener() {
                          @Override
                          public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Sign_UpPage.this, "Your Add Falide..", Toast.LENGTH_SHORT).show();
                          }
                      });

                           Toast.makeText(Sign_UpPage.this, "Your Sign Up SuccessFull..", Toast.LENGTH_SHORT).show();
                          }else if(task.getException() instanceof FirebaseAuthUserCollisionException){
                            Toast.makeText(Sign_UpPage.this, "Your Regestion Alrady Exite..", Toast.LENGTH_SHORT).show();
                                     editText_comf_pass.setText("");
                                     editText_email.setText("");
                                     editText_password.setText("");
                                     editText_phon.setText("");
                                     editText_userName.setText("");
                                     radioGroup.clearCheck();
                                     editText_userName.requestFocus();
                                     progressBar.setVisibility(View.INVISIBLE);
                                 }else {

                 Toast.makeText(Sign_UpPage.this, "Your Sign Up Failde..", Toast.LENGTH_SHORT).show();
                 editText_comf_pass.setText("");
                 editText_email.setText("");
                 editText_password.setText("");
                 editText_phon.setText("");
                 editText_userName.setText("");
                 radioGroup.clearCheck();
                 editText_userName.requestFocus();
                progressBar.setVisibility(View.INVISIBLE);
                                 }
                             }
                         });
             }
        }
    }

}