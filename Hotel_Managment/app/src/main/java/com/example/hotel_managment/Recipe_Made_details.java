package com.example.hotel_managment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Recipe_Made_details extends AppCompatActivity {
 private WebView webView;
 private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe__made_details);
        webView=(WebView)findViewById(R.id.recipe_webview);

       progressDialog=new ProgressDialog(this);
       progressDialog.setMessage("Please Wite..");
       progressDialog.show();
        WebSettings webSettings=webView.getSettings();
         webSettings.setJavaScriptEnabled(true);
         webView.setWebViewClient(new WebViewClient());
         webView.loadUrl("https://www.themealdb.com/");
        progressDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
         webView.goBack();
        }else{
          super.onBackPressed();
        }

    }
}