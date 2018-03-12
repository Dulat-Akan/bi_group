package bi.bigroup.life.ui.main.biboard.top_questions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import bi.bigroup.life.R;
import bi.bigroup.life.mvp.main.biboard.top_questions.TopQuestionsPresenter;
import bi.bigroup.life.mvp.main.biboard.top_questions.TopQuestionsView;
import bi.bigroup.life.ui.base.BaseActivity;
import butterknife.BindView;

public class TopQuestionsActivity extends BaseActivity implements TopQuestionsView {
    @InjectPresenter
    TopQuestionsPresenter mvpPresenter;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.floating_menu) FloatingActionsMenu floating_menu;

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

    ///////////////////////////////////////////////////////////////////////////
    // TopQuestionsView implementation
    ///////////////////////////////////////////////////////////////////////////
}