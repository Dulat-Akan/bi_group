package bi.bigroup.life.ui.main.office;

import android.os.Bundle;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;

import bi.bigroup.life.R;
import bi.bigroup.life.mvp.main.office.OfficePresenter;
import bi.bigroup.life.mvp.main.office.OfficeView;
import bi.bigroup.life.ui.base.BaseFragment;

public class OfficeFragment extends BaseFragment implements OfficeView {
    @InjectPresenter
    OfficePresenter mvpPresenter;

    public static OfficeFragment newInstance() {
        OfficeFragment fragment = new OfficeFragment();
        Bundle data = new Bundle();
//        data.putParcelable(FORM_KEY, Parcels.wrap(authForm));
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.frgm_office;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState, View view) {
        handleIntent();
    }

    private void handleIntent() {
//        if (getArguments() != null && getArguments().containsKey(FORM_KEY)) {
//            AuthForm form = Parcels.unwrap(getArguments().getParcelable(FORM_KEY));
//            mvpPresenter.init(dataLayer, form);
//        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // OfficeView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void successSent() {
    }
}