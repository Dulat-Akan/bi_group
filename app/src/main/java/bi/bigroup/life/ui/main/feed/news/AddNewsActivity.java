package bi.bigroup.life.ui.main.feed.news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.arellomobile.mvp.presenter.InjectPresenter;

import bi.bigroup.life.R;
import bi.bigroup.life.mvp.main.feed.news.AddNewsPresenter;
import bi.bigroup.life.mvp.main.feed.news.AddNewsView;
import bi.bigroup.life.ui.base.BaseActivity;
import butterknife.OnClick;

public class AddNewsActivity extends BaseActivity implements AddNewsView {
    @InjectPresenter
    AddNewsPresenter mvpPresenter;

    public static Intent getIntent(Context context) {
        return new Intent(context, AddNewsActivity.class);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_news_add;
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
    // AddNewsView implementation
    ///////////////////////////////////////////////////////////////////////////
}