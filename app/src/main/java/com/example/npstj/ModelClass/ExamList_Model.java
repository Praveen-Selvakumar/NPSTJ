package com.example.npstj.ModelClass;

import com.google.gson.annotations.SerializedName;

public class ExamList_Model {


    @SerializedName("id") String id;
    @SerializedName("exam_name") String exam_name;
    @SerializedName("starting_date") String starting_date;
    @SerializedName("ending_date") String ending_date;
    @SerializedName("expirary_date") String expirary_date;
    @SerializedName("exam_command") String exam_command;
    @SerializedName("publish_status") String publish_status;
    @SerializedName("added_by") String added_by;
    @SerializedName("added_date") String added_date;
    @SerializedName("class_id") String class_id;
    @SerializedName("section_id") String section_id;

    public ExamList_Model(String id, String exam_name, String starting_date, String ending_date, String expirary_date, String exam_command, String publish_status, String added_by, String added_date, String class_id, String section_id) {
        this.id = id;
        this.exam_name = exam_name;
        this.starting_date = starting_date;
        this.ending_date = ending_date;
        this.expirary_date = expirary_date;
        this.exam_command = exam_command;
        this.publish_status = publish_status;
        this.added_by = added_by;
        this.added_date = added_date;
        this.class_id = class_id;
        this.section_id = section_id;
    }

    public ExamList_Model() {
     }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExam_name() {
        return exam_name;
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
    }

    public String getStarting_date() {
        return starting_date;
    }

    public void setStarting_date(String starting_date) {
        this.starting_date = starting_date;
    }

    public String getEnding_date() {
        return ending_date;
    }

    public void setEnding_date(String ending_date) {
        this.ending_date = ending_date;
    }

    public String getExpirary_date() {
        return expirary_date;
    }

    public void setExpirary_date(String expirary_date) {
        this.expirary_date = expirary_date;
    }

    public String getExam_command() {
        return exam_command;
    }

    public void setExam_command(String exam_command) {
        this.exam_command = exam_command;
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
}
