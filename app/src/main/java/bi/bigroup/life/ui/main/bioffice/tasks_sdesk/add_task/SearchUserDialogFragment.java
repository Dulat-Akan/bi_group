package bi.bigroup.life.ui.main.bioffice.tasks_sdesk.add_task;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.employees.Employee;
import bi.bigroup.life.mvp.main.bioffice.tasks_sdesk.add_task.SearchUserPresenter;
import bi.bigroup.life.mvp.main.bioffice.tasks_sdesk.add_task.SearchUserView;
import bi.bigroup.life.ui.base.BaseDialogFragment;
import bi.bigroup.life.ui.main.employees.EmployeesAdapter;
import butterknife.BindView;
import butterknife.OnClick;

import static bi.bigroup.life.views.edittext.EditTextUtils.setCursorColor;

public class SearchUserDialogFragment extends BaseDialogFragment implements SearchUserView {
    @InjectPresenter
    SearchUserPresenter mvpPresenter;

    @BindView(R.id.search_view) android.support.v7.widget.SearchView search_view;
    @BindView(R.id.recycler_view) protected RecyclerView recycler_view;
    private EmployeesAdapter mAdapter;
    private DialogFragmentCallback callback;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.dialog_search_user;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState, View view) {
        mvpPresenter.init(getContext(), dataLayer);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recycler_view.setLayoutManager(mLayoutManager);
        configureSearchView();
        configureRecyclerView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (DialogFragmentCallback) context;
        } catch (ClassCastException castException) {
            castException.printStackTrace();
        }
    }

    private void configureSearchView() {
        EditText searchEditText = search_view.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setHint(getString(R.string.search_hint));
        searchEditText.setTextSize(14f);

        ImageView search_close_btn = search_view.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        search_close_btn.setOnClickListener(view -> closeSearchView());

        setCursorColor(searchEditText, ContextCompat.getColor(getContext(), R.color.feed_time));
        searchEditText.setTextColor(ContextCompat.getColor(getContext(), R.color.feed_time));
        searchEditText.setHintTextColor(ContextCompat.getColor(getContext(), R.color.feed_time));

        search_view.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                mvpPresenter.onQueryTextChange(query);
                return false;
            }
        });
    }

    private void closeSearchView() {
        if (!search_view.isIconified()) {
            search_view.setIconified(true);
        }
    }

    protected void configureRecyclerView() {
        mAdapter = new EmployeesAdapter(getContext(), dataLayer.getPicasso(), false, true);
        mAdapter.setCallback(new EmployeesAdapter.Callback() {
            @Override
            public void onItemClick(String code) {

            }

            @Override
            public void onUserClick(Employee employee) {
                if (callback != null) {
                    callback.onEmployeeSelected(employee);
                    dismiss();
                }
            }

            @Override
            public void onDobCongratsClick(Employee employee) {

            }
        });
        recycler_view.setAdapter(mAdapter);
    }

    @OnClick(R.id.btn_cancel)
    void onCancelClick() {
        dismiss();
    }

    ///////////////////////////////////////////////////////////////////////////
    // SearchUserView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void setSearchResults(List<Employee> searchResults) {
        mAdapter.setSearchResult(searchResults);
    }

    @Override
    public void showLoadingIndicator(boolean show) {
        pb_indicator.post(() -> pb_indicator.setVisibility(show ? View.VISIBLE : View.GONE));
    }
}
