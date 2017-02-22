package com.ywp.nice.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ywp.nice.R;
import com.ywp.nice.model.GankFuLiResultModel;
import com.ywp.nice.view.MyToast;

import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/2/9.
 */

public class CollapsingRvSimpAdapter extends BaseItemDraggableAdapter<GankFuLiResultModel, BaseViewHolder> {
    private Context context;

    public CollapsingRvSimpAdapter(Context context, int layoutResId, List<GankFuLiResultModel> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final GankFuLiResultModel item) {
        Glide.with(context).load(item.getUrl()).placeholder(R.mipmap.ic_launcher).error(R.drawable.ic_ico).into((ImageView) helper.getView(R.id.iv_coll_img));
        Random random = new Random();
        int sum = random.nextInt(10);
        if (sum < 3) {
            helper.setText(R.id.tv_coll_title, item.get_id());
        } else if (sum > 3 && sum < 7) {
            helper.setText(R.id.tv_coll_title, item.get_id() + "---" + item.getUrl());
        } else {
            helper.setText(R.id.tv_coll_title, item.get_id() + "---" + item.getUrl() + "---" + item.getCreatedAt() + "---" + item.getPublishedAt());
        }

        View.OnClickListener itemClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyToast.makeText(v.getContext(), "你点了" + item.getUrl(), ((ImageView) helper.getView(R.id.iv_coll_img)).getDrawable(), Toast.LENGTH_SHORT).show();
            }
        };

//       helper.addOnClickListener(R.id.cd_item);

    }
}
