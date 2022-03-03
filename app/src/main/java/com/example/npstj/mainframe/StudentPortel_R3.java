package com.example.npstj.mainframe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.util.Pair;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.npstj.Common.Common_Methods;
import com.example.npstj.Common.UserLocalStore;
import com.example.npstj.DAO.CommonRetroAPI_Interface;
import com.example.npstj.ModelClass.Attendance_Model;
import com.example.npstj.ModelClass.CircularList_Model;
import com.example.npstj.ModelClass.FeeModel;
import com.example.npstj.ModelClass.NotificationList;
import com.example.npstj.ModelClass.TransportModel;
import com.example.npstj.R;
import com.example.npstj.RetrofitAPIClient;
import com.example.npstj.adapter.CircularList_Adapter;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.mikephil.charting.charts.BarChart;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import im.dacer.androidcharts.PieHelper;
import im.dacer.androidcharts.PieView;
import lib.folderpicker.FolderPicker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentPortel_R3 extends AppCompatActivity {

    @BindView(R.id.menu_btn) ImageView menu_btn;
    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.header_title) TextView header_title;
    @BindView(R.id.error_alert_txt) TextView error_alert_txt;
    public static RelativeLayout home_lt, profile_lt, logout_lt,pdf_activity_lt,main_lt;
    Intent intent;
    public RecyclerView recycler_view_circular;
    String get_id = "";
    ArrayList<Attendance_Model> arrayList_attendance;
    CommonRetroAPI_Interface api_interface,api_interface_;
    ArrayList<CircularList_Model> arrayList_circular_List;
    Common_Methods common_methods;
    ProgressDialog progressDialog;
    public static int FOLDERPICKER_CODE = 101;
    MaterialButton download_pdf_btn ;
    @BindView(R.id.notification_number) TextView notification_number;
    @BindView(R.id.notification_btn)public ImageView notification_btn;
     @BindView(R.id.menu_btn_tint) ImageView menu_btn_tint;
    @BindView(R.id.toolbar_bg) RelativeLayout toolbar_bg;

    public ImageView upload_btn,back_btn;
    ArrayList<NotificationList> notificationLists;
    LayoutInflater layoutInflater;
    AlertDialog.Builder builder;
     TextView null_alert,pdf_name, back_txt;

    URL url = null;
    private String filename;
    PDFView pdfView;
    SwipeRefreshLayout refreshLayout;
    public static UserLocalStore userLocalStore;
    //textview fees
    TextView student_name_, class_section_name_, environment_id_name_, fee_structure_,
            pandemic_discount_, payable_amount_, fee_structure_amount_txt_, pandemic_discount_amount_txt_,
            payable_amount_txt_;

  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      if (Build.VERSION.SDK_INT < 16) {
          getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                  WindowManager.LayoutParams.FLAG_FULLSCREEN);
      }
        setContentView(R.layout.student_portel__r3);
        ButterKnife.bind(StudentPortel_R3.this);
        IntializeFields();

        page_detail();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                 page_detail();
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

      if (!userLocalStore.get_theme().isEmpty()){
          if (userLocalStore.get_theme().equals("dark")){
              valid_theme();
              RelativeLayout main_rel_bg = (RelativeLayout)findViewById(R.id.main_rel_bg);
              main_rel_bg.setBackgroundColor(getResources().getColor(R.color.black));
          }
      }


      drawerLayout = findViewById(R.id.drawer_layout);
      ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
              R.string.navigation_drawer_open, R.string.navigation_drawer_close);
      toggle.setHomeAsUpIndicator(R.drawable.menu_icon);
      drawerLayout.addDrawerListener(toggle);
      toggle.syncState();

      NavigationView nav_view = (NavigationView)findViewById(R.id.nav_view);
      common_methods.cmn_nav_drawer(nav_view,StudentPortel_R3.this,
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

    private void valid_theme() {
        //toolbar
        menu_btn_tint.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        notification_btn.setColorFilter(getResources().getColor(R.color.white),PorterDuff.Mode.SRC_IN);
        toolbar_bg.setBackgroundColor(getResources().getColor(R.color.black));

        //Atendance
        ConstraintLayout attendance_lt = (ConstraintLayout)findViewById(R.id.attendance_lt);
         RelativeLayout rel_bg = (RelativeLayout)attendance_lt.findViewById(R.id.rel_bg);
         RelativeLayout present_bg = (RelativeLayout)attendance_lt.findViewById(R.id.present_bg);
         RelativeLayout absent_bg = (RelativeLayout)attendance_lt.findViewById(R.id.absent_bg);
         RelativeLayout  total_bg = (RelativeLayout)attendance_lt.findViewById(R.id.total_bg);
         rel_bg.setBackgroundColor(getResources().getColor(R.color.black));
         present_bg.setBackgroundColor(getResources().getColor(R.color.two_bg));
         absent_bg.setBackgroundColor(getResources().getColor(R.color.two_bg));
         total_bg.setBackgroundColor(getResources().getColor(R.color.two_bg));
        TextView total_num = (TextView)attendance_lt.findViewById(R.id.total_num);
        TextView present_num = (TextView)attendance_lt.findViewById(R.id.present_num);
        TextView absent_num = (TextView)attendance_lt.findViewById(R.id.absent_num);

        TextView total_txt = (TextView)attendance_lt.findViewById(R.id.total_txt);
        TextView present_txt = (TextView)attendance_lt.findViewById(R.id.present_txt);
        TextView absent_txt = (TextView)attendance_lt.findViewById(R.id.absent_txt);
        total_num.setTextColor(getResources().getColor(R.color.white)); total_txt.setTextColor(getResources().getColor(R.color.white));
        present_num.setTextColor(getResources().getColor(R.color.white));present_txt.setTextColor(getResources().getColor(R.color.white));
        absent_num.setTextColor(getResources().getColor(R.color.white));absent_txt.setTextColor(getResources().getColor(R.color.white));

        //load fees
        ConstraintLayout fees_lt = (ConstraintLayout)findViewById(R.id.fees_lt);
        RelativeLayout reg_lt = (RelativeLayout)fees_lt.findViewById(R.id.other_details_content_lt);
        reg_lt.setBackgroundColor(getResources().getColor(R.color.two_bg));
        TextView text_link = (TextView)fees_lt.findViewById(R.id.text_link);
        TextView student_name_txt = (TextView)fees_lt.findViewById(R.id.student_name_txt);
        TextView class_section_name_txt = (TextView)fees_lt.findViewById(R.id.class_section_name_txt);
        TextView environment_id_txt = (TextView)fees_lt.findViewById(R.id.environment_id_txt);

        text_link.setBackgroundColor(getResources().getColor(R.color.one_bg_));
        ImageView icon_1 = (ImageView)fees_lt.findViewById(R.id.icon_1);
        ImageView icon_2 = (ImageView)fees_lt.findViewById(R.id.icon_2);
        ImageView icon_3 = (ImageView)fees_lt.findViewById(R.id.icon_3);

        //student detail
        student_name_ = (TextView)fees_lt.findViewById(R.id.student_name);
        class_section_name_ = (TextView)fees_lt.findViewById(R.id.class_section_name);
        environment_id_name_ = (TextView)fees_lt.findViewById(R.id.environment_id_name);
        //fee txt
        fee_structure_ = (TextView)fees_lt.findViewById(R.id.fee_structure);
        pandemic_discount_ = (TextView)fees_lt.findViewById(R.id.pandemic_discount);
        payable_amount_ = (TextView)fees_lt.findViewById(R.id.payable_amount);
        //fee amount
        fee_structure_amount_txt_ = (TextView)fees_lt.findViewById(R.id.fee_structure_amount_txt);
        pandemic_discount_amount_txt_ = (TextView)fees_lt.findViewById(R.id.pandemic_discount_amount_txt);
        payable_amount_txt_ = (TextView)fees_lt.findViewById(R.id.payable_amount_txt);

        into_white(student_name_); into_white(class_section_name_); into_white(environment_id_name_);
        into_white(pandemic_discount_); into_white(payable_amount_); into_white(fee_structure_);
        into_white(fee_structure_amount_txt_); into_white(pandemic_discount_amount_txt_); into_white(payable_amount_txt_);
        icon_tint_white(icon_1); icon_tint_white(icon_2); icon_tint_white(icon_3);
        into_white(student_name_txt);  into_white(class_section_name_txt);
        into_white(environment_id_txt);


    }

    private void page_detail() {
        if (get_id.equals("7")){
            progressDialog.show();
            header_title.setText("CIRCULAR");
            recycler_view_circular.setVisibility(View.VISIBLE);
            recycler_view_circular.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
            String get_class = getIntent().getStringExtra("student_class");
            String get_section = getIntent().getStringExtra("student_class_section");
            load_circularList(get_class,get_section);
        }else if (get_id.equals("8")){
            progressDialog.show();
            header_title.setText("ATTENDANCE");
            ScrollView scrollView = (ScrollView)findViewById(R.id.scroll_view);scrollView.setVisibility(View.VISIBLE);
            ConstraintLayout attendance_lt = (ConstraintLayout)findViewById(R.id.attendance_lt);
            attendance_lt.setVisibility(View.VISIBLE);
            String envi_id = getIntent().getStringExtra("student_id");
            load_attendance(envi_id);
            Log.d("envi_id",envi_id);
        }else if (get_id.equals("9")) {
            progressDialog.show();
            header_title.setText("FEES");
            ScrollView scrollView = (ScrollView)findViewById(R.id.scroll_view);scrollView.setVisibility(View.VISIBLE);
            ConstraintLayout fees_lt = (ConstraintLayout)findViewById(R.id.fees_lt);
            String get_section = getIntent().getStringExtra("student_class_section");
            String get_name = getIntent().getStringExtra("student_name");
            String get_student_id = getIntent().getStringExtra("student_id");
            load_fees_details(get_section,get_name,get_student_id);
        }
    }


    private void load_fees_details(String get_section, String get_name, String get_student_id) {
        ConstraintLayout fees_lt = (ConstraintLayout)findViewById(R.id.fees_lt);
        TextView class_section_name_txt = (TextView)fees_lt.findViewById(R.id.class_section_name_txt);
        class_section_name_txt.setText("CLASS & SECTION");

        //student detail
        student_name_ = (TextView)fees_lt.findViewById(R.id.student_name);
         class_section_name_ = (TextView)fees_lt.findViewById(R.id.class_section_name);
         environment_id_name_ = (TextView)fees_lt.findViewById(R.id.environment_id_name);
        //fee txt
         fee_structure_ = (TextView)fees_lt.findViewById(R.id.fee_structure);
         pandemic_discount_ = (TextView)fees_lt.findViewById(R.id.pandemic_discount);
         payable_amount_ = (TextView)fees_lt.findViewById(R.id.payable_amount);
        //fee amount
         fee_structure_amount_txt_ = (TextView)fees_lt.findViewById(R.id.fee_structure_amount_txt);
         pandemic_discount_amount_txt_ = (TextView)fees_lt.findViewById(R.id.pandemic_discount_amount_txt);
         payable_amount_txt_ = (TextView)fees_lt.findViewById(R.id.payable_amount_txt);
        TextView text_link = (TextView)fees_lt.findViewById(R.id.text_link);
        MaterialButton text_link_btn = (MaterialButton)fees_lt.findViewById(R.id.text_link_btn);


        api_interface  = RetrofitAPIClient.getClient("APIMobileFeeeList/").create(CommonRetroAPI_Interface.class);
        Call<FeeModel> call  = api_interface.load_fees_list(getIntent().getStringExtra("student_id"));

         call.enqueue(new Callback<FeeModel>() {
             @Override
             public void onResponse(Call<FeeModel> call, Response<FeeModel> response) {

                 if (response.isSuccessful()) {

                     if (response != null) {
                         student_name_.setText(get_name);
                         class_section_name_.setText(get_section);
                         environment_id_name_.setText(get_student_id);

                         fee_structure_.setText(response.body().getAmount_description());

                         String[] discount_description = response.body().getDiscount_description().split("%");
                         pandemic_discount_.setText(discount_description[0] + "%" + "\n" + discount_description[1]);

                         String[] academic_year = response.body().getAcademic_year().split("-");
                         payable_amount_.setText("Payable Amount for " + academic_year[0] + " -" + "\n" + academic_year[1]);

                         fee_structure_amount_txt_.setText("\u20B9" + response.body().getFee_amount());
                         pandemic_discount_amount_txt_.setText("\u20B9" + response.body().getFee_discount());
                         payable_amount_txt_.setText("\u20B9" + response.body().getTotal() + " /-");

                         text_link_btn.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View v) {
                                  Intent i2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://epay.federalbank.co.in/easypayments/"));
                                 startActivity(i2);
                             }
                         });

                         fees_lt.setVisibility(View.VISIBLE);

                     }else {
                       fees_lt.setVisibility(View.GONE);
                       null_alert.setVisibility(View.VISIBLE);
                     }
                 }else {
                     fees_lt.setVisibility(View.GONE);
                     null_alert.setVisibility(View.VISIBLE);
                 }
                 progressDialog.cancel();
                 refreshLayout.setRefreshing(false);
             }

             @Override
             public void onFailure(Call<FeeModel> call, Throwable t) {

             }
         });
        }


    private void load_circularList(String vii_std, String viib) {
     arrayList_circular_List = new ArrayList<CircularList_Model>();
     api_interface = RetrofitAPIClient.getClient("APIMobileCircular/").create(CommonRetroAPI_Interface.class);

     Call<ArrayList<CircularList_Model>> call = api_interface.get_cirlcuar_details(vii_std,viib);
      call.enqueue(new Callback<ArrayList<CircularList_Model>>() {
          @Override
          public void onResponse(Call<ArrayList<CircularList_Model>> call, Response<ArrayList<CircularList_Model>> response) {

              progressDialog.cancel();
              refreshLayout.setRefreshing(false);
              ArrayList<CircularList_Model> arrayList = response.body();

              for (int i=0; i<arrayList.size(); i++){
               CircularList_Model model = new CircularList_Model();
               model.setId(arrayList.get(i).getId());
               model.setTitle(arrayList.get(i).getTitle());
               model.setDescription(arrayList.get(i).getDescription());
               model.setCircular_date(arrayList.get(i).getCircular_date());
               model.setPublish_status(arrayList.get(i).getPublish_status());
               model.setAdded_by(arrayList.get(i).getAdded_by());
               model.setAdded_date(arrayList.get(i).getAdded_date());
               model.setClass_id(arrayList.get(i).getClass_id());
               model.setSection_id(arrayList.get(i).getSection_id());
               model.setCircular_file(arrayList.get(i).getCircular_file());
               arrayList_circular_List.add(model);
              }


              recycler_view_circular.setAdapter(new CircularList_Adapter(StudentPortel_R3.this,arrayList_circular_List));

          }
          @Override
          public void onFailure(Call<ArrayList<CircularList_Model>> call, Throwable t) {

          }
      });

    }

    private void load_attendance(String envi_id) {
        ConstraintLayout attendance_lt = (ConstraintLayout)findViewById(R.id.attendance_lt);
        TextView total_num = (TextView)attendance_lt.findViewById(R.id.total_num);
        TextView present_num = (TextView)attendance_lt.findViewById(R.id.present_num);
        TextView absent_num = (TextView)attendance_lt.findViewById(R.id.absent_num);
        PieView pie_view_chart = (PieView)attendance_lt.findViewById(R.id.pie_view_chart);
        LinearLayout ll_lt = (LinearLayout)attendance_lt.findViewById(R.id.ll_lt);
        CardView ll_lt_2 = (CardView) attendance_lt.findViewById(R.id.ll_lt_2);
        //AnyChartView  chart_view = (AnyChartView)attendance_lt.findViewById(R.id.chart_view);


      arrayList_attendance = new ArrayList<Attendance_Model>();
      api_interface = RetrofitAPIClient.getClient_("TJ_ATTENDANCE_TV.v1/?emplid="+envi_id).create(CommonRetroAPI_Interface.class);





        Call<Attendance_Model> call = api_interface.get_attendanceList_details();
        call.enqueue(new Callback<Attendance_Model>() {
            @Override
            public void onResponse(Call<Attendance_Model> call, Response<Attendance_Model> response) {

                if (response.body().getTJ_ERROR_TV_DOC() == null){
                    ArrayList<Attendance_Model.TJ_ATT_TV_RES_DOC_Model.TJ_ATT_TV_RES_SUB> arrayList = response.body().getTJ_ATT_TV_RES_DOC().getArrayList();

                    load_pie_view(absent_num,present_num,total_num,pie_view_chart,arrayList);

                }else {
                    error_alert_txt.setVisibility(View.VISIBLE);
                    ll_lt.setVisibility(View.GONE);
                    ll_lt_2.setVisibility(View.GONE);
                }

                // load_pie_chart_1(absent_num,present_num,total_num,chart_view,arrayList);

                // testpie_detail(absent_num,present_num,total_num,pie_view_chart);
                progressDialog.cancel();
                refreshLayout.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<Attendance_Model> call, Throwable t) {

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

    private void testpie_detail(TextView absent_num, TextView present_num, TextView total_num, PieView pieView) {
        ArrayList<PieHelper> pieHelperArrayList = new ArrayList<PieHelper>();
        int absent = 44;
        int get_divided_date = absent / 2 ;
        int present_days = 100 - get_divided_date;

        Log.d("present_day",String.valueOf(present_days));
        Log.d("absent_day",String.valueOf(get_divided_date));

        /*
        D/present_day: 19
        D/absent_day: 81
        * */
        pieHelperArrayList.add(new PieHelper(present_days,getResources().getColor(R.color.green)));
        pieHelperArrayList.add(new PieHelper(get_divided_date,getResources().getColor(R.color.red)));
        pieView.showPercentLabel(false);
        pieView.setDate(pieHelperArrayList);

        pieView.animate();
        pieView.setOnPieClickListener(new PieView.OnPieClickListener() {
            @Override
            public void onPieClick(int index) {

            }
        });

        //set values on text
        total_num.setText(String.valueOf(162));
        present_num.setText(String.valueOf(118));
        absent_num.setText(String.valueOf(44));
    }

    private void IntializeFields() {
      common_methods = new Common_Methods(StudentPortel_R3.this);
      recycler_view_circular = (RecyclerView)findViewById(R.id.recycler_view_circular);
      get_id = getIntent().getStringExtra("item_id");
      progressDialog = new ProgressDialog(this);
      progressDialog.setMessage("Please wait while loading...");
      progressDialog.setCanceledOnTouchOutside(false);
      null_alert = (TextView)findViewById(R.id.null_alert);
      pdf_name = (TextView)findViewById(R.id.pdf_name);
      upload_btn = (ImageView) findViewById(R.id.upload_btn);
      back_btn = (ImageView) findViewById(R.id.back_btn);
      download_pdf_btn  = (MaterialButton)findViewById(R.id.download_pdf_btn);
      pdfView = (PDFView)findViewById(R.id.pdf_viewer);
      back_txt = (TextView)findViewById(R.id.back_txt);
      refreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_lt);
      userLocalStore = new UserLocalStore(StudentPortel_R3.this);

      home_lt = (RelativeLayout)findViewById(R.id.lt_home);
      profile_lt = (RelativeLayout)findViewById(R.id.lt_profile);
      logout_lt = (RelativeLayout)findViewById(R.id.lt_logout);
      main_lt = (RelativeLayout)findViewById(R.id.main_lt);
      pdf_activity_lt = (RelativeLayout)findViewById(R.id.pdf_activity_lt);
      layoutInflater = getLayoutInflater();
      builder = new AlertDialog.Builder(StudentPortel_R3.this);
      common_methods.btm_nav(StudentPortel_R3.this,home_lt,profile_lt,logout_lt,builder,layoutInflater);

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

        download_pdf_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               pdf_activity_lt.setVisibility(View.GONE);
                main_lt.setVisibility(View.VISIBLE);

            }
        });

        back_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               pdf_activity_lt.setVisibility(View.GONE);
                main_lt.setVisibility(View.VISIBLE);
            }
        });

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(StudentPortel_R3.this, FolderPicker.class);

                //To show a custom title
                intent.putExtra("title", "Select file to upload");

                intent.putExtra("location",Environment.getExternalStorageDirectory().getAbsolutePath());

                //To pick files
                intent.putExtra("pickFiles", true);

                startActivity(intent);
                System.out.println("location_is:" +getIntent().getStringExtra("location"));
                //startActivityForResult(intent, FOLDERPICKER_CODE);
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



     private void  load_pie_view(TextView absent_num, TextView present_num, TextView total_num, PieView pieView,
                                 ArrayList<Attendance_Model.TJ_ATT_TV_RES_DOC_Model.TJ_ATT_TV_RES_SUB> arrayList){

         ArrayList<PieHelper> pieHelperArrayList = new ArrayList<PieHelper>();
         int present_days = arrayList.get(0).getTotalPresent() ;
         int absent_days = arrayList.get(0).getTotalabs();

         if (present_days == absent_days){
             absent_days = 50;
             present_days = 50;
         }else if (absent_days == 0){
             absent_days = 0;
             present_days = 100;
         }else if (absent_days < 50){
             absent_days = 30;
             present_days = 70;
         }else if (absent_days > 50){
             absent_days = 40;
             present_days = 60;
         }

         pieHelperArrayList.add(new PieHelper(present_days,getResources().getColor(R.color.green)));
         pieHelperArrayList.add(new PieHelper(absent_days,getResources().getColor(R.color.red)));
         pieView.showPercentLabel(false);
         pieView.setDate(pieHelperArrayList);

          pieView.animate();
         pieView.setOnPieClickListener(new PieView.OnPieClickListener() {
             @Override
             public void onPieClick(int index) {

             }
         });


         //set values on text
         total_num.setText(String.valueOf(arrayList.get(0).getTotalWorkDays()));
         present_num.setText(String.valueOf(arrayList.get(0).getTotalPresent()));
         absent_num.setText(String.valueOf(arrayList.get(0).getTotalabs()));
     }

    private void  load_pie_chart_1(TextView absent_num, TextView present_num, TextView total_num, AnyChartView chart_view, ArrayList<Attendance_Model.TJ_ATT_TV_RES_DOC_Model.TJ_ATT_TV_RES_SUB> arrayList){
        String[] months = {"Present List","Absent List"};
        int[] earning  ={arrayList.get(0).getTotalPresent(),arrayList.get(0).getTotalabs()};
        ArrayList<DataEntry> dataEntries = new ArrayList<>();
        Pie chart  = AnyChart.pie();
        for (int i=0; i <months.length; i++)
        {
            dataEntries.add(new ValueDataEntry(months[i],earning[i]));

        }
        String[] clrs = {"#18920c", "#f13d3c"};
        chart.data(dataEntries);
        chart.palette(clrs);
         chart_view.setChart(chart);


        //set values on text
        total_num.setText(String.valueOf(arrayList.get(0).getTotalWorkDays()));
        present_num.setText(String.valueOf(arrayList.get(0).getTotalPresent()));
        absent_num.setText(String.valueOf(arrayList.get(0).getTotalabs()));
    }


    public void saveFileToPdf(String url_){
        try {
            url = new URL(url_);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        filename = url.getPath();

        filename = filename.substring(filename.lastIndexOf('/')+1);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url+""));
        request.setTitle(filename);
        request.setMimeType("application/pdf");
        request.allowScanningByMediaScanner();
        request.setAllowedOverMetered(true);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filename);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,filename);
         //request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,filename);
        DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        dm.enqueue(request);

        show_PDF(file,filename);
        Toast.makeText(this,filename, Toast.LENGTH_LONG).show();
        Log.d("file_",file.toString());

    }

    public void show_PDF(File file, String gb_filname){

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



        String file_name_txt  = "<font color='#0066ae'>"+filename+"</font>";
        String downloaded_txt  = "<font color='#000000'> is Downloaded ..! </font>";
        comtent_txt.setText(Html.fromHtml(file_name_txt+" "+downloaded_txt));

        view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/"+gb_filname);
                Log.d("file__",file.toString());
                pdf_activity_lt.setVisibility(View.VISIBLE);
                main_lt.setVisibility(View.GONE);
                pdfView.fromFile(file).load();
                pdf_name.setText(gb_filname);
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

    public void into_white(TextView textView){ textView.setTextColor(getResources().getColor(R.color.white)); }
    public void icon_tint_white(ImageView imageView){  imageView.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);  }


    /*
    *  File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/"+filename);
                Uri uri = FileProvider.getUriForFile(DownloadActivity.this,"com.example.folderpicker"+".provider",file);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setDataAndType(uri,"application/pdf");
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(i);
    * */

}
