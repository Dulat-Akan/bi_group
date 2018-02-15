package bi.bigroup.life.ui.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import net.cachapa.expandablelayout.ExpandableLayout;

import bi.bigroup.life.R;
import bi.bigroup.life.mvp.profile.ProfileDataPresenter;
import bi.bigroup.life.mvp.profile.ProfileDataView;
import bi.bigroup.life.ui.base.BaseFragment;
import bi.bigroup.life.utils.LOTimber;
import butterknife.BindView;
import butterknife.OnClick;

public class ProfileDataFragment extends BaseFragment implements ProfileDataView {
    @InjectPresenter
    ProfileDataPresenter mvpPresenter;
    @BindView(R.id.ll_hidden_block) LinearLayout ll_hidden_block;
    @BindView(R.id.exp_layout) ExpandableLayout exp_layout;
    @BindView(R.id.tv_hide_show) TextView tv_hide_show;

    public static ProfileDataFragment newInstance() {
        ProfileDataFragment fragment = new ProfileDataFragment();
        Bundle data = new Bundle();
//        data.putParcelable(FORM_KEY, Parcels.wrap(authForm));
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.frgm_profile_data;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState, View view) {
        handleIntent();

        RowViewHolder v1 = new RowViewHolder(view.findViewById(R.id.v1));
        v1.bindHolder(R.string.prof_data_sub_company, R.drawable.domain);
        v1.setCallback(() -> LOTimber.d("asldkasjd clicked 1"));

        RowViewHolder v2 = new RowViewHolder(view.findViewById(R.id.v2));
        v2.bindHolder(R.string.prof_data_mobile, R.drawable.mobile);
        v2.setCallback(() -> LOTimber.d("asldkasjd clicked 2"));

        RowViewHolder v3 = new RowViewHolder(view.findViewById(R.id.v3));
        v3.bindHolder(R.string.prof_data_email, R.drawable.mail);
        v3.setCallback(() -> LOTimber.d("asldkasjd clicked 3"));

        RowViewHolder v4 = new RowViewHolder(view.findViewById(R.id.v4));
        v4.bindHolder(R.string.prof_data_work_phone, R.drawable.phone_inactive);
        v4.setCallback(() -> LOTimber.d("asldkasjd clicked 4"));

    }

    private void handleIntent() {
//        if (getArguments() != null && getArguments().containsKey(FORM_KEY)) {
//            AuthForm form = Parcels.unwrap(getArguments().getParcelable(FORM_KEY));
//            mvpPresenter.init(dataLayer, form);
//        }
    }

    @OnClick(R.id.ll_extra_info)
    void onExtraInfoClick() {
        if (exp_layout.isExpanded()) {
            tv_hide_show.setText(getString(R.string.prof_data_extra_info));
            exp_layout.collapse();
        } else {
            tv_hide_show.setText(getString(R.string.prof_data_hide_extra_info));
            exp_layout.expand();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // ProfileDataView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onAuthorizationSuccess() {

    }
}