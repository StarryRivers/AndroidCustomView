package com.zjjy.buildstudyzj.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.zjjy.buildstudyzj.R;
import com.zjjy.buildstudyzj.model.FatherMenuBean;
import com.zjjy.buildstudyzj.ui.subject.AnswerActivity;
import com.zjjy.buildstudyzj.util.CommonUtil;
import com.zjjy.buildstudyzj.widget.CustomExpandableListView;

import java.util.ArrayList;

import static com.zjjy.buildstudyzj.config.Constant.CHAPTER_TAG;

/**
 * 章节练习模块三级折叠菜单的中间级Adapter
 *
 * @author StarryRivers
 */
public class ChapterExpandableAdapter extends BaseExpandableListAdapter {
    private Context context;
    /**
     * 请求得到的数据
     */
    private ArrayList<FatherMenuBean> totalList;
    /**
     * 一级目录List
     */
    private ArrayList<FatherMenuBean> fatherChapterList = new ArrayList<>();

    public ChapterExpandableAdapter(Context context) {
        this.context = context;
    }

    public void setTotalList(ArrayList<FatherMenuBean> totalList) {
        this.totalList = totalList;
        fatherChapterList = totalList;
    }

    @Override
    public int getGroupCount() {
        // 父菜单长度
        return fatherChapterList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // 子菜单长度，嵌套所以返回只能1
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return fatherChapterList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return fatherChapterList.get(groupPosition).getSec().get(childPosition);
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
        GroupViewHolder groupHolder;
        // 尽可能重用旧view处理
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_expandable_group_view, parent, false);
            groupHolder = new GroupViewHolder();
            groupHolder.groupTitle = convertView.findViewById(R.id.adapter_title);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupViewHolder) convertView.getTag();
        }
        // 设置title
        groupHolder.groupTitle.setText(fatherChapterList.get(groupPosition).getName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new CustomExpandableListView(context);
        }
        CustomExpandableListView expandableListView = (CustomExpandableListView) convertView;
        // 加载子级Adapter
        ChapterExpandableLowAdapter lowAdapter = new ChapterExpandableLowAdapter(context);
        lowAdapter.setTotalList(fatherChapterList.get(groupPosition).getSec());
        expandableListView.setAdapter(lowAdapter);
        /*expandableListView.setDividerHeight(10);
        expandableListView.setDivider(context.getResources().getDrawable(R.color.alivc_green));*/
        if (fatherChapterList.get(groupPosition).getSec().get(childPosition).getThird().size() == 0) {
            expandableListView.setGroupIndicator(null);
        } /*else {
            expandableListView.setGroupIndicator();
        }*/
        // 本身的父级，相当于三级目录的子级监听
        expandableListView.setOnGroupClickListener((parent12, v, groupPosition12, id) -> {
            // 如果第三层size为0，就请求章节题目
            if (fatherChapterList != null && fatherChapterList.size() > 0 && fatherChapterList.get(groupPosition).getSec().get(groupPosition12).getThird().size() == 0) {
                Bundle bundle = new Bundle();
                bundle.putInt("type", CHAPTER_TAG);
                bundle.putString("chapterId", String.valueOf(fatherChapterList.get(groupPosition).getSec().get(groupPosition12).getId()));
                CommonUtil.startActivity(context, AnswerActivity.class, bundle);
            }
            // 存在第三级数据，事件分发机制继续想下传递
            return false;
        });
        expandableListView.setOnChildClickListener((parent1, v, groupPosition1, childPosition1, id) -> {
            Bundle bundle = new Bundle();
            bundle.putInt("type", CHAPTER_TAG);
            bundle.putString("chapterId", String.valueOf(fatherChapterList.get(groupPosition).getSec().get(groupPosition1).getThird().get(childPosition1).getId()));
            CommonUtil.startActivity(context, AnswerActivity.class, bundle);
            return true;
        });
        return expandableListView;
    }

    /**
     * 子列表是否可选，如果为false，则子项不能触发点击事件，默认为false
     *
     * @param groupPosition groupPosition
     * @param childPosition childPosition
     * @return result
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /**
     * 父级菜单的ViewHolder
     */
    static class GroupViewHolder {
        TextView groupTitle;
    }
}
