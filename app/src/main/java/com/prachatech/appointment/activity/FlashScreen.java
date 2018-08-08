package com.prachatech.appointment.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.prachatech.appointment.R;
import com.prachatech.appointment.utils.MessageToast;

public class FlashScreen extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.notifications_admin_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!isConnected()){
                    MessageToast.showToastMethod(FlashScreen.this,"No Internet");
                    return;
                }


                if(manager.isLoggedIn())
                    startActivity(new Intent(FlashScreen.this,HomeActivity.class));
                else
                    startActivity(new Intent(FlashScreen.this,SignInActivity.class));

                finish();
            }
        },2000);

    }

    @Override
    public void onClick(View v) {

    }
}
