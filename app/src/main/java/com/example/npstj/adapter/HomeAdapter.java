package com.example.npstj.adapter;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.npstj.ModelClass.HomeWork_Model;
import com.example.npstj.R;
import com.example.npstj.mainframe.StudentPortel_R1;
import com.example.npstj.test.Test;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    StudentPortel_R1 studentPortel_r1;
    ArrayList<HomeWork_Model> arrayList;
    Test test;
     ArrayList<String> ids = new ArrayList<String>();


    public HomeAdapter(StudentPortel_R1 studentPortel_r1, ArrayList<HomeWork_Model> arrayList) {
        this.studentPortel_r1 = studentPortel_r1;
        this.arrayList = arrayList;
    }

    public HomeAdapter(ArrayList<HomeWork_Model> arrayList, Test test) {
        this.arrayList = arrayList;
        this.test = test;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view =  layoutInflater.inflate(R.layout.item_home_work,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomeWork_Model app = arrayList.get(position);

        if (!studentPortel_r1.userLocalStore.get_theme().isEmpty()){
            if (studentPortel_r1.userLocalStore.get_theme().equals("dark")){
                holder.rel_bg.setBackgroundColor(studentPortel_r1.getResources().getColor(R.color.dull_black));
                holder.rel_bg_two.setBackgroundColor(studentPortel_r1.getResources().getColor(R.color.dull_black));
                holder.class_section_name.setTextColor(studentPortel_r1.getResources().getColor(R.color.white));
            }
        }

        holder.class_section_name.setText(app.getClass_id()+"  "+app.getSection_id());
        holder.issued_date.setText(app.getAssignment_given_date());
        holder.subject_name.setText(app.getSubject_name());
        holder.assignment_title.setText(app.getAssignment_title());
        holder.end_date.setText(app.getDeadline_date());
        holder.teacher_name.setText(app.getTeacher_name());


        Animation rotate = AnimationUtils.loadAnimation(studentPortel_r1.getApplicationContext(), R.anim.rotation_proper);
        Animation rotate_oppo = AnimationUtils.loadAnimation(studentPortel_r1.getApplicationContext(), R.anim.rotate_opposite);

        holder.circle_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ids.contains(app.getId())){
                    ids.add(app.getId());
                    holder.circle_tint_img.startAnimation(rotate);
                    holder.circle_tint_img.setImageDrawable(studentPortel_r1.getResources().getDrawable(R.drawable.minus_icon));
                    run_handler(app.getId(),holder.des_txt,holder.description,holder.circle_tint_img);
                }else{
                    ids.remove(app.getId());
                    holder.circle_tint_img.startAnimation(rotate_oppo);
                    holder.circle_tint_img.setImageDrawable(studentPortel_r1.getResources().getDrawable(R.drawable.plus_icon));
                    run_handler(app.getId(),holder.des_txt,holder.description,holder.circle_tint_img);
                }


              }

        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView teacher_name,issued_date,end_date,class_section_name;
        TextView des_txt,description,subject_name,assignment_title;
        RelativeLayout rel_bg, rel_bg_two;
        CircleImageView circle_img;
        ImageView circle_tint_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teacher_name = (TextView)itemView.findViewById(R.id.teacher_name);
            issued_date = (TextView)itemView.findViewById(R.id.issued_date);
            end_date = (TextView)itemView.findViewById(R.id.end_date);
            class_section_name = (TextView)itemView.findViewById(R.id.class_section_name);
            des_txt = (TextView)itemView.findViewById(R.id.des_txt);
            description = (TextView)itemView.findViewById(R.id.description);

            subject_name = (TextView)itemView.findViewById(R.id.subject_name);
            assignment_title = (TextView)itemView.findViewById(R.id.assignment_title);

            circle_img  = (CircleImageView)itemView.findViewById(R.id.circle_bg);
            circle_tint_img  = (ImageView) itemView.findViewById(R.id.circle_tint_img);

            rel_bg = (RelativeLayout)itemView.findViewById(R.id.rel_bg);
            rel_bg_two = (RelativeLayout)itemView.findViewById(R.id.rel_bg_two);
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
