package com.nanda.aadharreader.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.nanda.aadharreader.R;
import com.nanda.aadharreader.base.BaseActivity;
import com.nanda.aadharreader.home.HomeActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                finish();
            }
        }, 1200);

    }
}
