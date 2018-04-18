package bi.bigroup.life.ui.main.feed.questionnaires;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.feed.questionnaire.Questionnaire;
import bi.bigroup.life.mvp.main.feed.questionnaires.QuestStatisticsPresenter;
import bi.bigroup.life.mvp.main.feed.questionnaires.QuestStatisticsView;
import bi.bigroup.life.ui.base.BaseSwipeRefreshActivity;
import butterknife.BindView;
import butterknife.OnClick;

import static android.support.customtabs.CustomTabsIntent.KEY_ID;

public class QuestStatisticsActivity extends BaseSwipeRefreshActivity implements QuestStatisticsView {
    @InjectPresenter
    QuestStatisticsPresenter mvpPresenter;
    private QuestStatisticsAdapter mAdapter;
    @BindView(R.id.tv_title) TextView tv_title;
    private String questionnaireId;

    public static Intent getIntent(Context context) {
        return new Intent(context, QuestStatisticsActivity.class);
    }

    public static Intent getIntent(Context context, String id) {
        Intent intent = new Intent(context, QuestStatisticsActivity.class);
        intent.putExtra(KEY_ID, id);
        return intent;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_quest_statistics;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvpPresenter.init(this, dataLayer);
        handleIntent();
        configureRecyclerView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mvpPresenter.onDestroyView();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            questionnaireId = intent.getStringExtra(KEY_ID);
            mvpPresenter.getQuestStatistics(questionnaireId,false);
        }
    }

    @OnClick(R.id.img_close)
    void onCloseClick() {
        finish();
    }

    protected void configureRecyclerView() {
        super.configureRecyclerView();
        mAdapter = new QuestStatisticsAdapter(this);
//        mAdapter.setCallback(area -> startActivity(AreaDetailActivity.getIntent(this, area.id)));
        recycler_view.setAdapter(mAdapter);
        recycler_view.setHasFixedSize(true);
    }

    @Override
    protected void swipeToRefresh() {
        mvpPresenter.getQuestStatistics(questionnaireId, true);
    }

    ///////////////////////////////////////////////////////////////////////////
    // QuestStatisticsView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void setQuestionnaireList(List<Questionnaire> list) {
        mAdapter.clearData();
        mAdapter.addQuestionnaireList(list);
    }

    @Override
    public void addQuestionnaireList(List<Questionnaire> list) {
        mAdapter.addQuestionnaireList(list);
    }
}