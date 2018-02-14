package bi.bigroup.life.ui.base;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.arellomobile.mvp.MvpAppCompatActivity;

import bi.bigroup.life.R;
import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.mvp.BaseMvpView;
import bi.bigroup.life.utils.SnackbarUtils;
import bi.bigroup.life.utils.ToastUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseActivity extends MvpAppCompatActivity implements BaseMvpView {
    protected DataLayer dataLayer;
    @BindView(R.id.fl_parent) protected FrameLayout fl_parent;
    @BindView(R.id.pb_indicator) protected ViewGroup pb_indicator;

    protected abstract int getLayoutResourceId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutResourceId() != -1) setContentView(getLayoutResourceId());
        ButterKnife.bind(this);
        dataLayer = DataLayer.getInstance(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    ////////////////////////////////////////////////
    /// BaseMvpView
    ////////////////////////////////////////////////
    @Override
    public void showRequestError(String errorMessage) {
        SnackbarUtils.showSnackbar(fl_parent, errorMessage);
    }

    @Override
    public void showLoadingIndicator(boolean show) {
        pb_indicator.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onInvalidToken() {
        SnackbarUtils.showSnackbar(fl_parent, "UnAuthorized");
    }

    @Override
    public void showNotFoundPlaceholder() {
        SnackbarUtils.showSnackbar(fl_parent, getString(R.string.info_empty_data));
    }

    @Override
    public void showRequestSuccess(int message) {
        ToastUtils.showToast(this, getString(message));
    }
}
