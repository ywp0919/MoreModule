package com.ywp.nice;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/2/21.
 */

public class HelloAIDL implements Parcelable{
    private String msg;
    private int pid;

    public HelloAIDL(String msg, int pid) {
        this.msg = msg;
        this.pid = pid;
    }

    protected HelloAIDL(Parcel in) {
        msg = in.readString();
        pid = in.readInt();
    }



    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(msg);
        dest.writeInt(pid);
    }

    /**
     * 参数是一个Parcel,用它来存储与传输数据
     * @param dest
     */
    public void readFromParcel(Parcel dest) {
        //注意，此处的读值顺序应当是和writeToParcel()方法中一致的
        msg = dest.readString();
        pid = dest.readInt();
    }

    public static final Creator<HelloAIDL> CREATOR = new Creator<HelloAIDL>() {
        @Override
        public HelloAIDL createFromParcel(Parcel in) {
            return new HelloAIDL(in);
        }

        @Override
        public HelloAIDL[] newArray(int size) {
            return new HelloAIDL[size];
        }
    };
}
