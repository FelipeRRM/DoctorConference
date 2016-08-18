package com.feliperrm.doctororganizer.Fragments;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feliperrm.doctororganizer.Activities.MainActivity;
import com.feliperrm.doctororganizer.Models.Conference;
import com.feliperrm.doctororganizer.Models.Suggestion;
import com.feliperrm.doctororganizer.R;
import com.feliperrm.doctororganizer.Utils.Geral;
import com.feliperrm.doctororganizer.Utils.Singleton;

import java.lang.reflect.Field;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateEventFragment extends DialogFragment {


    public CreateEventFragment() {
        // Required empty public constructor
    }


    LinearLayout background;
    RelativeLayout mainLayout;
    RelativeLayout parent;

    EditText subject, start, end;
    TextView suggestingDateText;
    Button suggest;
    CreateEventInterface createEventInterface;

    private static final String DAY_KEY = "daykey";
    private static final String MONTH_KEY = "monthkey";
    private static final String YEAR_KEY = "yearkey";

    int day, month, year;

    public static CreateEventFragment newInstance(int day, int month, int year) {
        Bundle args = new Bundle();
        args.putInt(DAY_KEY, day);
        args.putInt(MONTH_KEY, month);
        args.putInt(YEAR_KEY, year);
        CreateEventFragment fragment = new CreateEventFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_create_event, container, false);
        recoverBundle();
        findViews(v);
        setUpViews();
        return v;
    }

    private void recoverBundle(){
        day = getArguments().getInt(DAY_KEY);
        month = getArguments().getInt(MONTH_KEY);
        year = getArguments().getInt(YEAR_KEY);
    }


    public static void setCursorDrawableColor(EditText editText, int color) {
        try {
            Field fCursorDrawableRes =
                    TextView.class.getDeclaredField("mCursorDrawableRes");
            fCursorDrawableRes.setAccessible(true);
            int mCursorDrawableRes = fCursorDrawableRes.getInt(editText);
            Field fEditor = TextView.class.getDeclaredField("mEditor");
            fEditor.setAccessible(true);
            Object editor = fEditor.get(editText);
            Class<?> clazz = editor.getClass();
            Field fCursorDrawable = clazz.getDeclaredField("mCursorDrawable");
            fCursorDrawable.setAccessible(true);

            Drawable[] drawables = new Drawable[2];
            Resources res = editText.getContext().getResources();
            drawables[0] = res.getDrawable(mCursorDrawableRes);
            drawables[1] = res.getDrawable(mCursorDrawableRes);
            drawables[0].setColorFilter(color, PorterDuff.Mode.SRC_IN);
            drawables[1].setColorFilter(color, PorterDuff.Mode.SRC_IN);
            fCursorDrawable.set(editor, drawables);

        } catch (final Throwable ignored) {
        }
    }


    private void findViews(View v){
        background = (LinearLayout) v.findViewById(R.id.popup_black_bg);
        mainLayout = (RelativeLayout) v.findViewById(R.id.popup_main_layout);
        parent = (RelativeLayout) v.findViewById(R.id.popup_parent_layout);
        subject = (EditText) v.findViewById(R.id.editSubject);
        start = (EditText) v.findViewById(R.id.editStart);
        end = (EditText) v.findViewById(R.id.editEnd);
        suggest = (Button) v.findViewById(R.id.idSuggest);
        suggestingDateText = (TextView) v.findViewById(R.id.suggestingText);
    }

    private void setUpViews(){

        suggestingDateText.setText(getString(R.string.suggesting_conference)+" "+Geral.getMonth(month)+", " + day + ", " + year);

        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateEventFragment.this.dismiss();
            }
        });

        suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Geral.testEmptyEditText(getString(R.string.cant_be_empty),subject, start, end)){
                    int startHour = Integer.parseInt(start.getText().toString());
                    int endHour = Integer.parseInt(end.getText().toString());
                    if(startHour<0 || startHour>24){
                        start.setError(getString(R.string.between_0_23));
                        start.requestFocus();
                    }
                    else if(endHour<0 || endHour>24){
                        end.setError(getString(R.string.between_1_24));
                        end.requestFocus();
                    }
                    else if(endHour <= startHour){
                        end.setError(getString(R.string.end_must_be_after));
                        end.requestFocus();
                    }
                    else{
                        createSuggestion(startHour, endHour, subject.getText().toString());
                    }
                }

            }
        });

        suggest.setBackgroundColor(MainActivity.TAB1_COLOR);

        enterAnimation();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        createEventInterface = (CreateEventInterface) context;
    }

    private void createSuggestion(int startHour, int endHour, String subject){
        Suggestion conference = new Suggestion();
        conference.setDay(day);
        conference.setMonth(month);
        conference.setYear(year);
        conference.setSubject(subject);
        conference.setStart(startHour);
        conference.setEnd(endHour);
        conference.setUser(Singleton.getSingleton().getLoggedUser());
        conference.save();

        CreateEventFragment.this.dismiss();

    }


    private void enterAnimation(){
        mainLayout.setVisibility(View.INVISIBLE);
        mainLayout.setAlpha(0f);
        mainLayout.setTranslationY(175f);
        background.setAlpha(0f);

        background.animate().alpha(1f).setInterpolator(new DecelerateInterpolator()).setDuration(500).start();
        mainLayout.setVisibility(View.VISIBLE);
        mainLayout.animate().alpha(1f).translationY(0f).setInterpolator(new DecelerateInterpolator()).setDuration(500).setStartDelay(250).start();
    }

    private void exitAnimation(final boolean isCanceled, @Nullable final DialogInterface dialogInterface){
        parent.animate().alpha(0f).setInterpolator(new DecelerateInterpolator()).setDuration(250).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if(isCanceled)
                    CreateEventFragment.super.onCancel(dialogInterface);
                else
                    CreateEventFragment.super.dismissAllowingStateLoss();
            }
        }).start();
    }

    @Override
    public void dismiss() {
        exitAnimation(false, null);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        exitAnimation(true, dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
     //   dialog.getWindow().clearFlags( WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
     //   dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return dialog;
    }



    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            dialog.getWindow().setStatusBarColor(MainActivity.TAB1_COLOR);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().setCancelable(false);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener()
        {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {

                if ((keyCode ==  KeyEvent.KEYCODE_BACK))
                {
                    //This is the filter
                    if (event.getAction()!= KeyEvent.ACTION_DOWN)
                        return true;
                    else
                    {
                        CreateEventFragment.this.dismiss();
                        return true; // pretend we've processed it
                    }
                }
                else
                    return false; // pass on to be processed as normal
            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogFullScreen);
    }

    public static interface CreateEventInterface{
        public void SuggestionCreated();
    }


}
