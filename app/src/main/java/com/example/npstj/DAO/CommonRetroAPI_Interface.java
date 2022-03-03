package com.example.npstj.DAO;

import android.util.Log;

import com.example.npstj.ModelClass.Attendance_Model;
import com.example.npstj.ModelClass.CatModel;
import com.example.npstj.ModelClass.CircularList_Model;
import com.example.npstj.ModelClass.ExamList_Model;
import com.example.npstj.ModelClass.FeeModel;
import com.example.npstj.ModelClass.HomeWork_Model;
import com.example.npstj.ModelClass.LoginCredentials;
import com.example.npstj.ModelClass.NotificationList;
import com.example.npstj.ModelClass.ParentProfile_Model;
import com.example.npstj.ModelClass.ProgressRepost_model;
import com.example.npstj.ModelClass.RegisterRes_Model;
import com.example.npstj.ModelClass.StoreModel;
import com.example.npstj.ModelClass.StudentList_model;
import com.example.npstj.ModelClass.Student_Model;
import com.example.npstj.ModelClass.TimeTable_Model;
import com.example.npstj.ModelClass.TransportModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CommonRetroAPI_Interface {

    @GET(" ")
    Call<ParentProfile_Model> getAll_parent_Data();

    //@GET("TJ_STUDENT_INFO.v1/?EMPLID=NSA191463")
    @GET(" ")
    Call<Student_Model> getAllData();

    //all student list
     @GET(" ")
    Call<StudentList_model> getAll_StudentListData();

     //get Progress eport details
    @GET(" ")
    Call<ProgressRepost_model> get_all_progress_report_details();


    //get all attendance details
    @GET(" ")
    Call<Attendance_Model> get_attendanceList_details();

    //get all timetable details
    @GET(" ")
    Call<TimeTable_Model> get_timetableList_details();

   @GET("CategoryList")
   Call<ArrayList<CatModel>> get_CatList();



    @GET("StoreList")
    Call<ArrayList<StoreModel>>  load_storedetails();




    @FormUrlEncoded
    @POST("HomeworkList")
    Call<ArrayList<HomeWork_Model>> get_home_work_details(@Field("class_name") String class_name,
                                                          @Field("section_name") String section_name);
    @FormUrlEncoded
    @POST("ExamList")
    Call<ArrayList<ExamList_Model>> get_exam_details(@Field("class_name") String class_name,
                                                          @Field("section_name") String section_name);
    @FormUrlEncoded
    @POST("CircularList")
    Call<ArrayList<CircularList_Model>> get_cirlcuar_details(@Field("class_name") String class_name,
                                                          @Field("section_name") String section_name);

    @FormUrlEncoded
    @POST("ParentRegistration")
    Call<RegisterRes_Model> register_user(@Field("primary_number") String primary_number);

    @FormUrlEncoded
    @POST("ForgotPassword")
    Call<LoginCredentials> forgot_password(@Field("primary_number") String primary_number);


    @FormUrlEncoded
    @POST("ParentLogin")
    Call<LoginCredentials> login_user(@Field("primary_number") String primary_number,
                                      @Field("user_password") String user_password );
    @FormUrlEncoded
    @POST("TransportInformation")
    Call<TransportModel> load_transportation(@Field("student_id") String student_id );


    @FormUrlEncoded
    @POST("FeeeList")
    Call<FeeModel> load_fees_list(@Field("student_id") String student_id );

    @FormUrlEncoded
    @POST("NotificationList")
    Call<ArrayList<NotificationList>> load_notification_list(@Field("class_name") String class_name,
                                                  @Field("section_name") String section_name );
    @FormUrlEncoded
    @POST("FCMUpdate")
    Call<StudentList_model> post_fcm_token(@Field("fcm_tocken") String fcm_tocken,
                                                  @Field("primary_number") String primary_number);
    @FormUrlEncoded
    @POST("Enquiry")
    Call<String> post_enquiry_deatil(@Field("category_name") String category_name,
                                                @Field("request_description") String request_description,
                                                @Field("added_by") String added_by);
   @FormUrlEncoded
    @POST("ParentFeedback")
    Call<LoginCredentials> post_feedback_deatil(@Field("feedbackname") String feedbackname,
                                   @Field("feedback_message") String feedback_message,
                                   @Field("class_name") String class_name,
                                   @Field("section_name") String section_name,
                                   @Field("student_name") String student_name,
                                   @Field("student_id") String student_id,
                                   @Field("emailid") String emailid
                                           );



}
