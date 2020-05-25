package com.message.alarm.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.message.alarm.R;
import com.message.alarm.bean.Message;

import java.util.List;

public class NoticeAdapter extends BaseQuickAdapter<Message, BaseViewHolder> {
    public NoticeAdapter(int layoutResId, List<Message> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Message item) {
        helper.setText(R.id.tv_from,item.getAddress());
        helper.setText(R.id.tv_content,item.getNoticeContent());
        helper.setText(R.id.tv_time,item.getNoticeTime());
        helper.setText(R.id.tv_noticed,item.getNoticed() ? "是" : "否");
    }
}
