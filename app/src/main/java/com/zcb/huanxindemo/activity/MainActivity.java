package com.zcb.huanxindemo.activity;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    private void login(){
        String username=usernameEdt.getText().toString().trim();
        String pwd=pwdEdt.getText().toString().trim();
        EMClient.getInstance().login(username, pwd, new EMCallBack() {
            @Override
            public void onSuccess() {
                startActivity(new Intent(MainActivity.this,ResultActivity.class));
                finish();
                Log.i("zcb","登录成功");
            }

            @Override
            public void onError(int i, String s) {
                Log.i("zcb","登录失败");
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
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    Log.i("zcb","注册失败");
                }
            }
        }).start();

    }
}
