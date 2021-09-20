package com.example.hotel_managment;

import com.google.firebase.database.DataSnapshot;

public class Recipe_payment_Modle {
    private String Room_id;
    private String Custom_name;
    private String Custom_Phon;
    private String user_allOrder;
    private int Toal_price;
    private String Custom_image;
    private String recipe_delivayDate;

    private String recipe_payment_Key;

     public Recipe_payment_Modle() {
    }

    public Recipe_payment_Modle(String room_id, String custom_name, String custom_Phon, String user_allOrder, int toal_price, String custom_image, String recipe_delivayDate) {
        Room_id = room_id;
        Custom_name = custom_name;
        Custom_Phon = custom_Phon;
        this.user_allOrder = user_allOrder;
        Toal_price = toal_price;
        Custom_image = custom_image;
        this.recipe_delivayDate = recipe_delivayDate;
    }

    public String getRecipe_payment_Key() {
        return recipe_payment_Key;
    }

    public void setRecipe_payment_Key(String recipe_payment_Key) {
        this.recipe_payment_Key = recipe_payment_Key;
    }

    public String getRoom_id() {
        return Room_id;
    }

    public void setRoom_id(String room_id) {
        Room_id = room_id;
    }

    public String getCustom_name() {
        return Custom_name;
    }

    public void setCustom_name(String custom_name) {
        Custom_name = custom_name;
    }

    public String getCustom_Phon() {
        return Custom_Phon;
    }

    public void setCustom_Phon(String custom_Phon) {
        Custom_Phon = custom_Phon;
    }

    public String getUser_allOrder() {
        return user_allOrder;
    }

    public void setUser_allOrder(String user_allOrder) {
        this.user_allOrder = user_allOrder;
    }

    public int getToal_price() {
        return Toal_price;
    }

    public void setToal_price(int toal_price) {
        Toal_price = toal_price;
    }

    public String getCustom_image() {
        return Custom_image;
    }

    public void setCustom_image(String custom_image) {
        Custom_image = custom_image;
    }

    public String getRecipe_delivayDate() {
        return recipe_delivayDate;
    }

    public void setRecipe_delivayDate(String recipe_delivayDate) {
        this.recipe_delivayDate = recipe_delivayDate;
    }
}
