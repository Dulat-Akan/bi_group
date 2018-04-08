package bi.bigroup.life.ui.main.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.arellomobile.mvp.presenter.InjectPresenter;

import bi.bigroup.life.R;
import bi.bigroup.life.mvp.main.menu.DevelopingStagePresenter;
import bi.bigroup.life.mvp.main.menu.DevelopingStageView;
import bi.bigroup.life.ui.base.BaseFragmentActivity;
import butterknife.BindView;

import static bi.bigroup.life.utils.StringUtils.EMPTY_STR;

public class DevelopingStageActivity extends BaseFragmentActivity implements DevelopingStageView {
    @InjectPresenter
    DevelopingStagePresenter mvpPresenter;

    @BindView(R.id.toolbar) Toolbar toolbar;

    public static Intent getIntent(Context context) {
        return new Intent(context, DevelopingStageActivity.class);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_developing_stage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        mvpPresenter.init(this, dataLayer);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    ///////////////////////////////////////////////////////////////////////////
    // DevelopingStageView implementation
    ///////////////////////////////////////////////////////////////////////////
}
