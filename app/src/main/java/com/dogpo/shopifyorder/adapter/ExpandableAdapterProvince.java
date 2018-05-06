package com.dogpo.shopifyorder.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dogpo.shopifyorder.R;
import com.dogpo.shopifyorder.model.DataChildProvince;
import com.dogpo.shopifyorder.model.DataChildYear;
import com.dogpo.shopifyorder.util.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpandableAdapterProvince extends BaseExpandableListAdapter {
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private Map<String, List<DataChildProvince>> _listDataChild;

    public ExpandableAdapterProvince(Context context, List<String> listDataHeader,
                                     Map<String, List<DataChildProvince>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final DataChildProvince dataChildProvince = (DataChildProvince) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_province, null);
        }

        TextView tvItemName = (TextView) convertView
                .findViewById(R.id.tv_item_name);

        TextView tvItemPrice = convertView.findViewById(R.id.tv_price);
        TextView tvCustomerName = convertView.findViewById(R.id.tv_customer_name);
        TextView tvOrderDate = convertView.findViewById(R.id.tv_order_date);
        TextView tvOrderNumber=convertView.findViewById(R.id.tv_order_id);
        tvCustomerName.setText("Customer Name: "+dataChildProvince.getCustomerName());
        tvItemPrice.setText("Total Prize: "+dataChildProvince.getPrice()+" CAD");
        tvOrderDate.setText("Order Date: "+ Util.convertServerDateToReadableDate(dataChildProvince.getOrderDate()));
        tvOrderNumber.setText("Order Number: "+dataChildProvince.getOrderNumber());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String  province =  (String)getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_province, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.tv_province);
        ImageView ivExpand = convertView.findViewById(R.id.iv_expand);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(province);

        if (isExpanded) {
            ivExpand.setImageResource(R.drawable.ic_chevron_up);
        } else {
            ivExpand.setImageResource(R.drawable.ic_chevron_down);
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
