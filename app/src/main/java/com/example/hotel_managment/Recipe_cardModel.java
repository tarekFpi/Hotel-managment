package com.example.hotel_managment;

public class Recipe_cardModel {
   private String rec_id;
   private String rec_Name;
   private  int rec_quantity;
   private  int rec_totalPrice;

   private String reci_key;


    public Recipe_cardModel() {
    }

    public Recipe_cardModel(String rec_id, String rec_Name, int rec_quantity, int rec_totalPrice) {
        this.rec_id = rec_id;
        this.rec_Name = rec_Name;
        this.rec_quantity = rec_quantity;
        this.rec_totalPrice = rec_totalPrice;
    }

    public String getReci_key() {
        return reci_key;
    }

    public void setReci_key(String reci_key) {
        this.reci_key = reci_key;
    }

    public String getRec_id() {
        return rec_id;
    }

    public void setRec_id(String rec_id) {
        this.rec_id = rec_id;
    }

    public String getRec_Name() {
        return rec_Name;
    }

    public void setRec_Name(String rec_Name) {
        this.rec_Name = rec_Name;
    }

    public int getRec_quantity() {
        return rec_quantity;
    }

    public void setRec_quantity(int rec_quantity) {
        this.rec_quantity = rec_quantity;
    }

    public int getRec_totalPrice() {
        return rec_totalPrice;
    }

    public void setRec_totalPrice(int rec_totalPrice) {
        this.rec_totalPrice = rec_totalPrice;
    }
}
