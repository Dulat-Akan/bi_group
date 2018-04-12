package bi.bigroup.life.ui.main.feed;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.feed.news.News;
import bi.bigroup.life.mvp.main.feed.MainFeedPresenter;
import bi.bigroup.life.mvp.main.feed.MainFeedView;
import bi.bigroup.life.ui.base.BaseFragment;
import bi.bigroup.life.ui.base.view_pager.ViewPagerAdapter;
import bi.bigroup.life.ui.main.BottomNavigationTabFragment;
import bi.bigroup.life.ui.main.bioffice.HotBoardViewPager;
import bi.bigroup.life.ui.main.feed.news.AddNewsActivity;
import bi.bigroup.life.ui.main.feed.news.NewsDetailActivity;
import bi.bigroup.life.utils.view_pager.ParallaxPageTransformer;
import bi.bigroup.life.views.circle_page_indicator.CirclePageIndicator;
import butterknife.BindView;

import static bi.bigroup.life.mvp.main.feed.FeedPresenter.TAB_FEED_ALL;
import static bi.bigroup.life.mvp.main.feed.FeedPresenter.TAB_FEED_NEWS;
import static bi.bigroup.life.mvp.main.feed.FeedPresenter.TAB_FEED_QUESTIONNAIRES;
import static bi.bigroup.life.mvp.main.feed.FeedPresenter.TAB_FEED_SUGGESTIONS;
import static bi.bigroup.life.ui.main.feed.FeedFragment.UPDATE_NEWS_FEED;
import static bi.bigroup.life.utils.Constants.KEY_CODE;

public class MainFeedFragment extends BaseFragment implements MainFeedView, BottomNavigationTabFragment {
    @InjectPresenter
    MainFeedPresenter mvpPresenter;

    @BindView(R.id.vp_images) ViewPager vp_images;
    @BindView(R.id.ci_images) CirclePageIndicator ci_images;
    @BindView(R.id.tabs) TabLayout tabs;
    @BindView(R.id.vp_pager) ViewPager viewPager;

    private HotBoardViewPager sliderAdapter;
    private FeedFragment feedAll;
    private ViewPagerAdapter adapter;

    public static MainFeedFragment newInstance() {
        return new MainFeedFragment();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.frgm_main_feed;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState, View view) {
        mvpPresenter.init(getContext(), dataLayer);
        configureSliderViewPager();
        configureViewPager();
    }

    private void configureSliderViewPager() {
        sliderAdapter = new HotBoardViewPager(getContext(), dataLayer.getPicasso(), R.layout.adapter_feed_news_slider);
        sliderAdapter.setCallback(newsId -> startActivity(NewsDetailActivity.getIntent(getContext(), newsId)));
        vp_images.setAdapter(sliderAdapter);
        vp_images.setPageTransformer(true, new ParallaxPageTransformer());
        ci_images.setViewPager(vp_images);
    }

    private void configureViewPager() {
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        tabs.setupWithViewPager(viewPager);
        feedAll = FeedFragment.newInstance(TAB_FEED_ALL);

        adapter.addFrag(feedAll, getString(R.string.filter_all));
        adapter.addFrag(FeedFragment.newInstance(TAB_FEED_NEWS), getString(R.string.filter_news));
        adapter.addFrag(FeedFragment.newInstance(TAB_FEED_SUGGESTIONS), getString(R.string.filter_offer));
        adapter.addFrag(FeedFragment.newInstance(TAB_FEED_QUESTIONNAIRES), getString(R.string.filter_poll));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (data.getIntExtra(KEY_CODE, 0) == UPDATE_NEWS_FEED) {
            if (feedAll != null) {
                feedAll.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    public void onAddNewsClick() {
        startActivityForResult(AddNewsActivity.getIntent(getContext()), UPDATE_NEWS_FEED);
    }

    public void selectSuggestion() {
        new Handler().post(() -> viewPager.setCurrentItem(2));
    }

    public void selectQuestionnaire() {
        new Handler().post(() -> viewPager.setCurrentItem(3));
    }

    ///////////////////////////////////////////////////////////////////////////
    // MainFeedView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void successSent() {
    }

    @Override
    public void setPopularNews(List<News> list) {
        sliderAdapter.addList(list);
    }

    ///////////////////////////////////////////////////////////////////////////
    // BottomNavigationTabFragment implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onBottomNavigationTabReselected() {
        FeedFragment fragment = (FeedFragment)adapter.getItem(viewPager.getCurrentItem());
        if (fragment != null) {
            fragment.onBottomNavigationTabReselected();
        }
    }
}