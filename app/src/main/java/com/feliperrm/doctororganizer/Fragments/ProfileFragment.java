package com.feliperrm.doctororganizer.Fragments;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.feliperrm.doctororganizer.Activities.LoginActivity;
import com.feliperrm.doctororganizer.Activities.MainActivity;
import com.feliperrm.doctororganizer.Models.User;
import com.feliperrm.doctororganizer.R;
import com.feliperrm.doctororganizer.Utils.Geral;
import com.feliperrm.doctororganizer.Utils.Singleton;
import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.File;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment {


    CircularImageView imageView;
    RelativeLayout backgroundPicture;
    Button logout, save;

    EditText name;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        findViews(v);
        setUpViews();
        return v;
    }

    private void findViews(View v){
        imageView = (CircularImageView) v.findViewById(R.id.imageView);
        backgroundPicture = (RelativeLayout) v.findViewById(R.id.backgroundPicture);
        logout = (Button) v.findViewById(R.id.btnLogout);
        save = (Button) v.findViewById(R.id.btnSave);
        name = (EditText) v.findViewById(R.id.editName);
    }

    private void setUpViews(){
        backgroundPicture.setBackgroundColor(MainActivity.TAB3_COLOR);
        User user = Singleton.getSingleton().getLoggedUser();
        if(user.getPicture()!=null && !user.getPicture().isEmpty()){
            Glide.with(getContext()).load(user.getPicture()).into(imageView);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyImage.openGallery(ProfileFragment.this, 8);
            }
        });

        if(user.getName()!=null && !user.getName().isEmpty()){
            name.setText(user.getName());
        }

        logout.setTextColor(MainActivity.TAB3_COLOR);

        save.setBackgroundColor(MainActivity.TAB3_COLOR);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BottomDialog.Builder(getContext())
                        .setTitle(getString(R.string.sucess))
                        .setContent(getString(R.string.profile_saved))
                        .setPositiveText(R.string.dismiss)
                        .onPositive(new BottomDialog.ButtonCallback() {
                            @Override
                            public void onClick(@NonNull BottomDialog bottomDialog) {
                                bottomDialog.dismiss();
                            }
                        })
                        .show();
                User user = Singleton.getSingleton().getLoggedUser();
                user.setName(name.getText().toString());
                user.save();
                Singleton.getSingleton().setLoggedUser(user);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Singleton.getSingleton().setLoggedUser(null);
                Intent next = new Intent(getContext(), LoginActivity.class);
                next.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(next);
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
                e.printStackTrace();
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                //Handle the image
                onPhotoReturned(imageFile);
            }
        });
    }

    private void onPhotoReturned(File imageFile){
        Glide.with(getContext()).load(imageFile).into(imageView);
        User user = Singleton.getSingleton().getLoggedUser();
        user.setPicture(imageFile.getAbsolutePath());
        user.save();
        Singleton.getSingleton().setLoggedUser(user);
    }

}
