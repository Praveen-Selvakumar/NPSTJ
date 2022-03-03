package com.example.npstj.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.npstj.ModelClass.StudentList_model;
import com.example.npstj.R;
import com.example.npstj.mainframe.Home_Page;
import com.example.npstj.mainframe.Student_Portal;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentList_HomePage_Adapter extends RecyclerView.Adapter<StudentList_HomePage_Adapter.ViewHolder> {

    Home_Page home_page;
    ArrayList<StudentList_model> arrayList;


    public StudentList_HomePage_Adapter(Home_Page home_page, ArrayList<StudentList_model> arrayList) {
        this.home_page = home_page;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view =  layoutInflater.inflate(R.layout.item_student_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         StudentList_model app = arrayList.get(position);
             holder.student_name.setText(app.getStudent_name());
             holder.student_id.setText(app.getStudent_id());
             if (app.getClass_gs().isEmpty()){
                 holder.student_class.setText("( Not Updated )");
             }else {
                 holder.student_class.setText("(" + app.getClass_gs() + ")");
             }

        String base64String = app.getPhoto_();
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

         holder.profile_image.setImageBitmap(decodedByte);
           //imageView.setImageBitmap(decodedByte);
             holder.student_lt.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Intent intent = new Intent(home_page.getApplicationContext(), Student_Portal.class);
                     call_dashboard_intent(intent,app);
                 }
             });


    }

    private void call_dashboard_intent(Intent intent, StudentList_model app) {
        intent.putExtra("student_name",app.getStudent_name());
        intent.putExtra("student_id",app.getStudent_id());
        intent.putExtra("student_class",app.getClass_gs());
        intent.putExtra("student_class_section",app.getClass_section());
        intent.putExtra("student_img",app.getPhoto_());
        String base64String =  app.getPhoto_();
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
       // intent.putExtra("bitmap",decodedByte);
        home_page.startActivity(intent);

        //home_page.load_themeby_intent(intent);
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView student_name,student_id,student_class;
        RelativeLayout student_lt;
        CircleImageView profile_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            student_name = (TextView)itemView.findViewById(R.id.student_name);
            student_id = (TextView)itemView.findViewById(R.id.student_id);
            student_class = (TextView)itemView.findViewById(R.id.student_class);
            student_lt = (RelativeLayout)itemView.findViewById(R.id.student_lt);
            profile_image = (CircleImageView)itemView.findViewById(R.id.profile_image);

        }


    }
}
