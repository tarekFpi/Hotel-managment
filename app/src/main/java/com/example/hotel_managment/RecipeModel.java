package com.example.hotel_managment;

import java.io.Serializable;

public class RecipeModel implements Serializable {
   private String recipe_id;
    private  String recipe_Name;
    private String recipe_Details;
    private int recipe_quantity;
    private int recipe_price;
    private String recipe_image;
    private  String recipe_date;


    private String recipe_data_key;

    public RecipeModel() {
    }

    public RecipeModel(String recipe_id, String recipe_Name, String recipe_Details, int recipe_quantity, int recipe_price, String recipe_image, String recipe_date) {
        this.recipe_id = recipe_id;
        this.recipe_Name = recipe_Name;
        this.recipe_Details = recipe_Details;
        this.recipe_quantity = recipe_quantity;
        this.recipe_price = recipe_price;
        this.recipe_image = recipe_image;
        this.recipe_date = recipe_date;

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

    public String getRecipe_Details() {
        return recipe_Details;
    }

    public void setRecipe_Details(String recipe_Details) {
        this.recipe_Details = recipe_Details;
    }

    public int getRecipe_quantity() {
        return recipe_quantity;
    }

    public void setRecipe_quantity(int recipe_quantity) {
        this.recipe_quantity = recipe_quantity;
    }

    public int getRecipe_price() {
        return recipe_price;
    }

    public void setRecipe_price(int recipe_price) {
        this.recipe_price = recipe_price;
    }

    public String getRecipe_image() {
        return recipe_image;
    }

    public void setRecipe_image(String recipe_image) {
        this.recipe_image = recipe_image;
    }

    public String getRecipe_date() {
        return recipe_date;
    }

    public void setRecipe_date(String recipe_date) {
        this.recipe_date = recipe_date;
    }

    public String getRecipe_data_key() {
        return recipe_data_key;
    }

    public void setRecipe_data_key(String recipe_data_key) {
        this.recipe_data_key = recipe_data_key;
    }
}
