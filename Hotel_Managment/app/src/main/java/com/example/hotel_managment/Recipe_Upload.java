package com.example.hotel_managment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import dalvik.system.DexFile;

public class Recipe_Upload extends AppCompatActivity {
  private DatabaseReference databaseReference;
  private StorageReference storageReference;
  private static int requentImageCode=1;
  private Uri image_uri;
  private EditText editTextId, editTextRecipe_name,editTextRecipe_deatils,editTextRecipe_price,editText_qunatity;
  private TextView textViewDate;
  private ImageView imageView_Recipe;
  private DatePickerDialog.OnDateSetListener mDateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe__upload);
        databaseReference= FirebaseDatabase.getInstance().getReference("Recipe_upload");
        storageReference= FirebaseStorage.getInstance().getReference("Recipe_upload");

        imageView_Recipe=(ImageView)findViewById(R.id.recipe_image);
        editTextId=(EditText) findViewById(R.id.recipe_id);
        editTextRecipe_name=(EditText)findViewById(R.id.recipe_name);
        editTextRecipe_deatils=(EditText)findViewById(R.id.recipe_deatils);
        editTextRecipe_price=(EditText)findViewById(R.id.recipe_price);
        editText_qunatity=(EditText)findViewById(R.id.recipe_quantity);
        textViewDate=(TextView)findViewById(R.id.recipe_date);
        //datePicker=(DatePicker)findViewById(R.id.date_picker);
        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                //textView.setText("");
                DatePickerDialog datePickerDialog =new DatePickerDialog(Recipe_Upload.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth
                ,mDateListener,year,month,day);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

                mDateListener=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month=month+1;
                        StringBuilder sp=new StringBuilder();
                        sp.append(dayOfMonth);
                        sp.append("/"+month);
                        sp.append("/"+year);
                        textViewDate.setText(sp);
                    }
                };
            }
        });

    /*    Calendar calendar= Calendar.getInstance();
       String currentDate=SimpleDateFormat.getDateInstance().format(calendar.getTime());
       textViewDate.setText(currentDate);*/
    }

    public void select_imageData(View view) {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,requentImageCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requentImageCode==requestCode && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            image_uri=data.getData();
            Picasso.get().load(image_uri).into(imageView_Recipe);
        }
    }

    public void recipe_addData(View view) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Uploding...");
        progressDialog.show();
        final String recipe_id = editTextId.getText().toString().trim();
        if (TextUtils.isEmpty(recipe_id)) {
            Toast.makeText(this, "Recipe id is Empty", Toast.LENGTH_SHORT).show();
            editTextId.setError("Recipe id is Empty..");
            editTextId.requestFocus();
            progressDialog.dismiss();
            return;
        } else if (editTextRecipe_name.getText().toString().isEmpty()) {
            Toast.makeText(this, "Recipe Name is Empty", Toast.LENGTH_SHORT).show();
            editTextRecipe_name.setError("Recipe Name is Empty..");
            editTextRecipe_name.requestFocus();
            progressDialog.dismiss();
        } else if (editTextRecipe_deatils.getText().toString().isEmpty()) {
            Toast.makeText(this, "Recipe Deatils is Empty.", Toast.LENGTH_SHORT).show();
            editTextRecipe_deatils.setError("Recipe Deatils is Empty..");
            editTextRecipe_deatils.requestFocus();
            progressDialog.dismiss();
        } else if (editTextRecipe_price.getText().toString().isEmpty()) {
            Toast.makeText(this, "Recipe Price is Name", Toast.LENGTH_SHORT).show();
            editTextRecipe_price.setError("Recipe price is Empty..");
            editTextRecipe_price.requestFocus();
            progressDialog.dismiss();

        } else if (editText_qunatity.getText().toString().isEmpty()) {
            Toast.makeText(this, "Recipe Quantity is Name", Toast.LENGTH_SHORT).show();
            editText_qunatity.setError("Recipe Quantity is Empty..");
            editText_qunatity.requestFocus();
            progressDialog.dismiss();
        } else if (textViewDate.getText().toString().isEmpty()) {
            Toast.makeText(this, "Recipe Expired Date is Empty..", Toast.LENGTH_SHORT).show();
            textViewDate.requestFocus();
            progressDialog.dismiss();
        }else if (imageView_Recipe.getDrawable() == null) {
            Toast.makeText(this, "Recipe Image is Empty..", Toast.LENGTH_SHORT).show();
            imageView_Recipe.requestFocus();
            progressDialog.dismiss();
        } else {
            progressDialog.show();
            //final String recipe_id = editTextId.getText().toString().trim();
            final String recipe_Name = editTextRecipe_name.getText().toString().trim();
            final int recipe_quantity = Integer.parseInt(editText_qunatity.getText().toString());
            final int recipe_price = Integer.parseInt(editTextRecipe_price.getText().toString());
            final String recipe_deatils = editTextRecipe_deatils.getText().toString().trim();
            final String recipe_Date = textViewDate.getText().toString().trim();

            StorageReference storageReferImage = storageReference.child(System.currentTimeMillis() + ".jpg");
            storageReferImage.putFile(image_uri)
               .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                      Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isComplete()) ;
                            Uri dowanload_uri = uriTask.getResult();

                            RecipeModel item = new RecipeModel(recipe_id, recipe_Name, recipe_deatils, recipe_quantity, recipe_price, dowanload_uri.toString(), recipe_Date);
                            String key = databaseReference.push().getKey();
                            databaseReference.child(key).setValue(item);
                            Toast.makeText(Recipe_Upload.this, "Your Data Upload SuccessFull", Toast.LENGTH_SHORT).show();
                            editTextId.setText("");
                            editText_qunatity.setText("");
                            editTextRecipe_deatils.setText("");
                            editTextRecipe_name.setText("");
                            editTextRecipe_price.setText("");
                            imageView_Recipe.setImageResource(0);
                            textViewDate.setText("");
                            textViewDate.setText(" Expired Date");

                            if (imageView_Recipe != null) {
                          imageView_Recipe.setImageResource(R.drawable.restaurant);
                            }
                            progressDialog.dismiss();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(Recipe_Upload.this, "Your Upload Failde.!!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    float percent = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    progressDialog.setMessage("Uploaded..." + (int) percent + "%");
                }
            });
        }
    }
}