package com.bluewebspark.happyservice.sohel;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bluewebspark.happyservice.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Sohel Khan on 12-Feb-18.
 */

public class S {
    public static void I(Context cx, Class<?> startActivity, Bundle data) {
        Intent i = new Intent(cx, startActivity);
        if (data != null)
            i.putExtras(data);
        cx.startActivity(i);
//        ((Activity) cx).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    public static void I_clear(Context cx, Class<?> startActivity, Bundle data) {
        Intent i = new Intent(cx, startActivity);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if (data != null)
            i.putExtras(data);
        cx.startActivity(i);
//        ((Activity) cx).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    public static void E(String msg) {
        if (true)
            Log.e("Log.E By Sohel", msg);
    }

    public static void setImageByPicaso(Context cx, String image_url, ImageView holder, final ProgressBar progressbar) {
        Picasso.with(cx).load(image_url).placeholder(R.mipmap.ic_launcher).into(holder, new Callback() {
            @Override
            public void onSuccess() {
                if (progressbar != null)
                    progressbar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                if (progressbar != null)
                    progressbar.setVisibility(View.GONE);
            }
        });
    }

    public static String changeHourFormat(String value) {
        String newHour = "";
        try {
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
            Date _24StartHourDt = _24HourSDF.parse(value);
            S.E("Time start : " + _12HourSDF.format(_24StartHourDt));
            newHour = _12HourSDF.format(_24StartHourDt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newHour;
    }

    public static String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return imgString;
    }

    public static Dialog initProgressDialog(Context c) {
        ProgressDialog pd = new ProgressDialog(c);
        pd.setMessage("loading");
        pd.show();
        return pd;
    }

    public static void dialogForAvailableStatus(Context cx) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(cx);
        dialog.setCancelable(false);
        dialog.setTitle("Not Available");
        dialog.setMessage("");
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        final AlertDialog alert = dialog.create();
        alert.setIcon(android.R.drawable.ic_dialog_alert);
        alert.show();
    }


    public static void T(Context c, String msg) {
        Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
    }

    public static void share(Context c, String subject, String shareBody) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        c.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public static void shareFile(Context c, File file, String subject, String shareBody) {
        Intent intentShareFile = new Intent(Intent.ACTION_SEND);
        if (file.exists()) {
            intentShareFile.setType("application/image");
            intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + file));
            intentShareFile.putExtra(Intent.EXTRA_SUBJECT, subject);
            intentShareFile.putExtra(Intent.EXTRA_TEXT, shareBody);
            c.startActivity(Intent.createChooser(intentShareFile, "Share File"));
        }
    }

   /* public static void CT(Context cx, String message) {
        Toast toast = new Toast(cx);
        toast.setDuration(Toast.LENGTH_LONG);

        LayoutInflater inflater = (LayoutInflater) cx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.toast_custom_layout, null);
        TextView tostText = (TextView) view.findViewById(R.id.tostText);
        tostText.setText(message);
        toast.setView(view);
        toast.show();
    }*/

    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            /*List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }*/
            isInBackground = false;
        }

        return isInBackground;
    }

    public static String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String dateStr = DateFormat.format("dd-MM-yyyy HH:mm:ss", cal).toString();
        return dateStr;
    }

    public static int getDifferenceInSecond(String startTime, String stopTime) {
        String dateStart = startTime;
        String dateStop = stopTime;
        long diffSeconds = 0;
        long diffMinutes = 0;
        //HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);

            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

            diffSeconds = diff / 1000 % 60;
            diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            Log.e("Sohel", "diffDays : " + diffDays);
            Log.e("Sohel", "diffHours : " + diffHours);
            Log.e("Sohel", "diffMinutes : " + diffMinutes);
            Log.e("Sohel", "diffSeconds : " + diffSeconds);

            System.out.print(diffDays + " days, ");
            System.out.print(diffHours + " hours, ");
            System.out.print(diffMinutes + " minutes, ");
            System.out.print(diffSeconds + " seconds.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*if (diffMinutes > 0) {
            diffSeconds = diffSeconds + TimeUnit.MINUTES.toSeconds(diffMinutes);
        }*/
        return (int) diffSeconds;
    }
}
