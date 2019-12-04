package com.apps.ifaldyprayanda.moviecatalogue3.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.apps.ifaldyprayanda.moviecatalogue3.MainActivity;
import com.apps.ifaldyprayanda.moviecatalogue3.R;
import com.apps.ifaldyprayanda.moviecatalogue3.data.MovieTopData;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;


public class SettingReceiver extends BroadcastReceiver {

    private static final String API_KEY = "cfed702593ebdac39044981c42203c72";
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    private String message, text_title;

    private final static int NOTIFICATION_REQUEST_CODE = 200;
    private final static String GROUP_KEY_EMAILS = "group_key_emails";
    public SettingReceiver()
    {

    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        message = intent.getStringExtra(EXTRA_MESSAGE);

        if (message.equalsIgnoreCase("EXTRA_MESSAGE"))
        {
                getNewReleaseMovie(context);
        }else
        {
            text_title = "Movie Catalogue Apps";
            showReminderDaily(context, text_title, message);
        }

    }

    public boolean dateInvalid(String date, String format)
    {
        try{
            DateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
            dateFormat.setLenient(false);
            dateFormat.parse(date);
            return false;
        }catch (ParseException e)
        {
            return true;
        }
    }

    private void showReminder(Context context, String text_title, String message) {
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "channel_reminder";

        int reqCode = message.equalsIgnoreCase("EXTRA_MESSAGE") ? 102 : 101;

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri soundNotif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap iconNotif = BitmapFactory.decodeResource(context.getResources(), R.drawable.movie_catalogue);

        NotificationCompat.Builder builder;
        NotificationCompat.InboxStyle inboxStyle;

        String textNotif = context.getString(R.string.check_now);

        inboxStyle = new NotificationCompat.InboxStyle()
                .addLine(message)
                .addLine(textNotif)
                .setBigContentTitle(text_title)
                .setSummaryText("New Movie Coming up!");

        builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_movie_black_24dp)
                .setLargeIcon(iconNotif)
                .setContentTitle(text_title)
                .setContentText(message)
                .setStyle(inboxStyle)
                .setColor(ContextCompat.getColor(context, R.color.colorBlackTransparent))
                .setSound(soundNotif)
                .setGroup(GROUP_KEY_EMAILS)
                .setGroupSummary(true)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000});

//        use when android version above oreo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);

            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }


        Notification notification = builder.build();

        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(reqCode, notification);
        }
    }

    private void showReminderDaily(Context context, String text_title, String message) {
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "channel_reminder";

        int reqCode = message.equalsIgnoreCase("EXTRA_MESSAGE") ? 102 : 101;

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri soundNotif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap iconNotif = BitmapFactory.decodeResource(context.getResources(), R.drawable.movie_catalogue);

        NotificationCompat.Builder builder;

        builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_movie_black_24dp)
                .setLargeIcon(iconNotif)
                .setContentTitle(text_title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, R.color.colorBlackTransparent))
                .setSound(soundNotif)
                .setGroup(GROUP_KEY_EMAILS)
                .setGroupSummary(true)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000});

//        use when android version above oreo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);

            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }


        Notification notification = builder.build();

        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(reqCode, notification);
        }
    }

    public void setTimeDailyNotif(Context context, String time, String message)
    {
        String TIME_FORMAT = "HH:mm";

        // check time valid or not
        if (dateInvalid(time, TIME_FORMAT)) return;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, SettingReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);

        String[] arrayTime = time.split(":");

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arrayTime[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(arrayTime[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 101, intent, 0);

        if (alarmManager != null)
        {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public void setNewRelease(Context context, String time, String message)
    {
        String formatTime = "HH:mm";

        // check time valid or not
        if (dateInvalid(time, formatTime)) return;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, SettingReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);

        String[] arrayTime = time.split(":");

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arrayTime[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(arrayTime[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 102, intent, 0);

        if (alarmManager != null)
        {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public void cancelDailyNotif(Context context)
    {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, SettingReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 101, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null)
        {
            alarmManager.cancel(pendingIntent);
        }
    }

    public void cancelNewReleaseNotif(Context context)
    {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, SettingReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 102, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null)
        {
            alarmManager.cancel(pendingIntent);
        }
    }

    private void getNewReleaseMovie(final Context context)
    {
        AsyncHttpClient client = new AsyncHttpClient();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateCurrent = dateFormat.format(new Date());

        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&primary_release_date.gte="
                + dateCurrent + "&primary_release_date.lte=" + dateCurrent;


        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try{
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    text_title = "New Release Movie";
                    message = " ";

                    for (int i = 0; i < list.length(); i++)
                    {
                        JSONObject releasedMovie = list.getJSONObject(i);
                        message = message + releasedMovie.getString("title") + ", ";
                        showReminder(context, text_title, message);
                    }
                }catch (Exception e)
                {
                    Log.d("Error Movie Release", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("movieNewReleaseNoConn", error.getMessage());
            }
        });
    }

}
