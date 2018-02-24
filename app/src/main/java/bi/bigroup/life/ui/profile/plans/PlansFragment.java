package bi.bigroup.life.ui.profile.plans;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.user.plans.Plans;
import bi.bigroup.life.mvp.profile.plans.PlansPresenter;
import bi.bigroup.life.mvp.profile.plans.PlansView;
import bi.bigroup.life.ui.base.BaseFragment;
import butterknife.BindView;

public class PlansFragment extends BaseFragment implements PlansView {
    @InjectPresenter
    PlansPresenter mvpPresenter;
    private PlansAdapter mAdapter;
    @BindView(R.id.recycler_view) RecyclerView recycler_view;

    public static PlansFragment newInstance() {
        return new PlansFragment();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.frgm_profile_results;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState, View view) {
        mvpPresenter.init(getContext(), dataLayer);
        mAdapter = new PlansAdapter();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setAdapter(mAdapter);
        // Add the sticky headers decoration
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(mAdapter);
        recycler_view.addItemDecoration(headersDecor);
        mvpPresenter.loadData();
    }

    ///////////////////////////////////////////////////////////////////////////
    // PlansView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void addPlansList(List<Plans> list) {
        mAdapter.setData(list);
    }
}