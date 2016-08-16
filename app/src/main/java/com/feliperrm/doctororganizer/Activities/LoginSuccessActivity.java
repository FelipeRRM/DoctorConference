package com.feliperrm.doctororganizer.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;

import com.feliperrm.doctororganizer.R;

public class LoginSuccessActivity extends BaseActivity {

    @Override
    public String getScreenName() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);

        Explode explode = new Explode();
        explode.setDuration(500);
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);
        findViewById(R.id.loginSuccess).animate().alpha(0f).setStartDelay(1000).setDuration(2000).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Intent next = new Intent(LoginSuccessActivity.this, MainActivity.class);
                next.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(next);
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
