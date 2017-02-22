package com.ywp.nice.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yolanda.nohttp.rest.Request;
import com.ywp.nice.Fragment.AppFragment;
import com.ywp.nice.Fragment.ContactsFragment;
import com.ywp.nice.Fragment.MsgFragment;
import com.ywp.nice.Fragment.PersonFragment;
import com.ywp.nice.R;
import com.ywp.nice.activity.base.BaseActivity;
import com.ywp.nice.adapter.MsgRvSimpleAdapter;
import com.ywp.nice.adapter.RCPaddingDecoration;
import com.ywp.nice.http.nohttp.CallServer;
import com.ywp.nice.http.nohttp.HttpListener;
import com.ywp.nice.http.nohttp.HttpResponseListener;
import com.ywp.nice.http.nohttp.JavaBeanRequest;
import com.ywp.nice.http.Urls;
import com.ywp.nice.model.GankFuLiModel;
import com.ywp.nice.model.GankFuLiResultModel;
import com.ywp.nice.utils.ActivityCollector;
import com.ywp.nice.view.BadgeView;
import com.ywp.nice.view.CustomPopWindow;
import com.ywp.nice.view.MyToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements MsgFragment.OnMsgFragmentListener,
        ContactsFragment.OnContactsFragmentListener,
        AppFragment.OnAppFragmentListener,
        PersonFragment.OnPersonFragmentListener, HttpListener<GankFuLiModel> {


    @BindView(R.id.main_viewPager)
    ViewPager mainViewPager;
    @BindView(R.id.main_tab)
    TabLayout mainTab;
    //    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindArray(R.array.tab_name)
    String[] tab_name;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_left)
    TextView toolbarLeft;
    @BindView(R.id.toolbar_right)
    Button toolbarRight;
    @BindView(R.id.navigation)
    LinearLayout navigation;
    @BindView(R.id.toolbar_leftIco)
    CircleImageView toolbarLeftIco;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.civ_head_ico)
    CircleImageView civHeadIco;
    @BindView(R.id.toolbar_rightImg)
    ImageButton toolbarRightImg;
    @BindView(R.id.rv_navigation)
    RecyclerView rvNavigation;
    @BindView(R.id.main_appbar)
    AppBarLayout appbar;
    //    @BindView(R.id.menu1)
