package bi.bigroup.life.ui.main.menu;

import android.os.Bundle;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;

import bi.bigroup.life.R;
import bi.bigroup.life.mvp.main.menu.MenuPresenter;
import bi.bigroup.life.mvp.main.menu.MenuView;
import bi.bigroup.life.ui.base.BaseFragment;
import bi.bigroup.life.utils.LOTimber;

public class MenuFragment extends BaseFragment implements MenuView {
    @InjectPresenter
    MenuPresenter mvpPresenter;

    public static MenuFragment newInstance() {
        return new MenuFragment();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.frgm_menu;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState, View view) {
        handleIntent();
        RowViewHolder v1 = new RowViewHolder(view.findViewById(R.id.v1));
        v1.bindHolder(R.string.menu_title_1, R.string.menu_desc_1);
        v1.setCallback(() -> LOTimber.d("asldkasjd clicked 1"));

        RowViewHolder v2 = new RowViewHolder(view.findViewById(R.id.v2));
        v2.bindHolder(R.string.menu_title_2, R.string.menu_desc_2);
        v2.setCallback(() -> LOTimber.d("asldkasjd clicked 2"));

        RowViewHolder v3 = new RowViewHolder(view.findViewById(R.id.v3));
        v3.bindHolder(R.string.menu_title_3, R.string.menu_desc_3);
        v3.setCallback(() -> LOTimber.d("asldkasjd clicked 3"));

        RowViewHolder v4 = new RowViewHolder(view.findViewById(R.id.v4));
        v4.bindHolder(R.string.menu_title_4, R.string.menu_desc_4);
        v4.setCallback(() -> LOTimber.d("asldkasjd clicked 4"));
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