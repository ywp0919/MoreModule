package com.ywp.nice.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ywp.nice.R;
import com.ywp.nice.utils.ImageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnAppFragmentListener} interface
 * to handle interaction events.
 * Use the {@link AppFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.progress_dialog)
    Button progressDialog;
    @BindView(R.id.basic_test)
    Button basicTest;
    @BindView(R.id.under_text_test)
    Button underTextTest;
    @BindView(R.id.error_text_test)
    Button errorTextTest;
    @BindView(R.id.success_text_test)
    Button successTextTest;
    @BindView(R.id.warning_confirm_test)
    Button warningConfirmTest;
    @BindView(R.id.warning_cancel_test)
    Button warningCancelTest;
    @BindView(R.id.custom_img_test)
    Button customImgTest;


//    @BindView(R.id.button3)
//    Button button3;
//    @BindView(R.id.button4)
//    Button button4;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnAppFragmentListener mListener;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AppFragment newInstance(String param1, String param2) {
        AppFragment fragment = new AppFragment();
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

    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_app, container, false);

        unbinder = ButterKnife.bind(this, rootView);

        return rootView;

    }

//    @Override
//    public int getLayout() {
//        return R.layout.fragment_app;
//    }

//    @Override
//    public void initAllMemberViews(Bundle savedInstanceState) {
//
//    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String str) {
        if (mListener != null) {
            mListener.OnAppFragmentListener(str);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAppFragmentListener) {
            mListener = (OnAppFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAppFragmentListenerListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private int i = -1;

    @OnClick({R.id.progress_dialog, R.id.basic_test, R.id.under_text_test, R.id.error_text_test, R.id.success_text_test, R.id.warning_confirm_test, R.id.warning_cancel_test, R.id.custom_img_test})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.progress_dialog:
                final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE)
                        .setTitleText("加载中...");
                pDialog.show();
                pDialog.setCancelable(false);
                new CountDownTimer(400 * 7, 800) {
                    public void onTick(long millisUntilFinished) {
                        i++;
                        switch (i) {
                            case 0:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.blue_btn_bg_color));
                                break;
                            case 1:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_50));
                                break;
                            case 2:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
                                break;
                            case 3:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_20));
                                break;
                            case 4:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_blue_grey_80));
                                break;
                            case 5:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.warning_stroke_color));
                                break;
                            case 6:
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
                                break;
                        }
                    }

                    public void onFinish() {
                        i = -1;
                        pDialog.setTitleText("成功!")
                                .setConfirmText("确定")
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                }.start();
                break;
            case R.id.basic_test:
                SweetAlertDialog sd = new SweetAlertDialog(getActivity());
                sd.setCancelable(true);
                sd.setCanceledOnTouchOutside(true);
                sd.setTitleText("一条用标题显示的消息");
                sd.show();
                break;
            case R.id.under_text_test:
                new SweetAlertDialog(getActivity())
                        .setTitleText("不只有标题")
                        .setContentText("还有内容说明的消息！")
                        .show();
                break;
            case R.id.error_text_test:
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("错误")
                        .setContentText("发生未知错误")
                        .show();
                break;
            case R.id.success_text_test:
                new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("成功了!")
                        .setContentText("点击确定吧!")
                        .show();
                break;
            case R.id.warning_confirm_test:
                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("确定吗?")
                        .setContentText("删除了就不能还原了哦!")
                        .setConfirmText("删除!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                // reuse previous dialog instance
                                sDialog.setTitleText("已删除!")
                                        .setContentText("文件已经删除!")
                                        .setConfirmText("确定")
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }
                        })
                        .show();
                break;
            case R.id.warning_cancel_test:
                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("确定吗?")
                        .setContentText("确定删除之后将无法还原！")
                        .setCancelText("取消")
                        .setConfirmText("删除")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                // reuse previous dialog instance, keep widget user state, reset them if you need
                                sDialog.setTitleText("已取消!")
                                        .setContentText("取消删除！")
                                        .setConfirmText("确定")
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);

                                // or you can new a SweetAlertDialog to show
                               /* sDialog.dismiss();
                                new SweetAlertDialog(SampleActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Cancelled!")
                                        .setContentText("Your imaginary file is safe :)")
                                        .setConfirmText("OK")
                                        .show();*/
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.setTitleText("已删除!")
                                        .setContentText("你的文件已经删除!")
                                        .setConfirmText("确定")
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }
                        })
                        .show();
                break;
            case R.id.custom_img_test:
                new SweetAlertDialog(getActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("标题")
                        .setContentText("还可以有图片哦！")
                        .setCustomImage(ImageUtils.bitmap2Drawable(getResources(), ImageUtils.toRound(ImageUtils.getBitmap(getResources(), R.drawable.top_ico))))
                        .show();
                break;
        }
    }


//    @OnClick({R.id.button3, R.id.button4})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.button3:
//                Toast.makeText(context, "Button 1", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.button4:
//                Toast.makeText(context, "Button 2", Toast.LENGTH_SHORT).show();
//                break;
//        }
//    }

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
    public interface OnAppFragmentListener {
        // TODO: Update argument type and name
        void OnAppFragmentListener(String str);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
