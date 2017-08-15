package com.cl.find;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by Administrator on 2017/3/12 0012.
 */

public class MyApplication extends Application {


    private android.content.IntentFilter filter;

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
        filter=new IntentFilter();
        filter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        filter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        filter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK);
        filter.addAction(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE);
       registerReceiver(BDRECEIVER,filter);

    }


    BroadcastReceiver BDRECEIVER=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)){
                Toast.makeText(context,"网络错误",Toast.LENGTH_LONG).show();
            }else if (intent.getAction().equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)){
                Toast.makeText(context,"key错误"+intent.getIntExtra(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE, 0)  ,Toast.LENGTH_LONG).show();
            }
        }
    };


    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterReceiver(BDRECEIVER);
    }
}
