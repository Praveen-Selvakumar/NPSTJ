package com.example.npstj.mainframe;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

 import com.example.npstj.Common.Common_Methods;
import com.example.npstj.Common.UserLocalStore;
import com.example.npstj.DAO.CommonRetroAPI_Interface;
import com.example.npstj.ModelClass.NotificationList;
import com.example.npstj.ModelClass.StudentPorfolio_Model;
import com.example.npstj.R;
import com.example.npstj.RetrofitAPIClient;
import com.example.npstj.adapter.StudentGridAdpter;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Student_Portal extends AppCompatActivity {

    ImageView menu_btn;
    @BindView(R.id.back_arrow) ImageView back_arrow;
    @BindView(R.id.menu_btn_tint) ImageView menu_btn_tint;
    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.grid_view) GridView gridView;
    @BindView(R.id.student_name) TextView student_name;
    @BindView(R.id.profile_image_) CircleImageView profile_image_;
    Common_Methods common_methods;
    RelativeLayout home_lt, profile_lt, logout_lt;
    CircleImageView goback_btn;
    @BindView(R.id.notification_number) TextView notification_number;
    @BindView(R.id.notification_btn) ImageView notification_btn;
    @BindView(R.id.toolbar_bg) RelativeLayout toolbar_bg;
    String get_name = "",get_id = "",get_img = "";
    CommonRetroAPI_Interface api_interface;
    ArrayList<NotificationList> notificationLists;
    LayoutInflater layoutInflater;
    AlertDialog.Builder builder;
    public static UserLocalStore userLocalStore;
    Intent intent ;



     @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.student_portal_test);
        ButterKnife.bind(Student_Portal.this);
        InitializeFields();

        //Intent intent_ = getIntent();
        //Bitmap bitmap = (Bitmap)intent_.getParcelableExtra("bitmap_img");

         String base64String = getIntent().getStringExtra("student_img");
         userLocalStore.store_bitmap(base64String);
         if (!userLocalStore.get_bitmap().isEmpty()){

         }
         byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
         Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
         profile_image_.setImageBitmap(decodedByte);


         get_name = getIntent().getStringExtra("student_name");
         get_id = getIntent().getStringExtra("student_id");
         get_img =  getIntent().getStringExtra("student_img");
         student_name.setText(get_name+"("+get_id+")");



       /*  String base64String =  getIntent().getStringExtra("student_img");
         byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
         Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
         profile_image_.setImageBitmap(BitmapFactory.decodeByteArray(decodedString,0 , decodedString.length));
        //Glide.with(Student_Portal.this).load(decodedByte).override(250, 250).into(profile_image_);
        //Picasso.with(Student_Portal.this).load(String.valueOf(decodedByte)).into(profile_image_);
        //profile_image_.setImageBitmap(decodedByte);*/





        if (!userLocalStore.get_theme().isEmpty()){
            if (userLocalStore.get_theme().equals("dark")){
                validate_theme();
            }
        }

        NavigationView nav_view = (NavigationView)findViewById(R.id.nav_view);
        common_methods.cmn_nav_drawer(nav_view,Student_Portal.this,
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



         goback_btn = (CircleImageView)findViewById(R.id.goback_btn);

        goback_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load_themeby_intent(intent);
               }
        });

        //intilaize Grid View
        ArrayList<StudentPorfolio_Model> courseModelArrayList = new ArrayList<StudentPorfolio_Model>();
        courseModelArrayList.add(new StudentPorfolio_Model("PROFILE", R.drawable.item_one_profile,"1"));
        courseModelArrayList.add(new StudentPorfolio_Model("TIME TABLE", R.drawable.item_two_time_table,"2"));
        courseModelArrayList.add(new StudentPorfolio_Model("HOMEWORKS", R.drawable.item_three_home_works,"3"));
        courseModelArrayList.add(new StudentPorfolio_Model("EXAMINATION", R.drawable.item_four_examination,"4"));
        courseModelArrayList.add(new StudentPorfolio_Model("PROGRESS REPORT", R.drawable.item_five_progress_report,"5"));
        courseModelArrayList.add(new StudentPorfolio_Model("TRANSPORT", R.drawable.item_six_transportation,"6"));
        courseModelArrayList.add(new StudentPorfolio_Model("CIRCULAR", R.drawable.item_seven_circular,"7"));
        courseModelArrayList.add(new StudentPorfolio_Model("ATTENDANCE", R.drawable.item_eight_attendance,"8"));
        courseModelArrayList.add(new StudentPorfolio_Model("FEES", R.drawable.item_nine_fees,"9"));
        courseModelArrayList.add(new StudentPorfolio_Model("STORE", R.drawable.item_ten_store,"10"));
        courseModelArrayList.add(new StudentPorfolio_Model("REQUEST", R.drawable.item_eleven_request,"11"));
        courseModelArrayList.add(new StudentPorfolio_Model("FEEDBACK", R.drawable.item_twelve_feedback,"12"));

        StudentGridAdpter adapter = new StudentGridAdpter(courseModelArrayList,Student_Portal.this);
        gridView.setAdapter(adapter);


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


    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }


    private void validate_theme() {
        menu_btn_tint.setColorFilter(getResources().getColor(R.color.white),PorterDuff.Mode.SRC_IN);
        notification_btn.setColorFilter(getResources().getColor(R.color.white),PorterDuff.Mode.SRC_IN);
        toolbar_bg.setBackgroundColor(getResources().getColor(R.color.black));
     }

    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(),inImage, "Title", null);
        return Uri.parse(path);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //startActivity(new Intent(Student_Portal.this,Home_Page.class));
        load_themeby_intent(intent);
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


    private void InitializeFields() {
        common_methods = new Common_Methods(this);
        home_lt = (RelativeLayout)findViewById(R.id.lt_home);
        profile_lt = (RelativeLayout)findViewById(R.id.lt_profile);
        logout_lt = (RelativeLayout)findViewById(R.id.lt_logout);
        menu_btn = (ImageView)findViewById(R.id.menu_btn);
        layoutInflater = getLayoutInflater();
        builder = new AlertDialog.Builder(Student_Portal.this);
        common_methods.btm_nav(Student_Portal.this,home_lt,profile_lt,logout_lt,builder,layoutInflater);
        api_interface = RetrofitAPIClient.getClient("APIMobileNotification/").create(CommonRetroAPI_Interface.class);
        userLocalStore = new UserLocalStore(Student_Portal.this);
        intent = new Intent(getApplicationContext(),Home_Page.class);

        Call<ArrayList<NotificationList>> call = api_interface.load_notification_list(
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
                      notificationLists,api_interface,
                      getIntent().getStringExtra("student_class"),
                      getIntent().getStringExtra("student_class_section"),
                      getIntent().getStringExtra("student_img"),
                      getIntent().getStringExtra("student_name"),
                      getIntent().getStringExtra("student_id")
                      );

            }
        });

    }


    
}