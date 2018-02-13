package bi.bigroup.life.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.arellomobile.mvp.presenter.InjectPresenter;

import bi.bigroup.life.R;
import bi.bigroup.life.mvp.profile.ProfilePresenter;
import bi.bigroup.life.mvp.profile.ProfileView;
import bi.bigroup.life.ui.base.BaseFragmentActivity;
import bi.bigroup.life.ui.base.view_pager.ViewPagerAdapter;
import butterknife.BindView;

public class ProfileActivity extends BaseFragmentActivity implements ProfileView {
    @InjectPresenter
    ProfilePresenter mvpPresenter;

    @BindView(R.id.tabs) TabLayout tabs;
    @BindView(R.id.pager) ViewPager viewPager;
    private ViewPagerAdapter adapter;

    public static Intent getIntent(Context context) {
        return new Intent(context, ProfileActivity.class);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_profile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvpPresenter.init(this, dataLayer);

        configureViewPager();
    }

    private void configureViewPager() {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        tabs.setupWithViewPager(viewPager);
        adapter.addFrag(ProfileDataFragment.newInstance(), getString(R.string.tabs_data));
        adapter.addFrag(ProfileDataFragment.newInstance(), getString(R.string.tabs_results));
        adapter.addFrag(ProfileDataFragment.newInstance(), getString(R.string.tabs_plans));
        adapter.addFrag(ProfileDataFragment.newInstance(), getString(R.string.tabs_advantages));

        viewPager.setAdapter(adapter);
    }

    ///////////////////////////////////////////////////////////////////////////
    // ProfileView implementation                                              ///
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onAuthorizationSuccess() {

    }
}
