package bi.bigroup.life.ui.main.bioffice.tasks_sdesk;

import android.os.Bundle;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.employees.Employee;
import bi.bigroup.life.mvp.main.bioffice.tasks_sdesk.TasksServicesPresenter;
import bi.bigroup.life.mvp.main.bioffice.tasks_sdesk.TasksServicesView;
import bi.bigroup.life.ui.base.BaseSwipeRefreshFragment;
import bi.bigroup.life.utils.recycler_view.EndlessScrollListener;

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
        mvpPresenter.getEmployees(false, false, isInbox);
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
//        mAdapter.setCallback(code -> startActivity(EmployeePageActivity.getIntent(getContext(), code)));
        recycler_view.setAdapter(mAdapter);
        recycler_view.addOnScrollListener(new EndlessScrollListener(recycler_view, 1) {
            @Override
            public void onRequestMoreItems() {
                if (!mAdapter.getLoading() && mAdapter.getData().size() > 0) {
                    mvpPresenter.getEmployees(true, false, isInbox);
                }
            }
        });
    }

    @Override
    protected void swipeToRefresh() {
        mvpPresenter.getEmployees(false, true, isInbox);
    }

    ///////////////////////////////////////////////////////////////////////////
    // TasksServicesView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void setEmployeesList(List<Employee> list) {
        mAdapter.clearData();
        mAdapter.addData(list);
    }

    @Override
    public void addEmployeesList(List<Employee> list) {
        mAdapter.addData(list);
    }

    @Override
    public void showLoadingItemIndicator(boolean show) {
        recycler_view.post(() -> mAdapter.setLoading(show));
    }
}