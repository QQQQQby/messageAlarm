package com.message.alarm.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.message.alarm.R;
import com.message.alarm.bean.Message;

import java.util.List;

/**
 * 短信列表adapter
 */
public class AllMessageAdapter extends BaseQuickAdapter<Message, BaseViewHolder> {
    public AllMessageAdapter(int layoutResId, List<Message> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Message item) {
        //短信号码
        helper.setText(R.id.listnum,item.getAddress());
        //短信内容
        helper.setText(R.id.listmsg,item.getBody());
        //短信时间
        helper.setText(R.id.listtime,item.getDate());
        //收发类型
        if (item.getType() == 0){
            helper.setText(R.id.type,"收");
        }else {
            helper.setText(R.id.type,"发");
        }
    }
}
