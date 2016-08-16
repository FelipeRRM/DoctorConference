package com.feliperrm.doctororganizer.Activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.feliperrm.doctororganizer.Models.User;
import com.feliperrm.doctororganizer.R;
import com.feliperrm.doctororganizer.Utils.Geral;
import com.github.javiersantos.bottomdialogs.BottomDialog;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @InjectView(R.id.et_username)
    EditText etUsername;
    @InjectView(R.id.et_password)
    EditText etPassword;
    @InjectView(R.id.bt_go)
    Button btGo;
    @InjectView(R.id.cv)
    CardView cv;
    @InjectView(R.id.fab)
    FloatingActionButton fab;

    @Override
    public String getScreenName() {
        return "Login";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

    }

    @OnClick({R.id.bt_go, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
                    startActivity(new Intent(this, RegisterActivity.class), options.toBundle());
                } else {
                    startActivity(new Intent(this, RegisterActivity.class));
                }
                break;
            case R.id.bt_go:
                if(etUsername.getText().toString().isEmpty() || etUsername.getText().toString() == null){
                    etUsername.setError(getString(R.string.cant_be_empty));
                    etUsername.requestFocus();
                }
                else if(etPassword.getText().toString().isEmpty() || etPassword.getText().toString() == null){
                    etPassword.setError(getString(R.string.cant_be_empty));
                    etPassword.requestFocus();
                }else {
                    User user = User.getUser(etUsername.getText().toString());
                    if (user == null) {
                        new BottomDialog.Builder(LoginActivity.this)
                                .setTitle(getString(R.string.user_not_found_title))
                                .setContent("\"" + etUsername.getText().toString() + "\" " + "is not a registered user!")
                                .setIcon(R.drawable.error)
                                .setPositiveText(R.string.dismiss)
                                .onPositive(new BottomDialog.ButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull BottomDialog bottomDialog) {
                                        etUsername.requestFocus();
                                        bottomDialog.dismiss();
                                    }
                                })
                                .show();
                    } else if (user.getPassword().equals(Geral.toMD5(etPassword.getText().toString()))) {
                        Explode explode = new Explode();
                        explode.setDuration(500);
                        getWindow().setExitTransition(explode);
                        getWindow().setEnterTransition(explode);
                        ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
                        Intent i2 = new Intent(this, LoginSuccessActivity.class);
                        startActivity(i2, oc2.toBundle());
                    } else {
                        new BottomDialog.Builder(LoginActivity.this)
                                .setTitle(getString(R.string.incorrect_password_title))
                                .setContent(getString(R.string.incorrect_password))
                                .setIcon(R.drawable.error)
                                .setPositiveText(R.string.dismiss)
                                .onPositive(new BottomDialog.ButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull BottomDialog bottomDialog) {
                                        etPassword.requestFocus();
                                        bottomDialog.dismiss();
                                    }
                                })
                                .show();
                    }
                }
                break;
        }
    }
}
