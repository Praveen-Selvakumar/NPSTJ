package com.example.npstj.ModelClass;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProgressRepost_model {

    public String GB_filname,GB_file;

    public ProgressRepost_model(String GB_filname, String GB_file, TJ_GRADE_BOOK_TV_DOC_model TJ_GRADE_BOOK_TV_DOC) {
        this.GB_filname = GB_filname;
        this.GB_file = GB_file;
        this.TJ_GRADE_BOOK_TV_DOC = TJ_GRADE_BOOK_TV_DOC;
    }

    public ProgressRepost_model( ) {
    }

    public String getGB_filname() {
        return GB_filname;
    }

    public void setGB_filname(String GB_filname) {
        this.GB_filname = GB_filname;
    }

    public String getGB_file() {
        return GB_file;
    }

    public void setGB_file(String GB_file) {
        this.GB_file = GB_file;
    }

    public void setTJ_GRADE_BOOK_TV_DOC(TJ_GRADE_BOOK_TV_DOC_model TJ_GRADE_BOOK_TV_DOC) {
        this.TJ_GRADE_BOOK_TV_DOC = TJ_GRADE_BOOK_TV_DOC;
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

    @SerializedName("TJ_GRADE_BOOK_TV_DOC")
    public TJ_GRADE_BOOK_TV_DOC_model TJ_GRADE_BOOK_TV_DOC;

    public TJ_GRADE_BOOK_TV_DOC_model getTJ_GRADE_BOOK_TV_DOC() {
        return TJ_GRADE_BOOK_TV_DOC;
    }

    public class TJ_GRADE_BOOK_TV_DOC_model{

        @SerializedName("TJ_GRADE_BOOK_TV_SUB_DOC")
        ArrayList<TJ_GRADE_BOOK_TV_SUB_DOC> arrayList = new ArrayList<TJ_GRADE_BOOK_TV_SUB_DOC>();

        public ArrayList<TJ_GRADE_BOOK_TV_SUB_DOC> getArrayList() {
            return arrayList;
        }

       public class TJ_GRADE_BOOK_TV_SUB_DOC{

            @SerializedName("GradeBook_FileName") String gradeBook_filename;

            @SerializedName("GradeBook_File") String gradeBook_file;


            public String getGradeBook_filename() {
                return gradeBook_filename;
            }

            public void setGradeBook_filename(String gradeBook_filename) {
                this.gradeBook_filename = gradeBook_filename;
            }

            public String getGradeBook_file() {
                return gradeBook_file;
            }

            public void setGradeBook_file(String gradeBook_file) {
                this.gradeBook_file = gradeBook_file;
            }
        }

    }
}
