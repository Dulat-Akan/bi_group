package bi.bigroup.life.ui.main.office.tasks_sdesk.add_sdesk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.arellomobile.mvp.presenter.InjectPresenter;

import bi.bigroup.life.R;
import bi.bigroup.life.mvp.main.office.tasks_sdesk.add_sdesk.AddSdeskPresenter;
import bi.bigroup.life.mvp.main.office.tasks_sdesk.add_sdesk.AddSdeskView;
import bi.bigroup.life.ui.base.BaseActivity;
import butterknife.OnClick;

public class AddSdeskActivity extends BaseActivity implements AddSdeskView {
    @InjectPresenter
    AddSdeskPresenter mvpPresenter;

    public static Intent getIntent(Context context) {
        return new Intent(context, AddSdeskActivity.class);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_add_sdesk;
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
    // AddSdeskView implementation
    ///////////////////////////////////////////////////////////////////////////
}