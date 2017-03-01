package com.zcb.huanxindemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.hyphenate.easeui.EaseConstant;
import com.zcb.huanxindemo.R;
import com.zcb.huanxindemo.utils.APPConfig;
import com.zcb.huanxindemo.utils.SharedPreferencesUtils;

/**
 * 登录成功后跳转的activity
 */

public class ResultActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnStartChat;
    private Button btnListChat;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        btnStartChat= (Button) findViewById(R.id.startChat);
        btnListChat= (Button) findViewById(R.id.list_chat);
        editText= (EditText) findViewById(R.id.receiver_id);

        btnStartChat.setOnClickListener(this);
        btnListChat.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startChat :
                startChat();
                break;
            case R.id.list_chat :
                openListChat();
                break;
        }

    }


    private void startChat(){

        //设置要发送出去的昵称
        SharedPreferencesUtils.setParam(this,APPConfig.USER_NAME,"li_si");
        //设置要发送出去的头像
        SharedPreferencesUtils.setParam(this,APPConfig.USER_HEAD_IMG,"http://img5.duitang.com/uploads/item/201507/21/20150721172011_mGYkh.thumb.224_0.jpeg");

        Intent intent=new Intent(ResultActivity.this,MyChatActivity.class);
        //传入参数
        Bundle args=new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID,editText.getText().toString());
        intent.putExtra("conversation",args);

        startActivity(intent);
    }

    private void openListChat(){
        startActivity(new Intent(ResultActivity.this,MyConversationListActivity.class));
    }
}
