package com.example.npstj.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.example.npstj.ModelClass.StudentPorfolio_Model;
import com.example.npstj.R;
import com.example.npstj.mainframe.StudentPortel_R1;
import com.example.npstj.mainframe.StudentPortel_R2;
import com.example.npstj.mainframe.StudentPortel_R3;
import com.example.npstj.mainframe.StudentPortel_R4;
import com.example.npstj.mainframe.Student_Portal;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentGridAdpter extends BaseAdapter {

   ArrayList<StudentPorfolio_Model> arrayList;
   public static Student_Portal student_portal;

    // first define colors
    private final int[] backgroundColors = {
            R.color.dashboard_item_1,R.color.dashboard_item_2,R.color.dashboard_item_3,
            R.color.dashboard_item_4,R.color.dashboard_item_5,R.color.dashboard_item_6,
            R.color.dashboard_item_7,R.color.dashboard_item_8,R.color.dashboard_item_9,
            R.color.dashboard_item_10,R.color.dashboard_item_11,R.color.dashboard_item_12,
    };


    public StudentGridAdpter(ArrayList<StudentPorfolio_Model> arrayList,
                             Student_Portal student_portal) {
        this.arrayList = arrayList;
        this.student_portal = student_portal;
     }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // in onBindViewHolder
        int index = position % backgroundColors.length;
        int color = ContextCompat.getColor(student_portal.getApplicationContext(), backgroundColors[index]);

        if (view == null)
        {
            LayoutInflater inflater = (LayoutInflater)student_portal.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_student_portal, null);
        }
        TextView item_name = (TextView)view.findViewById(R.id.item_name);
        CircleImageView item_image = (CircleImageView)view.findViewById(R.id.item_img);
        CardView cd_item = (CardView) view.findViewById(R.id.cd_item);
        RelativeLayout cd_item_bg = (RelativeLayout) view.findViewById(R.id.cd_item_bg);

         StudentPorfolio_Model app  = arrayList.get(position);

        Picasso.with(student_portal.getApplicationContext())
                .load(app.getImage_id())
                  .into(item_image);

        cd_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String get_item_number = app.getItem_number().toString();
                if (get_item_number.equals("1") || get_item_number.equals("2") || get_item_number.equals("3")){
                    Intent intent = new Intent(student_portal.getApplicationContext(), StudentPortel_R1.class);
                    common_vol_intent(intent,app.getItem_number().toString());
                   }else if (get_item_number.equals("4") || get_item_number.equals("5") || get_item_number.equals("6")){
                    Intent intent1 = new Intent(student_portal.getApplicationContext(), StudentPortel_R2.class);
                    common_vol_intent(intent1,app.getItem_number().toString());
                  }else if (get_item_number.equals("7") || get_item_number.equals("8") || get_item_number.equals("9")){
                    Intent intent2 = new Intent(student_portal.getApplicationContext(), StudentPortel_R3.class);
                    common_vol_intent(intent2,app.getItem_number().toString());
                }else if (get_item_number.equals("10") || get_item_number.equals("11") || get_item_number.equals("12")){
                    Intent intent3 = new Intent(student_portal.getApplicationContext(), StudentPortel_R4.class);
                    common_vol_intent(intent3,app.getItem_number().toString());
                }


            }
        });

        item_name.setTextColor(color);
        item_name.setText(app.getItem_name());

        if (!student_portal.userLocalStore.get_theme().isEmpty()){
            if (student_portal.userLocalStore.get_theme().equals("dark")){
                cd_item_bg.setBackgroundColor(student_portal.getResources().getColor(R.color.black));
            }
        }

        return view;
    }

    public static void common_vol_intent(Intent intent, String get_item_number){
        intent.putExtra("item_id",get_item_number);
        //intent.putExtra("student_img",student_portal.getIntent().getStringExtra("student_img"));
        intent.putExtra("student_img",student_portal.getIntent().getStringExtra("student_img"));
        intent.putExtra("student_name",student_portal.getIntent().getStringExtra("student_name"));
        intent.putExtra("student_id",student_portal.getIntent().getStringExtra("student_id"));
        intent.putExtra("student_class",student_portal.getIntent().getStringExtra("student_class"));
        intent.putExtra("student_class_section",student_portal.getIntent().getStringExtra("student_class_section"));
         student_portal.startActivity(intent);
        //student_portal.load_themeby_intent(intent);
    }
}
