package com.ywp.nice.activity;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.ywp.nice.R;
import com.ywp.nice.adapter.CollapsingRvSimpAdapter;
import com.ywp.nice.http.nohttp.HttpListener;
import com.ywp.nice.http.retrofitRx.HttpMethods;
import com.ywp.nice.http.retrofitRx.ProgressSubscriber;
import com.ywp.nice.http.retrofitRx.SubscriberListener;
import com.ywp.nice.model.GankFuLiModel;
import com.ywp.nice.model.GankFuLiResultModel;
import com.ywp.nice.view.MyToast;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class CollapsingActivity extends AppCompatActivity implements HttpListener<GankFuLiModel>,BaseQuickAdapter.RequestLoadMoreListener {
    private int page = 1;
    private int SIZE = 10;
    private List<GankFuLiResultModel> mDatas = new ArrayList<>();
    private CollapsingRvSimpAdapter adapter;

    private RecyclerView rv_collap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getSupportActionBar().setDisplayShowTitleEnabled(false);

//        NoScrollListView lv_collapsing = (NoScrollListView) findViewById(R.id.lv_collapsing);
        rv_collap = (RecyclerView) findViewById(R.id.rv_collap);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv_collap.setLayoutManager(manager);
        adapter = new CollapsingRvSimpAdapter(this, R.layout.item_recy_collapsing, mDatas);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.isFirstOnly(false);
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
        itemTouchHelper.attachToRecyclerView(rv_collap);

        //开启拖拽
        adapter.enableDragItem(itemTouchHelper, R.id.tv_coll_title, true);
        adapter.setOnItemDragListener(onItemDragListener);

        // 开启滑动删除
        adapter.enableSwipeItem();
        adapter.setOnItemSwipeListener(onItemSwipeListener);


        //加载更多
        adapter.setEnableLoadMore(true);
        adapter.setAutoLoadMoreSize(1);
        adapter.setOnLoadMoreListener(this);

        //设置加载布局


        rv_collap.setAdapter(adapter);


        //设置禁止嵌套滑动解决不流畅问题
        rv_collap.setNestedScrollingEnabled(false);

        rv_collap.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
//                MyToast.makeText(view.getContext(), "你点了" + mDatas.get(position).getUrl(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CollapsingActivity.this,ShowPhotoActivity.class);
//                Bundle bundle = captureValues((ImageView)view.findViewById(R.id.iv_coll_img));

                intent.putExtra("path",((GankFuLiResultModel)adapter.getItem(position)).getUrl());
                //安卓5.0以上实现共享元素方式
                //主要的语句
                //通过makeSceneTransitionAnimation传入多个Pair
                //每个Pair将一个当前Activity的View和目标Activity中的一个Key绑定起来
                //在目标Activity中会调用这个Key
//                ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                        CollapsingActivity.this,
//                        new Pair<View, String>(view.findViewById(R.id.iv_coll_img),
//                                "img"));

                // ActivityCompat是android支持库中用来适应不同android版本的，不能实现动画的版本也不会报错。
//                ActivityCompat.startActivity(CollapsingActivity.this, intent, activityOptions.toBundle());

//                intent.putExtra("viewInfo",bundle);

                ImageView imageView = (ImageView)view.findViewById(R.id.iv_coll_img);
                int location[] = new int[2];

                imageView.getLocationOnScreen(location);
                intent.putExtra("left", location[0]);
                intent.putExtra("top", location[1]);
                intent.putExtra("height", imageView.getHeight());
                intent.putExtra("width", imageView.getWidth());

                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        loadDatas(page);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private Bundle captureValues(@NonNull View view) {
        Bundle b = new Bundle();
        int[] screenLocation = new int[2];
        view.getLocationOnScreen(screenLocation);
        b.putInt("left", screenLocation[0]);
        b.putInt("top", screenLocation[1]);
        b.putInt("width", view.getWidth());
        b.putInt("height", view.getHeight());
        return b;
    }

    @Override
    public void onSucceed(int what, GankFuLiModel response) {
        if (response.getResults() != null && response.getResults().size() != 0) {
            if (page == 1) {
                mDatas.clear();
            }
            mDatas.addAll(response.getResults());
            adapter.notifyDataSetChanged();
            adapter.loadMoreComplete();
            MyToast.makeText(this, "数据加载成功",R.drawable.top_ico, 0).show();

        }else
            adapter.loadMoreEnd();
    }

    @Override
    public void onFailed(int what, GankFuLiModel response) {
        page--;
        adapter.loadMoreFail();
        MyToast.makeText(this, "数据加载失败", 0).show();
    }

    private void loadDatas(int mPage) {
        //retrofit请求
        HttpMethods.getGankApi().loadGankPage(SIZE, mPage)
                .subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<GankFuLiModel, List<GankFuLiResultModel>>() {
                    @Override
                    public List<GankFuLiResultModel> call(GankFuLiModel gankFuLiModel) {
                        List<GankFuLiResultModel> resultModels = gankFuLiModel.getResults();
                        return resultModels;
                    }
                })
                .subscribe(new ProgressSubscriber<List<GankFuLiResultModel>>(this, new SubscriberListener<List<GankFuLiResultModel>>() {
                    @Override
                    public void onNext(List<GankFuLiResultModel> resultModels) {
                        if (resultModels != null && resultModels.size() != 0) {
                            if (page == 1) {
                                mDatas.clear();
                            }
                            mDatas.addAll(resultModels);
                            adapter.notifyDataSetChanged();
                            adapter.loadMoreComplete();
                            MyToast.makeText(getApplicationContext(), "数据加载成功",R.drawable.top_ico, 0).show();

                        }else
                            adapter.loadMoreEnd();
                    }

                    @Override
                    public void onError(Throwable e) {
                        page--;
                        adapter.loadMoreFail();
                        MyToast.makeText(getApplicationContext(), e.toString(), 1).show();
                    }
                }));


        // nohttp 请求
//        Request<GankFuLiModel> fuliRequest = new JavaBeanRequest<GankFuLiModel>(Urls.HTTP_GANK_FULI + SIZE + "/" + mPage, GankFuLiModel.class);
//        CallServer.getInstance().add(0, fuliRequest, new HttpResponseListener(this, fuliRequest, this, true, true));
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onLoadMoreRequested() {
        rv_collap.postDelayed(new Runnable() {
            @Override
            public void run() {
                page++;
                loadDatas(page);
            }
        },1000);
    }
}
