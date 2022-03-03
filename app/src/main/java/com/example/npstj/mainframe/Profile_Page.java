package com.example.npstj.mainframe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.npstj.Common.Common_Methods;
import com.example.npstj.Common.UserLocalStore;
import com.example.npstj.DAO.CommonRetroAPI_Interface;
import com.example.npstj.ModelClass.ParentDetailModel;
import com.example.npstj.ModelClass.ParentProfile_Model;
import com.example.npstj.ModelClass.Student_Model;
import com.example.npstj.R;
import com.example.npstj.RetrofitAPIClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile_Page extends AppCompatActivity   {

    RelativeLayout home_lt, profile_lt, logout_lt,hus_content,wife_content,add_content;
     CardView lt_husband, lt_wife, lt_address ;
    CommonRetroAPI_Interface apiInterface;
    TextView husband_job,husband_phone,husband_address,
    wife_job,wife_phone,wife_address,address_txt;

    TextView husband_name,wife_name,address_user_name;
    ImageView address_header,job_icon_wife,phone_icon_wife,email_icon_wife,
    job_icon_hus,phone_icon_hus,email_icon_hus,icon_address;

    @BindView(R.id.banner) ImageView banner;
    @BindView(R.id.null_alert) TextView null_alert;
    @BindView(R.id.scroll_view) ScrollView scrollView;
    UserLocalStore userLocalStore;
    RelativeLayout address_content_lt;
    Common_Methods common_methods;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile__page);
        ButterKnife.bind(Profile_Page.this);
        InitializeFields();


        Picasso.with(Profile_Page.this)
                .load(R.drawable.bannerweb)
                .fit()
                .into(banner);

        home_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getApplicationContext(), Home_Page.class);
                intent.putExtra("number",getIntent().getStringExtra("number"));
                intent.putExtra("converted_number",getIntent().getStringExtra("converted_number"));
                startActivity(intent);
            }
        });

        profile_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout_lt.setBackgroundColor(getResources().getColor(R.color.white));
                profile_lt.setBackgroundColor(getResources().getColor(R.color.btm_bg_clr));

            }
        });

        logout_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile_lt.setBackgroundColor(getResources().getColor(R.color.white));
                logout_lt.setBackgroundColor(getResources().getColor(R.color.btm_bg_clr));

                AlertDialog.Builder builder = new AlertDialog.Builder(Profile_Page.this);
                LayoutInflater layoutInflater = getLayoutInflater();
                new Common_Methods(Profile_Page.this).showOfferAlert(layoutInflater,builder);

            }
        });

        if (!userLocalStore.get_theme().isEmpty()){
            if (userLocalStore.get_theme().equals("dark")){
                validate_theme();
            }
        }

        String posting_number = "";
        String get_number = "";
        String implemented_first = "",implemented_second = "";
        String  number =  userLocalStore.get_number();
        posting_number = getIntent().getStringExtra("converted_number");

     /*   String get_n[]  =  number.split("");
        posting_number = get_n[0]+get_n[1]+get_n[2]+"%2F"+get_n[3]
                +get_n[4] +get_n[5]+"-"+get_n[6] +get_n[7] +get_n[8]+get_n[9];*/

       /* for(int i = 0;i <  number.length(); i++){
            if (number.charAt(i) == number.charAt(2) && implemented_second.isEmpty()){
                implemented_second = "done";
                get_number = String.valueOf(number.charAt(i)+"%2F");
            }else if (number.charAt(i) == number.charAt(5) && implemented_first.isEmpty()){
                implemented_first = "done";
                get_number = String.valueOf(number.charAt(i)+"-");
            }else {
                get_number = String.valueOf(number.charAt(i));
            }
            posting_number = posting_number + get_number;
        }*/

        if (userLocalStore.get_parent_details().getAd_1().isEmpty())
        {
            get_parent_data(posting_number);
            common_methods.start_progress_dialog(progressDialog);
        }else {
            load_parent_data_from_local();
        }

    }

    private void validate_theme() {
        address_content_lt.setBackground(getResources().getDrawable(R.drawable.profile_content_lt_dark));
        wife_content.setBackground(getResources().getDrawable(R.drawable.profile_content_lt_dark));
        hus_content.setBackground(getResources().getDrawable(R.drawable.profile_content_lt_dark));

       //  job_icon_hus.setImageTintList(getResources().getColor(R.col));
        husband_job.setTextColor(getResources().getColor(R.color.white));
        husband_phone.setTextColor(getResources().getColor(R.color.white));
        husband_address.setTextColor(getResources().getColor(R.color.white));

        wife_job.setTextColor(getResources().getColor(R.color.white));
        wife_phone.setTextColor(getResources().getColor(R.color.white));
        wife_address.setTextColor(getResources().getColor(R.color.white));


        address_txt.setTextColor(getResources().getColor(R.color.white));
    }


    private void InitializeFields() {
        home_lt = (RelativeLayout)findViewById(R.id.lt_home);
        profile_lt = (RelativeLayout)findViewById(R.id.lt_profile);
        logout_lt = (RelativeLayout)findViewById(R.id.lt_logout);
        common_methods = new Common_Methods(Profile_Page.this);
        progressDialog = new ProgressDialog(Profile_Page.this);
        progressDialog.setMessage("Please wait while loading...");

        lt_wife = (CardView)findViewById(R.id.lt_wife);
        lt_husband = (CardView)findViewById(R.id.lt_husband);
        lt_address = (CardView)findViewById(R.id.lt_address);
        userLocalStore = new UserLocalStore(Profile_Page.this);

        profile_lt.setBackgroundColor(getResources().getColor(R.color.btm_bg_clr));

        RelativeLayout parent_data_lt = (RelativeLayout)lt_address.findViewById(R.id.other_details_content_lt);
        address_content_lt = (RelativeLayout)lt_address.findViewById(R.id.address_content_lt);

        parent_data_lt.setVisibility(View.GONE);
        address_content_lt.setVisibility(View.VISIBLE);
        scrollView =  (ScrollView)findViewById(R.id.scroll_view);

        //husband
         husband_job  = (TextView)lt_husband.findViewById(R.id.job_txt);
         husband_phone = (TextView)lt_husband.findViewById(R.id.phone_txt);
         husband_address  = (TextView)lt_husband.findViewById(R.id.mail_id_txt);
         husband_name = (TextView)lt_husband.findViewById(R.id.user_name);
         hus_content = (RelativeLayout)lt_husband.findViewById(R.id.content);

         job_icon_hus  = (ImageView)lt_husband.findViewById(R.id.icon_1);
         email_icon_hus  = (ImageView)lt_husband.findViewById(R.id.icon_2);
         phone_icon_hus  = (ImageView)lt_husband.findViewById(R.id.icon_3);

         //wife
         wife_job  = (TextView)lt_wife.findViewById(R.id.job_txt);
         wife_phone = (TextView)lt_wife.findViewById(R.id.phone_txt);
         wife_address  = (TextView)lt_wife.findViewById(R.id.mail_id_txt);
         wife_name = (TextView)lt_wife.findViewById(R.id.user_name);
         wife_content = (RelativeLayout)lt_wife.findViewById(R.id.content);

        job_icon_wife  = (ImageView)lt_wife.findViewById(R.id.icon_1);
        email_icon_wife  = (ImageView)lt_wife.findViewById(R.id.icon_2);
        phone_icon_wife  = (ImageView)lt_wife.findViewById(R.id.icon_3);


        //address_lt
        address_header = (ImageView)lt_address.findViewById(R.id.header); address_header.setVisibility(View.GONE);
        address_user_name = (TextView)lt_address.findViewById(R.id.user_name);  address_user_name.setVisibility(View.GONE);
        address_txt = (TextView)lt_address.findViewById(R.id.address_txt);
        icon_address = (ImageView)lt_address.findViewById(R.id.icon_address);
        add_content = (RelativeLayout)lt_address.findViewById(R.id.address_content_lt);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),Home_Page.class));
    }


    private void get_parent_data(String posting_number){
        apiInterface = RetrofitAPIClient.getClient_("TJ_STUDENT_DETAILS.v1/?phone="+posting_number).create(CommonRetroAPI_Interface.class);
        Call<ParentProfile_Model> call = apiInterface.getAll_parent_Data();
        call.enqueue(new Callback<ParentProfile_Model>() {
            @Override
            public void onResponse(Call<ParentProfile_Model> call, Response<ParentProfile_Model> response) {
                   common_methods.cancel_progress_dialog(progressDialog);

                  if(response.body().getTJ_ERROR_TV_DOC() == null) {
                    ArrayList<ParentProfile_Model.TJ_STUDENT_DETAILS_Model.TJ_PARENT_DETAILS> arrayList = response.body().getTJ_STUDENT_DETAILS().getArrayList();
                    ParentProfile_Model.TJ_STUDENT_DETAILS_Model.TJ_PARENT_DETAILS husband_details = arrayList.get(0);
                    ParentProfile_Model.TJ_STUDENT_DETAILS_Model.TJ_PARENT_DETAILS wife_details = arrayList.get(1);

                    userLocalStore.store_parent_detail(
                    new ParentDetailModel(response.body().getTJ_STUDENT_DETAILS().getADDRESS1(),
                            response.body().getTJ_STUDENT_DETAILS().getADDRESS2(),
                            response.body().getTJ_STUDENT_DETAILS().getCity(),
                            response.body().getTJ_STUDENT_DETAILS().getCountry(),
                            husband_details.getName(),
                            husband_details.getJob(),"",
                            husband_details.getPhone(),
                            husband_details.getEmail_id(),"",
                            wife_details.getName(),
                            wife_details.getJob(),"",
                            wife_details.getPhone(),
                            wife_details.getEmail_id(),""
                            ));
                    load_parent_data_from_local();
                    /*
                    //husband
                    husband_job.setText(husband_details.getJob());
                    husband_phone.setText(husband_details.getPhone());
                    husband_address.setText(husband_details.getEmail_id());
                    husband_name.setText(husband_details.getName());

                    wife_job.setText(wife_details.getJob());
                    wife_phone.setText(wife_details.getPhone());
                    wife_address.setText(wife_details.getEmail_id());
                    wife_name.setText(wife_details.getName());

                    address_txt.setText(response.body().getTJ_STUDENT_DETAILS().getADDRESS1() + "\n" +
                            response.body().getTJ_STUDENT_DETAILS().getADDRESS2() + "\n" +
                            response.body().getTJ_STUDENT_DETAILS().getCity() + "\n" +
                            response.body().getTJ_STUDENT_DETAILS().getCountry());*/
                }else {
                    scrollView.setVisibility(View.GONE);
                    null_alert.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ParentProfile_Model> call, Throwable t) {

            }
        });
    }


    private void load_parent_data_from_local() {
        scrollView.setVisibility(View.VISIBLE);
        null_alert.setVisibility(View.GONE);
        //husband
        husband_job.setText(userLocalStore.get_parent_details().getHus_job());
        husband_phone.setText(userLocalStore.get_parent_details().getHus_phone());
        husband_address.setText(userLocalStore.get_parent_details().getHus_email_id());
        husband_name.setText(userLocalStore.get_parent_details().getHus_name());

        wife_job.setText(userLocalStore.get_parent_details().getW_job());
        wife_phone.setText(userLocalStore.get_parent_details().getW_phone());
        wife_address.setText(userLocalStore.get_parent_details().getW_email_id());
        wife_name.setText(userLocalStore.get_parent_details().getW_name());

        address_txt.setText(userLocalStore.get_parent_details().getAd_1()+ "\n" +
                userLocalStore.get_parent_details().getAd_2() + "\n" +
                userLocalStore.get_parent_details().getCity() + "\n" +
                userLocalStore.get_parent_details().getCountry());
    }


}