package com.zjjy.buildstudyzj.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * Custom ExpandableListView for fix incomplete display data in Nested by StarryRivers
 *
 * @author StarryRivers
 */
public class CustomExpandableListView extends ExpandableListView {
    public CustomExpandableListView(Context context) {
        super(context);
    }

    public CustomExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 处理嵌套场景下，数据显示不完整的问题，更换绘制高度
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST));
    }
}
