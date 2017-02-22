package com.ywp.nice.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ywp.nice.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ywp on 2017/1/5.
 */

public class MyToast {

    private static Toast mToast = null;

    private MyToast(Context context, CharSequence cs, int duration) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
        CircleImageView toast_ico = (CircleImageView) rootView.findViewById(R.id.toast_ico);
        TextView toast_text = (TextView) rootView.findViewById(R.id.toast_text);
        toast_text.setText(cs);
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = new Toast(context);
        mToast.setView(rootView);
        mToast.setDuration(duration);
    }

    private MyToast(Context context, CharSequence cs, Drawable drawable, int duration) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
        CircleImageView toast_ico = (CircleImageView) rootView.findViewById(R.id.toast_ico);
        toast_ico.setImageDrawable(drawable);
        TextView toast_text = (TextView) rootView.findViewById(R.id.toast_text);
        toast_text.setText(cs);
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = new Toast(context);
        mToast.setView(rootView);
        mToast.setDuration(duration);
    }
    private MyToast(Context context, CharSequence cs, Bitmap bitmap, int duration) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
        CircleImageView toast_ico = (CircleImageView) rootView.findViewById(R.id.toast_ico);
        toast_ico.setImageBitmap(bitmap);
        TextView toast_text = (TextView) rootView.findViewById(R.id.toast_text);
        toast_text.setText(cs);
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = new Toast(context);
        mToast.setView(rootView);
        mToast.setDuration(duration);
    }

    private MyToast(Context context, CharSequence cs, int imgSrc, int duration) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
        CircleImageView toast_ico = (CircleImageView) rootView.findViewById(R.id.toast_ico);
        toast_ico.setImageResource(imgSrc);
        TextView toast_text = (TextView) rootView.findViewById(R.id.toast_text);
        toast_text.setText(cs);
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = new Toast(context);
        mToast.setView(rootView);
        mToast.setDuration(duration);
    }

    public static MyToast makeText(Context context, CharSequence cs, int duration) {
        return new MyToast(context, cs, duration);
    }

    public static MyToast makeText(Context context, CharSequence cs, Drawable drawable, int duration) {
        return new MyToast(context, cs, drawable, duration);
    }

    public static MyToast makeText(Context context, CharSequence cs, Bitmap bitmap, int duration) {
        return new MyToast(context, cs, bitmap, duration);
    }

    public static MyToast makeText(Context context, CharSequence cs, int imgSrc, int duration) {
        return new MyToast(context, cs, imgSrc, duration);
    }


    public void show() {
        if (mToast != null) {
            mToast.show();
        }
    }

    public void setGravity(int gravity, int xOffset, int yOffset) {
        if (mToast != null) {
            mToast.setGravity(gravity, xOffset, yOffset);
        }
    }
}
