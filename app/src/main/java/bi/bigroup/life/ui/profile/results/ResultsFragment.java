package bi.bigroup.life.ui.profile.results;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.user.results.Results;
import bi.bigroup.life.mvp.profile.ResultsPresenter;
import bi.bigroup.life.mvp.profile.ResultsView;
import bi.bigroup.life.ui.base.BaseFragment;
import butterknife.BindView;

public class ResultsFragment extends BaseFragment implements ResultsView {
    @InjectPresenter
    ResultsPresenter mvpPresenter;
    private ResultsAdapter mAdapter;
    @BindView(R.id.recycler_view) RecyclerView recycler_view;

    public static ResultsFragment newInstance() {
        return new ResultsFragment();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.frgm_profile_results;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState, View view) {
        mvpPresenter.init(getContext(), dataLayer);
        mAdapter = new ResultsAdapter();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setAdapter(mAdapter);
        // Add the sticky headers decoration
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(mAdapter);
        recycler_view.addItemDecoration(headersDecor);
        mvpPresenter.loadData();
    }

    ///////////////////////////////////////////////////////////////////////////
    // ResultsView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void addResultsList(List<Results> list) {
        mAdapter.setData(list);
    }
}