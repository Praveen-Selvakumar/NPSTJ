package com.example.npstj.mainframe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.npstj.Common.Common_Methods;
import com.example.npstj.Common.UserLocalStore;
import com.example.npstj.DAO.CommonRetroAPI_Interface;
import com.example.npstj.ModelClass.ExamList_Model;
import com.example.npstj.ModelClass.NotificationList;
import com.example.npstj.ModelClass.ProgressRepost_model;
import com.example.npstj.ModelClass.TransportModel;
import com.example.npstj.R;
import com.example.npstj.RetrofitAPIClient;
import com.example.npstj.adapter.ExaminationAdapter;
import com.example.npstj.adapter.ProgressReport_Adapter;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import android.util.Base64;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import lib.folderpicker.FolderPicker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentPortel_R2 extends AppCompatActivity {
    @BindView(R.id.menu_btn)
    ImageView menu_btn;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.header_title) TextView header_title;
    String get_id = "";
    CommonRetroAPI_Interface api_interface,api_interface_;
    public RecyclerView rv_exam,  rv_progress_report;
    ArrayList<ExamList_Model> arrayList_exam;
    ArrayList<ProgressRepost_model> arrayList_progress_report;
    Common_Methods common_methods;
    public static RelativeLayout home_lt, profile_lt, logout_lt, pdf_activity_lt,main_lt;
    TextView alert_txt,folder_txt,pdf_name, back_txt;
    ProgressDialog progressDialog;
    public static int FOLDERPICKER_CODE = 101;
    Intent intent;
    @BindView(R.id.notification_number) TextView notification_number;
    @BindView(R.id.notification_btn) ImageView notification_btn;
    @BindView(R.id.menu_btn_tint) ImageView menu_btn_tint;
    @BindView(R.id.toolbar_bg) RelativeLayout toolbar_bg;

    ArrayList<NotificationList> notificationLists;
    LayoutInflater layoutInflater;
    AlertDialog.Builder builder;
    public PDFView pdfView;
    public ImageView upload_btn,back_btn;
    public MaterialButton download_pdf_btn;
    File downloads_path = null;
    public  static  UserLocalStore userLocalStore;
    String student_id  ;
    SwipeRefreshLayout refreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.student_portel__r2);
        ButterKnife.bind(StudentPortel_R2.this);
        InitilaizeFields();


        if(get_id.equals("4")){
            progressDialog.show();
            header_title.setText("EXAMINATION");
            rv_exam.setVisibility(View.VISIBLE);
            rv_exam.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
            String get_class = getIntent().getStringExtra("student_class");
            String get_section = getIntent().getStringExtra("student_class_section");
            load_exam_details(get_class,get_section);
        }else if (get_id.equals("5")){
            progressDialog.show();
            header_title.setVisibility(View.VISIBLE);
            header_title.setText("PROGRESS REPORT");
            String file_name = getIntent().getStringExtra("file_name");
            String file_path = getIntent().getStringExtra("file_path");
            rv_progress_report.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            if (file_name != null){
                rv_progress_report.setVisibility(View.GONE);
                pdf_activity_lt.setVisibility(View.VISIBLE);
                pdfView.setVisibility(View.VISIBLE);
                pdf_name.setText(file_name);
                main_lt.setVisibility(View.GONE);
                pdfView.fromFile(new File(file_path)).load();
                progressDialog.show();
            }
            if (!userLocalStore.get_progress_report_list().isEmpty()
                    && userLocalStore.get_student_id().equals(student_id)){
                rv_progress_report.setVisibility(View.VISIBLE);
                ArrayList<ProgressRepost_model> arrayList = userLocalStore.get_progress_report_list();
                rv_progress_report.setVisibility(View.VISIBLE);
                rv_progress_report.setAdapter(new ProgressReport_Adapter(this,arrayList));
                progressDialog.cancel();
            }else {
                rv_progress_report.setVisibility(View.VISIBLE);
                load_progress_report(student_id);
            }
        }else if (get_id.equals("6")){
            progressDialog.show();
            String p1 = "<font color='#3d753e'><b>ohh!</b> You have not opted for transportation</font>";
            alert_txt.setText(Html.fromHtml(p1));
            header_title.setText("TRANSPORT");
            ScrollView scrollView = (ScrollView)findViewById(R.id.transport_scroll);scrollView.setVisibility(View.VISIBLE);
            RelativeLayout transportation_layout = (RelativeLayout)findViewById(R.id.transportation_layout);
            laod_transport_detail();
        }

        if (!userLocalStore.get_theme().isEmpty()){
            if (userLocalStore.get_theme().equals("dark")){
                valid_theme();
            }
        }

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                if(get_id.equals("4")){
                    progressDialog.show();
                    header_title.setText("EXAMINATION");
                    rv_exam.setVisibility(View.VISIBLE);
                    rv_exam.setLayoutManager(new LinearLayoutManager(StudentPortel_R2.this,RecyclerView.VERTICAL,false));
                    String get_class = getIntent().getStringExtra("student_class");
                    String get_section = getIntent().getStringExtra("student_class_section");
                    load_exam_details(get_class,get_section);
                }else if (get_id.equals("5")){
                    progressDialog.show();
                    header_title.setVisibility(View.VISIBLE);
                    header_title.setText("PROGRESS REPORT");
                    rv_progress_report.setLayoutManager(new LinearLayoutManager(StudentPortel_R2.this, RecyclerView.VERTICAL, false));
                    rv_progress_report.setVisibility(View.VISIBLE);
                    load_progress_report(student_id);
                }else if (get_id.equals("6")){
                    progressDialog.show();
                    String p1 = "<font color='#3d753e'><b>ohh!</b> You have not opted for transportation</font>";
                    alert_txt.setText(Html.fromHtml(p1));
                    header_title.setText("TRANSPORT");
                    ScrollView scrollView = (ScrollView)findViewById(R.id.transport_scroll);scrollView.setVisibility(View.VISIBLE);
                    RelativeLayout transportation_layout = (RelativeLayout)findViewById(R.id.transportation_layout);
                    laod_transport_detail();
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
        common_methods.cmn_nav_drawer(nav_view,StudentPortel_R2.this,
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

        //trasportation lt
        RelativeLayout tt_lt = (RelativeLayout)findViewById(R.id.transportation_layout);
        RelativeLayout rel_bg = (RelativeLayout)tt_lt.findViewById(R.id.rel_bg);
        final TextView vechicle_number = (TextView)tt_lt.findViewById(R.id.vechicle_number);
        TextView start_spot = (TextView)tt_lt.findViewById(R.id.start_spot);
        TextView drop_spot = (TextView)tt_lt.findViewById(R.id.drop_spot);
        TextView driver_details_txt = (TextView)tt_lt.findViewById(R.id.driver_details_txt);
        TextView name_num_txt = (TextView)tt_lt.findViewById(R.id.name_num_txt);
        TextView start_date = (TextView)tt_lt.findViewById(R.id.start_date);
        TextView end_date = (TextView)tt_lt.findViewById(R.id.end_date);

         into_white(start_spot); into_white(drop_spot); into_white(vechicle_number);
        into_white(driver_details_txt); into_white(name_num_txt); into_white(start_date);
        into_white(end_date);

        rel_bg.setBackgroundColor(getResources().getColor(R.color.one_bg));
    }




    public void show_PDF(String gb_filname, File dwldsPath){

        View view = layoutInflater.inflate(R.layout.alert_view_pdf,null);
        builder.setView(view);
        //final AlertDialog dialog = builder.show();
        final AlertDialog dialog = builder.create();

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();

        wmlp.gravity = Gravity.CENTER ;
        wmlp.x = 0;   //x position
        wmlp.y = 120;   //y position

        TextView comtent_txt = (TextView)view.findViewById(R.id.content_txt);
        TextView view_btn = (TextView)view.findViewById(R.id.view_btn);
        TextView discard_btn = (TextView)view.findViewById(R.id.discard_btn);


        String[] separted = gb_filname.split("-");
        String par_1 = separted[0];
        String par_2 = separted[1];
        String file_name_txt  = "<font color='#0066ae'>"+par_1+"\n"+par_2+"</font>";
        String downloaded_txt  = "<font color='#000000'> is Downloaded ..! </font>";
        comtent_txt.setText(Html.fromHtml(file_name_txt+" "+downloaded_txt));

        view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 rv_progress_report.setVisibility(View.GONE);
                 main_lt.setVisibility(View.GONE);
                 pdf_activity_lt.setVisibility(View.VISIBLE);
                 pdfView.setVisibility(View.VISIBLE);
                 pdfView.fromFile(dwldsPath).load();
                 pdf_name.setText(Html.fromHtml(file_name_txt));
                 dialog.dismiss();
            }
        });

        discard_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    private void laod_transport_detail() {
        RelativeLayout tt_lt = (RelativeLayout)findViewById(R.id.transportation_layout);
        TextView name_num_txt = (TextView)tt_lt.findViewById(R.id.name_num_txt);
        TextView start_date = (TextView)tt_lt.findViewById(R.id.start_date);
        TextView end_date = (TextView)tt_lt.findViewById(R.id.end_date);
        TextView rupees_txt = (TextView)tt_lt.findViewById(R.id.rupees_txt);
        TextView vechicle_number = (TextView)tt_lt.findViewById(R.id.vechicle_number);
        TextView start_spot = (TextView)tt_lt.findViewById(R.id.start_spot);
        TextView drop_spot = (TextView)tt_lt.findViewById(R.id.drop_spot);
        api_interface = RetrofitAPIClient.getClient("APIMobileTransport/").create(CommonRetroAPI_Interface.class);

        Call<TransportModel> call = api_interface.load_transportation(getIntent().getStringExtra("student_id"));
        call.enqueue(new Callback<TransportModel>() {
            @Override
            public void onResponse(Call<TransportModel> call, Response<TransportModel> response) {
                 if (response.isSuccessful()){

                     tt_lt.setVisibility(View.VISIBLE);
                     alert_txt.setVisibility(View.GONE);

                TransportModel app = response.body();
                progressDialog.cancel();
                name_num_txt.setText(app.getDriver_name()+"\n\n"+app.getDriver_contact_number());
                start_date.setText("Start Date : "+app.getStart_date());
                end_date.setText("End Date   :  "+app.getEnd_date());
                rupees_txt.setText("\u20B9 "+app.getAmount());
                vechicle_number.setText(app.getVechicle_number());
                start_spot.setText(app.getPickup_and_drop());
                drop_spot.setText(app.getPickup_and_drop());

                 }else {
                     tt_lt.setVisibility(View.GONE);
                     alert_txt.setVisibility(View.VISIBLE);
                     alert_txt.setText("ohh! You have not opted for transportation ");
                     progressDialog.cancel();
                 }
                  refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<TransportModel> call, Throwable t) {

            }
        });
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

    private void load_progress_report(String envi_id) {
        api_interface = RetrofitAPIClient.getClient_("TJ_GRADE_BOOK_TV.v1/?emplid="+envi_id).create(CommonRetroAPI_Interface.class);
        arrayList_progress_report = new ArrayList<ProgressRepost_model>();

        Call<ProgressRepost_model> call  = api_interface.get_all_progress_report_details();
        call.enqueue(new Callback<ProgressRepost_model>() {
            @Override
            public void onResponse(Call<ProgressRepost_model> call, Response<ProgressRepost_model> response) {
                if (response.body().getTJ_ERROR_TV_DOC() != null) {
                     alert_txt.setVisibility(View.VISIBLE);
                     alert_txt.setText("ohh! No Grade Book available");
                     progressDialog.cancel();
                } else {
                    ArrayList<ProgressRepost_model.TJ_GRADE_BOOK_TV_DOC_model.TJ_GRADE_BOOK_TV_SUB_DOC> arrayList = response.body().getTJ_GRADE_BOOK_TV_DOC().getArrayList();

                    for (int i = 0; i < arrayList.size(); i++) {
                        ProgressRepost_model model = new ProgressRepost_model();
                         model.setGB_filname(arrayList.get(i).getGradeBook_filename());
                         model.setGB_file(arrayList.get(i).getGradeBook_file());

                        if (!arrayList.get(i).getGradeBook_filename().equals(" ")) {
                            arrayList_progress_report.add(model);
                            progressDialog.cancel();
                        }
                    }

                    rv_progress_report.setAdapter(new ProgressReport_Adapter(StudentPortel_R2.this, arrayList_progress_report));
                    userLocalStore.store_progress_report_list(arrayList_progress_report);
                    userLocalStore.store_student_id(envi_id);
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<ProgressRepost_model> call, Throwable t) {

            }
        });
    }




    private void load_exam_details(String get_class, String get_section) {
        api_interface = RetrofitAPIClient.getClient("ApiMobileExamination/").create(CommonRetroAPI_Interface.class);
        arrayList_exam = new ArrayList<ExamList_Model>();

        Call<ArrayList<ExamList_Model>> call = api_interface.get_exam_details(get_class,get_section);
        call.enqueue(new Callback<ArrayList<ExamList_Model>>() {
            @Override
            public void onResponse(Call<ArrayList<ExamList_Model>> call, Response<ArrayList<ExamList_Model>> response) {
                progressDialog.cancel();
                refreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    ArrayList<ExamList_Model> arrayList = response.body();
                    for (int i = 0; i < arrayList.size(); i++) {
                        ExamList_Model model = new ExamList_Model();
                        model.setId(arrayList.get(i).getId());
                        model.setExam_name(arrayList.get(i).getExam_name());
                        model.setStarting_date(arrayList.get(i).getStarting_date());
                        model.setEnding_date(arrayList.get(i).getEnding_date());
                        model.setExpirary_date(arrayList.get(i).getExpirary_date());
                        model.setExam_command(arrayList.get(i).getExam_command());
                        model.setPublish_status(arrayList.get(i).getPublish_status());
                        model.setAdded_by(arrayList.get(i).getAdded_by());
                        model.setAdded_date(arrayList.get(i).getAdded_date());
                        model.setClass_id(arrayList.get(i).getClass_id());
                        model.setSection_id(arrayList.get(i).getSection_id());
                        arrayList_exam.add(model);
                    }
                    rv_exam.setAdapter(new ExaminationAdapter(StudentPortel_R2.this, arrayList_exam));
                }else{
                    rv_exam.setVisibility(View.VISIBLE);
                    alert_txt.setVisibility(View.VISIBLE);
                    alert_txt.setText("Ohh! No HomeWork Available");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ExamList_Model>> call, Throwable t) {

            }
        });
    }



    private void InitilaizeFields() {
        common_methods = new Common_Methods(StudentPortel_R2.this);
        get_id = getIntent().getStringExtra("item_id");
        rv_exam = (RecyclerView)findViewById(R.id.recycler_view_examination);
        rv_progress_report = (RecyclerView)findViewById(R.id.recycler_view_progress_report);
        alert_txt = (TextView)findViewById(R.id.alert_txt);

        home_lt = (RelativeLayout)findViewById(R.id.lt_home);
        profile_lt = (RelativeLayout)findViewById(R.id.lt_profile);
        logout_lt = (RelativeLayout)findViewById(R.id.lt_logout);
        pdf_activity_lt = (RelativeLayout)findViewById(R.id.pdf_activity_lt);
        main_lt = (RelativeLayout)findViewById(R.id.main_lt);
        layoutInflater = getLayoutInflater();
        builder = new AlertDialog.Builder(StudentPortel_R2.this);
        common_methods.btm_nav(StudentPortel_R2.this,home_lt,profile_lt,logout_lt,builder,layoutInflater);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait while loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        pdfView = (PDFView)findViewById(R.id.pdf_viewer);
         upload_btn = (ImageView)findViewById(R.id.upload_btn);
        back_btn = (ImageView)findViewById(R.id.back_btn);
        folder_txt = (TextView)findViewById(R.id.folder_txt);
        pdf_name = (TextView)findViewById(R.id.pdf_name);
        download_pdf_btn = (MaterialButton)findViewById(R.id.download_pdf_btn);
        userLocalStore = new UserLocalStore(StudentPortel_R2.this);
        student_id = getIntent().getStringExtra("student_id");
        back_txt = (TextView)findViewById(R.id.back_txt);
         refreshLayout  = (SwipeRefreshLayout)findViewById(R.id.swipe_lt);

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


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdf_activity_lt.setVisibility(View.GONE);
                rv_progress_report.setVisibility(View.VISIBLE);
                main_lt.setVisibility(View.VISIBLE);
                header_title.setText("PROGRESS REPORT");
            }
        });

        
        back_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdf_activity_lt.setVisibility(View.GONE);
                rv_progress_report.setVisibility(View.VISIBLE);
                main_lt.setVisibility(View.VISIBLE);
                header_title.setText("PROGRESS REPORT");
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



    public void download_pdf(String filepath, String filename){
             DownloadManager.Request request = new DownloadManager.Request(Uri.parse(filepath));
            request.setTitle(filename);
            request.setMimeType("application/pdf");
            request.allowScanningByMediaScanner();
            request.setAllowedOverMetered(true);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,filename);
            DownloadManager dm = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
            dm.enqueue(request);
    }
    public void into_white(TextView textView){ textView.setTextColor(getResources().getColor(R.color.white)); }





}