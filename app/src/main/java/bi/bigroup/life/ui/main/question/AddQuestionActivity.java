package bi.bigroup.life.ui.main.question;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.arellomobile.mvp.presenter.InjectPresenter;

import bi.bigroup.life.R;
import bi.bigroup.life.mvp.main.question.AddQuestionPresenter;
import bi.bigroup.life.mvp.main.question.AddQuestionView;
import bi.bigroup.life.ui.base.BaseActivity;
import butterknife.OnClick;

public class AddQuestionActivity extends BaseActivity implements AddQuestionView {
    @InjectPresenter
    AddQuestionPresenter mvpPresenter;

    public static Intent getIntent(Context context) {
        return new Intent(context, AddQuestionActivity.class);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_add_question;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvpPresenter.init(this, dataLayer);
    }

    @OnClick(R.id.img_close)
    void onCloseClick() {
        finish();
    }

    ///////////////////////////////////////////////////////////////////////////
    // AddQuestionView implementation
    ///////////////////////////////////////////////////////////////////////////
}