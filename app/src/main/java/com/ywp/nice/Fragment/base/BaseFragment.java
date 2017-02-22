package com.ywp.nice.Fragment.base;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {

    protected View rootView ;
    protected Context context;
    private Unbinder unbinder;

    public abstract int getLayout();
    public abstract void initAllMemberViews(Bundle savedInstanceState);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(getLayout(), container,false);
        this.context = getActivity();
        unbinder = ButterKnife.bind(this, rootView);
        initAllMemberViews(savedInstanceState);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
