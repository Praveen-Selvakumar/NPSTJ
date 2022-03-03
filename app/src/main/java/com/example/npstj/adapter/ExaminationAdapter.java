package com.example.npstj.adapter;

import android.os.Handler;
import android.os.Looper;
import android.text.Html;
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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.npstj.ModelClass.ExamList_Model;
import com.example.npstj.R;
import com.example.npstj.mainframe.StudentPortel_R1;
import com.example.npstj.mainframe.StudentPortel_R2;

import java.util.ArrayList;

public class ExaminationAdapter extends RecyclerView.Adapter<ExaminationAdapter.ViewHolder> {

    StudentPortel_R2 studentPortel_r2;
    ArrayList<ExamList_Model> arrayList;
     ArrayList<String> ids = new ArrayList<String>();

    public ExaminationAdapter(StudentPortel_R2 studentPortel_r2, ArrayList<ExamList_Model> arrayList) {
        this.studentPortel_r2 = studentPortel_r2;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view =  layoutInflater.inflate(R.layout.item_examination,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExamList_Model app  = arrayList.get(position);

        if (!studentPortel_r2.userLocalStore.get_theme().isEmpty()){
            if (studentPortel_r2.userLocalStore.get_theme().equals("dark")){
                holder.rel_bg.setBackgroundColor(studentPortel_r2.getResources().getColor(R.color.two_bg));
                holder.test_name.setTextColor(studentPortel_r2.getResources().getColor(R.color.white));
                holder.test_end_date.setTextColor(studentPortel_r2.getResources().getColor(R.color.white));
                holder.dot_line_btn.setColorFilter(ContextCompat.getColor(studentPortel_r2.getApplicationContext(), R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
            }
        }

        holder.test_start_date.setText("Start Date: "+app.getStarting_date());
        holder.test_end_date.setText("End Date: "+app.getEnding_date());
        holder.comments.setText(Html.fromHtml(app.getExam_command()));
        holder.test_name.setText(app.getExam_name());

        Animation rotate = AnimationUtils.loadAnimation(studentPortel_r2.getApplicationContext(), R.anim.rotation_proper);
        Animation rotate_oppo = AnimationUtils.loadAnimation(studentPortel_r2.getApplicationContext(), R.anim.rotate_opposite);

        holder.dot_line_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ids.contains(app.getId())){
                    ids.add(app.getId());
                    holder.dot_line_btn.startAnimation(rotate);
                    holder.comments.setVisibility(View.VISIBLE);holder.comments_txt.setVisibility(View.VISIBLE);
                    run_handler(app.getId(),holder.comments_txt,holder.comments,holder.dot_line_btn);
                }else{
                    ids.remove(app.getId());
                    holder.dot_line_btn.startAnimation(rotate_oppo);
                    holder.comments.setVisibility(View.GONE);holder.comments_txt.setVisibility(View.GONE);
                    run_handler(app.getId(),holder.comments_txt,holder.comments,holder.dot_line_btn);
                }


            }

        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView test_name, test_start_date, comments,comments_txt, test_end_date;
        ImageView dot_line_btn;
        RelativeLayout rel_bg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            test_name = (TextView)itemView.findViewById(R.id.test_name);
            test_start_date = (TextView)itemView.findViewById(R.id.test_start_date);
            comments = (TextView)itemView.findViewById(R.id.comments);
            comments_txt = (TextView)itemView.findViewById(R.id.comments_txt);
            test_end_date = (TextView)itemView.findViewById(R.id.test_end_date);
            dot_line_btn = (ImageView)itemView.findViewById(R.id.dot_line_btn);
            rel_bg = (RelativeLayout)itemView.findViewById(R.id.rel_bg);

        }
    }

    private void run_handler(String ID,TextView des_txt, TextView description, ImageView circle_tint_img){

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ids.contains(ID)){
                    //ids.remove(ID);
                    des_txt.setVisibility(View.VISIBLE); description.setVisibility(View.VISIBLE);
                    circle_tint_img.clearAnimation();
                }else {
                    //ids.add(ID);
                    des_txt.setVisibility(View.GONE); description.setVisibility(View.GONE);
                    circle_tint_img.clearAnimation();
                }

            }
        }, 400);
    }


}
