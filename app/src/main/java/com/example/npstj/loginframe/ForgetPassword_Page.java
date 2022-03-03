package com.example.npstj.loginframe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.npstj.DAO.CommonRetroAPI_Interface;
import com.example.npstj.ModelClass.LoginCredentials;
import com.example.npstj.R;
import com.example.npstj.RetrofitAPIClient;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPassword_Page extends AppCompatActivity {


    @BindView(R.id.re_register_btn) TextView re_register_btn;
    @BindView(R.id.login_btn_re) TextView login_btn_re;
    @BindView(R.id.reset_btn) TextView reset_btn;
    @BindView(R.id.ed_primary_number) EditText ed_primary_number;
    @BindView(R.id.null_alert) TextView null_alert;
    @BindView(R.id.alert_msg_lt) RelativeLayout msg_lt;
    TextView title_txt;
    CommonRetroAPI_Interface api_interface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password__page);
        InitializeFields();
        ButterKnife.bind(this);

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String get_primary_number = ed_primary_number.getText().toString();
                if (!get_primary_number.isEmpty()){

                    send_request(get_primary_number);

                }else {
                    msg_lt.setVisibility(View.VISIBLE);
                    null_alert.setVisibility(View.VISIBLE);
                }
            }
        });

        re_register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(getApplicationContext(),Register_Page.class);
                startActivity(intent);
            }
        });

        login_btn_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(getApplicationContext(),Login_Page.class);
                startActivity(intent);
            }
        });

    }

    private void send_request(String mobile_number ){

        api_interface.forgot_password(mobile_number).enqueue(new Callback<LoginCredentials>() {
            @Override
            public void onResponse(Call<LoginCredentials> call, Response<LoginCredentials> response) {

                if (response.isSuccessful()){
                   if (response != null){
                       Show_Toast(response.body().getMessage());
                       if (response.body().getStatus().equals("Success")){
                           msg_lt.setVisibility(View.GONE);
                           null_alert.setVisibility(View.GONE);
                           ed_primary_number.setText("");
                       }
                   }
                }else {
                   Show_Toast("Please , try again later");
               }

            }

            @Override
            public void onFailure(Call<LoginCredentials> call, Throwable t) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),Login_Page.class));
    }

    private void InitializeFields() {
         api_interface = RetrofitAPIClient.getClient("APIForgotPassword/").create(CommonRetroAPI_Interface.class);
        title_txt = (TextView)findViewById(R.id.title_txt);
        String first = "<font color='#000000'>Welcome to</font>";
        String next = "<font color='#0066ae'>NPS TJ</font>";
        title_txt.setText(Html.fromHtml(first+" "+next));
    }

    public  void Show_Toast(String txt){
        Toast.makeText(this,txt, Toast.LENGTH_SHORT).show();
    }
}