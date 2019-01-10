package com.example.adam.mini_projekt_5;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.RemoteViews;

import java.text.ParseException;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    private static String WEB_INTENT = "webIntent";
    private static String IMAGE_CHANGE_INTENT = "changeImageIntent";
    private static String LIST_INTENT_CONST = "com.example.adam.mini.projekt_1.ShopListActivity";
    private static String MUSIC_INTENT_START = "musicIntent";
    private static String MUSIC_INTENT_STOP = "musicStop";
    private static String MUSIC_INTENT_NEXT = "musicNext";
    private static String PLAY = "PLAY";
    private static String STOP = "STOP";
    private static String NEXT = "NEXT";
    private static Boolean PIESEL_FLAG = false;


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

        views.setOnClickPendingIntent(R.id.web_button, getPendingIntent(context, WEB_INTENT));
        views.setOnClickPendingIntent(R.id.change_img_button, changeImage(context));
        views.setOnClickPendingIntent(R.id.button_shop_list, shopList(context));
        views.setOnClickPendingIntent(R.id.start_button, startMusic(context));
        views.setOnClickPendingIntent(R.id.stop_button, stopMusic(context));
        views.setOnClickPendingIntent(R.id.next_button, nextSong(context));


        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

        if (intent.getAction().equals(IMAGE_CHANGE_INTENT)) {
            if (!PIESEL_FLAG){
            views.setImageViewResource(R.id.imageView, R.drawable.dskho);
            PIESEL_FLAG = true;
            } else{
                views.setImageViewResource(R.id.imageView, R.drawable.doggo1);
                PIESEL_FLAG = false;
            }

            appWidgetManager.updateAppWidget(new ComponentName(context, NewAppWidget.class), views);
        }
        else if(intent.getAction().equals(MUSIC_INTENT_START)){
            Intent startIntent = new Intent(context, MusicService.class);
            startIntent.setAction(PLAY);
            context.startService(startIntent);
        }
        else if(intent.getAction().equals(MUSIC_INTENT_STOP)){
            Intent stopIntent = new Intent(context, MusicService.class);
            stopIntent.setAction(STOP);
            context.startService(stopIntent);
        }
        else if(intent.getAction().equals(MUSIC_INTENT_NEXT)){
            Intent nextIntent = new Intent(context, MusicService.class);
            nextIntent.setAction(NEXT);
            context.startService(nextIntent);
        }

        else if (intent.getAction().equals(LIST_INTENT_CONST)) {
            Intent webIntent = new Intent("com.example.adam.mini_projekt_1.ShopListActivity");
            webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            requstCode = 101;
            context.startActivity(webIntent);
        }
    }

    public static PendingIntent getPendingIntent(Context context, String flag) {
        int requstCode = 0;
        Intent intent;
        if (flag.equals(WEB_INTENT)) {
            intent = new Intent(context, WebViewActivity.class);
            intent.setAction(WEB_INTENT);
        }else{
            intent = new Intent();
        }

        return PendingIntent.getActivity(context, requstCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    public static PendingIntent changeImage(Context context){
        Intent intent = new Intent(context, NewAppWidget.class);
        intent.setAction(IMAGE_CHANGE_INTENT);
        return PendingIntent.getBroadcast(context, 1001, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static PendingIntent startMusic(Context context){
        Intent intent = new Intent(context, NewAppWidget.class);
        intent.setAction(MUSIC_INTENT_START);
        return PendingIntent.getBroadcast(context, 1012, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static PendingIntent stopMusic(Context context){
        Intent intent = new Intent(context, NewAppWidget.class);
        intent.setAction(MUSIC_INTENT_STOP);
        return PendingIntent.getBroadcast(context, 1013, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static PendingIntent shopList(Context context){
        Intent intent = new Intent(context, NewAppWidget.class);
        intent.setAction(LIST_INTENT_CONST);
        return PendingIntent.getBroadcast(context, 1011, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static PendingIntent nextSong(Context context){
        Intent intent = new Intent(context, NewAppWidget.class);
        intent.setAction(MUSIC_INTENT_NEXT);
        return PendingIntent.getBroadcast(context, 1012, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

}