package com.choicemmed.ichoice.healthcheck.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.healthcheck.adddevice.entity.DeviceIconvo;

import java.util.List;


public class recycleViewAdapter extends RecyclerView.Adapter<recycleViewAdapter.viewHolder> {

    public static class viewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView imageView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.select_device_recyclerView_item_editText);
            imageView = itemView.findViewById(R.id.select_device_recyclerView_item_imageView);

        }
    }

    private List<DeviceIconvo> mDatas;
    private recycleViewItemSelectListener listener;

    public recycleViewAdapter(List<DeviceIconvo> data, recycleViewItemSelectListener listener) {
        this.mDatas = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_select_device_item, viewGroup, false);
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        layoutParams.width = (viewGroup.getWidth() / 2);
        viewHolder mViewHolder = new viewHolder(v);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, final int i) {
        viewHolder.title.setText(mDatas.get(i).getIconName());
        viewHolder.imageView.setImageResource(mDatas.get(i).getIconImage());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //item点击事件
                listener.onItemClick(mDatas.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public interface recycleViewItemSelectListener {
        public void onItemClick(DeviceIconvo deviceIconvo);
    }
}
