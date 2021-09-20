package com.example.hotel_managment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ViewPager_Adapter extends PagerAdapter {
 private Context context;
 private LayoutInflater layoutInflater;
 private int []image={R.drawable.back_color,R.drawable.check_out,R.drawable.romm,R.drawable.room2};
    public ViewPager_Adapter(Context context) {
        this.context=context;
    }

    @Override
    public int getCount() {
        return image.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.coustom_view,null);

        ImageView imageView=(ImageView)view.findViewById(R.id.myImage_view);
        imageView.setImageResource(image[position]);

        ViewPager viewPager=(ViewPager)container;
        viewPager.addView(view);
        return view;


    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        ViewPager viewPager=(ViewPager)container;
        View view=(View)object;
        viewPager.removeView(view);

      //  super.destroyItem(container, position, object);
    }
}
