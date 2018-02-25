package bi.bigroup.life.ui.main.publication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.arellomobile.mvp.presenter.InjectPresenter;

import bi.bigroup.life.R;
import bi.bigroup.life.mvp.main.publication.PublicationPresenter;
import bi.bigroup.life.mvp.main.publication.PublicationView;
import bi.bigroup.life.ui.base.BaseActivity;
import butterknife.OnClick;

public class PublicationActivity extends BaseActivity implements PublicationView {
    @InjectPresenter
    PublicationPresenter mvpPresenter;

    public static Intent getIntent(Context context) {
        return new Intent(context, PublicationActivity.class);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_publication;
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
    // PublicationView implementation
    ///////////////////////////////////////////////////////////////////////////
}