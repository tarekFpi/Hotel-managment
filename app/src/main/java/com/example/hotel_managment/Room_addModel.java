package com.example.hotel_managment;

public class Room_addModel {
   private String roomId;
   private String roomType;
   private String badType;
   private String room_price;
   private String  Image_uri;
   private String date;
    private String data_Key;

    public Room_addModel() {
    }

    public Room_addModel(String roomId, String roomType, String badType, String room_price, String room_image, String date) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.badType = badType;
        this.room_price = room_price;
        this.Image_uri = room_image;
        this.date = date;
    }

    public String getData_Key() {
        return data_Key;
    }

    public void setData_Key(String data_Key) {
        this.data_Key = data_Key;
    }

    public String getRoom_image() {
        return Image_uri;
    }

    public void setRoom_image(String room_image) {
        this.Image_uri = room_image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getBadType() {
        return badType;
    }

    public void setBadType(String badType) {
        this.badType = badType;
    }

    public String getRoom_price() {
        return room_price;
    }

    public void setRoom_price(String room_price) {
        this.room_price = room_price;
    }
}
