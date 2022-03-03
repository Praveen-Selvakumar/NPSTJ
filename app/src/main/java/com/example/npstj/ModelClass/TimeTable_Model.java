package com.example.npstj.ModelClass;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TimeTable_Model {

    public String subject_,weekday_,timestart_;

    public TimeTable_Model(String subject_, String weekday_, String timestart_ ) {
        this.subject_ = subject_;
        this.weekday_ = weekday_;
        this.timestart_ = timestart_;
     }

    public TimeTable_Model(   ) {
     }

    public String getSubject_() {
        return subject_;
    }

    public void setSubject_(String subject_) {
        this.subject_ = subject_;
    }

    public String getWeekday_() {
        return weekday_;
    }

    public void setWeekday_(String weekday_) {
        this.weekday_ = weekday_;
    }

    public String getTimestart_() {
        return timestart_;
    }

    public void setTimestart_(String timestart_) {
        this.timestart_ = timestart_;
    }


    @SerializedName("TJ_ERROR_TV_DOC")
    public TJ_ERROR_TV_DOC_model TJ_ERROR_TV_DOC;

    public TJ_ERROR_TV_DOC_model getTJ_ERROR_TV_DOC() {
        return TJ_ERROR_TV_DOC;
    }

    public class TJ_ERROR_TV_DOC_model{

        @SerializedName("status") String status;

        public String getStatus() {
            return status;
        }
    }

    @SerializedName("TJ_TIME_TABLE_TV_RESP_DOC")
    public TJ_TIME_TABLE_TV_RESP_DOC_Model TJ_TIME_TABLE_TV_RESP_DOC;

    public TJ_TIME_TABLE_TV_RESP_DOC_Model getTJ_TIME_TABLE_TV_RESP_DOC() {
        return TJ_TIME_TABLE_TV_RESP_DOC;
    }

    public class TJ_TIME_TABLE_TV_RESP_DOC_Model {

        @SerializedName("TJ_TIME_TABLE_TV_RESP")
        ArrayList<TJ_TIME_TABLE_TV_RESP> arrayList = new ArrayList<>();

        public ArrayList<TJ_TIME_TABLE_TV_RESP> getArrayList() {
            return arrayList;
        }

        public class TJ_TIME_TABLE_TV_RESP{

            @SerializedName("subject") String subject;
            @SerializedName("weekday") String weekday;
            @SerializedName("timestart") String timestart;

            public String getSubject() {
                return subject;
            }

            public String getWeekday() {
                return weekday;
            }

            public String getTimestart() {
                return timestart;
            }
        }
    }
}
