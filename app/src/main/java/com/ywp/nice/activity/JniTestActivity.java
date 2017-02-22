package com.ywp.nice.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ywp.nice.IRemoteService;
import com.ywp.nice.R;
import com.ywp.nice.service.RemoteService;
import com.ywp.nice.utils.StringUtils;
import com.ywp.nice.view.MyToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class JniTestActivity extends AppCompatActivity {

    @BindView(R.id.et_first)
    EditText etFirst;
    @BindView(R.id.et_second)
    EditText etSecond;
    @BindView(R.id.bt_hello)
    Button btHello;
    @BindView(R.id.bt_add)
    Button btAdd;
    @BindView(R.id.tv_show)
    TextView tvShow;
    @BindView(R.id.bt_helloAIDL)
    Button btHelloAIDL;
    @BindView(R.id.bt_pid)
    Button btPid;
    @BindView(R.id.bt_bind)
    Button btBind;
    @BindView(R.id.tv_show2)
    TextView tvShow2;
    private Unbinder bind;

    private boolean isBind = false;

    private IRemoteService mRemoteService;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mRemoteService = IRemoteService.Stub.asInterface(service);
            isBind = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBind = false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni_test);
        bind = ButterKnife.bind(this);

    }

    public static native String helloJni();

    public static native int addCalc(int a, int b);

    static {
        System.loadLibrary("helloJni");
    }

    @OnClick({R.id.bt_hello, R.id.bt_add, R.id.bt_helloAIDL, R.id.bt_pid, R.id.bt_bind})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_hello:
                tvShow.setText(helloJni());
                break;
            case R.id.bt_add:
                int a = 0;
                if (!StringUtils.isEmpty(etFirst.getText().toString().trim())) {
                    a = Integer.valueOf(etFirst.getText().toString().trim());
                }
                int b = 0;
                if (!StringUtils.isEmpty(etSecond.getText().toString().trim())) {
                    b = Integer.valueOf(etSecond.getText().toString().trim());
                }
                tvShow.setText(addCalc(a, b) + "");
                break;
            case R.id.bt_bind:
                Intent intent = new Intent(this, RemoteService.class);
                bindService(intent, connection, BIND_AUTO_CREATE);
                break;
            case R.id.bt_helloAIDL:
                if (isBind) {
                    try {
                        MyToast.makeText(getApplicationContext(), "the msg  is " + mRemoteService.sayHelloAIDL().getMsg(), 1).show();
                        tvShow2.setText("the msg  is " + mRemoteService.sayHelloAIDL().getMsg());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    MyToast.makeText(getApplicationContext(), "请先绑定Service", 1).show();
                }
                break;
            case R.id.bt_pid:
                if (isBind) {
                    try {
                        MyToast.makeText(getApplicationContext(), "the service pid is " + mRemoteService.sayHelloAIDL().getPid(), 1).show();
                        tvShow2.setText("the service pid is " + mRemoteService.sayHelloAIDL().getPid());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    MyToast.makeText(getApplicationContext(), "请先绑定Service", 1).show();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        if(isBind){

        }
    }

}
