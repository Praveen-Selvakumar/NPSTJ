package com.example.npstj.ModelClass;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StudentList_model {


    public String parent_name,student_name,student_id,class_gs,class_section,photo_;

    public StudentList_model( ) {
    }

    @SerializedName("status")
    public String FCM_Response;

    @SerializedName("primary_number")
    public String FCM_primary_number;

    @SerializedName("fcm_tocken")
    public String FCM_fcm_tocken;

    public String getFCM_Response() {
        return FCM_Response;
    }

    public String getFCM_primary_number() {
        return FCM_primary_number;
    }

    public String getFCM_fcm_tocken() {
        return FCM_fcm_tocken;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getClass_gs() {
        return class_gs;
    }

    public void setClass_gs(String class_gs) {
        this.class_gs = class_gs;
    }

    public String getClass_section() {
        return class_section;
    }

    public void setClass_section(String class_section) {
        this.class_section = class_section;
    }

    public String getPhoto_() {
        return photo_;
    }

    public void setPhoto_(String photo_) {
        this.photo_ = photo_;
    }


    public void setTJ_PARENT_LOGIN_RESP_DOC(TJ_PARENT_LOGIN_RESP_DOC_Model TJ_PARENT_LOGIN_RESP_DOC) {
        this.TJ_PARENT_LOGIN_RESP_DOC = TJ_PARENT_LOGIN_RESP_DOC;
    }

    @SerializedName("TJ_ERROR_TV_DOC")
    public TimeTable_Model.TJ_ERROR_TV_DOC_model TJ_ERROR_TV_DOC;

    public TimeTable_Model.TJ_ERROR_TV_DOC_model getTJ_ERROR_TV_DOC() {
        return TJ_ERROR_TV_DOC;
    }

    public class TJ_ERROR_TV_DOC_model{

        @SerializedName("status") String status;

        public String getStatus() {
            return status;
        }
    }

    @SerializedName("TJ_PARENT_LOGIN_RESP_DOC")
    public TJ_PARENT_LOGIN_RESP_DOC_Model TJ_PARENT_LOGIN_RESP_DOC;



    public TJ_PARENT_LOGIN_RESP_DOC_Model getTJ_PARENT_LOGIN_RESP_DOC() {
        return TJ_PARENT_LOGIN_RESP_DOC;
    }

    public class TJ_PARENT_LOGIN_RESP_DOC_Model{

        @SerializedName("TJ_PARENT_LOGIN")
        public ArrayList<TJ_PARENT_LOGIN> arrayList =  new ArrayList<>();

        public ArrayList<TJ_PARENT_LOGIN> getArrayList() {
            return arrayList;
        }


        public  class TJ_PARENT_LOGIN {



            @SerializedName("parentname")
            public String parentname;

            @SerializedName("studentname")
            public String studentname;

            @SerializedName("studentid")
            public String studentid;

            @SerializedName("class")
            public String class_;

            @SerializedName("class_section")
            public String class_section;

            @SerializedName("photo")
            public String photo;


            public void setParentname(String parentname) {
                this.parentname = parentname;
            }

            public void setStudentname(String studentname) {
                this.studentname = studentname;
            }

            public void setStudentid(String studentid) {
                this.studentid = studentid;
            }

            public void setClass_(String class_) {
                this.class_ = class_;
            }

            public void setClass_section(String class_section) {
                this.class_section = class_section;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public String getParentname() {
                return parentname;
            }

            public String getStudentname() {
                return studentname;
            }

            public String getStudentid() {
                return studentid;
            }

            public String getClass_() {
                return class_;
            }

            public String getClass_section() {
                return class_section;
            }

            public String getPhoto() {
                return photo;
            }
        }

    }

}
