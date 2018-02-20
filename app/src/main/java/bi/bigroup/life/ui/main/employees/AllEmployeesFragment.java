package bi.bigroup.life.ui.main.employees;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.EditText;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.employees.Employee;
import bi.bigroup.life.mvp.main.employees.AllEmployeesPresenter;
import bi.bigroup.life.mvp.main.employees.AllEmployeesView;
import bi.bigroup.life.ui.base.BaseSwipeRefreshFragment;
import bi.bigroup.life.utils.recycler_view.EndlessScrollListener;
import butterknife.BindView;

import static bi.bigroup.life.utils.Constants.KEY_BOOL;
import static bi.bigroup.life.views.edittext.EditTextUtils.setCursorColor;

public class AllEmployeesFragment extends BaseSwipeRefreshFragment implements AllEmployeesView {
    @InjectPresenter
    AllEmployeesPresenter mvpPresenter;
    @BindView(R.id.search_view) SearchView search_view;
    private EmployeesAdapter mAdapter;
    private boolean isBirthdayToday;

    public static AllEmployeesFragment newInstance(boolean isBirthdayToday) {
        AllEmployeesFragment fragment = new AllEmployeesFragment();
        Bundle data = new Bundle();
        data.putBoolean(KEY_BOOL, isBirthdayToday);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.frgm_employees_all;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState, View view) {
        mvpPresenter.init(getContext(), dataLayer);
        handleIntent();
        mvpPresenter.getEmployees(false, false, isBirthdayToday);
        configureSearchView();
        configureRecyclerView();
    }

    private void handleIntent() {
        if (getArguments() != null && getArguments().containsKey(KEY_BOOL)) {
            isBirthdayToday = getArguments().getBoolean(KEY_BOOL);
        }
    }

    private void configureSearchView() {
        EditText searchEditText = search_view.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setHint(getString(R.string.search_hint));
        searchEditText.setTextSize(14f);
        setCursorColor(searchEditText, ContextCompat.getColor(getContext(), R.color.feed_time));
        searchEditText.setTextColor(ContextCompat.getColor(getContext(), R.color.feed_time));
        searchEditText.setHintTextColor(ContextCompat.getColor(getContext(), R.color.feed_time));
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
//                mvpPresenter.onQueryTextChange(query);
                return false;
            }
        });
    }

    protected void configureRecyclerView() {
        super.configureRecyclerView();
        mAdapter = new EmployeesAdapter(getContext());
        mAdapter.setCallback(code -> startActivity(EmployeePageActivity.getIntent(getContext(), code)));
        recycler_view.setAdapter(mAdapter);
        recycler_view.addOnScrollListener(new EndlessScrollListener(recycler_view, 1) {
            @Override
            public void onRequestMoreItems() {
                if (!mAdapter.getLoading() && mAdapter.getData().size() > 0) {
                    mvpPresenter.getEmployees(true, false, isBirthdayToday);
                }
            }
        });
    }

    @Override
    protected void swipeToRefresh() {
        mvpPresenter.getEmployees(false, true, isBirthdayToday);
    }

    ///////////////////////////////////////////////////////////////////////////
    // AllEmployeesView implementation
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