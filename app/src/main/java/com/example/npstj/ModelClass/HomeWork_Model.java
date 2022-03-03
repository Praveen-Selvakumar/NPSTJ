package com.example.npstj.ModelClass;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HomeWork_Model {

    @SerializedName("id") public String id;
    @SerializedName("class_id") public String class_id;
    @SerializedName("section_id") public String section_id;
    @SerializedName("subject_name") public String subject_name;
    @SerializedName("teacher_name") public String teacher_name;
    @SerializedName("assignment_title") public String assignment_title;
    @SerializedName("assignment_description") public String assignment_description;
    @SerializedName("deadline_date") public String deadline_date;
    @SerializedName("assignment_file") public String assignment_file;
    @SerializedName("assignment_given_date") public String assignment_given_date;
    @SerializedName("published_status") public String published_status;
    @SerializedName("added_by") public String added_by;
    @SerializedName("added_date") public String added_date;


    public HomeWork_Model(String id, String class_id, String section_id, String subject_name, String teacher_name, String assignment_title, String assignment_description, String deadline_date, String assignment_file, String assignment_given_date, String published_status, String added_by, String added_date) {
        this.id = id;
        this.class_id = class_id;
        this.section_id = section_id;
        this.subject_name = subject_name;
        this.teacher_name = teacher_name;
        this.assignment_title = assignment_title;
        this.assignment_description = assignment_description;
        this.deadline_date = deadline_date;
        this.assignment_file = assignment_file;
        this.assignment_given_date = assignment_given_date;
        this.published_status = published_status;
        this.added_by = added_by;
        this.added_date = added_date;
    }

    public HomeWork_Model( ) {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getAssignment_title() {
        return assignment_title;
    }

    public void setAssignment_title(String assignment_title) {
        this.assignment_title = assignment_title;
    }

    public String getAssignment_description() {
        return assignment_description;
    }

    public void setAssignment_description(String assignment_description) {
        this.assignment_description = assignment_description;
    }

    public String getDeadline_date() {
        return deadline_date;
    }

    public void setDeadline_date(String deadline_date) {
        this.deadline_date = deadline_date;
    }

    public String getAssignment_file() {
        return assignment_file;
    }

    public void setAssignment_file(String assignment_file) {
        this.assignment_file = assignment_file;
    }

    public String getAssignment_given_date() {
        return assignment_given_date;
    }

    public void setAssignment_given_date(String assignment_given_date) {
        this.assignment_given_date = assignment_given_date;
    }

    public String getPublished_status() {
        return published_status;
    }

    public void setPublished_status(String published_status) {
        this.published_status = published_status;
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
}
