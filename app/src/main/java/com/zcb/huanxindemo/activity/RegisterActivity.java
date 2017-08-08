package com.zcb.huanxindemo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.zcb.huanxindemo.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText usernameEdt;
    private EditText pwdEdt;
    private Button registerBtn;
    private Handler mHandler;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                        break;
                    case 0:
                        Toast.makeText(RegisterActivity.this,"注册失败"+msg.obj.toString(),Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Toast.makeText(RegisterActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        Toast.makeText(RegisterActivity.this,"登录失败"+msg.obj.toString(),Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };
        findView();

        ActionBar bar=getSupportActionBar();
        if (bar!=null)
        bar.setTitle("注册");
    progressDialog=new ProgressDialog(this);
    }

    private void findView(){
        usernameEdt= (EditText) findViewById(R.id.usernameEdt);
        pwdEdt= (EditText) findViewById(R.id.pwdEdt);
        registerBtn= (Button) findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registerBtn :
                register();
                break;
        }
    }


    /**
     * 注册方法
     */
    private void register(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String username=usernameEdt.getText().toString().trim();
                    String pwd=pwdEdt.getText().toString().trim();
                    EMClient.getInstance().createAccount(username,pwd);
                    Log.i("zcb","注册成功");
                    Message msg=new Message();
                    msg.what=1;
                    mHandler.sendMessage(msg);
                    //注册成功之后直接登录
                    login();
                } catch (HyphenateException e) {
                    Message msg=new Message();
                    msg.what=0;
                    msg.obj=e.toString();
                    mHandler.sendMessage(msg);
//                    e.printStackTrace();
                    Log.i("zcb","注册失败");
                }
            }
        }).start();
    }

    String username;
    String pwd;
    /**
     * 登录方法
     */
    private void login(){
        username=usernameEdt.getText().toString().trim();
        pwd=pwdEdt.getText().toString().trim();
        EMClient.getInstance().login(username, pwd, new EMCallBack() {
            @Override
            public void onSuccess() {
                progressDialog.dismiss();
                Message msg=new Message();
                msg.what=2;
                mHandler.sendMessage(msg);
                Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                intent.putExtra("userName",username);
                startActivity(intent);
                Log.i("huanxin","登录成功");
            }

            @Override
            public void onError(int i, String s) {
                progressDialog.dismiss();
                Message msg=new Message();
                msg.what=3;
                msg.obj=s;
                mHandler.sendMessage(msg);
                Log.e("huanxin",s);
                Log.i("huanxin","登录失败");
            }

            @Override
            public void onProgress(int i, String s) {
                progressDialog.show(RegisterActivity.this, "登录", "正在登录中");
            }
        });

    }
}
