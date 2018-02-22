package bi.bigroup.life.ui.main.office.tasks_sdesk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.arellomobile.mvp.presenter.InjectPresenter;

import bi.bigroup.life.R;
import bi.bigroup.life.mvp.main.office.tasks_sdesk.TasksSdeskPresenter;
import bi.bigroup.life.mvp.main.office.tasks_sdesk.TasksSdeskView;
import bi.bigroup.life.ui.base.BaseFragmentActivity;
import bi.bigroup.life.ui.base.view_pager.ViewPagerAdapter;
import butterknife.BindView;

public class TasksSdeskActivity extends BaseFragmentActivity implements TasksSdeskView {
    @InjectPresenter
    TasksSdeskPresenter mvpPresenter;

    @BindView(R.id.tabs) TabLayout tabs;
    @BindView(R.id.pager) ViewPager viewPager;
    private ViewPagerAdapter adapter;

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

        configureViewPager();
    }

    private void configureViewPager() {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
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
