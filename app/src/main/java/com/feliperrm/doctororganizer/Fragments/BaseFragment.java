package com.feliperrm.doctororganizer.Fragments;

import android.support.v4.app.Fragment;
import android.view.animation.Animation;

import com.labo.kaji.fragmentanimations.MoveAnimation;
import com.labo.kaji.fragmentanimations.PushPullAnimation;


/**
 * Created by felip on 17/05/2016.
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if(enter)
            return PushPullAnimation.create(PushPullAnimation.LEFT, enter, 500);
        else
            return MoveAnimation.create(MoveAnimation.DOWN, enter, 750);

    }

}
