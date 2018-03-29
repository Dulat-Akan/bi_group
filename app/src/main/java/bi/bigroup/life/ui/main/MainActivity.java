package bi.bigroup.life.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.eightbitlab.supportrenderscriptblur.SupportRenderScriptBlur;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

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
import bi.bigroup.life.ui.main.bioffice.tasks_sdesk.add_sdesk.AddSdeskActivity;
import bi.bigroup.life.ui.main.bioffice.tasks_sdesk.add_task.AddTaskActivity;
import bi.bigroup.life.ui.main.employees.EmployeesFragment;
import bi.bigroup.life.ui.main.feed.FeedFragment;
import bi.bigroup.life.ui.main.feed.suggestions.NewSuggestionActivity;
import bi.bigroup.life.ui.main.menu.MenuFragment;
import bi.bigroup.life.ui.notifications.NotificationsActivity;
import bi.bigroup.life.ui.profile.ProfileActivity;
import bi.bigroup.life.utils.picasso.PicassoUtils;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

import static bi.bigroup.life.utils.Constants.getProfilePicture;
import static bi.bigroup.life.utils.DeviceUtils.isJellyBeanMR1OrAbove;

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
    private Drawable windowBackground;
    @BindView(R.id.v_bottom_navigation) BottomNavigationView v_bottom_navigation;
    @BindView(R.id.img_avatar) CircleImageView img_avatar;
    @BindView(R.id.blurView) BlurView blurView;
    @BindView(R.id.fam_feed) FloatingActionsMenu fam_feed;
    @BindView(R.id.fam_bi_office) FloatingActionsMenu fam_bi_office;
    @BindView(R.id.fam_bi_board) FloatingActionsMenu fam_bi_board;

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
            v_bottom_navigation.setSelectedItemId(R.id.action_feed);
            replaceFragment(fragments.get(ACTION_FEED), false, null,
                    true, false, false);
        }

        FloatingActionsMenu.OnFloatingActionsMenuUpdateListener listener = new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                blurView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onMenuCollapsed() {
                blurView.setVisibility(View.GONE);
            }
        };
        fam_feed.setOnFloatingActionsMenuUpdateListener(listener);
        fam_bi_office.setOnFloatingActionsMenuUpdateListener(listener);
        fam_bi_board.setOnFloatingActionsMenuUpdateListener(listener);

        if (windowBackground == null) {
            float radius = 1.3f;
            View decorView = getWindow().getDecorView();
            windowBackground = decorView.getBackground();
            blurView.setupWith(fl_parent)
                    .windowBackground(windowBackground)
                    .blurAlgorithm(
                            isJellyBeanMR1OrAbove() ? new RenderScriptBlur(this)
                                    : new SupportRenderScriptBlur(this))
                    .blurRadius(radius)
                    .setHasFixedTransformationMatrix(true);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void replaceFragment(Fragment fragment, boolean addToBackStack, String tag,
                                   boolean showFeedFab, boolean showBiOfficeFab, boolean showBiBoard) {
        fam_feed.setVisibility(showFeedFab ? View.VISIBLE : View.GONE);
        fam_bi_office.setVisibility(showBiOfficeFab ? View.VISIBLE : View.GONE);
        fam_bi_office.setVisibility(showBiBoard ? View.VISIBLE : View.GONE);
        super.replaceFragment(fragment, addToBackStack, tag, showFeedFab, showBiOfficeFab, showBiBoard);
    }

    // ========== Feed float buttons actions =======
    @OnClick(R.id.fbn_add_news)
    void onAddNewsClick() {
        fam_feed.collapse();
        if (fragments.get(ACTION_FEED) != null) {
            ((FeedFragment) fragments.get(ACTION_FEED)).onAddNewsClick();
        }
    }

    @OnClick(R.id.fbn_add_suggestion)
    void onAddSuggestionClick() {
        fam_feed.collapse();
        if (fragments.get(ACTION_FEED) != null) {
            ((FeedFragment) fragments.get(ACTION_FEED)).onAddSuggestionClick();
        }
    }

    // ========== Bi Office float buttons actions =======
    @OnClick(R.id.fbn_new_sdesk)
    void onNewSdeskClick() {
        fam_bi_office.collapse();
        startActivity(AddSdeskActivity.getIntent(this));
    }

    @OnClick(R.id.fbn_new_task)
    void onNewTaskClick() {
        fam_bi_office.collapse();
        startActivity(AddTaskActivity.getIntent(this));
    }

    // ========== Bi Office float buttons actions =======
    @OnClick(R.id.fbn_new_suggestion)
    void onNewSuggestion() {
        fam_bi_board.collapse();
        startActivity(NewSuggestionActivity.getIntent(this));
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
        v_bottom_navigation.setItemIconTintList(null);
        disableShiftMode(v_bottom_navigation);
    }

    private void initFragments() {
        fragments.add(ACTION_MAIN, BiOfficeFragment.newInstance());
        fragments.add(ACTION_BOARD, BiBoardFragment.newInstance());
        fragments.add(ACTION_FEED, FeedFragment.newInstance());
        fragments.add(ACTION_EMPLOYEES, EmployeesFragment.newInstance());
        fragments.add(ACTION_MENU, MenuFragment.newInstance());
    }

    @SuppressLint("RestrictedApi")
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
        Fragment fragment = findFragmentById();
        if (fragment != null
                && fragment instanceof BottomNavigationTabFragment) {
            ((BottomNavigationTabFragment) fragment).onBottomNavigationTabReselected();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_main:
                replaceFragment(fragments.get(ACTION_MAIN), false, null,
                        false, true, false);
                return true;
            case R.id.action_board:
                replaceFragment(fragments.get(ACTION_BOARD), false, null,
                        false, false, true);
                return true;
            case R.id.action_feed:
                replaceFragment(fragments.get(ACTION_FEED), false, null,
                        true, false, false);
                return true;
            case R.id.action_stuff:
                replaceFragment(fragments.get(ACTION_EMPLOYEES), true, null,
                        false, false, false);
                return true;
            case R.id.action_menu:
                replaceFragment(fragments.get(ACTION_MENU), false, null,
                        false, false, false);
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
        PicassoUtils.showAvatar(dataLayer.getPicasso(), img_avatar, getProfilePicture(localUser.getCode()), R.drawable.ic_user);
    }

    ///////////////////////////////////////////////////////////////////////////
    // PageSwapCallback implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onEmployeesTabsSelect() {
        if (v_bottom_navigation != null) {
            v_bottom_navigation.setSelectedItemId(R.id.action_stuff);
        }
    }

    @Override
    public void onFeedTabsSelect() {
        if (v_bottom_navigation != null) {
            v_bottom_navigation.setSelectedItemId(R.id.action_feed);
        }
    }
}
