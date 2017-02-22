package com.ywp.nice.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.ywp.nice.IRemoteService;
import com.ywp.nice.HelloAIDL;


/**
 * Created by Administrator on 2017/2/21.
 */

public class RemoteService extends Service {
    IRemoteService.Stub mBinder =  new IRemoteService.Stub() {
        @Override
        public HelloAIDL sayHelloAIDL() throws RemoteException {
            return new HelloAIDL("msg from service at Thread " + Thread.currentThread().toString() + "\n" +
                    "tid is " + Thread.currentThread().getId() + "\n" +
                    "main thread id is " + getMainLooper().getThread().getId(), Process.myPid());
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


}
