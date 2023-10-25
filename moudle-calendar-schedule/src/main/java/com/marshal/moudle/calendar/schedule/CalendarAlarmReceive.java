package com.marshal.moudle.calendar.schedule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

//FLAG_ACTIVITY_NEW_TASK flag
//在广播中启动activity需要添加启动新任务栈标识
public class CalendarAlarmReceive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("TAG","CalendarAlarmReceive  收到信息");

        if(intent !=null && !TextUtils.isEmpty(intent.getAction()) && intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
            Log.d("TAG","收到 ACTION_TIME_TICK ");
            Toast.makeText(context,"闹钟响了!!!",Toast.LENGTH_SHORT).show();
        }



    }
}
