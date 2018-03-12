package bi.bigroup.life.ui.main.biboard.top_questions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.squareup.picasso.Picasso;

import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.biboard.top_questions.TopQuestions;
import bi.bigroup.life.mvp.main.biboard.top_questions.TopQuestionsPresenter;
import bi.bigroup.life.mvp.main.biboard.top_questions.TopQuestionsView;
import bi.bigroup.life.ui.base.BaseActivity;
import bi.bigroup.life.ui.main.biboard.BricksTop7Adapter;
import bi.bigroup.life.ui.main.feed.news.CommentsAdapter;
import butterknife.BindView;

public class TopQuestionsActivity extends BaseActivity implements TopQuestionsView {
    @InjectPresenter
    TopQuestionsPresenter mvpPresenter;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.floating_menu) FloatingActionsMenu floating_menu;
    @BindView(R.id.lv_top_questions) ListView lv_top_questions;

    private BricksTop7Adapter top7Adapter;
    private CommentsAdapter commentsAdapter;
    private TopAnswersAdapter topAnswersAdapter;
    private TextView tv_author_name;
    private TextView tv_specialty;
    private TextView tv_like_quantity;
    private Picasso picasso;

    public static Intent getIntent(Context context) {
        return new Intent(context, TopQuestionsActivity.class);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_top_questions;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        mvpPresenter.init(this, dataLayer);
        picasso = dataLayer.getPicasso();
        configureListView();
        floating_menu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                startActivity(AddQuestionActivity.getIntent(TopQuestionsActivity.this));
                floating_menu.collapse();
            }

            @Override
            public void onMenuCollapsed() {

            }
        });
    }

    private void configureListView() {
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.adapter_topq_header, lv_top_questions, false);
        // Top 7 questions
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recycler_view = header.findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(layoutManager);
        tv_author_name = header.findViewById(R.id.tv_author_name);
        tv_specialty = header.findViewById(R.id.tv_specialty);
        tv_like_quantity = header.findViewById(R.id.tv_like_quantity);
        top7Adapter = new BricksTop7Adapter(this, picasso);
        top7Adapter.setCallback(this::setAuthorData);
        recycler_view.setAdapter(top7Adapter);

        // Top answers
        RecyclerView rv_top_answers = header.findViewById(R.id.rv_top_answers);
        LinearLayoutManager topAnswersLm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv_top_answers.setLayoutManager(topAnswersLm);
        topAnswersAdapter = new TopAnswersAdapter(this);
        rv_top_answers.setAdapter(topAnswersAdapter);
        lv_top_questions.addHeaderView(header, null, false);

        commentsAdapter = new CommentsAdapter(this, picasso);
        lv_top_questions.setAdapter(commentsAdapter);
    }

    void setAuthorData(TopQuestions question) {
        tv_author_name.setText(question.getAuthorName());
        tv_like_quantity.setText(String.valueOf(question.getLikesQuantity()));
    }

    ///////////////////////////////////////////////////////////////////////////
    // TopQuestionsView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void setTopQuestions(List<TopQuestions> list) {
        top7Adapter.addList(list);
        if (list.size() > 0) {
            setAuthorData(list.get(0));
        }
    }
}