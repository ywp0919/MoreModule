package com.ywp.nice.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ywp.nice.R;
import com.ywp.nice.view.MyToast;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/1/5.
 */

public class MsgRvAdapter extends RecyclerView.Adapter<MsgRvAdapter.ViewHolder> {
    private List<String> mList;

    static class ViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        CircleImageView img;
        TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            img = (CircleImageView) itemView.findViewById(R.id.iv_msg_img);
            text = (TextView) itemView.findViewById(R.id.item_msg_text);
        }
    }

    public MsgRvAdapter(List<String> list) {
        mList = list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler_msg, parent, false);
        ViewHolder holder = new ViewHolder(rootView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.img.setImageResource(R.drawable.top_ico);
        holder.text.setText(mList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyToast.makeText(v.getContext(),"你点了"+position,holder.img.getDrawable(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }
}
