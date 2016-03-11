package com.cs160.joleary.represent;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.net.sip.SipSession.Listener;
import android.content.Intent;

/**
 * Created by yannie on 3/3/16.
 */

public class OverviewFragment extends Fragment implements View.OnClickListener {

    ViewGroup mRootView;
    ImageButton vEllipses;
    TextView vName;
    private static Listener mListener;
//    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = (ViewGroup) inflater.inflate(R.layout.overview_fragment, container, false);
        return inflater.inflate(R.layout.overview_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vName = (TextView) view.findViewById(R.id.name);
//            vEllipses = (ImageButton) view.findViewById(R.id.ellipses);
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d("HIIIIIII", "OKAYYYY");
        Intent sendIntent = new Intent(getActivity(), WatchToPhoneService.class);
////                sendIntent.putExtra("CAT_NAME", "Fred");
////                Log.d("In wear listener", "GOT CATNAME:" + catName);
        getActivity().startService(sendIntent);
    }
}
