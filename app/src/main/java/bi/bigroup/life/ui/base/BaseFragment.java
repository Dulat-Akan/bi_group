package bi.bigroup.life.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.arellomobile.mvp.MvpAppCompatFragment;

import bi.bigroup.life.R;
import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.mvp.BaseMvpView;
import bi.bigroup.life.utils.SnackbarUtils;
import bi.bigroup.life.utils.ToastUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseFragment extends MvpAppCompatFragment implements BaseMvpView {
    @BindView(R.id.fl_parent) protected FrameLayout fl_parent;
    @BindView(R.id.pb_indicator) protected ViewGroup pb_indicator;
    protected DataLayer dataLayer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataLayer = DataLayer.getInstance(getContext());
    }

    protected abstract int getLayoutResourceId();

    protected abstract void onViewCreated(Bundle savedInstanceState, View view);

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResourceId(), container, false);
        ButterKnife.bind(this, view);
        onViewCreated(savedInstanceState, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    ///////////////////////////////////////////////////////////////////////////
    // BaseMvpView implementation                                           ///
    ///////////////////////////////////////////////////////////////////////////

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
//        InvalidToken.wipeOut(getContext(), dataLayer);
    }

    @Override
    public void showNotFoundPlaceholder() {
        SnackbarUtils.showSnackbar(fl_parent, getString(R.string.info_empty_data));
    }

    @Override
    public void showRequestSuccess(int message) {
        ToastUtils.showToast(getContext(), getString(message));
    }
}
