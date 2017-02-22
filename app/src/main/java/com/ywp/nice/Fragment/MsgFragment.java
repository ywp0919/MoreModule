package com.ywp.nice.Fragment;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.yolanda.nohttp.rest.Request;
import com.ywp.nice.R;
import com.ywp.nice.adapter.MsgRvSimpleAdapter;
import com.ywp.nice.adapter.RCPaddingDecoration;
import com.ywp.nice.http.nohttp.CallServer;
import com.ywp.nice.http.nohttp.HttpListener;
import com.ywp.nice.http.nohttp.HttpResponseListener;
import com.ywp.nice.http.nohttp.JavaBeanRequest;
import com.ywp.nice.http.Urls;
import com.ywp.nice.model.GankFuLiModel;
import com.ywp.nice.model.GankFuLiResultModel;
import com.ywp.nice.view.MyToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Unbinder;

import static master.flame.danmaku.ui.widget.DanmakuTextureView.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnMsgFragmentListener} interface
 * to handle interaction events.
 * Use the {@link MsgFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MsgFragment extends Fragment implements HttpListener<GankFuLiModel> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
/*    @BindView(R.id.lv_msg)
    ListView lvMsg;*/

//    @BindView(R.id.rv_msg)
//    RecyclerView rv_msg;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnMsgFragmentListener mListener;
    private Unbinder unbinder;

    private int page = 1;
    private int SIZE = 50;
    private List<GankFuLiResultModel> mDatas = new ArrayList<>();
    private MsgRvSimpleAdapter adapter;
    private RecyclerView rv_msg;
    private View rootView;
    private Context mContext;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MsgFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MsgFragment newInstance(String param1, String param2) {
        MsgFragment fragment = new MsgFragment();
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
        mContext = getActivity();
        Log.v(TAG, "Fragment onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v(TAG, "Fragment onCreateView()");

        rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_msg, null);
//        unbinder = ButterKnife.bind(this, rootView);
        rv_msg = (RecyclerView) rootView.findViewById(R.id.rv_msg);

        adapter = new MsgRvSimpleAdapter(getActivity(), R.layout.item_recycler_msg, mDatas);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        adapter.isFirstOnly(false);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rv_msg.setLayoutManager(manager);
//        rv_msg.setAdapter(new MsgRvAdapter(mDatas));
        //拖拽和滑动删除的回调方法
        OnItemDragListener onItemDragListener = new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
            }
        };

        OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

            }
        };

        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(rv_msg);

        //开启拖拽
        adapter.enableDragItem(itemTouchHelper, R.id.tv_coll_title, true);
        adapter.setOnItemDragListener(onItemDragListener);

        // 开启滑动删除
        adapter.enableSwipeItem();
        adapter.setOnItemSwipeListener(onItemSwipeListener);

        rv_msg.setHasFixedSize(true);
        rv_msg.setAdapter(adapter);

        rv_msg.addOnItemTouchListener(new SimpleClickListener() {
                                          @Override
                                          public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                              //item的点击事件
                                              MyToast.makeText(mContext, "点击了第" + (position + 1) + "个item", 0).show();
                                              Log.v(TAG,"点击了第" + (position + 1) + "个item");
                                          }

                                          @Override
                                          public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                                              //item的长按事件
                                              MyToast.makeText(mContext, "长按了第" + (position + 1) + "个item", 0).show();
                                              Log.v(TAG, "长按了第" + (position + 1) + "个item");
                                          }

                                          @Override
                                          public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                              //item的子控件点击事件
                                              int id = view.getId();
                                              switch (id) {
                                                  case R.id.iv_msg_img:
//                                                      view.setDrawingCacheEnabled(true);
                                                      MyToast.makeText(view.getContext(), "点击了第" + (position + 1) + "个头像", ((ImageView)view).getDrawable(), 0).show();
//                                                      view.setDrawingCacheEnabled(false);
                                                      Log.v(TAG,"点击了第" + (position + 1) + "个头像");
                                                      break;
                                              }
                                          }

                                          @Override
                                          public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
                                              //item的子控件长按事件
                                              int id = view.getId();
                                              switch (id) {
                                                  case R.id.iv_msg_img:
                                                      view.setDrawingCacheEnabled(true);
                                                      MyToast.makeText(view.getContext(), "长按了第" + (position + 1) + "个头像", ((ImageView)view).getDrawable(), 0).show();
                                                      view.setDrawingCacheEnabled(false);
                                                      Log.v(TAG,"长按了第" + (position + 1) + "个头像");
                                                      break;
                                              }
                                          }
                                      }
        );

        rv_msg.addItemDecoration(new

                RCPaddingDecoration(getActivity(), R

                .drawable.shape_recy_item));
        rv_msg.setNestedScrollingEnabled(false);
        hasStarted = true;
        Request<GankFuLiModel> fuliRequest = new JavaBeanRequest<GankFuLiModel>(Urls.HTTP_GANK_FULI + SIZE + "/" + page, GankFuLiModel.class);
        CallServer.getInstance().

                add(0, fuliRequest, new HttpResponseListener(getContext(), fuliRequest,

                        this, true, true));


//        lvMsg.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, mDatas));
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.v(TAG, "Fragment onDestroyView()");
//        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "Fragment onDestroy()");
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String str) {
        if (mListener != null) {
            mListener.OnMsgFragmentListener(str);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v(TAG, "Fragment onAttach()");
        if (context instanceof OnMsgFragmentListener) {
            mListener = (OnMsgFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMsgFragmentListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.v(TAG, "Fragment onDetach()");
        mListener = null;
    }


    @Override
    public void onSucceed(int what, GankFuLiModel response) {
        if (response.getResults() != null && response.getResults().size() != 0) {
            mDatas.clear();
            mDatas.addAll(response.getResults());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFailed(int what, GankFuLiModel response) {

    }

    //
//    /**
//     * 判断是否是初始化Fragment
//     */
    private boolean hasStarted = false;
//
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//
//        if (hasStarted && isVisibleToUser) {
//
//            Log.v(TAG, "Fragment开始界面");
//            hasStarted = true;
//        } else {
//
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
    public interface OnMsgFragmentListener {
        // TODO: Update argument type and name
        void OnMsgFragmentListener(String str);
    }
}
