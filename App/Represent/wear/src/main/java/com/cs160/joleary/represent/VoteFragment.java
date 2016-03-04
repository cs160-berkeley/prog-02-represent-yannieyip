package com.cs160.joleary.represent;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yannie on 3/3/16.
 */
public class VoteFragment extends Fragment {

    ViewGroup mRootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        mRootView = (ViewGroup) inflater.inflate(R.layout.custom_fragment, container, false);

        return inflater.inflate(R.layout.vote_fragment, container, false);
    }

}
