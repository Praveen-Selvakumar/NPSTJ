package com.example.npstj.ModelClass;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ParentProfile_Model {


    public TJ_STUDENT_DETAILS_Model TJ_STUDENT_DETAILS;

    public TJ_STUDENT_DETAILS_Model getTJ_STUDENT_DETAILS() {
        return TJ_STUDENT_DETAILS;
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

    public class TJ_STUDENT_DETAILS_Model{
        public String ADDRESS1;

        public String ADDRESS2;

        @SerializedName("CITY")
        public String city;

        @SerializedName("COUNTRY")
        public String country;


        @SerializedName("TJ_PARENT_DETAILS")
        public ArrayList<TJ_PARENT_DETAILS> arrayList = new ArrayList<>();


        public String getADDRESS1() {
            return ADDRESS1;
        }

        public String getADDRESS2() {
            return ADDRESS2;
        }

        public String getCity() {
            return city;
        }

        public String getCountry() {
            return country;
        }

        //arrayList
        public ArrayList<TJ_PARENT_DETAILS> getArrayList() {
            return arrayList;
        }

        public void setArrayList(ArrayList<TJ_PARENT_DETAILS> arrayList) {
            this.arrayList = arrayList;
        }

        //json object inside arrays list
      public class TJ_PARENT_DETAILS{

            @SerializedName("NAME")
            public String name;

            @SerializedName("JOB")
            public String job;

            @SerializedName("POSITION")
            public String position;

            @SerializedName("PHONE")
            public String phone;

            @SerializedName("EMAILID")
            public String email_id;

            @SerializedName("SEX")
            public String sex;


            public String getName() {
                return name;
            }

            public String getJob() {
                return job;
            }

            public String getPosition() {
                return position;
            }

            public String getPhone() {
                return phone;
            }

            public String getEmail_id() {
                return email_id;
            }

            public String getSex() {
                return sex;
            }

        }



    }


}
