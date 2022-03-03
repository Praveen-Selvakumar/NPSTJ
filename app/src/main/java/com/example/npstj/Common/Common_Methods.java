package com.example.npstj.Common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.npstj.DAO.CommonRetroAPI_Interface;
import com.example.npstj.ModelClass.NotificationList;
import com.example.npstj.R;
import com.example.npstj.RetrofitAPIClient;
import com.example.npstj.adapter.NoticationListAdapter;
import com.example.npstj.adapter.StudentGridAdpter;
import com.example.npstj.loginframe.Login_Page;
import com.example.npstj.mainframe.Home_Page;
import com.example.npstj.mainframe.Profile_Page;
import com.example.npstj.mainframe.StudentPortel_R1;
import com.example.npstj.mainframe.StudentPortel_R2;
import com.example.npstj.mainframe.StudentPortel_R3;
import com.example.npstj.mainframe.StudentPortel_R4;
import com.example.npstj.mainframe.Student_Portal;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Common_Methods {

    Context context ;
    UserLocalStore userLocalStore;
    ProgressDialog progressDialog;

    public Common_Methods(Context context) {
        this.context  = context;
        userLocalStore = new UserLocalStore(context.getApplicationContext());
        progressDialog = new ProgressDialog(context.getApplicationContext());
    }

    public void set_theme(Intent intent){

    }

    public void start_progress_dialog(ProgressDialog progressDialog){
        progressDialog.setMessage("Please wait while loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void show_notification_alert(LayoutInflater layoutInflater,
                                        AlertDialog.Builder builder,
                                        ArrayList<NotificationList> notification_list,
                                        CommonRetroAPI_Interface api_interface,
                                        String class_,
                                        String section_,
                                        String img_,
                                        String name_,
                                        String envi_id_){
        View view = layoutInflater.inflate(R.layout.alert_notication,null);

        builder.setView(view);
        //final AlertDialog dialog = builder.show();
        final AlertDialog dialog = builder.create();

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();

        wmlp.gravity = Gravity.TOP | Gravity.RIGHT;
        wmlp.x = 0;   //x position
        wmlp.y = 120;   //y position

        dialog.show();

        /*show.getWindow().setGravity(Gravity.TOP|Gravity.RIGHT);
        WindowManager.LayoutParams wmlp = show.getWindow().getAttributes();

        wmlp.gravity = Gravity.TOP | Gravity.RIGHT;
        wmlp.x = 200;   //x position
        wmlp.y = 400;   //y position*/

        RecyclerView recyclerView_ = (RecyclerView)view.findViewById(R.id.recycler_view);
        recyclerView_.setLayoutManager(new LinearLayoutManager(context.getApplicationContext(),RecyclerView.VERTICAL,false));
        get_notification(dialog,notification_list, api_interface, class_, section_,img_,name_,envi_id_, recyclerView_);
    }

    public void onbackpressed_intent(String student_name, String student_id,
                                      String student_class,String student_class_section,
                                      String student_img){
        Intent intent = new Intent(context.getApplicationContext(), Student_Portal.class);
        intent.putExtra("student_name", student_name);
        intent.putExtra("student_id",student_id);
        intent.putExtra("student_class", student_class);
        intent.putExtra("student_class_section",student_class_section);
        intent.putExtra("student_img", student_img);
        context.startActivity(intent);
    }


    public void get_notification(AlertDialog dialog,ArrayList<NotificationList> notification_list,
                                 CommonRetroAPI_Interface api_interface,
                                 String class_,
                                 String section_,
                                 String img_,
                                 String name_,
                                 String envi_id_,
                                 RecyclerView recyclerView){
        ArrayList<NotificationList> notificationList_array = notification_list;
        Call<ArrayList<NotificationList>> call = api_interface.load_notification_list(class_,section_);
        call.enqueue(new Callback<ArrayList<NotificationList>>() {
            @Override
            public void onResponse(Call<ArrayList<NotificationList>> call, Response<ArrayList<NotificationList>> response) {
                ArrayList<NotificationList> arrayList = response.body();

                 for (int i=0; i<arrayList.size(); i++){
                  NotificationList model = new NotificationList();
                  model.setId(arrayList.get(i).getId());
                  model.setContent_id(arrayList.get(i).getContent_id());
                  model.setContent_type(arrayList.get(i).getContent_type());
                  model.setNotification_message(arrayList.get(i).getNotification_message());
                  model.setClass_info(arrayList.get(i).getClass_info());
                  model.setSection_info(arrayList.get(i).getSection_info());
                  model.setDatex(arrayList.get(i).getDatex());
                  model.setEnvi_id_(envi_id_);
                  model.setName_(name_);
                  model.setClass_(class_);
                  model.setSection_(section_);
                  model.setImg_(img_);
                  notificationList_array.add(model);
                 }
                recyclerView.setAdapter(new NoticationListAdapter(dialog,context.getApplicationContext(),notificationList_array));
            }

            @Override
            public void onFailure(Call<ArrayList<NotificationList>> call, Throwable t) {

            }
        });

    }

    public void cancel_progress_dialog(ProgressDialog progressDialog){
        progressDialog.cancel();
    }

    public void tint_day(TextView t){
        if (!userLocalStore.get_theme().isEmpty()){
            if (userLocalStore.get_theme().equals("dark")){
                t.setBackground(context.getResources().getDrawable(R.drawable.time_table_btn_bg_dark));
                t.setTextColor(context.getResources().getColor(R.color.white));
            }else {
                t.setBackground(context.getResources().getDrawable(R.drawable.time_tablw_btn_bg));
                t.setTextColor(context.getResources().getColor(R.color.white));
            }
        }else {
            t.setBackground(context.getResources().getDrawable(R.drawable.time_tablw_btn_bg));
            t.setTextColor(context.getResources().getColor(R.color.white));
        }
    }

    public void days_back_to_normal(TextView t1, TextView t2, TextView t3, TextView t4, TextView t5){
        t1.setBackground(context.getResources().getDrawable(R.color.lite_src_clr));
        t1.setTextColor(context.getResources().getColor(R.color.splash_src_clr));

        t2.setBackground(context.getResources().getDrawable(R.color.lite_src_clr));
        t2.setTextColor(context.getResources().getColor(R.color.splash_src_clr));

        t3.setBackground(context.getResources().getDrawable(R.color.lite_src_clr));
        t3.setTextColor(context.getResources().getColor(R.color.splash_src_clr));

        t4.setBackground(context.getResources().getDrawable(R.color.lite_src_clr));
        t4.setTextColor(context.getResources().getColor(R.color.splash_src_clr));

        t5.setBackground(context.getResources().getDrawable(R.color.lite_src_clr));
        t5.setTextColor(context.getResources().getColor(R.color.splash_src_clr));
    }

    public void btm_nav(Context context, RelativeLayout home_lt,
                        RelativeLayout profile_lt, RelativeLayout logout_lt,
                        AlertDialog.Builder builder, LayoutInflater layoutInflater){
        home_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(context.getApplicationContext(), Home_Page.class);
                context.startActivity(intent);
            }
        });

        profile_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(context.getApplicationContext(), Profile_Page.class);
                context.startActivity(intent);
            }
        });

        logout_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 new Common_Methods(context.getApplicationContext()).showOfferAlert(layoutInflater,builder);

            }
        });

    }


    public void cmn_nav_drawer(NavigationView navigationView, Context context,
                               String student_name,
                               String student_id,
                               String  student_class,
                               String student_class_section,
                               String img,
                               DrawerLayout drawerLayout,
                               String page_id,AlertDialog.Builder builder, LayoutInflater layoutInflater){

        View header_view = navigationView.getHeaderView(0);
        ImageView close_btn = (ImageView)header_view.findViewById(R.id.close_btn);
        CardView theme_btn = (CardView)header_view.findViewById(R.id.theme_btn);
        RelativeLayout theme_bg = (RelativeLayout) header_view.findViewById(R.id.theme_bg);
        TextView theme_txt = (TextView)header_view.findViewById(R.id.theme_txt);
        ImageView theme_icon = (ImageView) header_view.findViewById(R.id.theme_icon);

        theme_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_theme(layoutInflater,builder);
            }
        });

        if (!userLocalStore.get_theme().isEmpty()){
            if (userLocalStore.get_theme().equals("light")){
                theme_bg.setBackgroundColor(context.getResources().getColor(R.color.white));
                theme_txt.setText("Light Mode");
                theme_txt.setTextColor(context.getResources().getColor(R.color.black));
                theme_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.sun));
            }else {
                theme_bg.setBackgroundColor(context.getResources().getColor(R.color.black));
                theme_txt.setText("Dark Mode");
                theme_txt.setTextColor(context.getResources().getColor(R.color.white));
                theme_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.moon));

            }
        }

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });


        Menu menu = navigationView.getMenu();

        navigationView.setItemTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.lite_src_clr)));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = new Intent(context.getApplicationContext(), StudentPortel_R1.class);
                Intent intent1 = new Intent(context.getApplicationContext(), StudentPortel_R2.class);
                Intent intent2 = new Intent(context.getApplicationContext(), StudentPortel_R3.class);
                Intent intent3 = new Intent(context.getApplicationContext(), StudentPortel_R4.class);

                switch (item.getItemId()){
                    case R.id.btm_profile:
                         startintent(drawerLayout,context,"1",intent,student_name,
                                 student_id, student_class, student_class_section, img);
                         break;

                    case R.id.btm_time_table:
                        startintent(drawerLayout,context,"2",intent, student_name,
                                student_id, student_class, student_class_section, img);
                        break;

                    case R.id.btm_home_work:
                        startintent(drawerLayout,context,"3",intent, student_name,
                                student_id, student_class, student_class_section, img);
                        break;

                    case R.id.btm_Examinations:
                        startintent(drawerLayout,context,"4",intent1, student_name,
                                student_id, student_class, student_class_section, img);
                        break;

                    case R.id.btm_progress_report:
                        startintent(drawerLayout,context,"5",intent1, student_name,
                                student_id, student_class, student_class_section, img);
                        break;

                    case R.id.btm_transportations:
                        startintent(drawerLayout,context,"6",intent1, student_name,
                                student_id, student_class, student_class_section, img);
                        break;

                    case R.id.btm_circular:
                        startintent(drawerLayout,context,"7",intent2, student_name,
                                student_id, student_class, student_class_section, img);
                        break;

                    case R.id.btm_attendance:
                        startintent(drawerLayout,context,"8",intent2, student_name,
                                student_id, student_class, student_class_section, img);
                        break;

                    case R.id.btm_fees:
                        startintent(drawerLayout,context,"9",intent2, student_name,
                                student_id, student_class, student_class_section, img);
                        break;

                    case R.id.btm_store:
                        startintent(drawerLayout,context,"10",intent3, student_name,
                                student_id, student_class, student_class_section, img);
                        break;


                    case R.id.btm_request:
                        startintent(drawerLayout,context,"11",intent3, student_name,
                                student_id, student_class, student_class_section, img);
                        break;

                    case R.id.btm_feedback:
                        startintent(drawerLayout,context,"12",intent3, student_name,
                                student_id, student_class, student_class_section, img);
                        break;


                }
                return false;
            }
        });
    }

    private void startintent(DrawerLayout drawerLayout,Context context,String item_id,Intent intent,
                    String student_name, String student_id,String  student_class,
                             String student_class_section,String img){
        intent.putExtra("item_id",item_id);
        intent.putExtra("student_name",student_name);
        intent.putExtra("student_id",student_id);
        intent.putExtra("student_class",student_class);
        intent.putExtra("student_class_section",student_class_section);
        intent.putExtra("student_img",img);
        context.startActivity(intent);
        drawerLayout.closeDrawer(GravityCompat.START);
    }


    public void showOfferAlert(LayoutInflater layoutInflater,
                                AlertDialog.Builder builder) {

        View view = layoutInflater.inflate(R.layout.logout_alert,null);

        builder.setView(view);
        //final AlertDialog dialog = builder.show();
        final AlertDialog dialog = builder.create();

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();

        wmlp.gravity = Gravity.TOP ;
        wmlp.x = 0;   //x position
        wmlp.y = 120;   //y position

        dialog.show();


        CardView yes_btn = (CardView)view.findViewById(R.id.yes_btn);
        CardView no_btn = (CardView)view.findViewById(R.id.no_btn);
        ImageView close_btn = (ImageView)view.findViewById(R.id.close_btn);

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                userLocalStore.clear_data();
                Intent intent = new Intent(context.getApplicationContext(), Login_Page.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void select_theme(LayoutInflater layoutInflater,AlertDialog.Builder builder){
        View view = layoutInflater.inflate(R.layout.alert_app_theme,null);
        builder.setView(view);
        //final AlertDialog dialog = builder.show();
        final AlertDialog dialog = builder.create();

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();

        wmlp.gravity = Gravity.CENTER ;
        wmlp.x = 0;   //x position
        wmlp.y = 120;   //y position

        CardView light_mode = (CardView)view.findViewById(R.id.lite_theme_btn);
        CardView dark_mode = (CardView)view.findViewById(R.id.dark_theme_btn);
        ImageView close_btn = (ImageView)view.findViewById(R.id.close_btn);
        RelativeLayout bg_lt = (RelativeLayout) view.findViewById(R.id.bg_lt);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView content_txt = (TextView) view.findViewById(R.id.content_txt);

        if (!userLocalStore.get_theme().isEmpty()){
            if(userLocalStore.get_theme().equals("light")){
                bg_lt.setBackgroundColor(context.getResources().getColor(R.color.lite_src_clr));
                title.setTextColor(context.getResources().getColor(R.color.black));
                content_txt.setTextColor(context.getResources().getColor(R.color.black));
            }
        }else {
            bg_lt.setBackgroundColor(context.getResources().getColor(R.color.lite_src_clr));
            title.setTextColor(context.getResources().getColor(R.color.black));
            content_txt.setTextColor(context.getResources().getColor(R.color.black));
        }


        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        light_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                Intent intent = new Intent(context.getApplicationContext(),Home_Page.class);
               userLocalStore.store_theme("light");
                dialog.dismiss();
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.abc_slide_in_bottom,R.anim.abc_slide_out_bottom);

            }
        });

        dark_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                Intent intent = new Intent(context.getApplicationContext(),Home_Page.class);
                userLocalStore.store_theme("dark");
                dialog.dismiss();
                 context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.abc_slide_in_bottom,R.anim.abc_slide_out_bottom);


            }
        });

        dialog.show();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

}
