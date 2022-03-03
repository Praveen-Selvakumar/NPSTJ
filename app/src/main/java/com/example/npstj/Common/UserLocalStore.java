package com.example.npstj.Common;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.npstj.ModelClass.ParentDetailModel;
import com.example.npstj.ModelClass.ParentProfile_Model;
import com.example.npstj.ModelClass.ProgressRepost_model;
import com.example.npstj.ModelClass.StudentList_model;
import com.example.npstj.ModelClass.Student_Model;
import com.example.npstj.ModelClass.TimeTable_Model;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Time;
import java.util.ArrayList;

public class UserLocalStore {

    SharedPreferences sharedPreferences ;
    Context context;
    public UserLocalStore(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("NPSTJ",Context.MODE_PRIVATE);
    }


    public void store_bitmap(String bitmap){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("bitmap",bitmap);
        editor.commit();
    }

    public String get_bitmap(){
        String get_theme  =  sharedPreferences.getString("bitmap","");
        return get_theme;
    }

    public void store_theme(String theme){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("theme",theme);
        editor.commit();
    }

    public String get_theme(){
        String get_theme  =  sharedPreferences.getString("theme","");
        return get_theme;
    }

    public void store_user_number(String mobile_number){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("number",mobile_number);
        editor.commit();
    }

    public  String get_number(){
        String get_mobile_number = sharedPreferences.getString("number","");
        return get_mobile_number;
    }

    public void store_FCM_token(String FCM_TOKEN){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("fcm",FCM_TOKEN);
        editor.commit();
    }

    public void store_student_id(String student_id){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("student_id",student_id);
        editor.commit();
    }

    public String get_student_id(){
        String get_student_id = sharedPreferences.getString("student_id","");
        return get_student_id;
    }

    public String get_FCM_token(){
        String get_FCM_Token = sharedPreferences.getString("fcm","");
        return get_FCM_Token;
    }



    public void store_parent_detail(ParentDetailModel model){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ad_1",model.getAd_1());
        editor.putString("ad_2",model.getAd_2());
        editor.putString("city",model.getCity());
        editor.putString("country",model.getCountry());
        editor.putString("hus_name",model.getHus_name());
        editor.putString("hus_job",model.getHus_job());
        editor.putString("hus_position",model.getHus_position());
        editor.putString("hus_phone",model.getHus_phone());
        editor.putString("hus_email_id",model.getHus_email_id());
        editor.putString("hus_sex",model.getHus_sex());
        editor.putString("w_name",model.getW_name());
        editor.putString("w_job",model.getW_job());
        editor.putString("w_position",model.getW_position());
        editor.putString("w_phone",model.getW_phone());
        editor.putString("w_email_id",model.getW_email_id());
        editor.putString("w_sex",model.getW_sex());
        editor.commit();
    }

    public ParentDetailModel get_parent_details(){
        String get_ad_1 = sharedPreferences.getString("ad_1","");
        String get_ad_2 = sharedPreferences.getString("ad_2","");
        String get_city = sharedPreferences.getString("city","");
        String get_country = sharedPreferences.getString("country","");
        String get_hus_name = sharedPreferences.getString("hus_name","");
        String get_hus_job = sharedPreferences.getString("hus_job","");
        String get_hus_position = sharedPreferences.getString("hus_position","");
        String get_hus_phone = sharedPreferences.getString("hus_phone","");
        String get_hus_email_id = sharedPreferences.getString("hus_email_id","");
        String get_hus_sex = sharedPreferences.getString("hus_sex","");
        String get_w_name = sharedPreferences.getString("w_name","");
        String get_w_job = sharedPreferences.getString("w_job","");
        String get_w_position = sharedPreferences.getString("w_position","");
        String get_w_phone = sharedPreferences.getString("w_phone","");
        String get_w_email_id = sharedPreferences.getString("w_email_id","");
        String get_w_sex = sharedPreferences.getString("w_sex","");
        ParentDetailModel model = new ParentDetailModel(get_ad_1,get_ad_2,get_city,get_country,
                get_hus_name,get_hus_job,get_hus_position,get_hus_phone,get_hus_email_id,
                get_hus_sex,get_w_name,get_w_job,get_w_position,get_w_phone,get_w_email_id,get_w_sex);
        return  model;
    }

    public void store_timetable_list(ArrayList<TimeTable_Model> arrayList){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json  = gson.toJson(arrayList);
        editor.putString("tt_list",json);
        editor.commit();
    }

    public void store_kid_list(ArrayList<StudentList_model> arrayList){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json  = gson.toJson(arrayList);
        editor.putString("kid_list",json);
        editor.commit();
    }

    public ArrayList<StudentList_model> get_kid_list(){
        ArrayList<StudentList_model> arrayList;
        Gson gson = new Gson();
        String json = sharedPreferences.getString("kid_list","");
        Type type =  new TypeToken<ArrayList<StudentList_model>>(){}.getType();
         arrayList = gson.fromJson(json,type);
        if (arrayList == null){
            arrayList = new ArrayList<>();
        }
        return arrayList;
    }

    public void store_progress_report_list(ArrayList<ProgressRepost_model> arrayList){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json  = gson.toJson(arrayList);
        editor.putString("pr_list",json);
        editor.commit();
    }

    public ArrayList<ProgressRepost_model> get_progress_report_list(){
        ArrayList<ProgressRepost_model> arrayList;
        Gson gson = new Gson();
        String json = sharedPreferences.getString("pr_list","");
        Type type =  new TypeToken<ArrayList<ProgressRepost_model>>(){}.getType();
        arrayList = gson.fromJson(json,type);
        if (arrayList == null){
            arrayList = new ArrayList<>();
        }
        return arrayList;
    }


    public ArrayList<TimeTable_Model> get_tt_list(){
        ArrayList<TimeTable_Model> arrayList;
        Gson gson = new Gson();
        String json = sharedPreferences.getString("tt_list","");
        Type type =  new TypeToken<ArrayList<TimeTable_Model>>(){}.getType();
         arrayList = gson.fromJson(json,type);
        if (arrayList == null){
            arrayList = new ArrayList<>();
        }
        return arrayList;
    }


    public void store_user_data(Student_Model model){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name",model.getST_NAME());
        editor.putString("class",model.getST_CLASS());
        editor.putString("section",model.getST_SECTION());
        editor.putString("photo",model.getST_PHOTO());
        editor.putString("roll_number",model.getST_ROLLNUMBER());
        editor.commit();
    }

    public Student_Model get_user_date(){
        String get_name = sharedPreferences.getString("name","");
        String get_class = sharedPreferences.getString("class","");
        String get_section = sharedPreferences.getString("section","");
        String get_photo = sharedPreferences.getString("photo","");
        String get_roll_number = sharedPreferences.getString("roll_number","");
        Student_Model student_model = new Student_Model(get_name,get_class,get_section,get_photo,get_roll_number);
        return student_model;
    }

    public void clear_data(){
        SharedPreferences.Editor editor  = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
