package com.example.npstj.ModelClass;

public class Student_Model {

    private String isUserLogged_in,ST_NAME, ST_CLASS, ST_SECTION, ST_PHOTO, ST_ROLLNUMBER;

    public Student_Model(String ST_NAME, String ST_CLASS, String ST_SECTION, String ST_PHOTO, String ST_ROLLNUMBER) {
        this.ST_NAME = ST_NAME;
        this.ST_CLASS = ST_CLASS;
        this.ST_SECTION = ST_SECTION;
        this.ST_PHOTO = ST_PHOTO;
        this.ST_ROLLNUMBER = ST_ROLLNUMBER;
     }

    public Student_Model( ) {
    }

    public String getST_NAME() {
        return ST_NAME;
    }

    public void setST_NAME(String ST_NAME) {
        this.ST_NAME = ST_NAME;
    }

    public String getST_CLASS() {
        return ST_CLASS;
    }

    public void setST_CLASS(String ST_CLASS) {
        this.ST_CLASS = ST_CLASS;
    }

    public String getST_SECTION() {
        return ST_SECTION;
    }

    public void setST_SECTION(String ST_SECTION) {
        this.ST_SECTION = ST_SECTION;
    }

    public String getST_PHOTO() {
        return ST_PHOTO;
    }

    public void setST_PHOTO(String ST_PHOTO) {
        this.ST_PHOTO = ST_PHOTO;
    }

    public String getST_ROLLNUMBER() {
        return ST_ROLLNUMBER;
    }

    public void setST_ROLLNUMBER(String ST_ROLLNUMBER) {
        this.ST_ROLLNUMBER = ST_ROLLNUMBER;
    }

    public void setTJ_STUDENT_INFORMATION(TJ_STUDENT_INFORMATION_Model TJ_STUDENT_INFORMATION) {
        this.TJ_STUDENT_INFORMATION = TJ_STUDENT_INFORMATION;
    }

    private  TJ_STUDENT_INFORMATION_Model TJ_STUDENT_INFORMATION;

    public TJ_STUDENT_INFORMATION_Model getTJ_STUDENT_INFORMATION() {
        return TJ_STUDENT_INFORMATION;
    }

  public  class TJ_STUDENT_INFORMATION_Model{

        private TJ_STUDENT_INFO_Model TJ_STUDENT_INFO;

        public TJ_STUDENT_INFO_Model getTJ_STUDENT_INFO() {
            return TJ_STUDENT_INFO;
        }
    }

  public class TJ_STUDENT_INFO_Model{

        private String NAME;
        private String CLASS;
        private String SECTION;
        private String PHOTO;
        private String ROLLNUMBER;

        public TJ_STUDENT_INFO_Model(String NAME, String CLASS, String SECTION, String PHOTO, String ROLLNUMBER) {
            this.NAME = NAME;
            this.CLASS = CLASS;
            this.SECTION = SECTION;
            this.PHOTO = PHOTO;
            this.ROLLNUMBER = ROLLNUMBER;
        }

        public String getNAME() {
            return NAME;
        }

        public String getCLASS() {
            return CLASS;
        }

        public String getSECTION() {
            return SECTION;
        }

        public String getPHOTO() {
            return PHOTO;
        }

        public String getROLLNUMBER() {
            return ROLLNUMBER;
        }
    }











}

