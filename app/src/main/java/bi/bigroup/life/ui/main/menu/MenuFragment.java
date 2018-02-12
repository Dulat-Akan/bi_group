package bi.bigroup.life.ui.main.menu;

import android.os.Bundle;

import com.arellomobile.mvp.presenter.InjectPresenter;

import bi.bigroup.life.R;
import bi.bigroup.life.mvp.main.menu.MenuPresenter;
import bi.bigroup.life.mvp.main.menu.MenuView;
import bi.bigroup.life.ui.base.BaseFragment;

public class MenuFragment extends BaseFragment implements MenuView {
    @InjectPresenter
    MenuPresenter mvpPresenter;

    public static MenuFragment newInstance() {
        MenuFragment fragment = new MenuFragment();
        Bundle data = new Bundle();
//        data.putParcelable(FORM_KEY, Parcels.wrap(authForm));
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.frgm_menu;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {
        handleIntent();
    }

    private void handleIntent() {
//        if (getArguments() != null && getArguments().containsKey(FORM_KEY)) {
//            AuthForm form = Parcels.unwrap(getArguments().getParcelable(FORM_KEY));
//            mvpPresenter.init(dataLayer, form);
//        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // MenuView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void successSent() {
    }
}