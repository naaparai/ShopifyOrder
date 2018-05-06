package com.dogpo.shopifyorder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dogpo.shopifyorder.model.DataProvince;
import com.dogpo.shopifyorder.R;
import com.dogpo.shopifyorder.holder.ViewHolderProvince;

import java.util.ArrayList;

public class AdapterProvince extends RecyclerView.Adapter<ViewHolderProvince> {
    Context context;
    ArrayList<DataProvince> data;
    LayoutInflater inflater;

    public AdapterProvince(Context context, ArrayList<DataProvince> data) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;

    }

    @Override
    public ViewHolderProvince onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_province, parent, false);
        ViewHolderProvince viewHolderProvince = new ViewHolderProvince(view);
        return viewHolderProvince;
    }

    @Override
    public void onBindViewHolder(ViewHolderProvince holder, int position) {
        DataProvince dataProvince=data.get(position);
        holder.tvProvince.setText(dataProvince.getCount()+" number of orders from "+dataProvince.getProvince());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
