package bi.bigroup.life.ui.main.suggestion;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.arellomobile.mvp.presenter.InjectPresenter;

import bi.bigroup.life.R;
import bi.bigroup.life.mvp.main.suggestion.NewSuggestionPresenter;
import bi.bigroup.life.mvp.main.suggestion.NewSuggestionView;
import bi.bigroup.life.ui.base.BaseActivity;
import butterknife.OnClick;

public class NewSuggestionActivity extends BaseActivity implements NewSuggestionView {
    @InjectPresenter
    NewSuggestionPresenter mvpPresenter;

    public static Intent getIntent(Context context) {
        return new Intent(context, NewSuggestionActivity.class);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_new_suggestion;
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