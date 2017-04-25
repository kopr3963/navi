package com.example.mac.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.mac.navi.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Mac on 2016. 11. 18..
 */

public class NaviService extends Service {
    public final String TAG = "NaviService";
    public static int flag = 1;
    Timer timer = new Timer();

    MainActivity mainActivity = new MainActivity();
    @Override
    public void onCreate() {
        super.onCreate();
        mainActivity.setState(true);
        Log.i(TAG, "NaviService.onCreate()");
        Log.i(TAG, "timeout :  "+ mainActivity.getTimeout());
        
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand() ");
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(receiver, filter);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainActivity.service_State = false;
        timer.cancel();
        timer = null;
        timer = new Timer();
        Log.i(TAG, "onDestroy: ");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            final String action = intent.getAction();
            final Context con = context;

            Toast.makeText(context,"",Toast.LENGTH_SHORT).show();

            if (action.equals(intent.ACTION_POWER_DISCONNECTED)) {
                flag = 0;
                timer.schedule(new TimerTask() {
                    int count = 0;

                    @Override
                    public void run() {
                        count++;
                        Log.i(TAG, "run: count: " + count);
                        if (count == mainActivity.getTimeout()) {
                            try {
                                Process proc = Runtime.getRuntime()
                                        .exec(new String[]{"su", "-c", "reboot -p"});
                                proc.waitFor();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }, 0, 1000);

            } else if (action.equals(intent.ACTION_POWER_CONNECTED)) {
                flag = 1;
            }
            if (action.equals(intent.ACTION_POWER_CONNECTED)) {
                flag = 1;
                timer.cancel();
                timer = null;
                timer = new Timer();
            }
        }
    };
}



/*
* 배터리 연결중인 상태
*  ACTION_BATTERY_CHANGED
*
* 배터리 전원 분리 했을 때..
* ACTION_BATTERY_CHANGED  -->  ACTION_POWER_DISCONNECTED
*
*
* 전원 끊었을 때
* ACTION_BATTERY_CHANGED??
*
*
* 전원 연결 했을 때 ACTION_POWER_CONNECTED
*
* */