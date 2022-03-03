package com.example.npstj.ModelClass;

import com.google.gson.annotations.SerializedName;

public class CircularList_Model {

    @SerializedName("id") String id;
    @SerializedName("title") String title;
    @SerializedName("description") String description;
    @SerializedName("circular_date") String circular_date;
    @SerializedName("publish_status") String publish_status;
    @SerializedName("added_by") String added_by;
    @SerializedName("added_date") String added_date;
    @SerializedName("class_id") String class_id;
    @SerializedName("section_id") String section_id;
    @SerializedName("circular_file") String circular_file;

    public CircularList_Model() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCircular_date() {
        return circular_date;
    }

    public void setCircular_date(String circular_date) {
        this.circular_date = circular_date;
    }

    public String getPublish_status() {
        return publish_status;
    }

    public void setPublish_status(String publish_status) {
        this.publish_status = publish_status;
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

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getSection_id() {
        return section_id;
    }

    public void setSection_id(String section_id) {
        this.section_id = section_id;
    }

    public String getCircular_file() {
        return circular_file;
    }

    public void setCircular_file(String circular_file) {
        this.circular_file = circular_file;
    }
}
