package com.example.npstj.adapter;

import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.npstj.Common.Typewriter;
import com.example.npstj.ModelClass.StoreModel;
import com.example.npstj.R;
import com.example.npstj.mainframe.StudentPortel_R1;
import com.example.npstj.mainframe.StudentPortel_R4;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.ViewHolder> {

    ArrayList<StoreModel> arrayList;
    StudentPortel_R4 studentPortel_r4;
    ArrayList<String> ids = new ArrayList<String>();

    public StoreListAdapter(ArrayList<StoreModel> arrayList, StudentPortel_R4 studentPortel_r4) {
        this.arrayList = arrayList;
        this.studentPortel_r4 = studentPortel_r4;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view =  layoutInflater.inflate(R.layout.item_store,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StoreModel app = arrayList.get(position);

        String des = "<font color= #FFFFFF >"+app.getBrand_description()+"</font>" ;
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(app.getBrand_description(), Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(des);
        }

        if (!studentPortel_r4.userLocalStore.get_theme().isEmpty()){
            if (studentPortel_r4.userLocalStore.get_theme().equals("dark")){
                 holder.item_bg.setBackgroundColor(studentPortel_r4.getResources().getColor(R.color.lite_src_clr));
                holder.item_title.setTextColor(studentPortel_r4.getResources().getColor(R.color.black));
                holder.item_description.setBackgroundColor(studentPortel_r4.getResources().getColor(R.color.lite_src_clr));
                holder.show_btn.setTextColor(studentPortel_r4.getResources().getColor(R.color.black));
               }
        }

        

        holder.show_btn.setText("Show more");

        holder.item_title.setText(app.getBrand_name());


        holder.purchase_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url =  app.getBrand_url();
                studentPortel_r4.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });

        holder.show_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!ids.contains(app.getId())){
                    ids.add(app.getId());
                    holder.show_btn.setText("Show Less");
                    holder.item_title.setVisibility(View.VISIBLE);
                    holder.item_description.setVisibility(View.VISIBLE);
                   }else{
                    ids.remove(app.getId());
                    holder.show_btn.setText("Show More");
                    holder.item_description.setVisibility(View.GONE);
                }

            }
        });

        holder.item_description.setText(result);
        holder.item_description.setMovementMethod(LinkMovementMethod.getInstance());

        Picasso.with(studentPortel_r4.getApplicationContext())
                .load("http://webstudent.npstj.com"+app.getBrand_logo())
                .into(holder.store_logo);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView item_title,item_description,show_btn;
         ImageView store_logo;
        CardView purchase_btn;
        RelativeLayout item_bg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_title = (TextView)itemView.findViewById(R.id.item_name);
            item_description = (TextView)itemView.findViewById(R.id.item_description);
            show_btn = (TextView)itemView.findViewById(R.id.show_txt);

            purchase_btn = (CardView) itemView.findViewById(R.id.purchase_btn);
            store_logo = (ImageView) itemView.findViewById(R.id.store_logo);
            item_bg = (RelativeLayout)itemView.findViewById(R.id.item_bg);
        }


    }
}
