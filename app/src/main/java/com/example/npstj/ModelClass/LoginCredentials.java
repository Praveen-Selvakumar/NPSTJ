package com.example.npstj.ModelClass;

import com.google.gson.annotations.SerializedName;

public class LoginCredentials {

    @SerializedName("status")String status;
    @SerializedName("message")String message;
    @SerializedName("user_check")String user_check;
    @SerializedName("parimary_number")String parimary_number;
    @SerializedName("user_id")String user_id;
    @SerializedName("converted_number")String converted_number;


    public LoginCredentials(String status, String message, String user_check, String parimary_number, String user_id,String converted_number) {
        this.status = status;
        this.message = message;
        this.user_check = user_check;
        this.parimary_number = parimary_number;
        this.user_id = user_id;
        this.converted_number = converted_number;
    }

    public LoginCredentials() {
     }



    public String getConverted_number() {
        return converted_number;
    }


    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getUser_check() {
        return user_check;
    }

    public String getParimary_number() {
        return parimary_number;
    }

    public String getUser_id() {
        return user_id;
    }
}
