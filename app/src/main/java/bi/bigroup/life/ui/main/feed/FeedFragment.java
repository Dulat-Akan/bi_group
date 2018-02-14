package bi.bigroup.life.ui.main.feed;

import android.os.Bundle;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.feed.FilterButton;
import bi.bigroup.life.mvp.main.feed.FeedPresenter;
import bi.bigroup.life.mvp.main.feed.FeedView;
import bi.bigroup.life.ui.base.BaseFragment;
import bi.bigroup.life.utils.LOTimber;
import butterknife.BindView;
import it.sephiroth.android.library.widget.HListView;

public class FeedFragment extends BaseFragment implements FeedView {
    @InjectPresenter
    FeedPresenter mvpPresenter;

    @BindView(R.id.hlv_filters) HListView hlv_filters;
    private FilterButtonsAdapter horizontalAdapter;

    public static FeedFragment newInstance() {
        FeedFragment fragment = new FeedFragment();
        Bundle data = new Bundle();
//        data.putParcelable(FORM_KEY, Parcels.wrap(authForm));
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.frgm_feed;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState, View view) {
        handleIntent();
        configureHorizontalList();
        mvpPresenter.init(getContext(), dataLayer);
    }

    private void handleIntent() {
//        if (getArguments() != null && getArguments().containsKey(FORM_KEY)) {
//            AuthForm form = Parcels.unwrap(getArguments().getParcelable(FORM_KEY));
//            mvpPresenter.init(dataLayer, form);
//        }
    }

    private void configureHorizontalList() {
        horizontalAdapter = new FilterButtonsAdapter(getContext());
        horizontalAdapter.setCallback((position) -> LOTimber.d("salkdasjd"));
        hlv_filters.setAdapter(horizontalAdapter);
    }

    ///////////////////////////////////////////////////////////////////////////
    // FeedView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void successSent() {
    }

    @Override
    public void setFiltersList(List<FilterButton> filterButtonList) {
        horizontalAdapter.setData(filterButtonList);
    }
}