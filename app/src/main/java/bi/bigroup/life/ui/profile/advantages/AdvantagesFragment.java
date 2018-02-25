package bi.bigroup.life.ui.profile.advantages;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.user.advantages.Advantages;
import bi.bigroup.life.mvp.profile.advantages.AdvantagesPresenter;
import bi.bigroup.life.mvp.profile.advantages.AdvantagesView;
import bi.bigroup.life.ui.base.BaseFragment;
import butterknife.BindView;

public class AdvantagesFragment extends BaseFragment implements AdvantagesView {
    @InjectPresenter
    AdvantagesPresenter mvpPresenter;
    private AdvantagesAdapter mAdapter;
    @BindView(R.id.recycler_view) RecyclerView recycler_view;

    public static AdvantagesFragment newInstance() {
        return new AdvantagesFragment();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.frgm_profile_advantages;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState, View view) {
        mvpPresenter.init(getContext(), dataLayer);
        mAdapter = new AdvantagesAdapter(getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setAdapter(mAdapter);
        // Add the sticky headers decoration
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(mAdapter);
        recycler_view.addItemDecoration(headersDecor);
        mvpPresenter.loadData();
    }

    ///////////////////////////////////////////////////////////////////////////
    // AdvantagesView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void addAdvantagesList(List<Advantages> list) {
        mAdapter.setData(list);
    }
}