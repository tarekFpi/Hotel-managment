package com.example.hotel_managment;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;


import java.util.ArrayList;
import java.util.List;


public class Image_Slider extends AppCompatActivity {
  private ImageSlider imageSlider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image__slider);

      imageSlider=(ImageSlider)findViewById(R.id.image_sliderid);
    final List<SlideModel>slideModels=new ArrayList<>();
    slideModels.add(new SlideModel("https://bit.ly/2YoJ77H","title", ScaleTypes.FIT));
    slideModels.add(new SlideModel("https://bit.ly/2BteuF2","title1", ScaleTypes.FIT));
    slideModels.add(new SlideModel("https://bit.ly/3fLJf72","title2", ScaleTypes.FIT));
    imageSlider.setImageList(slideModels,ScaleTypes.FIT);

    imageSlider.setItemClickListener(new ItemClickListener() {
      @Override
      public void onItemSelected(int position) {
    Toast.makeText(Image_Slider.this, "Select :"+slideModels.get(position).getTitle(), Toast.LENGTH_SHORT).show();
      }
    });

    }
}