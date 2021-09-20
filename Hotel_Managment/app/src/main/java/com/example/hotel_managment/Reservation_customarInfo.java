package com.example.hotel_managment;

public class Reservation_customarInfo {
     private String Res_RoomNo;
    private String Res_RoomType;
    private String Res_BadType;
    private int Res_RoomPrice;
    private String Res_RoomImage;
    private String cut_name;
    private String cut_address;
    private String cut_Nad;
    private String cut_phon;
    private String Date;
    private String RoomCheekIn;

   private String user_Gmail;
    private String reservation_data_Key;

   public   Reservation_customarInfo(){

     }

    public Reservation_customarInfo(String res_RoomNo, String res_RoomType, String res_BadType, int res_RoomPrice, String res_RoomImage, String cut_name, String cut_address, String cut_Nad, String cut_phon, String date, String roomCheekIn, String user_Gmail) {
        Res_RoomNo = res_RoomNo;
        Res_RoomType = res_RoomType;
        Res_BadType = res_BadType;
        Res_RoomPrice = res_RoomPrice;
        Res_RoomImage = res_RoomImage;
        this.cut_name = cut_name;
        this.cut_address = cut_address;
        this.cut_Nad = cut_Nad;
        this.cut_phon = cut_phon;
        Date = date;
        RoomCheekIn = roomCheekIn;
        this.user_Gmail = user_Gmail;
    }

    public String getUser_Gmail() {
        return user_Gmail;
    }

    public void setUser_Gmail(String user_Gmail) {
        this.user_Gmail = user_Gmail;
    }

    public String getReservation_data_Key() {
        return reservation_data_Key;
    }

    public void setReservation_data_Key(String reservation_data_Key) {
        this.reservation_data_Key = reservation_data_Key;
    }

    public String getRes_RoomNo() {
        return Res_RoomNo;
    }

    public void setRes_RoomNo(String res_RoomNo) {
        Res_RoomNo = res_RoomNo;
    }

    public String getRes_RoomType() {
        return Res_RoomType;
    }

    public void setRes_RoomType(String res_RoomType) {
        Res_RoomType = res_RoomType;
    }

    public String getRes_BadType() {
        return Res_BadType;
    }

    public void setRes_BadType(String res_BadType) {
        Res_BadType = res_BadType;
    }

    public int getRes_RoomPrice() {
        return Res_RoomPrice;
    }

    public void setRes_RoomPrice(int res_RoomPrice) {
        Res_RoomPrice = res_RoomPrice;
    }

    public String getRes_RoomImage() {
        return Res_RoomImage;
    }

    public void setRes_RoomImage(String res_RoomImage) {
        Res_RoomImage = res_RoomImage;
    }

    public String getCut_name() {
        return cut_name;
    }

    public void setCut_name(String cut_name) {
        this.cut_name = cut_name;
    }

    public String getCut_address() {
        return cut_address;
    }

    public void setCut_address(String cut_address) {
        this.cut_address = cut_address;
    }

    public String getCut_Nad() {
        return cut_Nad;
    }

    public void setCut_Nad(String cut_Nad) {
        this.cut_Nad = cut_Nad;
    }

    public String getCut_phon() {
        return cut_phon;
    }

    public void setCut_phon(String cut_phon) {
        this.cut_phon = cut_phon;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getRoomCheekIn() {
        return RoomCheekIn;
    }

    public void setRoomCheekIn(String roomCheekIn) {
        RoomCheekIn = roomCheekIn;
    }
}
