package com.example.hotel_managment;

public class Recipe_categoryModel {
    private String recipe_id;
    private String recipe_Name;
    private String recipe_expDate;
    private int recipe_quantity;
  //  private String recipe_Image;
    private int  recipe_totalPrice;
      private String  user_gmail;
    private String  user_room_id;
    private String  firebase_dataKey;

    public Recipe_categoryModel() {
    }

    public Recipe_categoryModel(String recipe_id, String recipe_Name, String recipe_expDate, int recipe_quantity, int recipe_totalPrice, String user_gmail, String user_room_id) {
        this.recipe_id = recipe_id;
        this.recipe_Name = recipe_Name;
        this.recipe_expDate = recipe_expDate;
        this.recipe_quantity = recipe_quantity;
        this.recipe_totalPrice = recipe_totalPrice;
        this.user_gmail = user_gmail;
        this.user_room_id = user_room_id;
    }

    public String getUser_room_id() {
        return user_room_id;
    }

    public void setUser_room_id(String user_room_id) {
        this.user_room_id = user_room_id;
    }

    public String getUser_gmail() {
        return user_gmail;
    }

    public void setUser_gmail(String user_gmail) {
        this.user_gmail = user_gmail;
    }

    public String getFirebase_dataKey() {
        return firebase_dataKey;
    }

    public void setFirebase_dataKey(String firebase_dataKey) {
        this.firebase_dataKey = firebase_dataKey;
    }

    public int getRecipe_quantity() {
        return recipe_quantity;
    }

    public int setRecipe_quantity(int recipe_quantity) {
        this.recipe_quantity = recipe_quantity;
        return recipe_quantity;
    }

    public String getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(String recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getRecipe_Name() {
        return recipe_Name;
    }

    public void setRecipe_Name(String recipe_Name) {
        this.recipe_Name = recipe_Name;
    }

    public String getRecipe_expDate() {
        return recipe_expDate;
    }

    public void setRecipe_expDate(String recipe_expDate) {
        this.recipe_expDate = recipe_expDate;
    }

   /* public String getRecipe_Image() {
        return recipe_Image;
    }

    public void setRecipe_Image(String recipe_Image) {
        this.recipe_Image = recipe_Image;
    }*/

    public int getRecipe_totalPrice() {
        return recipe_totalPrice;
    }

    public void setRecipe_totalPrice(int recipe_totalPrice) {
        this.recipe_totalPrice = recipe_totalPrice;
    }
}
