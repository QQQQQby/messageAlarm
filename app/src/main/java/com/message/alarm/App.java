package com.message.alarm;

import android.app.Application;
import android.util.Log;

import com.message.alarm.dao.DaoManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化数据库
        DaoManager.initContext(this);
        DaoManager.getInstance();

    }
}
