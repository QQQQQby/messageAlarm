package com.message.alarm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.message.alarm.bean.Message;
import com.message.alarm.clock.AlarmManagerUtil;
import com.message.alarm.dao.DaoManager;
import com.message.alarm.ui.MsgListActivity;
import com.message.alarm.ui.NoticeActivity;
import com.message.alarm.ui.SplashActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private TextView receive;
    private TextView notice;
    private List<Message> list = new ArrayList<>();
    final String SMS_URI_ALL = "content://sms/";
    private String reg;
    private ImageView ivMsg;
    private ImageView ivNotice;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean showed = getIntent().getBooleanExtra("showed", false);
        if (!showed) {
            Intent intent = new Intent(MainActivity.this, SplashActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.activity_main);
        //所有短信
        receive = findViewById(R.id.tv_receive);
        //提醒
        notice = findViewById(R.id.tv_notice);
        //短信图片
        ivMsg = findViewById(R.id.iv_allmsg);
        //提醒图片
        ivNotice = findViewById(R.id.iv_notice);

        ivMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查看所有短信页面
                Intent intent = new Intent(MainActivity.this, MsgListActivity.class);
                startActivity(intent);
            }
        });

        ivNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转提醒页面
                Intent intent = new Intent(MainActivity.this, NoticeActivity.class);
                startActivity(intent);
            }
        });

        //设置点击事件
        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查看所有短信页面
                Intent intent = new Intent(MainActivity.this, MsgListActivity.class);
                startActivity(intent);
            }
        });

        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转提醒页面
                Intent intent = new Intent(MainActivity.this, NoticeActivity.class);
                startActivity(intent);
            }
        });
        //正则匹配yyyy-mm-dd hh:mm时间的字符  2019-03-06 15:27
        reg = "[1-2][0-9][0-9][0-9]-([1][0-2]|0?[1-9])-([12][0-9]|3[01]|0?[1-9]) ([01][0-9]|[2][0-3]):[0-5][0-9]";
        //初始化数据
        initData();

    }

    private void initData() {
        //读取数据
        getSmsfromPhone();

        //查询数据库
        List<Message> messages = DaoManager.getInstance().mDaoSession.getMessageDao().queryBuilder().list();

        //存储进数据库
        if (this.list != null && this.list.size() > 0) {
            for (int i = 0; i < this.list.size(); i++) {
                boolean contains = false;
                for (int j = 0; j < messages.size(); j++) {
                    if (list.get(i).getMessageId() == messages.get(j).getMessageId()) {
                        //判断数据库中是否已经有存储,有存储则不进行添加存储
                        contains = true;
                        break;
                    }
                }
                //属于新短信时,添加进数据库,同时主页面弹窗提示用户是否添加进提醒
                if (!contains) {

                    //不属于提醒的内容 直接存进数据库
                    if (TextUtils.isEmpty(list.get(i).getNoticeTime())) {
                        DaoManager.getInstance().mDaoSession.insert(list.get(i));
                    } else {
                        final int finalI = i;
                        new AlertDialog
                                .Builder(MainActivity.this).setTitle("提醒")
                                .setMessage("是否将此条信息内容添加到提醒," + list.get(i).getNoticeContent())
                                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        list.get(finalI).setNeedNotice(true);
                                        try {
                                            Date date = simpleDateFormat.parse(list.get(finalI).getNoticeTime());
                                            AlarmManagerUtil.setAlarmOnce(MainActivity.this, date.getTime(), (int) list.get(finalI).getMessageId(), list.get(finalI).getNoticeContent());
                                            DaoManager.getInstance().mDaoSession.insert(list.get(finalI));
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                })
                                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                DaoManager.getInstance().mDaoSession.insert(list.get(finalI));
                            }
                        }).create().show();
                    }

                }
            }
        }
    }


    //读取所有短信信息
    private List<Message> getSmsfromPhone() {
        list.clear();
        Uri uri = Uri.parse(SMS_URI_ALL);
        //所要查询的列
        String[] projection = new String[]{"_id", "address", "person", "body", "date", "type"};
        //查询
        Cursor cur = getContentResolver().query(uri, projection, null, null, "date desc");//获取手机内部信息
        //将查询结果保存到列表中
        while (cur.moveToNext()) {
            Message message = new Message();
            //取得列的索引
            int index_id = cur.getColumnIndex("_id");
            int index_Address = cur.getColumnIndex("address");
            int index_Person = cur.getColumnIndex("person");
            int index_Body = cur.getColumnIndex("body");
            int index_Date = cur.getColumnIndex("date");
            int index_Type = cur.getColumnIndex("type");
            //id
            long messageId = cur.getLong(index_id);
            //取得日期
            long longDate = cur.getLong(index_Date);
            Date d = new Date(longDate);

            //取得短信发送目标的电话
            String strAddress = cur.getString(index_Address);
            //取得索引
            cur.getInt(index_Person);
            //取得信息内容
            String strbody = cur.getString(index_Body);
            //取得信息类型，1为已接收，2为已发送
            int type = cur.getInt(index_Type);
            //格式化日期
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String strDate = dateFormat.format(d);
            //将数据存储到,message中
            message.setMessageId(messageId);
            message.setAddress(strAddress);
            message.setBody(strbody);
            message.setDate(strDate);
            Pattern pattern = Pattern.compile(reg);
            Matcher matcher = pattern.matcher(strbody);


            if (d.getTime() + 50000 < System.currentTimeMillis()) {
                if (matcher.find()) {
                    String noticeContent = "您有一项待办事项需要处理";
                    Log.i("msglist", "onCreate: " + matcher.group());
                    message.setNoticeTime(matcher.group());

                    message.setNoticed(false);
                    if (strbody.contains("开会")) {
                        noticeContent = noticeContent + " : 开会";
                    }

                    if (strbody.contains("出差")) {
                        noticeContent = noticeContent + " : 出差";
                    }

                    message.setNoticeContent(noticeContent);
                }

                if (type == 1) {
                    //只保存收的
                    //                message.setType(0);
                    list.add(message);

                }
            }
        }
        if (!cur.isClosed()) {
            cur.close();
            cur = null;
        }

        return list;
    }

}
