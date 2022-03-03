package com.example.npstj.ModelClass;

import com.google.gson.annotations.SerializedName;

public class NotificationList {

    @SerializedName("id")String id;
    @SerializedName("content_id")String content_id;
    @SerializedName("content_type")String content_type;
    @SerializedName("notification_message")String notification_message;
    @SerializedName("class_info")String class_info;
    @SerializedName("section_info")String section_info;
    @SerializedName("datex")String datex;

    public  String class_,section_,img_,name_,envi_id_;

    public String getClass_() {
        return class_;
    }

    public void setClass_(String class_) {
        this.class_ = class_;
    }

    public String getSection_() {
        return section_;
    }

    public void setSection_(String section_) {
        this.section_ = section_;
    }

    public String getImg_() {
        return img_;
    }

    public void setImg_(String img_) {
        this.img_ = img_;
    }

    public String getName_() {
        return name_;
    }

    public void setName_(String name_) {
        this.name_ = name_;
    }

    public String getEnvi_id_() {
        return envi_id_;
    }

    public void setEnvi_id_(String envi_id_) {
        this.envi_id_ = envi_id_;
    }

    public NotificationList( ) {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent_id() {
        return content_id;
    }

    public void setContent_id(String content_id) {
        this.content_id = content_id;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getNotification_message() {
        return notification_message;
    }

    public void setNotification_message(String notification_message) {
        this.notification_message = notification_message;
    }

    public String getClass_info() {
        return class_info;
    }

    public void setClass_info(String class_info) {
        this.class_info = class_info;
    }

    public String getSection_info() {
        return section_info;
    }

    public void setSection_info(String section_info) {
        this.section_info = section_info;
    }

    public String getDatex() {
        return datex;
    }

    public void setDatex(String datex) {
        this.datex = datex;
    }

}
