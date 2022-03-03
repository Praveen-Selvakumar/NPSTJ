package com.example.npstj.mainframe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.npstj.Common.Common_Methods;
import com.example.npstj.Common.UserLocalStore;
import com.example.npstj.DAO.CommonRetroAPI_Interface;
import com.example.npstj.ModelClass.StudentList_model;
import com.example.npstj.ModelClass.Student_Model;
import com.example.npstj.R;
import com.example.npstj.RetrofitAPIClient;
import com.example.npstj.adapter.StudentList_HomePage_Adapter;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home_Page extends AppCompatActivity {

    RelativeLayout home_lt, profile_lt, logout_lt,theme_bg;
    CommonRetroAPI_Interface apiInterface;
    TextView student_name, student_id, theme_txt;
    public String environment_id = "NSA191463";
    RecyclerView recyclerView;
    ArrayList<StudentList_model> studentList;
    public static UserLocalStore userLocalStore;
    String get_number = "";
    Common_Methods common_methods;
    ProgressDialog progressDialog;
    ImageView banner, banner_bg_clr, theme_icon;
    TextView null_alert;
    private static final int SDCARD_PERMISSION = 1;
    MaterialButton default_btn, dark_btn;
    LayoutInflater layoutInflater;
    AlertDialog.Builder builder;
    CardView  theme_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         //  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
         super.onCreate(savedInstanceState);
        setContentView(R.layout.home__page);
        ButterKnife.bind(Home_Page.this);

        InitializeFields();

        Picasso.with(Home_Page.this)
                .load(R.drawable.bannerweb)
                .fit()
                .into(banner);

        Log.d("local_store",userLocalStore.get_theme().toString());
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

        home_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home_lt.setBackgroundColor(getResources().getColor(R.color.btm_bg_clr));
                logout_lt.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });

        profile_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Profile_Page.class);
                intent.putExtra("converted_number",getIntent().getStringExtra("converted_number"));
                load_themeby_intent(intent);
             }
        });

        logout_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home_lt.setBackgroundColor(getResources().getColor(R.color.white));
                logout_lt.setBackgroundColor(getResources().getColor(R.color.btm_bg_clr));

                AlertDialog.Builder builder = new AlertDialog.Builder(Home_Page.this);
                LayoutInflater layoutInflater = getLayoutInflater();
                new Common_Methods(Home_Page.this).showOfferAlert(layoutInflater, builder);

            }
        });


        if (userLocalStore.get_kid_list().isEmpty()) {

            String posting_number = "";
            String get_number = "";
            String implemented_first = "", implemented_second = "";
            String number = userLocalStore.get_number();
            posting_number = getIntent().getStringExtra("converted_number");

           /* String get_n[]  =  number.split("");
            posting_number = get_n[0]+get_n[1]+get_n[2]+"%2F"+get_n[3]
                                              +get_n[4] +get_n[5]+"-"+get_n[6] +get_n[7] +get_n[8]+get_n[9];*/

             /*for (int i = 0; i < cArray.length; i++) {
                if (cArray[i]== number.charAt(2)) {
                     get_number = String.valueOf(number.charAt(i) + "%2F");
                } else if (cArray[i] == number.charAt(5)) {
                    get_number = String.valueOf(number.charAt(i) + "-");
                } else {
                    get_number = String.valueOf(number.charAt(i));
                }
                posting_number = posting_number + get_number;
            }*/

           /* for (int i = 0; i < number.length(); i++) {
                if (number.charAt(i) == number.charAt(2) && implemented_second.isEmpty()) {
                    implemented_second = "done";
                    get_number = String.valueOf(number.charAt(i) + "%2F");
                } else if (number.charAt(i) == number.charAt(5) && implemented_first.isEmpty()) {
                    implemented_first = "done";
                    get_number = String.valueOf(number.charAt(i) + "-");
                } else {
                    get_number = String.valueOf(number.charAt(i));
                }
                posting_number = posting_number + get_number;
            }*/

            getAllstudentDetails(posting_number);
            common_methods.start_progress_dialog(progressDialog);

        } else {
            recyclerView.setAdapter(new StudentList_HomePage_Adapter(this, userLocalStore.get_kid_list()));
        }

        if (!userLocalStore.get_FCM_token().isEmpty()) {
            register_with_FCM();
        }


        String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
         Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Note")
                .setSettingsDialogTitle("Warning");
        Permissions.check(this, permission, null, options, new PermissionHandler() {
            @Override
            public void onGranted() {

            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                super.onDenied(context, deniedPermissions);
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
    protected void onStart() {
        home_lt.setBackgroundColor(getResources().getColor(R.color.btm_bg_clr));
        logout_lt.setBackgroundColor(getResources().getColor(R.color.white));
        super.onStart();
    }

    private void InitializeFields() {
        common_methods = new Common_Methods(Home_Page.this);
        userLocalStore = new UserLocalStore(Home_Page.this);
        progressDialog = new ProgressDialog(Home_Page.this);
        banner = (ImageView)findViewById(R.id.banner);
        banner_bg_clr = (ImageView)findViewById(R.id.banner_bg_clr);
        theme_icon = (ImageView)findViewById(R.id.theme_icon);
        progressDialog.setMessage("Please wait while loading...");
        home_lt = (RelativeLayout)findViewById(R.id.lt_home);
        profile_lt = (RelativeLayout)findViewById(R.id.lt_profile);
        logout_lt = (RelativeLayout)findViewById(R.id.lt_logout);
        theme_bg = (RelativeLayout)findViewById(R.id.theme_bg);
         home_lt.setBackgroundColor(getResources().getColor(R.color.btm_bg_clr));
        student_name = (TextView)findViewById(R.id.student_name);
        student_id = (TextView)findViewById(R.id.student_id);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        get_number = getIntent().getStringExtra("number");
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        null_alert = (TextView)findViewById(R.id.null_alert);
        theme_txt = (TextView)findViewById(R.id.theme_txt);
        layoutInflater = getLayoutInflater();
        builder = new AlertDialog.Builder(Home_Page.this);
       theme_btn = (CardView)findViewById(R.id.theme_btn);

        dark_btn = (MaterialButton)findViewById(R.id.dark_btn);
        default_btn = (MaterialButton)findViewById(R.id.default_btn);
       //apiInterface = RetrofitAPIClient.getClient_("TJ_STUDENT_INFO.v1/?EMPLID="+environment_id).create(CommonRetroAPI_Interface.class);

       // Log.d("FCMTOKEN",FCM_token);

        theme_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_theme();
            }
        });

        dark_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                Intent intent = new Intent(getApplicationContext(),Home_Page.class);
                startActivity(intent);
             }
        });
        default_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                Intent intent = new Intent(getApplicationContext(),Home_Page.class);
                startActivity(intent);
            }
        });
     }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private void getAllstudentDetails(String number){
        apiInterface = RetrofitAPIClient.getClient_("TJ_PARENT_LOGIN.v1/?phone="+number).create(CommonRetroAPI_Interface.class);
        studentList = new ArrayList<StudentList_model>();
        Call<StudentList_model>  call  = apiInterface.getAll_StudentListData();
        call.enqueue(new Callback<StudentList_model>() {
            @Override
            public void onResponse(Call<StudentList_model> call, Response<StudentList_model> response) {

                common_methods.cancel_progress_dialog(progressDialog);

                 if (response.body().getTJ_ERROR_TV_DOC() == null) {
                    ArrayList< StudentList_model.TJ_PARENT_LOGIN_RESP_DOC_Model.TJ_PARENT_LOGIN> arrayList = response.body().getTJ_PARENT_LOGIN_RESP_DOC().getArrayList();


                    for (int i = 0; i < arrayList.size(); i++) {
                        StudentList_model studentList_model = new StudentList_model();
                        studentList_model.setParent_name(arrayList.get(i).getParentname());
                        studentList_model.setStudent_name(arrayList.get(i).getStudentname());
                        studentList_model.setStudent_id(arrayList.get(i).getStudentid());
                        studentList_model.setClass_gs(arrayList.get(i).getClass_());
                        studentList_model.setClass_section(arrayList.get(i).getClass_section());
                        studentList_model.setPhoto_(arrayList.get(i).getPhoto());

                        if (!arrayList.get(i).getStudentname().equals(" ")) {
                            studentList.add(studentList_model);
                        }
                    }
                    userLocalStore.store_kid_list(studentList);
                    recyclerView.setAdapter(new StudentList_HomePage_Adapter(Home_Page.this, studentList));
                 }else {
                    recyclerView.setVisibility(View.GONE);
                    null_alert.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<StudentList_model> call, Throwable t) {

            }
        });
    }


    private void register_with_FCM() {
         apiInterface = RetrofitAPIClient.getClient("APIFCMTockenUpdate/").create(CommonRetroAPI_Interface.class);
             Call<StudentList_model> call = apiInterface.post_fcm_token(FirebaseInstanceId.getInstance().getToken(), userLocalStore.get_number());
            call.enqueue(new Callback<StudentList_model>() {
                @Override
                public void onResponse(Call<StudentList_model> call, Response<StudentList_model> response) {
                    if (response.isSuccessful()) {

                        if (response.body().getFCM_Response().equals("sucess")) {
                            userLocalStore.store_FCM_token(response.body().getFCM_fcm_tocken());
                            Toast.makeText(Home_Page.this,"Device registered", Toast.LENGTH_SHORT).show();
                           // Toast.makeText(Home_Page.this,response.body().getFCM_fcm_tocken(), Toast.LENGTH_SHORT).show();
                            Log.d("FCM",response.body().getFCM_fcm_tocken());
                        }

                    } else {
                        Log.d("make","nothing performaed");
                     }


                }

                @Override
                public void onFailure(Call<StudentList_model> call, Throwable t) {

                }

            });


        }


        public void select_theme(){
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
                   bg_lt.setBackgroundColor(getResources().getColor(R.color.lite_src_clr));
                   title.setTextColor(getResources().getColor(R.color.black));
                   content_txt.setTextColor(getResources().getColor(R.color.black));
                  }
            }else {
                bg_lt.setBackgroundColor(getResources().getColor(R.color.lite_src_clr));
                title.setTextColor(getResources().getColor(R.color.black));
                content_txt.setTextColor(getResources().getColor(R.color.black));
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
                    Intent intent = new Intent(getApplicationContext(),Home_Page.class);
                    startActivity(intent);
                    userLocalStore.store_theme("light");
                    dialog.dismiss();
                }
            });

            dark_mode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    Intent intent = new Intent(getApplicationContext(),Home_Page.class);
                    startActivity(intent);
                    userLocalStore.store_theme("dark");
                    dialog.dismiss();
                }
            });

            dialog.show();

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        }


}