package com.ywp.nice.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ywp.nice.R;
import com.ywp.nice.model.GankFuLiResultModel;

import java.util.List;

/**
 * Created by Administrator on 2017/2/9.
 */

public class MsgRvSimpleAdapter extends BaseItemDraggableAdapter<GankFuLiResultModel, BaseViewHolder> {
    private Context context;

    public MsgRvSimpleAdapter(Context context, int layoutResId, List<GankFuLiResultModel> data) {
        super(layoutResId, data);
        this.context = context;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final GankFuLiResultModel item) {
        helper.setText(R.id.item_msg_text, item.get_id());
//        helper.setImageResource(R.id.iv_msg_img,R.drawable.top_ico);
        Glide.with(context).load(item.getUrl()).error(R.drawable.top_ico).centerCrop().into((ImageView) helper.getView(R.id.iv_msg_img));
//        View.OnClickListener itemClick = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MyToast.makeText(v.getContext(), "你点了" + item.getUrl(), ((ImageView) helper.getView(R.id.iv_msg_img)).getDrawable(), Toast.LENGTH_SHORT).show();
//            }
//        };

//        helper.setOnClickListener(R.id.cd_item, itemClick);

        helper.addOnClickListener(R.id.iv_msg_img).addOnLongClickListener(R.id.iv_msg_img);
    }
}
