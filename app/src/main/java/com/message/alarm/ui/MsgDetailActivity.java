package com.message.alarm.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.message.alarm.R;
import com.message.alarm.bean.Message;
import com.message.alarm.bean.MessageDao;
import com.message.alarm.dao.DaoManager;

public class MsgDetailActivity extends AppCompatActivity {


    LinearLayout titleBack;
    TextView titleCenter;
    private TextView tvNum;
    private TextView tvTime;
    private TextView tvContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msgdetail);

        titleCenter =  findViewById(R.id.title_center);
        titleCenter.setText("短信详情");
        //返回键
        titleBack = findViewById(R.id.title_back);
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //发件人号码
        tvNum = findViewById(R.id.tv_num);
        //收件时间
        tvTime = findViewById(R.id.tv_time);
        //内容
        tvContent = findViewById(R.id.tv_content);

        long id = getIntent().getLongExtra("id",0L);
        MessageDao messageDao = DaoManager.getInstance().mDaoSession.getMessageDao();
        Message data = messageDao.queryBuilder().where(MessageDao.Properties.MessageId.eq(id)).build().unique();
        tvNum.setText(data.getAddress());
        tvTime.setText(data.getDate());
        tvContent.setText(data.getBody());

    }
}
