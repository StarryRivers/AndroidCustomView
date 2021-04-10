package com.zjjy.buildstudyzj.ui.subject;

import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.noober.background.view.BLImageButton;
import com.zjjy.buildstudyzj.R;
import com.zjjy.buildstudyzj.adapter.ChapterExpandableAdapter;
import com.zjjy.buildstudyzj.contract.QuestionContract;
import com.zjjy.buildstudyzj.model.FatherMenuBean;
import com.zjjy.buildstudyzj.presenter.QuestionPresenter;
import com.zjjy.buildstudyzj.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * add ChapterExercises Features Modules
 *
 * @author StarryRivers
 */
public class ChapterActivity extends BaseActivity implements QuestionContract.ChapterView {

    /**
     * ActionBar Title
     */
    @BindView(R.id.top_center_title)
    TextView top_center_title;
    @BindView(R.id.back_btn)
    BLImageButton back_btn;
    @BindView(R.id.chapter_elv)
    ExpandableListView chapterExpandable;

    /**
     * 章节ExpandableAdapter
     */
    private ChapterExpandableAdapter chapterExpandableAdapter;

    /**
     * Adapter需要List
     */
    private ArrayList<FatherMenuBean> totalList;

    private QuestionPresenter questionPresenter;


    @Override
    public void getMContext() {

    }

    @Override
    public int inflateContentView() {
        return R.layout.activity_chapter;
    }

    @Override
    public void configData() {
        totalList = new ArrayList<>();
    }

    @Override
    public void initView() {
        chapterExpandableAdapter = new ChapterExpandableAdapter(this);
        chapterExpandable.setAdapter(chapterExpandableAdapter);
    }

    @Override
    public void createBindNetReq() {
        top_center_title.setText(R.string.chapterExercises);
        questionPresenter = new QuestionPresenter(this, this);
        // 获取章节练习List
        questionPresenter.getChapterList();
    }

    @OnClick({R.id.back_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 获取章节练习数据回调
     *
     * @param result result
     */
    @Override
    public void setChapterList(List<FatherMenuBean> result) {
        if (result != null && result.size() > 0) {
            totalList = (ArrayList<FatherMenuBean>) result;
            // Adapter设置数据源
            chapterExpandableAdapter.setTotalList(totalList);
            chapterExpandableAdapter.notifyDataSetChanged();
        } else {
            ToastUtils.showShort(R.string.noData);
        }
    }

    /**
     * 获取数据为空回调
     */
    @Override
    public void setChapterEmpty(String msg) {
        ToastUtils.showShort(msg.isEmpty() ? "暂无数据" : msg);
    }
}
