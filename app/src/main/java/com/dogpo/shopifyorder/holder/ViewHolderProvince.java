package com.dogpo.shopifyorder.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dogpo.shopifyorder.R;

public class ViewHolderProvince extends RecyclerView.ViewHolder {
    public TextView tvProvince;
    public ViewHolderProvince(View itemView) {
        super(itemView);
        tvProvince=itemView.findViewById(R.id.tv_province);
    }
}