//    LinearLayout menu1;
//    @BindView(R.id.menu2)
//    LinearLayout menu2;
//    @BindView(R.id.menu3)
//    LinearLayout menu3;
//    @BindView(R.id.menu4)
//    LinearLayout menu4;
//    @BindView(R.id.menu5)
//    LinearLayout menu5;
//    @BindViews({R.id.menu1, R.id.menu2, R.id.menu3, R.id.menu4, R.id.menu5})
//    List<LinearLayout> menus;

    private List<Fragment> fragmentList;
    private AnimationSet setScale;
    private CustomPopWindow menuPop;
    private View menuView;
    private int page = 5;
    private int SIZE = 20;
    private List<GankFuLiResultModel> mDatas = new ArrayList<>();
    private MsgRvSimpleAdapter adapter;

    @Override
    public Toolbar getToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        return toolbar;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {

        setScale = new AnimationSet(true);
        ScaleAnimation scale_1 = new ScaleAnimation(1.0f, 0.8f, 1.0f, 0.8f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale_1.setDuration(30);

        ScaleAnimation scale_2 = new ScaleAnimation(0.8f, 1.0f, 0.8f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale_2.setDuration(30);

        setScale.addAnimation(scale_1);
        setScale.addAnimation(scale_2);

        initNavigation();

        toolbarRightImg.setVisibility(View.VISIBLE);
        toolbarRightImg.setImageResource(R.drawable.top_icon_add);

        fragmentList = new ArrayList<>();
        fragmentList.add(MsgFragment.newInstance(tab_name[0], ""));
        fragmentList.add(ContactsFragment.newInstance(tab_name[1], ""));
        fragmentList.add(AppFragment.newInstance(tab_name[2], ""));
        fragmentList.add(PersonFragment.newInstance(tab_name[3], ""));
        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                // 不让销毁UI,fragment量及里面的UI量不多的时候用，不然会OOM
//                super.destroyItem(container, position, object);
            }

            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList != null ? fragmentList.size() : 0;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tab_name[position];
            }
        };
        mainViewPager.setAdapter(pagerAdapter);

        mainTab.setupWithViewPager(mainViewPager);

        mainTab.getTabAt(0).setIcon(R.drawable.selector_ico01);
        mainTab.getTabAt(1).setIcon(R.drawable.selector_ico02);
        mainTab.getTabAt(2).setIcon(R.drawable.selector_ico03);
        mainTab.getTabAt(3).setIcon(R.drawable.selector_ico04);

        mainTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mainViewPager.setCurrentItem(tab.getPosition());
                toolbarTitle.setText(tab_name[tab.getPosition()]);
                switch (tab.getPosition()) {
                    case 0:
                        toolbarRight.setVisibility(View.GONE);
                        toolbarRightImg.setVisibility(View.VISIBLE);
                        toolbarRightImg.setImageResource(R.drawable.top_icon_add);
                        break;
                    case 1:

                        toolbarRightImg.setVisibility(View.GONE);
                        toolbarRight.setVisibility(View.VISIBLE);
                        toolbarRight.setText("添加");
                        break;
                    case 2:
                        toolbarRight.setVisibility(View.GONE);
                        toolbarRightImg.setVisibility(View.VISIBLE);
                        toolbarRightImg.setImageResource(R.drawable.top_icon_add);
                        break;
                    case 3:
                        toolbarRightImg.setVisibility(View.GONE);
                        toolbarRight.setVisibility(View.VISIBLE);
                        toolbarRight.setText("更多");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    private void handleLogic(final View menuView) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(v.getContext(), R.anim.popup_exit);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        menuPop.dissmiss();
                        menuPop = null;
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                menuView.startAnimation(animation);


                switch (v.getId()) {
                    case R.id.menu1:
                        MyToast.makeText(v.getContext(), "发起多人聊天", R.mipmap.ic_icon1, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu2:
                        MyToast.makeText(v.getContext(), "面对面收款", R.mipmap.ic_icon2, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu3:
                        MyToast.makeText(v.getContext(), "加好友", R.mipmap.icon_item3, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu4:
                        MyToast.makeText(v.getContext(), "QQ 音乐", R.mipmap.ic_icon4, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu5:
                        MyToast.makeText(v.getContext(), "付款", R.mipmap.ic_icon5, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
//        for (View v : menus
//                ) {
//            v.setOnClickListener(listener);
//        }

        menuView.findViewById(R.id.menu1).setOnClickListener(listener);
        menuView.findViewById(R.id.menu2).setOnClickListener(listener);
        menuView.findViewById(R.id.menu3).setOnClickListener(listener);
        menuView.findViewById(R.id.menu4).setOnClickListener(listener);
        menuView.findViewById(R.id.menu5).setOnClickListener(listener);

    }

    private void initNavigation() {

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationIcon(R.mipmap.ic_ico);

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_open);
//        drawerLayout.addDrawerListener(toggle);


        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                navigation.setAlpha(slideOffset);
                navigation.setScaleY(slideOffset);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                navigation.setClickable(true);
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
//        toggle.syncState();
//
//        toolbar.setNavigationIcon(ImageUtils.bitmap2Drawable(
//                getResources(),
//                ImageUtils.toRound(
//                        BitmapFactory.decodeResource(getResources(), R.drawable.top_ico),
//                        true)));
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawerLayout.openDrawer(navigation);
//            }
//        });

        toolbarLeftIco.setVisibility(View.VISIBLE);
        BadgeView badgeView = new BadgeView(this);
        badgeView.setTargetView(toolbarLeftIco);
        badgeView.setGravity(Gravity.TOP | Gravity.RIGHT);
        badgeView.setBadgeCount(6);

        BadgeView badgeViewNavigation = new BadgeView(this);
        badgeViewNavigation.setTargetView(civHeadIco);
        badgeViewNavigation.setBackground(20, Color.GREEN);
        badgeViewNavigation.setBadgeCount(6);

//        drawerLayout.setScrimColor(0x00000000);

        Request<GankFuLiModel> fuliRequest = new JavaBeanRequest<GankFuLiModel>(Urls.HTTP_GANK_FULI +SIZE +"/" + page, GankFuLiModel.class);
        CallServer.getInstance().add(0, fuliRequest, new HttpResponseListener(this, fuliRequest, this, true, true));

        rvNavigation.setLayoutManager(new LinearLayoutManager(this));
//        rvNavigation.setAdapter(new MsgRvAdapter(mDatas));
        adapter = new MsgRvSimpleAdapter(this, R.layout.item_recycler_msg, mDatas);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        adapter.isFirstOnly(false);
        rvNavigation.setHasFixedSize(true);
        rvNavigation.setAdapter(adapter);
        rvNavigation.addItemDecoration(new RCPaddingDecoration(this, R.drawable.shape_recy_item));
        rvNavigation.setNestedScrollingEnabled(false);
//        lvNavigation.setAdapter(new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, mDatas));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void OnContactsFragmentListener(String str) {
    }

    @Override
    public void OnMsgFragmentListener(String str) {
    }

    @Override
    public void OnAppFragmentListener(String str) {
    }

    @Override
    public void OnPersonFragmentListener(String str) {

        toolbarTitle.setText(str);
    }

    private boolean navigationOpened = false;

    @OnClick({R.id.toolbar_left, R.id.toolbar_right, R.id.civ_head_ico, R.id.toolbar_leftIco, R.id.toolbar_rightImg})
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.toolbar_left:
                break;
            case R.id.toolbar_rightImg:
            case R.id.toolbar_right:
                if (menuView == null) {
                    menuView = LayoutInflater.from(this).inflate(R.layout.pop_menu, null);
                    handleLogic(menuView);
                }
                menuPop = new CustomPopWindow.PopupWindowBuilder(this)
                        .setOutsideTouchable(false)
                        .setView(menuView)
                        .create()
                        .showAsDropDown(view, 0, 10);

                menuView.startAnimation(AnimationUtils.loadAnimation(view.getContext(), R.anim.popup_enter));

                break;
            case R.id.toolbar_leftIco:
                if (navigationOpened) {
                    drawerLayout.closeDrawer(navigation);
                    navigationOpened = true;
                } else {
                    drawerLayout.openDrawer(navigation);
                    navigationOpened = false;
                }
                break;
            case R.id.civ_head_ico:
                setScale.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        goActivity(PersonActivity.class);

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                view.startAnimation(setScale);

                break;
        }
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("退出")
                .setContentText("此次是否要退出应用 ?")
                .setConfirmText("确定")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        ActivityCollector.finishAllActivity();
                    }
                })
                .setCancelText("取消")
                .show();
    }

  /*  @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            if (menuPop !=null){
                MyToast.makeText(this, "Touch",Toast.LENGTH_SHORT).show();
//                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.popup_exit);
//                animation.setAnimationListener(new Animation.AnimationListener() {
//                    @Override
//                    public void onAnimationStart(Animation animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//                        menuPop.dissmiss();
//                        menuPop = null;
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {
//
//                    }
//                });
//                menuView.startAnimation(animation);
                return true;
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
*/

    public static void actionStart(Context context, String param_1, String param_2) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("param_1", param_1);
        intent.putExtra("param_2", param_2);
        context.startActivity(intent);
    }

    @Override
    public void onSucceed(int what, GankFuLiModel response) {
        if (response.getResults() != null && response.getResults().size() != 0) {
            mDatas.clear();
            mDatas.addAll(response.getResults());
            adapter.notifyDataSetChanged();
            MyToast.makeText(this, "数据加载成功", 0);
        }
    }

    @Override
    public void onFailed(int what, GankFuLiModel response) {
        MyToast.makeText(this, "数据加载失败", 0);
    }
}
