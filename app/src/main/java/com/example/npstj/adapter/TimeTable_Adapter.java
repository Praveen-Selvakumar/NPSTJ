package com.example.npstj.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.npstj.ModelClass.TimeTable_Model;
import com.example.npstj.R;
import com.example.npstj.mainframe.StudentPortel_R1;

import java.util.ArrayList;

public class TimeTable_Adapter extends RecyclerView.Adapter<TimeTable_Adapter.ViewHolder> {

    public StudentPortel_R1 studentPortel_r1;
    public ArrayList<TimeTable_Model> arrayList;

    public TimeTable_Adapter(StudentPortel_R1 studentPortel_r1, ArrayList<TimeTable_Model> arrayList) {
        this.studentPortel_r1 = studentPortel_r1;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view =  layoutInflater.inflate(R.layout.item_time_table,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       TimeTable_Model app = arrayList.get(position);

        holder.sub_txt.setText(app.getSubject_());

       String date= app.getTimestart_();
       String[] dates = date.split("-");

       holder.time_txt.setText(dates[0]+"\n\n"+dates[1]);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView sub_txt,time_txt;
        ImageView icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sub_txt = (TextView)itemView.findViewById(R.id.sub_txt);
            time_txt = (TextView)itemView.findViewById(R.id.time_txt);
            icon = (ImageView)itemView.findViewById(R.id.icon);

        }

    }
}
