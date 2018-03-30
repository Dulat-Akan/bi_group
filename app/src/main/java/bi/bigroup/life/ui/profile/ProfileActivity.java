package bi.bigroup.life.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.arellomobile.mvp.presenter.InjectPresenter;

import bi.bigroup.life.R;
import bi.bigroup.life.mvp.profile.ProfilePresenter;
import bi.bigroup.life.mvp.profile.ProfileView;
import bi.bigroup.life.ui.base.BaseFragmentActivity;
import bi.bigroup.life.ui.base.view_pager.ViewPagerAdapter;
import bi.bigroup.life.ui.profile.advantages.AdvantagesFragment;
import bi.bigroup.life.ui.profile.plans.PlansFragment;
import bi.bigroup.life.ui.profile.profile_data.ProfileDataFragment;
import bi.bigroup.life.ui.profile.results.ResultsFragment;
import bi.bigroup.life.utils.animation.AvatarAnimation;
import bi.bigroup.life.utils.picasso.PicassoUtils;
import butterknife.BindView;
import butterknife.OnClick;

public class ProfileActivity extends BaseFragmentActivity implements ProfileView, ProfileFragmentCallbacks {
    @InjectPresenter
    ProfilePresenter mvpPresenter;

    @BindView(R.id.tabs) TabLayout tabs;
    @BindView(R.id.pager) ViewPager viewPager;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.img_expanded) ImageView img_expanded;
    @BindView(R.id.user_photo_container) RelativeLayout user_photo_container;
    private AvatarAnimation avatarAnimation;

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
        setSupportActionBar(toolbar);
        mvpPresenter.init(this, dataLayer);
        configureViewPager();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void configureViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        tabs.setupWithViewPager(viewPager);
        adapter.addFrag(ProfileDataFragment.newInstance(), getString(R.string.tabs_data));
        adapter.addFrag(ResultsFragment.newInstance(), getString(R.string.tabs_results));
        adapter.addFrag(PlansFragment.newInstance(), getString(R.string.tabs_plans));
        adapter.addFrag(AdvantagesFragment.newInstance(), getString(R.string.tabs_advantages));
        viewPager.setAdapter(adapter);
    }

    @OnClick(R.id.img_full_close)
    void onCloseImage() {
        if (img_expanded.getVisibility() == View.VISIBLE) {
            if (avatarAnimation != null)
                avatarAnimation.closeImage();
        }
    }

    @Override
    public void onBackPressed() {
        if (img_expanded.getVisibility() == View.VISIBLE) {
            if (avatarAnimation != null)
                avatarAnimation.closeImage();
        } else {
            super.onBackPressed();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // ProfileView implementation                                              ///
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onAuthorizationSuccess() {

    }

    ///////////////////////////////////////////////////////////////////////////
    // ProfileFragmentCallbacks implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onFullImageView(ImageView img_avatar) {
        if (avatarAnimation == null) {
            avatarAnimation = new AvatarAnimation(this, user_photo_container,
                    img_expanded, img_avatar);
        }
        avatarAnimation.onOpenUserPhoto();
    }

    @Override
    public void showFullAvatar(String profilePicture) {
        PicassoUtils.showAvatar(dataLayer.getPicasso(), img_expanded, profilePicture, R.color.transparent);
    }
}
