package com.zcb.huanxindemo.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import com.zcb.huanxindemo.R;
import com.zcb.huanxindemo.utils.APPConfig;
import com.zcb.huanxindemo.utils.SharedPreferencesUtils;

/**
 * 启动页闪屏activity
 */
public class SplashActivity extends AppCompatActivity {
    private final Handler mShowHandler = new Handler();
    private final Runnable mShowRunnable = new Runnable() {
        @Override
        public void run() {
            show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setFullScreen(true);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        delayedShow(2000);
    }


    private void show() {

        String username= (String) SharedPreferencesUtils.getParam(SplashActivity.this, APPConfig.USER_NAME,"");
        if (username.equals("")|| username.equals("null")){
            Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
            startActivity(intent);
        }else{
            Intent intent=new Intent(SplashActivity.this,MainActivity.class);
            intent.putExtra("userName",username);
            startActivity(intent);
        }

        finish();
    }

    private void delayedShow(int delayMillis) {
        mShowHandler.postDelayed(mShowRunnable, delayMillis);
    }

    public void setFullScreen(boolean isFullScreen) {
        if (isFullScreen) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        }
    }
}
