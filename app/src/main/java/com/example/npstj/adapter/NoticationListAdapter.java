package com.example.npstj.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.npstj.ModelClass.NotificationList;
import com.example.npstj.R;
import com.example.npstj.mainframe.StudentPortel_R1;
import com.example.npstj.mainframe.StudentPortel_R2;
import com.example.npstj.mainframe.StudentPortel_R3;

import java.util.ArrayList;

public class NoticationListAdapter extends RecyclerView.Adapter<NoticationListAdapter.ViewHolder> {

    Context context;
    ArrayList<NotificationList> arrayList;
    AlertDialog dialog;

    public NoticationListAdapter(AlertDialog dialog,Context context, ArrayList<NotificationList> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.dialog = dialog;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view =  layoutInflater.inflate(R.layout.item_notication,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationList app = arrayList.get(position);

        holder.not_text.setText(app.getNotification_message());


        holder.not_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (app.getContent_type().equals("MobileCircular")){
                   Intent intent = new Intent(context.getApplicationContext(),StudentPortel_R3.class);
                    start_intent(intent,"7",app.getImg_(),app.getName_(),app.getEnvi_id_(),
                             app.getClass_(),app.getSection_());
                 }else if (app.getContent_type().equals("MobileHomework")) {
                    Intent intent = new Intent(context.getApplicationContext(), StudentPortel_R1.class);
                    start_intent(intent,"3",app.getImg_(),app.getName_(),app.getEnvi_id_(),
                            app.getClass_(),app.getSection_());
                }else if (app.getContent_type().equals("MobileExamination")){
                   Intent intent = new Intent(context.getApplicationContext(), StudentPortel_R2.class);
                    start_intent(intent,"4",app.getImg_(),app.getName_(),app.getEnvi_id_(),
                            app.getClass_(),app.getSection_());
                }else if (app.getContent_type().equals("MobileTransport")){
                   Intent intent = new Intent(context.getApplicationContext(),StudentPortel_R2.class);
                    start_intent(intent,"6",app.getImg_(),app.getName_(),app.getEnvi_id_(),
                            app.getClass_(),app.getSection_());
                }

            }
        });


        holder.rel_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), StudentPortel_R3.class);
                intent.putExtra("item_id","7");
                intent.putExtra("student_img",app.getImg_());
                intent.putExtra("student_name",app.getName_());
                intent.putExtra("student_id",app.getEnvi_id_());
                intent.putExtra("student_class",app.getClass_());
                intent.putExtra("student_class_section",app.getSection_());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                dialog.dismiss();

             }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView not_text;
        RelativeLayout rel_lt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            not_text = (TextView)itemView.findViewById(R.id.not_text);
            rel_lt = (RelativeLayout)itemView.findViewById(R.id.rel_lt);
        }
    }
    private void start_intent(Intent intent,String get_item_number,String student_img,
                              String student_name,String student_id,String student_class,
                              String student_class_section
                              ){
        intent.putExtra("item_id",get_item_number);
        intent.putExtra("student_img",student_img);
        intent.putExtra("student_name",student_name);
        intent.putExtra("student_id",student_id);
        intent.putExtra("student_class",student_class);
        intent.putExtra("student_class_section",student_class_section);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
