package com.ywp.nice.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ywp.nice.R;
import com.ywp.nice.activity.CollapsingActivity;
import com.ywp.nice.activity.JniTestActivity;
import com.ywp.nice.activity.ScrollingActivity;
import com.ywp.nice.alipay.PayDemoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnContactsFragmentListener} interface
 * to handle interaction events.
 * Use the {@link ContactsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.bt_scrolling)
    Button btScrolling;
    @BindView(R.id.bt_toolbar)
    Button btToolbar;
    @BindView(R.id.bt_alipay)
    Button btAlipay;
    @BindView(R.id.bt_jni)
    Button btJni;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnContactsFragmentListener mListener;

    public ContactsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactsFragment newInstance(String param1, String param2) {
        ContactsFragment fragment = new ContactsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String str) {
        if (mListener != null) {
            mListener.OnContactsFragmentListener(str);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnContactsFragmentListener) {
            mListener = (OnContactsFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnClick({R.id.bt_scrolling, R.id.bt_toolbar,R.id.bt_alipay,R.id.bt_jni})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_scrolling:
                startActivity(new Intent(getActivity(),ScrollingActivity.class));
                break;
            case R.id.bt_toolbar:
                startActivity(new Intent(getActivity(),CollapsingActivity.class));
                break;
            case R.id.bt_alipay :
                startActivity(new Intent(getActivity(), PayDemoActivity.class));
                break;
            case R.id.bt_jni:
                startActivity(new Intent(getActivity(), JniTestActivity.class));
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnContactsFragmentListener {
        // TODO: Update argument type and name
        void OnContactsFragmentListener(String str);
    }
}
