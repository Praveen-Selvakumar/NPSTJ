package com.example.npstj.loginframe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.npstj.DAO.CommonRetroAPI_Interface;
import com.example.npstj.ModelClass.RegisterRes_Model;
import com.example.npstj.R;
import com.example.npstj.RetrofitAPIClient;
import com.example.npstj.mainframe.Home_Page;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Register_Page extends AppCompatActivity {

    @BindView(R.id.login_btn_re) TextView login_btn_re;
    @BindView(R.id.title_txt) TextView title_txt;
    @BindView(R.id.null_alert) TextView null_alert;
    CommonRetroAPI_Interface api_interface;
    @BindView(R.id.ed_primary_number) EditText ed_primary_number;
    @BindView(R.id.alert_msg_lt) RelativeLayout msg_lt;
    @BindView(R.id.register_btn) TextView register_btn;
    @BindView(R.id.close_icon) ImageView close_icon;
    @BindView(R.id.forget_pwd_btn) TextView forget_pwd_btn;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register__page);
        ButterKnife.bind(this);
        InitilaizeFields();

        close_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg_lt.setVisibility(View.GONE);
            }
        });

        forget_pwd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ForgetPassword_Page.class);
                startActivity(intent);
            }
        });

        String first = "<font color='#000000'>Welcome to</font>";
        String next = "<font color='#0066ae'>NPS TJ</font>";
        title_txt.setText(Html.fromHtml(first +" "+next));

        login_btn_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login_Page.class));
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String get_primary_number =  ed_primary_number.getText().toString();
                if (get_primary_number.isEmpty()){
                    null_alert.setVisibility(View.VISIBLE);
                    msg_lt.setVisibility(View.VISIBLE);
                }else {
                    null_alert.setVisibility(View.GONE);
                    if (!validate_user(get_primary_number)){
                        msg_lt.setVisibility(View.VISIBLE);
                    }else {
                        // msg_lt.setVisibility(View.GONE);
                        progressDialog.show();
                        register(get_primary_number);
                    }
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),Login_Page.class));
    }



    private void InitilaizeFields() {
        api_interface = RetrofitAPIClient.getClient("APIMobileAccount/").create(CommonRetroAPI_Interface.class);
        progressDialog =  new ProgressDialog(Register_Page.this);
        progressDialog.setMessage("Please wait while loading...");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private boolean validate_user(String get_primary_number){
        if (get_primary_number.isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    private void register(String phone_number){
        Call<RegisterRes_Model> call = api_interface.register_user(phone_number);

        call.enqueue(new Callback<RegisterRes_Model>() {
            @Override
            public void onResponse(Call<RegisterRes_Model> call, Response<RegisterRes_Model> response) {
                progressDialog.cancel();
                String user_check =  response.body().getUser_check().toString();
                String msg =  response.body().getMessage().toString();

                if (user_check.equals("0")){
                    msg_lt.setVisibility(View.VISIBLE);
                    ed_primary_number.setText("");
                }else {
                    msg_lt.setVisibility(View.GONE);
                    Toast.makeText(Register_Page.this,msg, Toast.LENGTH_SHORT).show();
                    getUserData();
                }
            }

            @Override
            public void onFailure(Call<RegisterRes_Model> call, Throwable t) {

            }
        });

     }

    private void getUserData() {
    }
}