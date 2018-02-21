package bi.bigroup.life.ui.main.feed.news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.Collections;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.feed.news.News;
import bi.bigroup.life.mvp.main.feed.news.NewsDetailPresenter;
import bi.bigroup.life.mvp.main.feed.news.NewsDetailView;
import bi.bigroup.life.ui.base.BaseActivity;
import bi.bigroup.life.ui.main.feed.ViewPagerImage;
import bi.bigroup.life.utils.GlideUtils;
import bi.bigroup.life.views.RoundedImageView;
import bi.bigroup.life.views.circle_page_indicator.CirclePageIndicator;
import butterknife.BindView;
import butterknife.OnClick;

import static android.support.customtabs.CustomTabsIntent.KEY_ID;
import static bi.bigroup.life.utils.Constants.getProfilePicture;
import static bi.bigroup.life.utils.HtmlSourceUtils.fromHtml;

public class NewsDetailActivity extends BaseActivity implements NewsDetailView {
    @InjectPresenter
    NewsDetailPresenter mvpPresenter;
    @BindView(R.id.tv_subhead_top) TextView tv_subhead_top;
    @BindView(R.id.vp_images) ViewPager vp_images;
    @BindView(R.id.ci_images) CirclePageIndicator ci_images;
    @BindView(R.id.img_avatar) RoundedImageView img_avatar;
    @BindView(R.id.tv_username) TextView tv_username;
    @BindView(R.id.tv_time) TextView tv_time;
    @BindView(R.id.tv_title) TextView tv_title;
    @BindView(R.id.tv_description) TextView tv_description;

    private ViewPagerImage adapter;

    public static Intent getIntent(Context context, String id) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(KEY_ID, id);
        return intent;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvpPresenter.init(this, dataLayer);
        adapter = new ViewPagerImage(this);
        handleIntent();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            String id = intent.getStringExtra(KEY_ID);
            mvpPresenter.getNewsDetail(id);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mvpPresenter.onDestroyView();
    }

    @OnClick(R.id.img_close)
    void onCloseClick() {
        finish();
    }

    ///////////////////////////////////////////////////////////////////////////
    // NewsDetailView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void setNews(News object) {
        GlideUtils.showAvatar(this, img_avatar, getProfilePicture(object.getAuthorCode()), R.drawable.ic_avatar);
        tv_subhead_top.setText(object.getAuthorName());
        tv_title.setText(object.getTitle());
        tv_time.setText(object.getDate(this));
        tv_description.setText(fromHtml(object.getText()));
        tv_username.setText(object.getAuthorName());
        adapter.addImages(Collections.singletonList(object.getImageUrl()));
        vp_images.setAdapter(adapter);
        ci_images.setViewPager(vp_images);
    }
}