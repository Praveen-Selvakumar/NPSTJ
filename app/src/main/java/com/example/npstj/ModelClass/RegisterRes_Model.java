package com.example.npstj.ModelClass;

import com.google.gson.annotations.SerializedName;

public class RegisterRes_Model {


    @SerializedName("status") public String status;

    @SerializedName("message") public String message;

    @SerializedName("user_check") public String user_check;



    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getUser_check() {
        return user_check;
    }
}
