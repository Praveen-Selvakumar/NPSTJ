package com.example.npstj.ModelClass;

import com.google.gson.annotations.SerializedName;

public class StoreModel {



    @SerializedName("id") String id;
    @SerializedName("brand_name") String brand_name;
    @SerializedName("brand_logo") String brand_logo;
    @SerializedName("brand_url") String brand_url;
    @SerializedName("brand_description") String brand_description;
    @SerializedName("added_by") String added_by;
    @SerializedName("added_date") String added_date;
    @SerializedName("publish_status") String publish_status;

    public StoreModel() {
     }

    public void setId(String id) {
        this.id = id;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public void setBrand_logo(String brand_logo) {
        this.brand_logo = brand_logo;
    }

    public void setBrand_url(String brand_url) {
        this.brand_url = brand_url;
    }

    public void setBrand_description(String brand_description) {
        this.brand_description = brand_description;
    }

    public void setAdded_by(String added_by) {
        this.added_by = added_by;
    }

    public void setAdded_date(String added_date) {
        this.added_date = added_date;
    }

    public void setPublish_status(String publish_status) {
        this.publish_status = publish_status;
    }

    public String getId() {
        return id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public String getBrand_logo() {
        return brand_logo;
    }

    public String getBrand_url() {
        return brand_url;
    }

    public String getBrand_description() {
        return brand_description;
    }

    public String getAdded_by() {
        return added_by;
    }

    public String getAdded_date() {
        return added_date;
    }

    public String getPublish_status() {
        return publish_status;
    }
}
