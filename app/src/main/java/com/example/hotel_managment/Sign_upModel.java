package com.example.hotel_managment;

public class Sign_upModel {
    private String user_name;
    private String email;
    private String password;
    private String phon;
    private String Gender;
    private String User_key;

    public String getUser_key() {
        return User_key;
    }

    public void setUser_key(String user_key) {
        User_key = user_key;
    }
    public Sign_upModel() {
    }

    public Sign_upModel(String user_name, String email, String password, String phon, String gender) {
        this.user_name = user_name;
        this.email = email;
        this.password = password;
        this.phon = phon;
        Gender = gender;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getPhon() {
        return phon;
    }

    public void setPhon(String phon) {
        this.phon = phon;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }
}
