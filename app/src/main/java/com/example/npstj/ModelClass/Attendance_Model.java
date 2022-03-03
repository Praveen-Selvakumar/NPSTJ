package com.example.npstj.ModelClass;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Attendance_Model {

    public String totalabs_, TotalPresent_, TotalWorkDays_,si_no;

    public Attendance_Model() {

    }

    public String getSi_no() {
        return si_no;
    }

    public void setSi_no(String si_no) {
        this.si_no = si_no;
    }

    public void setTJ_ATT_TV_RES_DOC(TJ_ATT_TV_RES_DOC_Model TJ_ATT_TV_RES_DOC) {
        this.TJ_ATT_TV_RES_DOC = TJ_ATT_TV_RES_DOC;
    }

    public String getTotalabs_() {
        return totalabs_;
    }

    public void setTotalabs_(String totalabs_) {
        this.totalabs_ = totalabs_;
    }

    public String getTotalPresent_() {
        return TotalPresent_;
    }

    public void setTotalPresent_(String totalPresent_) {
        TotalPresent_ = totalPresent_;
    }

    public String getTotalWorkDays_() {
        return TotalWorkDays_;
    }

    public void setTotalWorkDays_(String totalWorkDays_) {
        TotalWorkDays_ = totalWorkDays_;
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


    @SerializedName("TJ_ATT_TV_RES_DOC")
    public TJ_ATT_TV_RES_DOC_Model TJ_ATT_TV_RES_DOC;

    public TJ_ATT_TV_RES_DOC_Model getTJ_ATT_TV_RES_DOC() {
        return TJ_ATT_TV_RES_DOC;
    }

    public class TJ_ATT_TV_RES_DOC_Model{

        @SerializedName("TJ_ATT_TV_RES_SUB")
        ArrayList<TJ_ATT_TV_RES_SUB> arrayList = new ArrayList<TJ_ATT_TV_RES_SUB>();

        public ArrayList<TJ_ATT_TV_RES_SUB> getArrayList() {
            return arrayList;
        }

        public class TJ_ATT_TV_RES_SUB {

            @SerializedName("totalabs")
            int totalabs;
            @SerializedName("TotalPresent")
            int TotalPresent;
            @SerializedName("TotalWorkDays")
            int TotalWorkDays;

            public int getTotalabs() {
                return totalabs;
            }

            public int getTotalPresent() {
                return TotalPresent;
            }

            public int getTotalWorkDays() {
                return TotalWorkDays;
            }


        }
    }

}
