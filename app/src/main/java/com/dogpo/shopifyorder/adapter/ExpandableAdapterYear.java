package com.dogpo.shopifyorder.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dogpo.shopifyorder.model.DataChildYear;
import com.dogpo.shopifyorder.model.DataYear;
import com.dogpo.shopifyorder.R;
import com.dogpo.shopifyorder.util.Util;

import java.util.HashMap;
import java.util.List;

public class ExpandableAdapterYear extends BaseExpandableListAdapter {
    private Context _context;
    private List<DataYear> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<Integer, List<DataChildYear>> _listDataChild;

    public ExpandableAdapterYear(Context context, List<DataYear> listDataHeader,
                                 HashMap<Integer, List<DataChildYear>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition).getYear())
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final DataChildYear dataChildYear = (DataChildYear) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_year, null);
        }

        TextView tvItemName = (TextView) convertView
                .findViewById(R.id.tv_item_name);

        TextView tvItemPrice = convertView.findViewById(R.id.tv_item_price);
        TextView tvCustomerName = convertView.findViewById(R.id.tv_customer_name);
        TextView tvOrderDate = convertView.findViewById(R.id.tv_order_date);
        tvCustomerName.setText("Customer Name: "+dataChildYear.getCustomerName());
        tvItemPrice.setText("Total Price: "+dataChildYear.getItemPrice()+" CAD");
        tvOrderDate.setText("Order Date: "+Util.convertServerDateToReadableDate(dataChildYear.getOrderDate()));
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition).getYear())
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
        DataYear dataYear = (DataYear) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_year, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.tv_year);
        ImageView ivExpand = convertView.findViewById(R.id.iv_expand);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(dataYear.getYear() + " : " + dataYear.getCount() + " orders");

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
