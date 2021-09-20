package com.example.hotel_managment;

public class check_out_rmModel {
    private String room_id;
    private String room_type;
    private String badType;
    private int room_price ;
    private String Custom_name;
    private String Custom_deatils;
    private String Custom_image;
    private String Nid_card;
    private String cust_phon;
    private String roomStatus_status;
    private String check_InDate;
    private String check_outDate;
    private String total_day;
    private int total_room_price;

    private String user_data_key;

    public String getUser_data_key() {
        return user_data_key;
    }

    public void setUser_data_key(String user_data_key) {
        this.user_data_key = user_data_key;
    }

    public check_out_rmModel() {

    }

    public check_out_rmModel(String room_id, String room_type, String badType, int room_price, String custom_name, String custom_deatils, String custom_image, String nid_card, String cust_phon, String roomStatus_status, String check_InDate, String check_outDate, String total_day, int total_room_price) {
        this.room_id = room_id;
        this.room_type = room_type;
        this.badType = badType;
        this.room_price = room_price;
        Custom_name = custom_name;
        Custom_deatils = custom_deatils;
        Custom_image = custom_image;
        Nid_card = nid_card;
        this.cust_phon = cust_phon;
        this.roomStatus_status = roomStatus_status;
        this.check_InDate = check_InDate;
        this.check_outDate = check_outDate;
        this.total_day = total_day;
        this.total_room_price = total_room_price;
    }

    public int getTotal_room_price() {
        return total_room_price;
    }

    public void setTotal_room_price(int total_room_price) {
        this.total_room_price = total_room_price;
    }

    public String getRoomStatus_status() {
        return roomStatus_status;
    }

    public void setRoomStatus_status(String roomStatus_status) {
        this.roomStatus_status = roomStatus_status;
    }

    public String getTotal_day() {
        return total_day;
    }

    public void setTotal_day(String total_day) {
        this.total_day = total_day;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public String getBadType() {
        return badType;
    }

    public void setBadType(String badType) {
        this.badType = badType;
    }

    public int getRoom_price() {
        return room_price;
    }

    public void setRoom_price(int room_price) {
        this.room_price = room_price;
    }

    public String getCustom_name() {
        return Custom_name;
    }

    public void setCustom_name(String custom_name) {
        Custom_name = custom_name;
    }

    public String getCustom_deatils() {
        return Custom_deatils;
    }

    public void setCustom_deatils(String custom_deatils) {
        Custom_deatils = custom_deatils;
    }

    public String getCustom_image() {
        return Custom_image;
    }

    public void setCustom_image(String custom_image) {
        Custom_image = custom_image;
    }

    public String getNid_card() {
        return Nid_card;
    }

    public void setNid_card(String nid_card) {
        Nid_card = nid_card;
    }

    public String getCust_phon() {
        return cust_phon;
    }

    public void setCust_phon(String cust_phon) {
        this.cust_phon = cust_phon;
    }

    public String getCheck_InDate() {
        return check_InDate;
    }

    public void setCheck_InDate(String check_InDate) {
        this.check_InDate = check_InDate;
    }

    public String getCheck_outDate() {
        return check_outDate;
    }

    public void setCheck_outDate(String check_outDate) {
        this.check_outDate = check_outDate;
    }
}
