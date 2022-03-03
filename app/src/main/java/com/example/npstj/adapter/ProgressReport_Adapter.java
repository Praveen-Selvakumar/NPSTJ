package com.example.npstj.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.anychart.core.Base;
import com.example.npstj.BuildConfig;
import com.example.npstj.ModelClass.ProgressRepost_model;
import com.example.npstj.R;
import com.example.npstj.loginframe.Login_Page;
import com.example.npstj.mainframe.StudentPortel_R1;
import com.example.npstj.mainframe.StudentPortel_R2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.ArrayList;
import android.util.Base64;
import java.util.Random;

import in.gauriinfotech.commons.Commons;

import static android.provider.Settings.AUTHORITY;

public class ProgressReport_Adapter extends RecyclerView.Adapter<ProgressReport_Adapter.ViewHolder> {

    StudentPortel_R2 studentPortel_r2;
    ArrayList<ProgressRepost_model> arrayList;

    public ProgressReport_Adapter(StudentPortel_R2 studentPortel_r2, ArrayList<ProgressRepost_model> arrayList) {
        this.studentPortel_r2 = studentPortel_r2;
        this.arrayList = arrayList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view =  layoutInflater.inflate(R.layout.item_progress_report,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ProgressRepost_model app = arrayList.get(position);

        String[] separted = app.getGB_filname().split("-");
        String par_1 = separted[0];
        String par_2 = separted[1];


        String click_btn  = "<font color='#000000'>(Click here)</font>";



        holder.text_link.setText(Html.fromHtml(par_1+"\n"+" - "+par_2+" "+click_btn));



        if (!studentPortel_r2.userLocalStore.get_theme().isEmpty()){
            if (studentPortel_r2.userLocalStore.get_theme().equals("dark")){
                holder.text_link.setTextColor(studentPortel_r2.getResources().getColor(R.color.white));
                holder.rel_bg.setBackgroundColor(studentPortel_r2.getResources().getColor(R.color.two_bg));

                String click_btn_  = "<font color='#e8e8e8'>(Click here)</font>";
                holder.text_link.setText(Html.fromHtml(par_1+"\n"+" - "+par_2+" "+click_btn_));
                holder.grade_book_txt.setTextColor(studentPortel_r2.getResources().getColor(R.color.lite_src_clr));

            }
        }



        holder.text_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String file_name = app.getGB_filname();
                File dwldsPath = null;
                dwldsPath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)  + "/" + file_name);
              //dwldsPath  = new File(Environment.getDataDirectory().getAbsolutePath(), file_name) ;


               byte[] pdfAsBytes = Base64.decode(app.getGB_file(),Base64.DEFAULT);
                FileOutputStream os;
                try {
                    os = new FileOutputStream(dwldsPath, false);
                    os.write(pdfAsBytes);
                    os.flush();
                    os.close();
                     studentPortel_r2.show_PDF(file_name,dwldsPath);
                    load_notification(dwldsPath,file_name);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });


    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_link,click_here_btn,grade_book_txt;
        CardView cd;
        RelativeLayout rel_bg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_link = (TextView)itemView.findViewById(R.id.text_link);
            grade_book_txt = (TextView)itemView.findViewById(R.id.grade_book_txt);
            click_here_btn = (TextView)itemView.findViewById(R.id.click_here_btn);
            cd = (CardView) itemView.findViewById(R.id.cd);
            rel_bg = (RelativeLayout) itemView.findViewById(R.id.rel_bg);
        }
    }

    public static boolean externalMemoryAvailable(Context context) {
        File[] storages = ContextCompat.getExternalFilesDirs(context.getApplicationContext(), null);
        if (storages.length > 1 && storages[0] != null && storages[1] != null)
            return true;
        else
            return false;
    }


    public void load_notification(File dwldsPath,String file_name) {
        Toast.makeText(studentPortel_r2, "Downloaded", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(studentPortel_r2.getApplicationContext(), StudentPortel_R2.class);
        intent.putExtra("item_id","5");
        intent.putExtra("student_img",studentPortel_r2.getIntent().getStringExtra("student_img"));
        intent.putExtra("student_name",studentPortel_r2.getIntent().getStringExtra("student_name"));
        intent.putExtra("student_id",studentPortel_r2.getIntent().getStringExtra("student_id"));
        intent.putExtra("student_class",studentPortel_r2.getIntent().getStringExtra("student_class"));
        intent.putExtra("student_class_section",studentPortel_r2.getIntent().getStringExtra("student_class_section"));
        intent.putExtra("file_path",dwldsPath.toString());
        intent.putExtra("file_name",file_name);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(studentPortel_r2.getApplicationContext(), 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = studentPortel_r2.getString(R.string.default_notification_channel_id);


        NotificationManager notificationManager =
                (NotificationManager) studentPortel_r2.getSystemService(Context.NOTIFICATION_SERVICE);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(studentPortel_r2.getApplicationContext(), channelId)
                        .setColor(studentPortel_r2.getResources().getColor(android.R.color.white))
                        .setSmallIcon(R.drawable.npstj_logo)
                        .setContentTitle("Downloaded Successfully")
                        .setContentText(file_name)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String id = studentPortel_r2.getString(R.string.default_notification_channel_id);
            CharSequence name = studentPortel_r2.getString(R.string.app_name);
            String description = "test";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            mChannel.setDescription(description);
            mChannel.setShowBadge(true);
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());


    }



}
