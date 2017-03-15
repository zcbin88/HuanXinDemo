package com.zcb.huanxindemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.zcb.huanxindemo.R;
import com.zcb.huanxindemo.utils.APPConfig;
import com.zcb.huanxindemo.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 登录成功后跳转的activity
 */

public class ResultActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnStartChat;
    private Button btnListChat;
    private Button btnLogout;
    private EditText editText;
    private TextView userTV;
    private String userName;
    private String[] avatar={"http://img5.duitang.com/uploads/item/201507/21/20150721172011_mGYkh.thumb.224_0.jpeg",
                             "http://www.qqxoo.com/uploads/allimg/160208/19291Q227-3.jpg",
                            "http://www.feizl.com/upload2007/2014_02/1402261732574111.jpg",
                            "http://img6.itiexue.net/1314/13143390.jpg",
                            "http://img5q.duitang.com/uploads/item/201505/26/20150526033548_NjZxS.thumb.224_0.jpeg",
                            "http://www.qqxoo.com/uploads/allimg/170314/1423145B3-6.jpg",
                            "http://diy.qqjay.com/u2/2012/1015/ce912cbb8f78ab9f77846dac2797903b.jpg",
                            "http://www.qqxoo.com/uploads/allimg/170314/1423145501-4.jpg",
                            "http://diy.qqjay.com/u2/2014/1208/ac9aa749faa68eecd84ed14b2da0f9e3.jpg",
                            "http://tupian.qqjay.com/tou2/2017/0120/39b35eed7d7000fc214d3f5198032f11.jpg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent=getIntent();

        btnStartChat= (Button) findViewById(R.id.startChat);
        btnListChat= (Button) findViewById(R.id.list_chat);
        btnLogout= (Button) findViewById(R.id.logout_btn);
        editText= (EditText) findViewById(R.id.receiver_id);
        userTV= (TextView) findViewById(R.id.user_text_view);

        if (intent!=null){
            userName=intent.getStringExtra("userName");
            userTV.setText("当前登录用户:"+userName);
        }


        btnStartChat.setOnClickListener(this);
        btnListChat.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
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
            case R.id.logout_btn :
                EMClient.getInstance().logout(true);
                finish();
                break;
        }

    }


    private void startChat(){

        //设置要发送出去的昵称
        SharedPreferencesUtils.setParam(this,APPConfig.USER_NAME,userName);
        //设置要发送出去的头像
        SharedPreferencesUtils.setParam(this,APPConfig.USER_HEAD_IMG,avatar[(int)(10.0*Math.random()) + 1]);

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
