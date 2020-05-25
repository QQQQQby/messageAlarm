package com.message.alarm.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.message.alarm.R;
import com.message.alarm.adapter.NoticeAdapter;
import com.message.alarm.bean.DaoSession;
import com.message.alarm.bean.Message;
import com.message.alarm.bean.MessageDao;
import com.message.alarm.dao.DaoManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoticeActivity extends AppCompatActivity {
    private RecyclerView ry;
    private List<Message> list = new ArrayList<>();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    LinearLayout titleBack;
    TextView titleCenter;
    private NoticeAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        titleCenter =  findViewById(R.id.title_center);
        titleCenter.setText("所有收件信息");
        //返回键
        titleBack = findViewById(R.id.title_back);
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //初始化界面元素
        ry = findViewById(R.id.ry);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        ry.setLayoutManager(linearLayoutManager);
        adapter = new NoticeAdapter(R.layout.item_notice, list);
        ry.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //从数据库获取数据
        getData();
    }


    private void getData() {
        MessageDao messageDao = DaoManager.getInstance().mDaoSession.getMessageDao();
        list = messageDao.queryBuilder().where(MessageDao.Properties.NeedNotice.eq(true)).build().list();
        for (int i = 0; i < list.size(); i++) {
            String noticeTime = list.get(i).getNoticeTime();
            try {
                Date date = simpleDateFormat.parse(noticeTime);
                if (date.getTime() < System.currentTimeMillis()){
                    list.get(i).setNoticed(true);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        adapter.setNewData(list);
    }
}
