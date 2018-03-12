package bi.bigroup.life.ui.main.biboard.top_questions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.arellomobile.mvp.presenter.InjectPresenter;

import bi.bigroup.life.R;
import bi.bigroup.life.mvp.main.biboard.top_questions.TopQuestionsPresenter;
import bi.bigroup.life.mvp.main.biboard.top_questions.TopQuestionsView;
import bi.bigroup.life.ui.base.BaseActivity;
import butterknife.BindView;

public class TopQuestionsActivity extends BaseActivity implements TopQuestionsView {
    @InjectPresenter
    TopQuestionsPresenter mvpPresenter;

    @BindView(R.id.toolbar) Toolbar toolbar;

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
    }

    ///////////////////////////////////////////////////////////////////////////
    // TopQuestionsView implementation
    ///////////////////////////////////////////////////////////////////////////
}