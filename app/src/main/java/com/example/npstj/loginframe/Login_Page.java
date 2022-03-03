package com.example.npstj.loginframe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.npstj.Common.UserLocalStore;
import com.example.npstj.DAO.CommonRetroAPI_Interface;
import com.example.npstj.ModelClass.LoginCredentials;
import com.example.npstj.R;
import com.example.npstj.RetrofitAPIClient;
import com.example.npstj.mainframe.Home_Page;
import com.example.npstj.mainframe.StudentPortel_R4;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Login_Page extends AppCompatActivity {

    @BindView(R.id.login_btn) TextView login_btn;
    @BindView(R.id.register_btn) TextView register_btn;
    @BindView(R.id.title_txt) TextView title_txt;
    @BindView(R.id.message_txt) TextView message_txt;
    @BindView(R.id.primary_number_error) TextView primary_number_error;
    @BindView(R.id.pwd_error) TextView pwd_error;
    @BindView(R.id.ed_primary_number)    EditText ed_primary_number;
    @BindView(R.id.ed_pwd) EditText ed_pwd;
    @BindView(R.id.close_icon) ImageView close_icon;
    CommonRetroAPI_Interface api_interface;
    @BindView(R.id.alert_msg_lt) RelativeLayout  msg_lt;
    UserLocalStore userLocalStore;
    @BindView(R.id.forget_pwd_btn) TextView forget_pwd_btn;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login__page);
        ButterKnife.bind(Login_Page.this);
        IntializeFileds();

        String first = "<font color='#000000'>Welcome to</font>";
        String next = "<font color='#0066ae'>NPS TJ</font>";
        title_txt.setText(Html.fromHtml(first +" "+next));

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register_Page.class));
            }
        });

        close_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title_txt.setVisibility(View.VISIBLE);
                msg_lt.setVisibility(View.GONE);
                primary_number_error.setVisibility(View.GONE);
                pwd_error.setVisibility(View.GONE);
            }
        });

        forget_pwd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ForgetPassword_Page.class);
                startActivity(intent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String get_pwd = ed_pwd.getText().toString();
                String get_primary_number = ed_primary_number.getText().toString();
                if (!validate_user(get_pwd,get_primary_number)){
                   msg_lt.setVisibility(View.VISIBLE);
                   primary_number_error.setVisibility(View.VISIBLE);
                   pwd_error.setVisibility(View.VISIBLE);
                   title_txt.setVisibility(View.GONE);
               }else {
                    progressDialog.show();
                 extra_validation(get_pwd,get_primary_number);
               }
             }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
         finishAffinity();
    }
    private void login(String num,String pwd){

        Call<LoginCredentials> call = api_interface.login_user(num,pwd);

        call.enqueue(new Callback<LoginCredentials>() {
            @Override
            public void onResponse(Call<LoginCredentials> call, Response<LoginCredentials> response) {
                LoginCredentials app = response.body();
                 progressDialog.cancel();
                System.out.println(app.getMessage());
                  if (!response.isSuccessful()){
                     msg_lt.setVisibility(View.VISIBLE);
                 }else if (app.getStatus().equals("Sucess")){
                     Toast.makeText(Login_Page.this,"Successfully, logged in ...!", Toast.LENGTH_SHORT).show();
                     msg_lt.setVisibility(View.GONE);
                     Intent intent = new Intent(Login_Page.this,Home_Page.class);
                     intent.putExtra("number",app.getParimary_number());
                     intent.putExtra("converted_number",app.getConverted_number());
                     userLocalStore.store_user_number(app.getParimary_number());
                     startActivity(intent);
                 }else if (app.getStatus().equals("Error")){
                     msg_lt.setVisibility(View.VISIBLE);
                 }

             }

            @Override
            public void onFailure(Call<LoginCredentials> call, Throwable t) {

            }
        });
    }

    private void IntializeFileds() {
        api_interface = RetrofitAPIClient.getClient("APIMobileAccount/").create(CommonRetroAPI_Interface.class);
        userLocalStore = new UserLocalStore(Login_Page.this);
        progressDialog =  new ProgressDialog(Login_Page.this);
        progressDialog.setMessage("Please wait while loading...");
        progressDialog.setCanceledOnTouchOutside(false);

    }

    private boolean validate_user(String pwd,String primary_number) {
        if (primary_number.isEmpty() && pwd.isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    private void extra_validation(String pwd,String primary_number){
        if (primary_number.isEmpty()){
            msg_lt.setVisibility(View.VISIBLE);
            primary_number_error.setVisibility(View.VISIBLE);
            pwd_error.setVisibility(View.GONE);
            progressDialog.cancel();
        }else if (pwd.isEmpty()){
            msg_lt.setVisibility(View.VISIBLE);
            primary_number_error.setVisibility(View.GONE);
            pwd_error.setVisibility(View.VISIBLE);
            progressDialog.cancel();
        }else {
            msg_lt.setVisibility(View.GONE);
            primary_number_error.setVisibility(View.GONE);
            pwd_error.setVisibility(View.GONE);
            login(primary_number,pwd);
         }
    }
}