package bi.bigroup.life.ui.main.employees;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.cache.db.AppDatabase;
import bi.bigroup.life.data.models.employees.Employee;
import bi.bigroup.life.mvp.main.employees.AllEmployeesPresenter;
import bi.bigroup.life.mvp.main.employees.AllEmployeesView;
import bi.bigroup.life.ui.base.BaseSwipeRefreshFragment;
import bi.bigroup.life.ui.main.BottomNavigationTabFragment;
import bi.bigroup.life.utils.ContextUtils;
import bi.bigroup.life.utils.recycler_view.EndlessScrollListener;
import bi.bigroup.life.utils.recycler_view.RecyclerScroll;
import butterknife.BindView;

import static bi.bigroup.life.utils.ConnectionDetector.isInternetOn;
import static bi.bigroup.life.utils.Constants.KEY_BOOL;
import static bi.bigroup.life.utils.StringUtils.EMPTY_STR;
import static bi.bigroup.life.views.edittext.EditTextUtils.setCursorColor;

public class AllEmployeesFragment extends BaseSwipeRefreshFragment implements AllEmployeesView, BottomNavigationTabFragment {
    @InjectPresenter
    AllEmployeesPresenter mvpPresenter;
    @BindView(R.id.ll_search_container) LinearLayout ll_search_container;
    @BindView(R.id.search_view) SearchView search_view;
    private EmployeesAdapter mAdapter;
    private boolean isBirthdayToday;

    private EditText searchEditText;
    private List<Employee> originalEmployeesList;
    private int searchViewHeight;

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
        originalEmployeesList = new ArrayList<>();

        searchViewHeight = ContextUtils.getSearchViewHeight(getContext());
        configureSearchView();
        configureRecyclerView();
        mvpPresenter.getEmployees(false, false, isBirthdayToday, isInternetOn(getContext()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppDatabase.destroyInstance();
    }

    private void handleIntent() {
        if (getArguments() != null && getArguments().containsKey(KEY_BOOL)) {
            isBirthdayToday = getArguments().getBoolean(KEY_BOOL);
        }
    }

    private void configureSearchView() {
        searchEditText = search_view.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setHint(getString(R.string.search_hint));
        searchEditText.setTextSize(14f);

        ImageView search_close_btn = search_view.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        search_close_btn.setOnClickListener(view -> closeSearchView());

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
                if (isBirthdayToday) {
                    final List<Employee> employeesList = mvpPresenter.filter(originalEmployeesList, query);
                    setSearchResults(employeesList);
                } else {
                    mvpPresenter.onQueryTextChange(query);
                }
                return false;
            }
        });
    }

    private void closeSearchView() {
        if (!search_view.isIconified()) {
            mAdapter.setSearchResult(originalEmployeesList);
            search_view.setIconified(true);
        }
    }

    protected void configureRecyclerView() {
        super.configureRecyclerView();
        mAdapter = new EmployeesAdapter(getContext(), dataLayer.getPicasso(), isBirthdayToday, false);
        mAdapter.setCallback(new EmployeesAdapter.Callback() {
            @Override
            public void onItemClick(String code) {
                startActivity(EmployeePageActivity.getIntent(getContext(), code));
            }

            @Override
            public void onUserClick(Employee employee) {

            }
        });
        recycler_view.setAdapter(mAdapter);
        recycler_view.addOnScrollListener(new EndlessScrollListener(recycler_view) {
            @Override
            public void onRequestMoreItems() {
                if (!mAdapter.getLoading() && mAdapter.getItemCount() > 1 && searchEditText.getText().toString().length() == 0) {
                    mvpPresenter.getEmployees(true, false, isBirthdayToday, true);
                }
            }
        });
        recycler_view.addOnScrollListener(new RecyclerScroll() {
            @Override
            public void show() {
                ll_search_container.animate().alpha(1).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        ll_search_container.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                }).setInterpolator(new DecelerateInterpolator(1)).start();
            }

            @Override
            public void hide() {
                ll_search_container.animate().alpha(0).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        ll_search_container.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                }).setInterpolator(new AccelerateInterpolator(1)).start();
            }
        });
    }

    @Override
    protected void swipeToRefresh() {
        mvpPresenter.getEmployees(false, true, isBirthdayToday, isInternetOn(getContext()));
    }

    ///////////////////////////////////////////////////////////////////////////
    // AllEmployeesView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void setEmployeesList(List<Employee> list) {
        if (searchEditText != null) searchEditText.setText(EMPTY_STR);

        mAdapter.clearData();
        originalEmployeesList.clear();
        originalEmployeesList.addAll(list);
        mAdapter.addData(list);
    }

    @Override
    public void addEmployeesList(List<Employee> list) {
        originalEmployeesList.addAll(list);
        mAdapter.addData(list);
    }

    @Override
    public void showLoadingItemIndicator(boolean show) {
        recycler_view.post(() -> mAdapter.setLoading(show));
    }

    @Override
    public void setSearchResults(List<Employee> searchResults) {
        mAdapter.setSearchResult(searchResults);
    }

    @Override
    public void showLoadingIndicator(boolean show) {
        pb_indicator.post(() -> pb_indicator.setVisibility(show ? View.VISIBLE : View.GONE));
    }

    ///////////////////////////////////////////////////////////////////////////
    // BottomNavigationTabFragment implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onBottomNavigationTabReselected() {
        if (mAdapter.getItemCount() > 0)
            ((LinearLayoutManager) recycler_view.getLayoutManager()).scrollToPositionWithOffset(0, 0);
    }
}