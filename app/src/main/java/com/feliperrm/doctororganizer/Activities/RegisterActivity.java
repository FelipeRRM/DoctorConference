package com.feliperrm.doctororganizer.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;

import com.feliperrm.doctororganizer.Models.User;
import com.feliperrm.doctororganizer.R;
import com.feliperrm.doctororganizer.Utils.Geral;
import com.github.javiersantos.bottomdialogs.BottomDialog;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RegisterActivity extends BaseActivity {

    @InjectView(R.id.fab)
    FloatingActionButton fab;
    @InjectView(R.id.cv_add)
    CardView cvAdd;
    @InjectView(R.id.et_username)
    EditText etUsername;
    @InjectView(R.id.et_password)
    EditText etPassword;
    @InjectView(R.id.et_repeatpassword)
    EditText etConfirmPassword;
    @InjectView(R.id.bt_go)
    Button register;

    @Override
    public String getScreenName() {
        return "Register";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ShowEnterAnimation();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateRevealClose();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etUsername.getText().toString().isEmpty() || etUsername.getText().toString() == null){
                    etUsername.setError(getString(R.string.cant_be_empty));
                    etUsername.requestFocus();
                }
                else if(etPassword.getText().toString().isEmpty() || etPassword.getText().toString() == null){
                    etPassword.setError(getString(R.string.cant_be_empty));
                    etPassword.requestFocus();
                }
                else if(etConfirmPassword.getText().toString().isEmpty() || etConfirmPassword.getText().toString() == null){
                    etConfirmPassword.setError(getString(R.string.cant_be_empty));
                    etConfirmPassword.requestFocus();
                }
                else if(!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())){
                    etConfirmPassword.setError(getString(R.string.pwd_dont_match));
                    etConfirmPassword.requestFocus();
                }
                else {
                    User user = User.getUser(etUsername.getText().toString());
                    if(user==null) {
                        user = new User();
                        user.setAdmin(false);
                        user.setUserName(etUsername.getText().toString());
                        user.setPassword(Geral.toMD5(etPassword.getText().toString()));
                        user.save();
                        onBackPressed();
                    }
                    else{
                        new BottomDialog.Builder(RegisterActivity.this)
                                .setTitle(getString(R.string.user_exists))
                                .setContent("\"" + etUsername.getText().toString() + "\" " + "is already a registered user!")
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
                    }
                }
            }
        });
    }

    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cvAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }


        });
    }

    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth()/2,0, fab.getWidth() / 2, cvAdd.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd,cvAdd.getWidth()/2,0, cvAdd.getHeight(), fab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.plus);
                RegisterActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }
    @Override
    public void onBackPressed() {
        animateRevealClose();
    }
}
