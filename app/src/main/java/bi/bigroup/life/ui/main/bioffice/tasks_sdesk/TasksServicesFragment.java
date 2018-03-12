package bi.bigroup.life.ui.main.bioffice.tasks_sdesk;

import android.os.Bundle;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.mvp.main.bioffice.tasks_sdesk.TasksServicesPresenter;
import bi.bigroup.life.mvp.main.bioffice.tasks_sdesk.TasksServicesView;
import bi.bigroup.life.ui.base.BaseSwipeRefreshFragment;

import static bi.bigroup.life.utils.Constants.KEY_BOOL;

public class TasksServicesFragment extends BaseSwipeRefreshFragment implements TasksServicesView {
    @InjectPresenter
    TasksServicesPresenter mvpPresenter;
    private TasksServicesAdapter mAdapter;
    private boolean isInbox;

    public static TasksServicesFragment newInstance(boolean isInbox) {
        TasksServicesFragment fragment = new TasksServicesFragment();
        Bundle data = new Bundle();
        data.putBoolean(KEY_BOOL, isInbox);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.frgm_tasks_services;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState, View view) {
        mvpPresenter.init(getContext(), dataLayer);
        handleIntent();
        if (isInbox) {
            mvpPresenter.getInboxTasks(false, false);
        } else {
            mvpPresenter.getServiceDeskOutbox(false);
        }

        configureRecyclerView();
    }

    private void handleIntent() {
        if (getArguments() != null && getArguments().containsKey(KEY_BOOL)) {
            isInbox = getArguments().getBoolean(KEY_BOOL);
        }
    }

    protected void configureRecyclerView() {
        super.configureRecyclerView();
        mAdapter = new TasksServicesAdapter(getContext());
        mAdapter.setCallback((id, isTask) -> {

        });
        recycler_view.setAdapter(mAdapter);
    }

    @Override
    protected void swipeToRefresh() {
        if (isInbox) {
            mvpPresenter.getInboxTasks(true, false);
        } else {
            mvpPresenter.getServiceDeskOutbox(true);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // TasksServicesView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void showLoadingItemIndicator(boolean show) {
        recycler_view.post(() -> mAdapter.setLoading(show));
    }

    @Override
    public void setList(List<?> list) {
        mAdapter.clearData();
        mAdapter.addData(list);
    }

    @Override
    public void addList(List<?> list) {
        mAdapter.addData(list);
    }
}