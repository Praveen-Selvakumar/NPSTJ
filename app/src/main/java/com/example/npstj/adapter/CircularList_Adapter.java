package com.example.npstj.adapter;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.npstj.ModelClass.CircularList_Model;
import com.example.npstj.R;
import com.example.npstj.mainframe.StudentPortel_R3;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.provider.Settings.AUTHORITY;

public class CircularList_Adapter extends RecyclerView.Adapter<CircularList_Adapter.ViewHolder> {

    StudentPortel_R3 studentPortel_r3;
    ArrayList<CircularList_Model> arrayList;
    ArrayList<String> ids = new ArrayList<String>();

    public CircularList_Adapter(StudentPortel_R3 studentPortel_r3, ArrayList<CircularList_Model> arrayList) {
        this.studentPortel_r3 = studentPortel_r3;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view =  layoutInflater.inflate(R.layout.item_circular_lt,parent,false);
        return new ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CircularList_Model app = arrayList.get(position);

        if (!studentPortel_r3.userLocalStore.get_theme().isEmpty()){
            if (studentPortel_r3.userLocalStore.get_theme().equals("dark")){
                holder.rel_bg.setBackgroundColor(studentPortel_r3.getResources().getColor(R.color.two_bg));
             }
        }

        String input = "<font color= #FF0000 >"+app.getCircular_date()+"</font>";
        String title = "<font color= #000000 ><b>"+app.getTitle()+"</b></font>";

        holder.grade_book_txt.setText(Html.fromHtml(title+" ( "+input+" )"));

        if (!studentPortel_r3.userLocalStore.get_theme().isEmpty()){
            if (studentPortel_r3.userLocalStore.get_theme().equals("dark")){
                String input_ = "<font color= #FF0000 >"+app.getCircular_date()+"</font>";
                String title_ = "<font color= #FFFFFF ><b>"+app.getTitle()+"</b></font>";
                String one_br_ = "<font color= #FFFFFF ><b>"+"("+"</b></font>";
                String two_br = "<font color= #FFFFFF ><b>"+")"+"</b></font>";

                holder.grade_book_txt.setText(Html.fromHtml(title_+" "+one_br_+" "+input_+" "+two_br));
            }
        }

        Animation rotate = AnimationUtils.loadAnimation(studentPortel_r3.getApplicationContext(), R.anim.rotation_proper);
        Animation rotate_oppo = AnimationUtils.loadAnimation(studentPortel_r3.getApplicationContext(), R.anim.rotate_opposite);

        holder.click_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  String url = "http://webstudent.npstj.com"+app.getCircular_file().replace("~","");
                 studentPortel_r3.saveFileToPdf(url);
                   //studentPortel_r3.saveFileToPdf(url);
                 // studentPortel_r3.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(url)));
                 // Toast.makeText(studentPortel_r3,"PDF downloaded successfully", Toast.LENGTH_SHORT).show();


            }
        });


        holder.circle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ids.contains(app.getId())){
                    ids.add(app.getId());
                    holder.circle_tint_img.startAnimation(rotate);
                    holder.circle_tint_img.setImageDrawable(studentPortel_r3.getResources().getDrawable(R.drawable.minus_icon));
                    run_handler(app.getId(),app.getDescription(),holder.description_txt,holder.description,holder.circle_tint_img,holder.click_btn);
                    Log.d("des",app.getDescription());
                }else{
                    ids.remove(app.getId());
                    holder.circle_tint_img.startAnimation(rotate_oppo);
                    holder.circle_tint_img.setImageDrawable(studentPortel_r3.getResources().getDrawable(R.drawable.plus_icon));
                    run_handler(app.getId(),app.getDescription(),holder.description_txt,holder.description,holder.circle_tint_img,holder.click_btn);
                }

            }

        });




    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView grade_book_txt, description_txt, description, click_btn  ;
        CircleImageView circle_btn;
        ImageView circle_tint_img;
        RelativeLayout rel_bg;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            grade_book_txt = (TextView)itemView.findViewById(R.id.grade_book_txt);
            description_txt = (TextView)itemView.findViewById(R.id.description_txt);
            description = (TextView)itemView.findViewById(R.id.description);
            click_btn = (TextView)itemView.findViewById(R.id.click_btn);

            circle_btn  = (CircleImageView)itemView.findViewById(R.id.circle_btn);
            circle_tint_img  = (ImageView) itemView.findViewById(R.id.cirlce_img_tint);

            rel_bg = (RelativeLayout) itemView.findViewById(R.id.rel_bg);
        }
    }

    private void run_handler(String ID, String appDescription, TextView des_txt, TextView description, ImageView circle_tint_img, TextView click_btn){

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ids.contains(ID)){
                    //ids.remove(ID);
                    des_txt.setVisibility(View.VISIBLE); description.setVisibility(View.VISIBLE); click_btn.setVisibility(View.VISIBLE);
                    Spanned result;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                         // result = Html.fromHtml("<font color= #FF0000 >"+app.getCircular_date()+"</font>", Html.FROM_HTML_MODE_LEGACY);
                        result = Html.fromHtml(appDescription, Html.FROM_HTML_MODE_LEGACY);
                    } else {
                        result = Html.fromHtml(appDescription);
                    }
                    des_txt.setText(result);
                    des_txt.setMovementMethod(LinkMovementMethod.getInstance());
                    //des_txt.setText(Html.fromHtml(appDescription));
                    circle_tint_img.clearAnimation();
                }else {
                    //ids.add(ID);
                    Spanned result;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        // result = Html.fromHtml("<font color= #FF0000 >"+app.getCircular_date()+"</font>", Html.FROM_HTML_MODE_LEGACY);
                        result = Html.fromHtml(appDescription, Html.FROM_HTML_MODE_LEGACY);
                    } else {
                        result = Html.fromHtml(appDescription);
                    }
                    des_txt.setText(result);
                    des_txt.setMovementMethod(LinkMovementMethod.getInstance());
                    des_txt.setVisibility(View.GONE); description.setVisibility(View.GONE); click_btn.setVisibility(View.GONE);
                    circle_tint_img.clearAnimation();
                }
            }
        }, 400);
    }



}
