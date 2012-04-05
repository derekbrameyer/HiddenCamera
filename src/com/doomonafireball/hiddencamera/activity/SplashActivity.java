package com.doomonafireball.hiddencamera.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.doomonafireball.hiddencamera.R;

public class SplashActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        try {
            Thread.sleep(1000);
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
