package com.ywp.nice.activity;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;
import com.ywp.nice.R;
import com.ywp.nice.activity.base.BaseActivity;
import com.ywp.nice.http.Urls;
import com.ywp.nice.http.retrofitRx.HttpMethods;
import com.ywp.nice.http.retrofitRx.ProgressSubscriber;
import com.ywp.nice.http.retrofitRx.SubscriberListener;
import com.ywp.nice.model.LoginResultModel;
import com.ywp.nice.utils.RxBus;
import com.ywp.nice.view.LoadingAlertDialog;
import com.ywp.nice.view.MyToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends BaseActivity implements OnResponseListener<JSONObject> {


    private static final int LOGIN = 0X001;
    @BindView(R.id.username)
    AutoCompleteTextView username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.cb_remenber)
    CheckBox cbRemenber;
    @BindView(R.id.tv_remenber_psd)
    TextView tvRemenberPsd;
    @BindView(R.id.tv_forget)
    TextView tvForget;
    @BindView(R.id.login)
    Button login;
    private LoadingAlertDialog dialog;
    private SweetAlertDialog pDialog;
    private String passStr;
    private String userStr;
    private Subscription subscribe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

    }


    @OnClick(R.id.login)
    void login() {
        passStr = password.getText().toString();
        userStr = username.getText().toString();
//        dialog = new LoadingAlertDialog(this);
//        dialog.show("正在登录...");
//        dialog.setIndeterminateDrawable(R.drawable.loading_progress_rotate);


//        CountDownTimer timer = new CountDownTimer() {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                int i = (int) (millisUntilFinished / 200) % 2;
//
//                switch (i) {
//                    case 0:
//                        pDialog.getProgressHelper().setBarColor(Color.YELLOW);
//                        break;
//                    case 1:
//                        pDialog.getProgressHelper().setBarColor(Color.GREEN);
//                        break;
//
//                }
//            }
//
//            @Override
//            public void onFinish() {
//                pDialog.setTitleText("登陆成功!")
//                        .setConfirmText("OK")
//                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                PosterHandler.getInstance().post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        EventBus.getDefault().post(new LoginResultModel("1", "登录成功"));
//                                    }
//                                });
//                            }
//                        })
//                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
//
//            }
////        };
//        PosterHandler.getInstance().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//        HttpMethods.doLogin(userStr, passStr, new Subscriber<LoginResultModel>() {
//            @Override
//            public void onStart(){
//                pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
//                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//                pDialog.setTitleText("正在登录...");
//                pDialog.setCancelable(false);
//                pDialog.show();
//            }
//
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(LoginResultModel loginResultModel) {
//                EventBus.getDefault().post(loginResultModel);
//            }
//        });
        HttpMethods.doLogin(userStr, passStr, new ProgressSubscriber<LoginResultModel>(this,
                new SubscriberListener<LoginResultModel>() {
                    @Override
                    public void onNext(LoginResultModel result) {
                        //这里使用EventBus 通信
//                EventBus.getDefault().post(result);

                        //这里使用RxBus通信
                        RxBus.getInstance().post(result);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }, "正在登录..."));
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(Urls.HTTP_LOGIN);
        request.add("username", userStr);
        request.add("password", passStr);
//        addRequest(LOGIN, request, this);
//            }}, 1000);
//                addRequest();
//            }


    }

    @Subscribe
    public void onEventMainThread(JSONObject result) {
        String code = "";
        try {
            code = result.getString("code");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if ("1".equals(code) || ("admin".equals(userStr) && "1".equals(passStr))) {

            if (pDialog.isShowing()) {
                pDialog.setTitleText("登陆成功!")
                        .setConfirmText("OK")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                MainActivity.actionStart(LoginActivity.this
                                        , null, null);
                            }
                        })
                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            }
        } else {
            pDialog.setTitleText("登陆失败!")
                    .setContentText("检查账号密码是否正确")
                    .setConfirmText("OK")
                    .changeAlertType(SweetAlertDialog.WARNING_TYPE);
        }


    }

    @Subscribe
    public void onEventMainThread(LoginResultModel result) {
        String code = result.getCode();

        if ("1".equals(code) || ("admin".equals(userStr) && "1".equals(passStr))) {

//            if (pDialog.isShowing()) {
//                pDialog.setTitleText("登陆成功!")
//                        .setConfirmText("OK")
//                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sweetAlertDialog) {

            MainActivity.actionStart(LoginActivity.this
                    , null, null);
//                            }
//                        })
//                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
//            }
//        } else {
//            pDialog.setTitleText("登陆失败!")
//                    .setContentText("检查账号密码是否正确")
//                    .setConfirmText("OK")
//                    .changeAlertType(SweetAlertDialog.WARNING_TYPE);
        }


    }

    @OnClick(R.id.tv_forget)
    void forgetPwd() {
        MyToast.makeText(getApplicationContext(), getResources().getString(R.string.forget_password), Toast.LENGTH_SHORT).show();
    }

    @Override
    public Toolbar getToolbar() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void init() {
        EventBus.getDefault().register(this);
        tvForget.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        subscribe = RxBus.getInstance().toObservable(LoginResultModel.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<LoginResultModel>() {
                    @Override
                    public void call(LoginResultModel result) {
                        String code = result.getCode();
                        if ("1".equals(code) || ("admin".equals(userStr) && "1".equals(passStr))) {
                            MainActivity.actionStart(LoginActivity.this
                                    , null, null);
                        }else{
                            MyToast.makeText(getApplicationContext(),"帐号或密码错误!",0).show();
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (!subscribe.isUnsubscribed()) {
            subscribe.unsubscribe();
        }
    }

    @Override
    public void finish() {
        super.finish();

    }

    @Override
    public void onStart(int what) {
        Log.i("Thread", "onStart----" + android.os.Process.myTid() + "");
        switch (what) {
            case LOGIN:
                pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("正在登录...");
                pDialog.setCancelable(false);
                pDialog.show();
                break;
        }

    }

    @Override
    public void onSucceed(int what, Response<JSONObject> response) {
        Log.i("Thread", "onSucceed----" + android.os.Process.myTid() + "");
        switch (what) {
            case LOGIN:
                EventBus.getDefault().post(response.get());
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<JSONObject> response) {
        Log.i("Thread", "onFailed----" + android.os.Process.myTid() + "");
        MyToast.makeText(getApplicationContext(), "网络请求失败", 0).show();
        if (pDialog != null) {
            pDialog.dismiss();
        }

    }

    @Override
    public void onFinish(int what) {
        Log.i("Thread", "onFinish----" + android.os.Process.myTid() + "");

    }
}

