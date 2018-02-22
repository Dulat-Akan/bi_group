package bi.bigroup.life.ui.main.employees;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.EditText;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.employees.Vacancy;
import bi.bigroup.life.mvp.main.employees.VacanciesPresenter;
import bi.bigroup.life.mvp.main.employees.VacanciesView;
import bi.bigroup.life.ui.base.BaseSwipeRefreshFragment;
import butterknife.BindView;

import static bi.bigroup.life.views.edittext.EditTextUtils.setCursorColor;

public class VacanciesFragment extends BaseSwipeRefreshFragment implements VacanciesView {
    @InjectPresenter
    VacanciesPresenter mvpPresenter;
    @BindView(R.id.search_view) SearchView search_view;
    private VacanciesAdapter mAdapter;

    public static VacanciesFragment newInstance() {
        return new VacanciesFragment();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.frgm_employees_all;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState, View view) {
        mvpPresenter.init(getContext(), dataLayer);
        mvpPresenter.getVacancies(false, false);
        configureSearchView();
        configureRecyclerView();
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
        mAdapter = new VacanciesAdapter(getContext());
        mAdapter.setCallback(code -> startActivity(EmployeePageActivity.getIntent(getContext(), code)));
        recycler_view.setAdapter(mAdapter);
    }

    @Override
    protected void swipeToRefresh() {
        mvpPresenter.getVacancies(false, true);
    }

    ///////////////////////////////////////////////////////////////////////////
    // VacanciesView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void setVacanciesList(List<Vacancy> list) {
        mAdapter.clearData();
        mAdapter.addData(list);
    }

    @Override
    public void addVacanciesList(List<Vacancy> list) {
        mAdapter.addData(list);
    }

    @Override
    public void showLoadingItemIndicator(boolean show) {
        recycler_view.post(() -> mAdapter.setLoading(show));
    }
}