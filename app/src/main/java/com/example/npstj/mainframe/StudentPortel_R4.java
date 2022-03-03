package com.example.npstj.mainframe;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

import com.example.npstj.Common.Common_Methods;
import com.example.npstj.Common.UserLocalStore;
import com.example.npstj.DAO.CommonRetroAPI_Interface;
import com.example.npstj.ModelClass.CatModel;
import com.example.npstj.ModelClass.LoginCredentials;
import com.example.npstj.ModelClass.NotificationList;
import com.example.npstj.ModelClass.StoreModel;
import com.example.npstj.R;
import com.example.npstj.RetrofitAPIClient;
import com.example.npstj.adapter.StoreListAdapter;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentPortel_R4 extends AppCompatActivity {

    @BindView(R.id.menu_btn)
    ImageView menu_btn;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Common_Methods common_methods;
    TextView title_txt;
    public static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    String get_id = "";
    ConstraintLayout request_lt, feedback_lt;
    RelativeLayout home_lt, profile_lt, logout_lt;
    String spinnerno = "1";
    CommonRetroAPI_Interface api_interface, api_interface_;
    ArrayList<StoreModel> store_list;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;

    ArrayList<CatModel> catModels;
    public int CAT_ID = 0;
    @BindView(R.id.notification_number)
    TextView notification_number;
    @BindView(R.id.notification_btn)
    ImageView notification_btn;
    @BindView(R.id.menu_btn_tint)
    ImageView menu_btn_tint;
    @BindView(R.id.toolbar_bg)
    RelativeLayout toolbar_bg;

    ArrayList<NotificationList> notificationLists;
    LayoutInflater layoutInflater;
    AlertDialog.Builder builder;
     public static UserLocalStore userLocalStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.student_portel__r4);
        ButterKnife.bind(StudentPortel_R4.this);
        InitilaizeFields();

        page_detail();


        if (!userLocalStore.get_theme().isEmpty()) {
            if (userLocalStore.get_theme().equals("dark")) {
                valid_theme();
                RelativeLayout main_rel_bg = (RelativeLayout) findViewById(R.id.main_rel_bg);
                main_rel_bg.setBackgroundColor(getResources().getColor(R.color.black));
            }
        }


        /*refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                page_detail();
            }
        });*/

        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
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

        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        common_methods.cmn_nav_drawer(nav_view, StudentPortel_R4.this,
                getIntent().getStringExtra("student_name"),
                getIntent().getStringExtra("student_id"),
                getIntent().getStringExtra("student_class"),
                getIntent().getStringExtra("student_class_section"),
                getIntent().getStringExtra("student_img")
                , drawerLayout, get_id, builder, layoutInflater);
        View header_view = nav_view.getHeaderView(0);
        RelativeLayout theme_bg = (RelativeLayout) header_view.findViewById(R.id.theme_bg);
        TextView theme_txt = (TextView) header_view.findViewById(R.id.theme_txt);
        ImageView theme_icon = (ImageView) header_view.findViewById(R.id.theme_icon);
        if (!userLocalStore.get_theme().isEmpty()) {
            if (userLocalStore.get_theme().equals("light")) {
                theme_bg.setBackgroundColor(getResources().getColor(R.color.white));
                theme_txt.setText("Light Mode");
                theme_txt.setTextColor(getResources().getColor(R.color.black));
                theme_icon.setImageDrawable(getResources().getDrawable(R.drawable.sun));
            } else {
                theme_bg.setBackgroundColor(getResources().getColor(R.color.black));
                theme_txt.setText("Dark Mode");
                theme_txt.setTextColor(getResources().getColor(R.color.white));
                theme_icon.setImageDrawable(getResources().getDrawable(R.drawable.moon));

            }
        }

        CircleImageView goback_btn = (CircleImageView) findViewById(R.id.goback_btn);
        goback_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Student_Portal.class);
                intent.putExtra("student_name", getIntent().getStringExtra("student_name"));
                intent.putExtra("student_id", getIntent().getStringExtra("student_id"));
                intent.putExtra("student_class", getIntent().getStringExtra("student_class"));
                intent.putExtra("student_class_section", getIntent().getStringExtra("student_class_section"));
                intent.putExtra("student_img", getIntent().getStringExtra("student_img"));
                //startActivity(intent);
                load_themeby_intent(intent);
            }
        });
    }

    public void load_themeby_intent(Intent intent) {

        if (!userLocalStore.get_theme().isEmpty()) {
            if (userLocalStore.get_theme().equals("dark")) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        }
        overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
        startActivity(intent);

    }


    private void valid_theme() {
        //toolbar
        menu_btn_tint.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        notification_btn.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        toolbar_bg.setBackgroundColor(getResources().getColor(R.color.black));

        //enquiry lt
        ConstraintLayout request_lt = (ConstraintLayout) findViewById(R.id.request_lt);
        TextView category = (TextView) request_lt.findViewById(R.id.category);
        TextView msg = (TextView) request_lt.findViewById(R.id.msg);
        TextView email = (TextView) request_lt.findViewById(R.id.email_txt);
        email.setTextColor(getResources().getColor(R.color.white));
        category.setTextColor(getResources().getColor(R.color.white));
        msg.setTextColor(getResources().getColor(R.color.white));

        //feedbak lt
        ConstraintLayout feedback_lt = (ConstraintLayout) findViewById(R.id.feedback_lt);
        RelativeLayout rel_bg = (RelativeLayout) feedback_lt.findViewById(R.id.rel_bg);
        TextView subject = (TextView) rel_bg.findViewById(R.id.subject);
        TextView msg_ = (TextView) rel_bg.findViewById(R.id.msg);
        TextView email_txt = (TextView) rel_bg.findViewById(R.id.email_txt);
        email_txt.setTextColor(getResources().getColor(R.color.white));
        subject.setTextColor(getResources().getColor(R.color.white));
        msg_.setTextColor(getResources().getColor(R.color.white));
        EditText sub_ed = (EditText) feedback_lt.findViewById(R.id.ed_sub);
        EditText email_ed = (EditText) feedback_lt.findViewById(R.id.email_ed);
        EditText ed_fb = (EditText) feedback_lt.findViewById(R.id.ed_fb);
        into_blue(sub_ed);
        into_blue(email_ed);
        into_blue(ed_fb);
    }

    private void page_detail() {
        if (get_id.equals("10")) {
            progressDialog.show();
            title_txt.setText("STORE");
            load_store_details();
        } else if (get_id.equals("11")) {
            recyclerView.setVisibility(View.GONE);
            ScrollView scroll_view = (ScrollView) findViewById(R.id.scroll_view);
            scroll_view.setVisibility(View.VISIBLE);
            ConstraintLayout request_lt = (ConstraintLayout) findViewById(R.id.request_lt);
            title_txt.setText("ENQUIRY");
            request_lt.setVisibility(View.VISIBLE);
            alert_spinner(request_lt);
        } else if (get_id.equals("12")) {
            recyclerView.setVisibility(View.GONE);
            ScrollView scroll_view = (ScrollView) findViewById(R.id.scroll_view);
            scroll_view.setVisibility(View.VISIBLE);
            ConstraintLayout feedback_lt = (ConstraintLayout) findViewById(R.id.feedback_lt);
            title_txt.setText("FEEDBACK");
            feedback_lt.setVisibility(View.VISIBLE);
            send_feed_back(feedback_lt);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        common_methods.onbackpressed_intent(getIntent().getStringExtra("student_name"),
                getIntent().getStringExtra("student_id"),
                getIntent().getStringExtra("student_class"),
                getIntent().getStringExtra("student_class_section"),
                getIntent().getStringExtra("student_img"));
    }

    private void send_feed_back(ConstraintLayout feedback_lt) {
        EditText ed_sub = (EditText) feedback_lt.findViewById(R.id.ed_sub);
        EditText ed_fb = (EditText) feedback_lt.findViewById(R.id.ed_fb);
        EditText email_ed = (EditText) feedback_lt.findViewById(R.id.email_ed);
        TextView submit_btn = (TextView) feedback_lt.findViewById(R.id.submit_btn);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String get_sub = ed_sub.getText().toString();
                String get_fb = ed_fb.getText().toString();
                String get_mail = email_ed.getText().toString();
                if (get_fb.isEmpty() || get_sub.isEmpty() || get_mail.isEmpty()) {
                    Toast.makeText(StudentPortel_R4.this, "Kindly ,fill all details", Toast.LENGTH_SHORT).show();
                } else if (!get_mail.matches(emailPattern)) {
                    Toast.makeText(StudentPortel_R4.this, "Kindly Enter Valid Email Address", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.show();
                    send_request(get_sub, get_fb, get_mail,
                             ed_fb,ed_sub,email_ed);
                }

            }
        });


    }

    private void load_store_details() {
        recyclerView.setVisibility(View.VISIBLE);
        store_list = new ArrayList<StoreModel>();
        api_interface = RetrofitAPIClient.getClient("APIMobileStoreList/").create(CommonRetroAPI_Interface.class);
        Call<ArrayList<StoreModel>> call = api_interface.load_storedetails();
        call.enqueue(new Callback<ArrayList<StoreModel>>() {
            @Override
            public void onResponse(Call<ArrayList<StoreModel>> call, Response<ArrayList<StoreModel>> response) {

                ArrayList<StoreModel> arrayList = response.body();
                for (int i = 0; i < arrayList.size(); i++) {
                    StoreModel model = new StoreModel();
                    model.setBrand_name(arrayList.get(i).getBrand_name());
                    model.setBrand_description(arrayList.get(i).getBrand_description());
                    model.setBrand_logo(arrayList.get(i).getBrand_logo());
                    model.setId(arrayList.get(i).getId());
                    model.setBrand_url(arrayList.get(i).getBrand_url());
                    store_list.add(model);
                }
                recyclerView.setAdapter(new StoreListAdapter(store_list, StudentPortel_R4.this));
                progressDialog.cancel();
            }

            @Override
            public void onFailure(Call<ArrayList<StoreModel>> call, Throwable t) {

            }
        });
    }

    private void InitilaizeFields() {
        common_methods = new Common_Methods(StudentPortel_R4.this);
        request_lt = (ConstraintLayout) findViewById(R.id.request_lt);
        feedback_lt = (ConstraintLayout) findViewById(R.id.feedback_lt);
        title_txt = (TextView) findViewById(R.id.title_txt);
        get_id = getIntent().getStringExtra("item_id");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_store);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setVisibility(View.GONE);
        progressDialog = new ProgressDialog(StudentPortel_R4.this);
        progressDialog.setMessage("Please wait while loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        userLocalStore = new UserLocalStore(this);


        home_lt = (RelativeLayout) findViewById(R.id.lt_home);
        profile_lt = (RelativeLayout) findViewById(R.id.lt_profile);
        logout_lt = (RelativeLayout) findViewById(R.id.lt_logout);
        layoutInflater = getLayoutInflater();
        builder = new AlertDialog.Builder(StudentPortel_R4.this);
        common_methods.btm_nav(StudentPortel_R4.this, home_lt, profile_lt, logout_lt, builder, layoutInflater);


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

                common_methods.show_notification_alert(layoutInflater, builder,
                        notificationLists, api_interface_,
                        getIntent().getStringExtra("student_class"),
                        getIntent().getStringExtra("student_class_section"),
                        getIntent().getStringExtra("student_img"),
                        getIntent().getStringExtra("student_name"),
                        getIntent().getStringExtra("student_id"));

            }
        });

    }

    private void send_request(String subject, String message, String mail_id,
                              EditText ed_fb, EditText ed_sub, EditText email_ed) {
      //http://webstudent.npstj.com/api/APIMobileFeedback/ParentFeedback
        api_interface = RetrofitAPIClient.getClient("APIMobileFeedback/").create(CommonRetroAPI_Interface.class);
        api_interface.post_feedback_deatil(subject,message,
                getIntent().getStringExtra("student_class"),
                getIntent().getStringExtra("student_class_section"),
                getIntent().getStringExtra("student_name"),
                getIntent().getStringExtra("student_id"),
                mail_id).enqueue(new Callback<LoginCredentials>() {
            @Override
            public void onResponse(Call<LoginCredentials> call, Response<LoginCredentials> response) {
                String get_status   = response.body().getStatus();
                String get_msg = response.body().getMessage();
                if (get_status.equals("Success")){
                    ed_fb.setText(""); ed_sub.setText(""); email_ed.setText("");
                }
                progressDialog.cancel();
                Toast.makeText(StudentPortel_R4.this,get_msg, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<LoginCredentials> call, Throwable t) {

            }
        });


    }

    private void alert_spinner(ConstraintLayout request_lt) {
        RelativeLayout alert_bg = (RelativeLayout) request_lt.findViewById(R.id.alert_bg);
        EditText cat_ed = (EditText) request_lt.findViewById(R.id.cat_ed);
        EditText msg_ed = (EditText) request_lt.findViewById(R.id.msg_ed);
        EditText email_ed = (EditText) request_lt.findViewById(R.id.email_ed);
        cat_ed.setText("Please Select Category");

        cat_ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StudentPortel_R4.this);
                LayoutInflater layoutInflater = getLayoutInflater();

                View view = layoutInflater.inflate(R.layout.alert_enquiry_list, null);

                builder.setView(view);
                final AlertDialog show = builder.show();


                // category_r_btn.setChecked( spinnerno.equals("1") ? true : false );
                //  request_r_btn.setChecked( spinnerno.equals("2") ? true : false );

                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(StudentPortel_R4.this, RecyclerView.VERTICAL, false));
                load_cat_List(recyclerView, cat_ed, show);

            }
        });

        TextView submit_btn = (TextView) request_lt.findViewById(R.id.submit_btn);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String get_cat = cat_ed.getText().toString();
                String get_msg = msg_ed.getText().toString();
                //String get_mail = email_ed.getText().toString();
                if (get_cat.isEmpty() || get_msg.isEmpty()) {
                    Toast.makeText(StudentPortel_R4.this, "Kindly Fill all details", Toast.LENGTH_SHORT).show();
                } else {
                    if (CAT_ID == 0) {
                        Toast.makeText(StudentPortel_R4.this, "Kindly Select Category ..!", Toast.LENGTH_SHORT).show();
                    } else {
                        //send_request(get_cat, get_msg);
                         post_enquiry_detail(get_cat,get_msg,cat_ed,msg_ed);
                    }
                }
            }
        });


    }

    private void post_enquiry_detail(String get_cat, String get_msg,
                                     EditText cat_ed, EditText msg_ed) {
        api_interface = RetrofitAPIClient.getClient("APIMobileEnquiry/").create(CommonRetroAPI_Interface.class);
         String added_by =  getIntent().getStringExtra("student_id")+", "+
          getIntent().getStringExtra("student_class")+", "+
          getIntent().getStringExtra("student_class_section");

        api_interface.post_enquiry_deatil(String.valueOf(CAT_ID),get_msg,added_by).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(StudentPortel_R4.this,"Request sent successfully", Toast.LENGTH_SHORT).show();
                    cat_ed.setText("Please Select Category");CAT_ID = 0;
                    msg_ed.setText("");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void load_cat_List(RecyclerView recyclerView, EditText cat_ed,
                               AlertDialog show) {
        catModels = new ArrayList<CatModel>();
        api_interface = RetrofitAPIClient.getClient("APICategoryRequestList/").create(CommonRetroAPI_Interface.class);
        api_interface.get_CatList().enqueue(new Callback<ArrayList<CatModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CatModel>> call, Response<ArrayList<CatModel>> response) {
                //CatModel model_ = new CatModel();
                catModels.add(new CatModel("Please Select Category", "", " ", " ", 0));

                ArrayList<CatModel> get_list = response.body();
                for (int i = 0; i < get_list.size(); i++) {
                    CatModel model = new CatModel();
                    model.setId(get_list.get(i).getId());
                    model.setCategory_name(get_list.get(i).getCategory_name());
                    model.setAdded_by(get_list.get(i).getAdded_by());
                    model.setAdded_date(get_list.get(i).getAdded_date());
                    model.setPublish_status(get_list.get(i).getPublish_status());
                    catModels.add(model);
                }
                recyclerView.setAdapter(new EnquiryCatAdapter(
                        StudentPortel_R4.this, catModels, cat_ed, show));

            }

            @Override
            public void onFailure(Call<ArrayList<CatModel>> call, Throwable t) {

            }
        });
    }

    public void into_blue(TextView textView) {
        textView.setTextColor(getResources().getColor(R.color.splash_src_clr));
        textView.setHintTextColor(getResources().getColor(R.color.splash_src_clr));
    }


    class EnquiryCatAdapter extends RecyclerView.Adapter<EnquiryCatAdapter.ViewHolder> {

        StudentPortel_R4 studentPortel_r4;
        ArrayList<CatModel> arrayList;
        EditText editText;
        AlertDialog show;

        public EnquiryCatAdapter(StudentPortel_R4 studentPortel_r4,
                                 ArrayList<CatModel> arrayList, EditText editText,
                                 AlertDialog show) {
            this.editText = editText;
            this.studentPortel_r4 = studentPortel_r4;
            this.arrayList = arrayList;
            this.show = show;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alert_enquiry, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            CatModel app = arrayList.get(position);
            holder.category_r_btn.setChecked(CAT_ID == app.getId() ? true : false);

            holder.cat_txt.setText(app.getCategory_name());
            holder.category_r_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.category_r_btn.setChecked(true);
                    editText.setText(app.getCategory_name());
                    show.dismiss();
                    CAT_ID = app.getId();
                }
            });

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView cat_txt;
            RadioButton category_r_btn;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                cat_txt = (TextView) itemView.findViewById(R.id.cat_type);
                category_r_btn = (RadioButton) itemView.findViewById(R.id.category_r_btn);

            }
        }
    }
}
