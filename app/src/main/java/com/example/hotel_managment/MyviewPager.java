package com.example.hotel_managment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.common.api.internal.OnConnectionFailedListener;

public class MyviewPager extends AppCompatActivity {
private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myview_pager);

        viewPager=(ViewPager)findViewById(R.id.my_viewpager_id);
        ViewPager_Adapter adapter=new ViewPager_Adapter(this);
        viewPager.setAdapter(adapter);

    }
}