package bi.bigroup.life.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.user.User;
import bi.bigroup.life.mvp.main.MainPresenter;
import bi.bigroup.life.mvp.main.MainView;
import bi.bigroup.life.ui.base.BaseFragmentActivity;
import bi.bigroup.life.ui.main.biboard.BiBoardFragment;
import bi.bigroup.life.ui.main.bioffice.BiOfficeFragment;
import bi.bigroup.life.ui.main.employees.EmployeesFragment;
import bi.bigroup.life.ui.main.feed.FeedFragment;
import bi.bigroup.life.ui.main.menu.MenuFragment;
import bi.bigroup.life.ui.notifications.NotificationsActivity;
import bi.bigroup.life.ui.profile.ProfileActivity;
import bi.bigroup.life.utils.picasso.PicassoUtils;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static bi.bigroup.life.utils.Constants.getProfilePicture;

public class MainActivity extends BaseFragmentActivity implements MainView, BottomNavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemReselectedListener, PageSwapCallback {

    public static Intent getIntent(Context context) {
        return new Intent(context, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }

    public static Intent alreadyAuthorized(Context context) {
        return new Intent(context, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    @InjectPresenter
    MainPresenter mvpPresenter;

    private static final int ACTION_MAIN = 0;
    private static final int ACTION_BOARD = 1;
    private static final int ACTION_FEED = 2;
    private static final int ACTION_EMPLOYEES = 3;
    private static final int ACTION_MENU = 4;
    private List<Fragment> fragments = new ArrayList<>();
    @BindView(R.id.v_bottom_navigation) BottomNavigationView v_bottom_navigation;
    @BindView(R.id.img_avatar) CircleImageView img_avatar;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvpPresenter.init(this, dataLayer);

        initBottomNavigationView();
        initFragments();
        if (v_bottom_navigation != null) {
            v_bottom_navigation.setSelectedItemId(R.id.action_main);
            replaceFragment(fragments.get(ACTION_MAIN), false, null);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.img_notification)
    void onNotificationClick() {
        startActivity(NotificationsActivity.getIntent(this));
    }

    @OnClick(R.id.img_avatar)
    void onAvatarClick() {
        startActivity(ProfileActivity.getIntent(this));
    }

    private void initBottomNavigationView() {
        v_bottom_navigation.setOnNavigationItemSelectedListener(this);
        v_bottom_navigation.setOnNavigationItemReselectedListener(this);
        disableShiftMode(v_bottom_navigation);
    }

    private void initFragments() {
        fragments.add(ACTION_MAIN, BiOfficeFragment.newInstance());
        fragments.add(ACTION_BOARD, BiBoardFragment.newInstance());
        fragments.add(ACTION_FEED, FeedFragment.newInstance());
        fragments.add(ACTION_EMPLOYEES, EmployeesFragment.newInstance());
        fragments.add(ACTION_MENU, MenuFragment.newInstance());
    }

    private void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ////////////////////////////////////////////////
    /// BottomNavigationView                     ///
    ////////////////////////////////////////////////

    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_main:
                replaceFragment(fragments.get(ACTION_MAIN), false, null);
                return true;
            case R.id.action_board:
                replaceFragment(fragments.get(ACTION_BOARD), false, null);
                return true;
            case R.id.action_feed:
                replaceFragment(fragments.get(ACTION_FEED), false, null);
                return true;
            case R.id.action_stuff:
                replaceFragment(fragments.get(ACTION_EMPLOYEES), true, null);
                return true;
            case R.id.action_menu:
                replaceFragment(fragments.get(ACTION_MENU), false, null);
                return true;
            default:
                return false;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // MainView implementation                                              ///
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void showUserInfo(User localUser) {
        PicassoUtils.showAvatar(dataLayer.getPicasso(), img_avatar, getProfilePicture(localUser.getCode()), R.drawable.ic_avatar);
    }

    ///////////////////////////////////////////////////////////////////////////
    // PageSwapCallback implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onEmployeesTabsSelect() {
        if (v_bottom_navigation != null) {
            v_bottom_navigation.setSelectedItemId(R.id.action_stuff);
        }
//        replaceFragment(fragments.get(ACTION_EMPLOYEES), true, null);
    }
}
