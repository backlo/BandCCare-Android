package com.example.hansung.band_cctv;


import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hansung.band_cctv.util.RtspViewPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    public List<String> parentList;
    private HashMap<String, String> childList;
    private RtspViewPlayer playView;
    private RelativeLayout surfaceView;

    public String uri;

    public ExpandableListAdapter(Context context, ArrayList<String> parentList, HashMap<String, String> childList) {
        this.context = context;
        this.parentList = parentList;
        this.childList = childList;
    }

    ;

    @Override
    public Object getChild(int groupPosition, int childPosititon) {


        return this.childList.get(this.parentList.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        uri = this.childList.get(this.parentList.get(groupPosition));
        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.child_listview, null);
        }
        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.test_text);
        txtListChild.setText(childText);

    /*    playView = new RtspViewPlayer(convertView.getContext(), uri);
        surfaceView = (RelativeLayout)convertView.findViewById(R.id.surface_video);
        surfaceView.addView(playView);*/

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.childList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.parentList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.parentList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.parent_listview, null);
        }

        TextView title = (TextView) convertView
                .findViewById(R.id.parent_textView);
        title.setTypeface(null, Typeface.BOLD);
        title.setText(headerTitle);

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
