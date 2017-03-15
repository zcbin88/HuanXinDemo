package com.zcb.huanxindemo.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.zcb.huanxindemo.R;

import static android.view.View.*;

/**
 * 登录和注册环信的activity
 */

public class MainActivity extends AppCompatActivity implements OnClickListener{

    private EditText usernameEdt;
    private EditText pwdEdt;
    private Button loginBtn;
    private Button registerBtn;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        Toast.makeText(MainActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                        break;
                    case 0:
                        Toast.makeText(MainActivity.this,"注册失败"+msg.obj.toString(),Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        Toast.makeText(MainActivity.this,"登录失败"+msg.obj.toString(),Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };
        findView();
    }

    private void findView(){
        usernameEdt= (EditText) findViewById(R.id.usernameEdt);
        pwdEdt= (EditText) findViewById(R.id.pwdEdt);
        loginBtn= (Button) findViewById(R.id.loginBtn);
        registerBtn= (Button) findViewById(R.id.registerBtn);

        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
        case R.id.loginBtn :
            login();
        break;
            case R.id.registerBtn :
                register();
                break;

        }
    }

    /**
     * 登录方法
     */
    String username;
    String pwd;
    private void login(){
        username=usernameEdt.getText().toString().trim();
        pwd=pwdEdt.getText().toString().trim();
        EMClient.getInstance().login(username, pwd, new EMCallBack() {
            @Override
            public void onSuccess() {
                Message msg=new Message();
                msg.what=2;
                mHandler.sendMessage(msg);
                Intent intent=new Intent(MainActivity.this,ResultActivity.class);
                intent.putExtra("userName",username);
                startActivity(intent);
                Log.i("huanxin","登录成功");
            }

            @Override
            public void onError(int i, String s) {
                Message msg=new Message();
                msg.what=3;
                msg.obj=s;
                mHandler.sendMessage(msg);
                Log.e("huanxin",s);
                Log.i("huanxin","登录失败");
//                if (s.equals("User is already login")){
//                    EMClient.getInstance().logout(true);
//                    Toast.makeText(MainActivity.this,"正在退出当前环信账号,稍后重新登录",Toast.LENGTH_LONG).show();
//                }
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });

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
}
