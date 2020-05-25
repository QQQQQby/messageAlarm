package com.message.alarm.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.message.alarm.R;
import com.message.alarm.adapter.AllMessageAdapter;
import com.message.alarm.bean.DaoMaster;
import com.message.alarm.bean.Message;
import com.message.alarm.dao.DaoManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MsgListActivity extends AppCompatActivity {
    private RecyclerView ry;
    private List<Message> list = new ArrayList<>();
    private AllMessageAdapter allMessageAdapter;
    LinearLayout titleBack;
    TextView titleCenter;
    //初始化函数
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除窗口标题
        setContentView(R.layout.activity_msglist);

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
        allMessageAdapter = new AllMessageAdapter(R.layout.showlist, list);
        ry.setAdapter(allMessageAdapter);





        //设置点击事件
        allMessageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Message item = (Message) adapter.getItem(position);
                Intent intent = new Intent(MsgListActivity.this, MsgDetailActivity.class);
                intent.putExtra("id",item.getMessageId());
                startActivity(intent);
            }
        });
        //从数据库获取数据
        getData();

    }

    private void getData() {
        list = DaoManager.getInstance().mDaoSession.queryBuilder(Message.class).list();

        allMessageAdapter.setNewData(list);

    }


}
