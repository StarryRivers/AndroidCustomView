package com.zjjy.buildstudyzj.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.zjjy.buildstudyzj.R;
import com.zjjy.buildstudyzj.model.ChildMenuBean;

import java.util.ArrayList;

/**
 * 章节练习，三级Adapter
 *
 * @author StarryRivers
 */
public class ChapterExpandableLowAdapter extends BaseExpandableListAdapter {

    private Context context;
    /**
     * 上级传入的数据
     */
    private ArrayList<ChildMenuBean> totalList;

    /**
     * 子级目录List
     */
    private ArrayList<ChildMenuBean> childChapterList = new ArrayList<>();

    public ChapterExpandableLowAdapter(Context context) {
        this.context = context;
    }

    public void setTotalList(ArrayList<ChildMenuBean> totalList) {
        this.totalList = totalList;
        childChapterList = totalList;
    }

    @Override
    public int getGroupCount() {
        // 父菜单长度
        return childChapterList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // 子菜单长度
        return childChapterList.get(groupPosition).getThird().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return childChapterList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (childChapterList.get(groupPosition).getThird() != null && childChapterList.get(groupPosition).getThird().size() > 0) {
            return childChapterList.get(groupPosition).getThird().get(childPosition);
        }
        return 0;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ChapterExpandableLowAdapter.GroupViewHolder groupHolder;
        // 尽可能重用旧view处理
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_expandable_child_view, parent, false);
            groupHolder = new ChapterExpandableLowAdapter.GroupViewHolder();
            groupHolder.groupTitle = convertView.findViewById(R.id.adapter_child_title);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (ChapterExpandableLowAdapter.GroupViewHolder) convertView.getTag();
        }
        // 设置title
        groupHolder.groupTitle.setText(childChapterList.get(groupPosition).getName());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChapterExpandableLowAdapter.ChildViewHolder childHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_expandable_grandson_view, parent, false);
            childHolder = new ChapterExpandableLowAdapter.ChildViewHolder();
            childHolder.childTitle = convertView.findViewById(R.id.adapter_grandson_title);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChapterExpandableLowAdapter.ChildViewHolder) convertView.getTag();
        }
        if (childChapterList.get(groupPosition).getThird() != null && childChapterList.get(groupPosition).getThird().size() > 0) {
            childHolder.childTitle.setText(childChapterList.get(groupPosition).getThird().get(childPosition).getName());
        }
        return convertView;
    }

    /**
     * 子列表是否可选，如果为false，则子项不能触发点击事件，默认为false
     * @param groupPosition groupPosition
     * @param childPosition childPosition
     * @return result
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /**
     * 子级菜单的ViewHolder
     */
    static class GroupViewHolder {
        TextView groupTitle;
    }

    /**
     * 孙子级菜单的ViewHolder
     */
    static class ChildViewHolder {
        TextView childTitle;
    }
}
