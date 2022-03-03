package com.example.npstj.ModelClass;

import com.google.gson.annotations.SerializedName;

public class CatModel {

    @SerializedName("category_name")
    String category_name;

    @SerializedName("added_by")
    String added_by;

    @SerializedName("added_date")
    String added_date;

    @SerializedName("publish_status")
    String publish_status;

    @SerializedName("id")
    public int id;

    public CatModel(String category_name, String added_by, String added_date, String publish_status, int id) {
        this.category_name = category_name;
        this.added_by = added_by;
        this.added_date = added_date;
        this.publish_status = publish_status;
        this.id = id;
    }

    public CatModel( ) {
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getAdded_by() {
        return added_by;
    }

    public void setAdded_by(String added_by) {
        this.added_by = added_by;
    }

    public String getAdded_date() {
        return added_date;
    }

    public void setAdded_date(String added_date) {
        this.added_date = added_date;
    }

    public String getPublish_status() {
        return publish_status;
    }

    public void setPublish_status(String publish_status) {
        this.publish_status = publish_status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
