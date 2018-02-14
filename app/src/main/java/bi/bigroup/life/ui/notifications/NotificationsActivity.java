package bi.bigroup.life.ui.notifications;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.notifications.Notification;
import bi.bigroup.life.mvp.notifications.NotificationsPresenter;
import bi.bigroup.life.mvp.notifications.NotificationsView;
import bi.bigroup.life.ui.base.BaseSwipeRefreshActivity;
import bi.bigroup.life.utils.recycler_view.EndlessScrollListener;
import butterknife.BindView;

public class NotificationsActivity extends BaseSwipeRefreshActivity implements NotificationsView {
    @InjectPresenter
    NotificationsPresenter mvpPresenter;
    private NotificationsAdapter mAdapter;

    @BindView(R.id.toolbar) Toolbar toolbar;

    public static Intent getIntent(Context context) {
        return new Intent(context, NotificationsActivity.class);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_notifications;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        mvpPresenter.init(this, dataLayer);

        mvpPresenter.getNotifications(false, false);
        configureRecyclerView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mvpPresenter.onDestroyView();
    }

    protected void configureRecyclerView() {
        super.configureRecyclerView();
        mAdapter = new NotificationsAdapter();
//        mAdapter.setCallback(area -> startActivity(AreaDetailActivity.getIntent(this, area.id)));
        recycler_view.setAdapter(mAdapter);
        recycler_view.addOnScrollListener(new EndlessScrollListener(recycler_view, 1) {
            @Override
            public void onRequestMoreItems() {
                if (!mAdapter.getLoading()) {
                    mvpPresenter.getNotifications(true, false);
                }
            }
        });
    }

    @Override
    protected void swipeToRefresh() {
        mvpPresenter.getNotifications(false, true);
    }

    ///////////////////////////////////////////////////////////////////////////
    // NotificationsView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void setNotificationsList(List<Notification> list) {
        mAdapter.clearData();
        mAdapter.addNotificationsList(list);
    }

    @Override
    public void addNotificationsList(List<Notification> list) {
        mAdapter.addNotificationsList(list);
    }

    @Override
    public void showLoadingItemIndicator(boolean show) {
        mAdapter.setLoading(show);
    }
}