package bi.bigroup.life.ui.main.bioffice.tasks_sdesk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import bi.bigroup.life.R;
import bi.bigroup.life.mvp.main.bioffice.tasks_sdesk.TasksSdeskPresenter;
import bi.bigroup.life.mvp.main.bioffice.tasks_sdesk.TasksSdeskView;
import bi.bigroup.life.ui.base.BaseFragmentActivity;
import bi.bigroup.life.ui.base.view_pager.ViewPagerAdapter;
import butterknife.BindView;
import butterknife.OnClick;

public class TasksSdeskActivity extends BaseFragmentActivity implements TasksSdeskView {
    @InjectPresenter
    TasksSdeskPresenter mvpPresenter;

    @BindView(R.id.tabs) TabLayout tabs;
    @BindView(R.id.pager) ViewPager viewPager;
    @BindView(R.id.floating_menu) FloatingActionsMenu floating_menu;
    @BindView(R.id.ll_float_menu) LinearLayout ll_float_menu;

    public static Intent getIntent(Context context) {
        return new Intent(context, TasksSdeskActivity.class);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_tasks_sdesk;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvpPresenter.init(this, dataLayer);
        floating_menu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                ll_float_menu.setVisibility(View.VISIBLE);
            }

            @Override
            public void onMenuCollapsed() {
                ll_float_menu.setVisibility(View.GONE);
            }
        });
        configureViewPager();
    }

    @OnClick(R.id.img_close)
    void onCloseClick() {
        finish();
    }

    private void configureViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        tabs.setupWithViewPager(viewPager);
        adapter.addFrag(TasksServicesFragment.newInstance(true), getString(R.string.inbox));
        adapter.addFrag(TasksServicesFragment.newInstance(false), getString(R.string.outbox));
        viewPager.setAdapter(adapter);
    }

    ///////////////////////////////////////////////////////////////////////////
    // ProfileView implementation                                              ///
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onAuthorizationSuccess() {

    }
}
