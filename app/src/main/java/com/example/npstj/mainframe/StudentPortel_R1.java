package com.example.npstj.mainframe;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.npstj.Common.Common_Methods;
import com.example.npstj.Common.UserLocalStore;
import com.example.npstj.DAO.CommonRetroAPI_Interface;
import com.example.npstj.ModelClass.HomeWork_Model;
import com.example.npstj.ModelClass.NotificationList;
import com.example.npstj.ModelClass.TimeTable_Model;
import com.example.npstj.R;
import com.example.npstj.RetrofitAPIClient;
import com.example.npstj.adapter.HomeAdapter;
import com.example.npstj.adapter.TimeTable_Adapter;
import com.google.android.material.navigation.NavigationView;

import java.sql.Time;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentPortel_R1 extends AppCompatActivity {

    @BindView(R.id.menu_btn)ImageView menu_btn;
    @BindView(R.id.drawer_layout)DrawerLayout drawerLayout;
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.other_details_content_lt) RelativeLayout rel_lt;
    String get_id = "",get_envi_id = "";
    TextView class_and_section,title;
    RecyclerView recyclerView,recycler_view_home_work;
    ArrayList<HomeWork_Model> arrayList;
    ArrayList<TimeTable_Model> arrayList_time_table;
    CardView student_profile_item_one ;
    CommonRetroAPI_Interface api_interface,api_interface_;
    Common_Methods common_methods;
    RelativeLayout home_lt, profile_lt, logout_lt,rel_bg;
    ProgressDialog progressDialog;
    public static UserLocalStore userLocalStore;
    @BindView(R.id.menu_btn_tint) ImageView menu_btn_tint;
    @BindView(R.id.toolbar_bg) RelativeLayout toolbar_bg;

    @BindView(R.id.notification_number) TextView notification_number;
    @BindView(R.id.notification_btn) ImageView notification_btn;
    ArrayList<NotificationList> notificationLists;
    LayoutInflater layoutInflater;
    AlertDialog.Builder builder;
    TextView null_alert;
    SwipeRefreshLayout swipeRefreshLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.student_portel_r1);
        ButterKnife.bind(StudentPortel_R1.this);
        InitializeFileds();


        if (get_id.equals("1")){
            title.setVisibility(View.GONE);
            CardView student_profile_lt = (CardView)findViewById(R.id.student_profile_item_one);
            student_profile_lt.setVisibility(View.VISIBLE);
            load_student_detail(student_profile_lt);
        }else if (get_id.equals("2")){
              common_methods.start_progress_dialog(progressDialog);
             title.setText("TIMETABLE");
             ConstraintLayout tt_lt = (ConstraintLayout)findViewById(R.id.student_timetable_item_two);
             tt_lt.setVisibility(View.VISIBLE);
             load_time_table("Monday");
        }else if (get_id.equals("3")){
            common_methods.start_progress_dialog(progressDialog);
            title.setText("HOMEWORKS");
            String get_class = getIntent().getStringExtra("student_class");
            String get_section = getIntent().getStringExtra("student_class_section");
            pass_homeworkList(get_class,get_section);
         }


        if (!userLocalStore.get_theme().isEmpty()){
            if (userLocalStore.get_theme().equals("dark")){
                valid_theme();
                RelativeLayout rel_bg = (RelativeLayout)findViewById(R.id.rel_bg);
                rel_bg.setBackgroundColor(getResources().getColor(R.color.black));
            }
        }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                if (get_id.equals("1")){
                    title.setVisibility(View.GONE);
                    CardView student_profile_lt = (CardView)findViewById(R.id.student_profile_item_one);
                    student_profile_lt.setVisibility(View.VISIBLE);
                    load_student_detail(student_profile_lt);
                }else if (get_id.equals("2")){
                    common_methods.start_progress_dialog(progressDialog);
                    title.setText("TIMETABLE");
                    ConstraintLayout tt_lt = (ConstraintLayout)findViewById(R.id.student_timetable_item_two);
                    tt_lt.setVisibility(View.VISIBLE);
                    load_time_table("Monday");
                }else if (get_id.equals("3")){
                    common_methods.start_progress_dialog(progressDialog);
                    title.setText("HOMEWORKS");
                    String get_class = getIntent().getStringExtra("student_class");
                    String get_section = getIntent().getStringExtra("student_class_section");
                    pass_homeworkList(get_class,get_section);
                }
            }
        });


        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else if (!drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.openDrawer(GravityCompat.START);
                }


            }
        });


        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setHomeAsUpIndicator(R.drawable.menu_icon);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView nav_view = (NavigationView)findViewById(R.id.nav_view);
        common_methods.cmn_nav_drawer(nav_view,StudentPortel_R1.this,
                getIntent().getStringExtra("student_name"),
                getIntent().getStringExtra("student_id"),
                getIntent().getStringExtra("student_class"),
                getIntent().getStringExtra("student_class_section"),
                getIntent().getStringExtra("student_img")
                , drawerLayout,get_id,builder,layoutInflater);

        View header_view = nav_view.getHeaderView(0);
        RelativeLayout theme_bg = (RelativeLayout) header_view.findViewById(R.id.theme_bg);
        TextView theme_txt = (TextView)header_view.findViewById(R.id.theme_txt);
        ImageView theme_icon = (ImageView) header_view.findViewById(R.id.theme_icon);
        if (!userLocalStore.get_theme().isEmpty()){
            if (userLocalStore.get_theme().equals("light")){
                theme_bg.setBackgroundColor(getResources().getColor(R.color.white));
                theme_txt.setText("Light Mode");
                theme_txt.setTextColor(getResources().getColor(R.color.black));
                theme_icon.setImageDrawable(getResources().getDrawable(R.drawable.sun));
            }else {
                theme_bg.setBackgroundColor(getResources().getColor(R.color.black));
                theme_txt.setText("Dark Mode");
                theme_txt.setTextColor(getResources().getColor(R.color.white));
                theme_icon.setImageDrawable(getResources().getDrawable(R.drawable.moon));

            }
        }


        CircleImageView goback_btn = (CircleImageView)findViewById(R.id.goback_btn);
        goback_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Student_Portal.class);
                    intent.putExtra("student_name",getIntent().getStringExtra("student_name"));
                    intent.putExtra("student_id",getIntent().getStringExtra("student_id"));
                    intent.putExtra("student_class",getIntent().getStringExtra("student_class"));
                    intent.putExtra("student_class_section",getIntent().getStringExtra("student_class_section"));
                    intent.putExtra("student_img",getIntent().getStringExtra("student_img"));
                    //startActivity(intent);
                    load_themeby_intent(intent);
            }
        });


    }

    public void load_themeby_intent(Intent intent){

        if (!userLocalStore.get_theme().isEmpty()){
            if (userLocalStore.get_theme().equals("dark")){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        }
        overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
        startActivity(intent);

    }


    private void valid_theme() {
         //toolbar
        menu_btn_tint.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        notification_btn.setColorFilter(getResources().getColor(R.color.white),PorterDuff.Mode.SRC_IN);
        toolbar_bg.setBackgroundColor(getResources().getColor(R.color.black));

        //student portal
        CardView student_profile_lt_ = (CardView)findViewById(R.id.student_profile_item_one);
        RelativeLayout rel_lt_ = (RelativeLayout)student_profile_lt_.findViewById(R.id.other_details_content_lt);
        TextView class_and_section_txt = (TextView)student_profile_lt_.findViewById(R.id.class_and_section_txt);
        TextView roll_num_txt = (TextView)student_profile_lt_.findViewById(R.id.roll_num_txt);
        class_and_section_txt.setTextColor(getResources().getColor(R.color.white));
        roll_num_txt.setTextColor(getResources().getColor(R.color.white));
        rel_lt_.setBackground(getResources().getDrawable(R.drawable.profile_content_lt_dark));
         //rel_bg.setBackgroundColor(getResources().getColor(R.color.black));

        //time table
        ConstraintLayout constraintLayout  = (ConstraintLayout)findViewById(R.id.student_timetable_item_two);
        TextView mon = (TextView)constraintLayout.findViewById(R.id.mon);
        mon.setBackground(getResources().getDrawable(R.drawable.time_table_btn_bg_dark));
        mon.setTextColor(getResources().getColor(R.color.white));
        TextView no_data_txt_ = (TextView)constraintLayout.findViewById(R.id.alert_time_table);
        no_data_txt_.setTextColor(getResources().getColor(R.color.black));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),Student_Portal.class);
        intent.putExtra("student_name",getIntent().getStringExtra("student_name"));
        intent.putExtra("student_id",getIntent().getStringExtra("student_id"));
        intent.putExtra("student_class",getIntent().getStringExtra("student_class"));
        intent.putExtra("student_class_section",getIntent().getStringExtra("student_class_section"));
        intent.putExtra("student_img",getIntent().getStringExtra("student_img"));
        startActivity(intent);
        //load_themeby_intent(intent);
    }

    private void load_time_table(String day) {
        String envi_id = getIntent().getStringExtra("student_id");
        //String envi_id = "NUK191311";
        ConstraintLayout constraintLayout  = (ConstraintLayout)findViewById(R.id.student_timetable_item_two);
         TextView mon = (TextView)constraintLayout.findViewById(R.id.mon);
        TextView tue = (TextView)constraintLayout.findViewById(R.id.tue);
        TextView wed = (TextView)constraintLayout.findViewById(R.id.wed);
        TextView thu = (TextView)constraintLayout.findViewById(R.id.thu);
        TextView fri = (TextView)constraintLayout.findViewById(R.id.fri);
        TextView sat = (TextView)constraintLayout.findViewById(R.id.sat);

        TextView no_data_txt = (TextView)constraintLayout.findViewById(R.id.alert_time_table);
        RecyclerView recyclerView_ = (RecyclerView)constraintLayout.findViewById(R.id.recycler_view);

        arrayList_time_table = new ArrayList<TimeTable_Model>();
        api_interface = RetrofitAPIClient.getClient_("TJ_TIME_TABLE_TV.v1/?emplid="+envi_id).create(CommonRetroAPI_Interface.class);
        Call<TimeTable_Model> call = api_interface.get_timetableList_details();

        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList_time_table = new ArrayList<TimeTable_Model>();
                common_methods.tint_day(mon);
                common_methods.days_back_to_normal(tue,wed,thu,fri,sat);
                load_time_table("Monday");
            }
        });

        tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList_time_table = new ArrayList<TimeTable_Model>();
                common_methods.tint_day(tue);
                common_methods.days_back_to_normal(mon,wed,thu,fri,sat);
                load_time_table("Tuesday");
            }
        });

        wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList_time_table = new ArrayList<TimeTable_Model>();
                common_methods.tint_day(wed);
                common_methods.days_back_to_normal(mon,tue,thu,fri,sat);
                load_time_table("Wednesday");
            }
        });
        thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList_time_table = new ArrayList<TimeTable_Model>();
                common_methods.tint_day(thu);
                common_methods.days_back_to_normal(mon,tue,wed,fri,sat);
                load_time_table("Thursday");
            }
        });

        fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList_time_table = new ArrayList<TimeTable_Model>();
                common_methods.tint_day(fri);
                common_methods.days_back_to_normal(mon,tue,wed,thu,sat);
                load_time_table("Friday");

            }
        });

        sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList_time_table = new ArrayList<TimeTable_Model>();
                common_methods.tint_day(sat);
                common_methods.days_back_to_normal(mon,tue,wed,thu,fri);
                load_time_table("Saturday");
            }
        });

        call.enqueue(new Callback<TimeTable_Model>() {
            @Override
            public void onResponse(Call<TimeTable_Model> call, Response<TimeTable_Model> response) {
                common_methods.cancel_progress_dialog(progressDialog);
                Log.d("response",response.body().toString());
                if (!response.isSuccessful()){
                    no_data_txt.setVisibility(View.VISIBLE);
                    recyclerView_.setVisibility(View.GONE);
                }else {
                    if(response.body().getTJ_ERROR_TV_DOC() != null ){
                        no_data_txt.setVisibility(View.VISIBLE);
                        recyclerView_.setVisibility(View.GONE);
                    }else {

                   ArrayList<TimeTable_Model.TJ_TIME_TABLE_TV_RESP_DOC_Model.TJ_TIME_TABLE_TV_RESP> arrayList_tt = response.body().getTJ_TIME_TABLE_TV_RESP_DOC().getArrayList();

                    if (arrayList_tt.isEmpty()){
                        no_data_txt.setVisibility(View.VISIBLE);
                        recyclerView_.setVisibility(View.GONE);
                    }else {
                        for (int i = 0; i< arrayList_tt.size(); i++){
                            if (day.equals(arrayList_tt.get(i).getWeekday())){

                                TimeTable_Model model = new TimeTable_Model();
                                model.setSubject_(arrayList_tt.get(i).getSubject());
                                model.setTimestart_(arrayList_tt.get(i).getTimestart());
                                model.setWeekday_(arrayList_tt.get(i).getWeekday());
                                arrayList_time_table.add(model);
                            }
                        }

                        if (arrayList_time_table.isEmpty()){
                            no_data_txt.setVisibility(View.VISIBLE);
                            recyclerView_.setVisibility(View.GONE);
                        }else {
                            recyclerView_.setVisibility(View.VISIBLE);
                            no_data_txt.setVisibility(View.GONE);
                            recyclerView_.setAdapter(new TimeTable_Adapter(StudentPortel_R1.this,arrayList_time_table));
                        }
                    }


                     }

                }
                swipeRefreshLayout.setRefreshing(false);
            }
            @Override
            public void onFailure(Call<TimeTable_Model> call, Throwable t) {

            }
        });


    }



    private void pass_homeworkList(String get_class, String get_section) {
        recycler_view_home_work.setVisibility(View.VISIBLE);
        arrayList = new ArrayList<HomeWork_Model>();
        api_interface = RetrofitAPIClient.getClient("APIMobileHomework/").create(CommonRetroAPI_Interface.class);
        Call<ArrayList<HomeWork_Model>> call = api_interface.get_home_work_details(get_class,get_section);

        call.enqueue(new Callback<ArrayList<HomeWork_Model>>() {
            @Override
            public void onResponse(Call<ArrayList<HomeWork_Model>> call, Response<ArrayList<HomeWork_Model>> response) {
                progressDialog.cancel();
                if (arrayList != null) {

                    ArrayList<HomeWork_Model> arrayList_ = response.body();
                    Log.d("array_resp", arrayList_.toString());
                    for (int i = 0; i < arrayList_.size(); i++) {
                        HomeWork_Model model = new HomeWork_Model();

                        model.setId(arrayList_.get(i).getId());
                        model.setClass_id(arrayList_.get(i).getClass_id());
                        model.setSection_id(arrayList_.get(i).getSection_id());
                        model.setSubject_name(arrayList_.get(i).getSubject_name());
                        model.setSubject_name(arrayList_.get(i).getSubject_name());
                        model.setTeacher_name(arrayList_.get(i).getTeacher_name());
                        model.setAssignment_title(arrayList_.get(i).getAssignment_title());
                        model.setAssignment_description(arrayList_.get(i).getAssignment_description());
                        model.setDeadline_date(arrayList_.get(i).getDeadline_date());
                        model.setAssignment_file(arrayList_.get(i).getAssignment_file());
                        model.setAssignment_given_date(arrayList_.get(i).getAssignment_given_date());
                        model.setPublished_status(arrayList_.get(i).getPublished_status());
                        model.setAdded_by(arrayList_.get(i).getAdded_by());
                        model.setAdded_date(arrayList_.get(i).getAdded_date());
                        arrayList.add(model);
                    }
                    HomeAdapter homeAdapter = new HomeAdapter(StudentPortel_R1.this, arrayList);
                    recycler_view_home_work.setAdapter(homeAdapter);
                }else {
                    recycler_view_home_work.setVisibility(View.GONE);
                    null_alert.setVisibility(View.VISIBLE);
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ArrayList<HomeWork_Model>> call, Throwable t) {

            }
        });

    }

    private void InitializeFileds() {
        common_methods = new Common_Methods(StudentPortel_R1.this);
        progressDialog = new ProgressDialog(StudentPortel_R1.this);
        userLocalStore = new UserLocalStore(StudentPortel_R1.this);
        progressDialog.setMessage("Please wait while loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        get_id = getIntent().getStringExtra("item_id");
        class_and_section  = (TextView)findViewById(R.id.class_and_section);
        class_and_section.setText("CLASS & SECTION");
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recycler_view_home_work = (RecyclerView)findViewById(R.id.recycler_view_hw);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recycler_view_home_work.setLayoutManager(new LinearLayoutManager(StudentPortel_R1.this,RecyclerView.VERTICAL,false));
        get_envi_id = getIntent().getStringExtra("student_id");
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_lt);

        home_lt = (RelativeLayout)findViewById(R.id.lt_home);
        profile_lt = (RelativeLayout)findViewById(R.id.lt_profile);
        logout_lt = (RelativeLayout)findViewById(R.id.lt_logout);
        rel_bg = (RelativeLayout)findViewById(R.id.rel_bg);
        title = (TextView)findViewById(R.id.title);
        null_alert = (TextView)findViewById(R.id.null_alert);

         layoutInflater = getLayoutInflater();
         builder = new AlertDialog.Builder(StudentPortel_R1.this);
        common_methods.btm_nav(StudentPortel_R1.this,home_lt,profile_lt,logout_lt,builder,layoutInflater);


        api_interface_ = RetrofitAPIClient.getClient("APIMobileNotification/").create(CommonRetroAPI_Interface.class);
        Call<ArrayList<NotificationList>> call = api_interface_.load_notification_list(
                getIntent().getStringExtra("student_class"),
                getIntent().getStringExtra("student_class_section"));
        call.enqueue(new Callback<ArrayList<NotificationList>>() {
            @Override
            public void onResponse(Call<ArrayList<NotificationList>> call, Response<ArrayList<NotificationList>> response) {
                ArrayList<NotificationList> arrayList = response.body();
                notification_number.setText(String.valueOf(arrayList.size()));
            }

            @Override
            public void onFailure(Call<ArrayList<NotificationList>> call, Throwable t) {

            }
        });


        notification_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationLists = new ArrayList<NotificationList>();

                common_methods.show_notification_alert(layoutInflater,builder,
                        notificationLists,api_interface_,
                        getIntent().getStringExtra("student_class"),
                        getIntent().getStringExtra("student_class_section"),
                        getIntent().getStringExtra("student_img"),
                        getIntent().getStringExtra("student_name"),
                        getIntent().getStringExtra("student_id")
                );

            }
        });


    }

    private void load_student_detail(CardView student_profile_item_one){
        TextView class_and_section_txt = (TextView)student_profile_item_one.findViewById(R.id.class_and_section_txt);
        TextView roll_num_txt = (TextView)student_profile_item_one.findViewById(R.id.roll_num_txt);
        TextView student_name_txt = (TextView)student_profile_item_one.findViewById(R.id.user_name);
        String getClass = getIntent().getStringExtra("student_class");
        String getSection = getIntent().getStringExtra("student_class_section");
        //class_and_section_txt
        if (getClass.isEmpty() && getSection.isEmpty()){
            class_and_section_txt.setText("Not updated");
        }else {
            class_and_section_txt.setText(getClass+" & "+getSection);
        }
        roll_num_txt.setText(getIntent().getStringExtra("student_id"));
        student_name_txt.setText(getIntent().getStringExtra("student_name"));

        CircleImageView student_profile_img = (CircleImageView)student_profile_item_one.findViewById(R.id.student_profile_img);

        String base64String =  getIntent().getStringExtra("student_img");
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        student_profile_img.setImageBitmap(decodedByte);
        swipeRefreshLayout.setRefreshing(false);

    }


}