package com.zcb.huanxindemo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.zcb.huanxindemo.R;
import com.zcb.huanxindemo.utils.APPConfig;
import com.zcb.huanxindemo.utils.SharedPreferencesUtils;

/**
 * 登录activity
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText usernameEdt;
    private EditText pwdEdt;
    private Button loginBtn;
    private Button registerBtn;
    private Handler mHandler;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        Toast.makeText(LoginActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                        break;
                    case 0:
                        Toast.makeText(LoginActivity.this,"注册失败"+msg.obj.toString(),Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        Toast.makeText(LoginActivity.this,"登录失败"+msg.obj.toString(),Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };
        findView();

        ActionBar bar=getSupportActionBar();
        if (bar!=null)
        bar.setTitle("登录");
        progressDialog=new ProgressDialog(this);
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
                Intent intent =new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 环信登录方法
     */
    String username;
    String pwd;
    private void login(){
        username=usernameEdt.getText().toString().trim();
        pwd=pwdEdt.getText().toString().trim();
        EMClient.getInstance().login(username, pwd, new EMCallBack() {
            @Override
            public void onSuccess() {
                progressDialog.dismiss();
                SharedPreferencesUtils.setParam(LoginActivity.this, APPConfig.USER_NAME,username);
                SharedPreferencesUtils.setParam(LoginActivity.this, APPConfig.PASS_WORD,pwd);
                Message msg=new Message();
                msg.what=2;
                mHandler.sendMessage(msg);
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
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
                progressDialog.show(LoginActivity.this, "登录", "正在登录中");
            }
        });

    }

}
