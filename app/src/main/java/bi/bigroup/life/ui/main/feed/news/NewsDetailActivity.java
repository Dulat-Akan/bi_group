package bi.bigroup.life.ui.main.feed.news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Collections;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.feed.news.Comment;
import bi.bigroup.life.data.models.feed.news.News;
import bi.bigroup.life.mvp.main.feed.news.NewsDetailPresenter;
import bi.bigroup.life.mvp.main.feed.news.NewsDetailView;
import bi.bigroup.life.ui.base.BaseActivity;
import bi.bigroup.life.ui.main.feed.ViewPagerImage;
import bi.bigroup.life.utils.GlideUtils;
import bi.bigroup.life.views.RoundedImageView;
import bi.bigroup.life.views.circle_page_indicator.CirclePageIndicator;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.customtabs.CustomTabsIntent.KEY_ID;
import static bi.bigroup.life.utils.Constants.getProfilePicture;
import static bi.bigroup.life.utils.ContextUtils.clearFocusFromAllViews;
import static bi.bigroup.life.utils.ContextUtils.hideSoftKeyboard;
import static bi.bigroup.life.utils.StringUtils.EMPTY_STR;

public class NewsDetailActivity extends BaseActivity implements NewsDetailView {
    @InjectPresenter
    NewsDetailPresenter mvpPresenter;

    @BindView(R.id.lv_news_detail) ListView lv_news_detail;
    @BindView(R.id.pb_indicator_transparent) ViewGroup pb_indicator_transparent;
    @BindView(R.id.et_content) MaterialEditText et_content;
    private CommentsAdapter adapter;
    private NewsHeader headerHolder;
    private String newsId;

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
        handleIntent();

        // Header view
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.adapter_news_header, lv_news_detail, false);
        headerHolder = new NewsHeader(this, header);
        lv_news_detail.addHeaderView(header, null, false);

        adapter = new CommentsAdapter(this);
        lv_news_detail.setAdapter(adapter);
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            newsId = intent.getStringExtra(KEY_ID);
            mvpPresenter.getNewsDetail(newsId);
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

    @OnClick(R.id.img_send_comment)
    void onSendComment() {
        mvpPresenter.addComment(newsId, et_content.getText().toString());
    }

    class NewsHeader {
        Context context;
        private News bindedObject;

        @BindView(R.id.tv_subhead_top) TextView tv_subhead_top;
        @BindView(R.id.vp_images) ViewPager vp_images;
        @BindView(R.id.ci_images) CirclePageIndicator ci_images;
        @BindView(R.id.img_avatar) RoundedImageView img_avatar;
        @BindView(R.id.tv_username) TextView tv_username;
        @BindView(R.id.tv_time) TextView tv_time;
        @BindView(R.id.tv_title) TextView tv_title;
        @BindView(R.id.wv_content) WebView wv_content;
        @BindView(R.id.img_like) ImageView img_like;
        @BindView(R.id.tv_like_quantity) TextView tv_like_quantity;
        @BindView(R.id.tv_comment_quantity) TextView tv_comment_quantity;
        @BindView(R.id.tv_comments) TextView tv_comments;
        @BindView(R.id.tv_view_quantity) TextView tv_view_quantity;
        private ViewPagerImage adapter;

        NewsHeader(Context context, View view) {
            ButterKnife.bind(this, view);
            this.context = context;
            adapter = new ViewPagerImage(context);
        }

        void bindHolder(News object) {
            bindedObject = object;
            GlideUtils.showAvatar(context, img_avatar, getProfilePicture(object.getAuthorCode()), R.drawable.ic_avatar);
            tv_subhead_top.setText(object.getAuthorName());
            tv_title.setText(object.getTitle());
            tv_time.setText(object.getDate(context));
            tv_username.setText(object.getAuthorName());
            // TODO add images list
            adapter.addImages(Collections.singletonList(object.getImageUrl()));
            vp_images.setAdapter(adapter);
            ci_images.setViewPager(vp_images);

            wv_content.clearCache(true);
            wv_content.clearHistory();
            wv_content.getSettings().setJavaScriptEnabled(true);
            wv_content.setWebChromeClient(new WebChromeClientHelper());
            wv_content.setWebViewClient(new WebViewClientQ());
            wv_content.loadDataWithBaseURL(null, EMPTY_STR + object.getText(), "text/html", "UTF-8", null);
            tv_comment_quantity.setText(String.valueOf(object.getOkIntQuantity(object.commentsQuantity)));
            tv_comments.setText(getString(R.string.comments_count, String.valueOf(object.getOkIntQuantity(object.commentsQuantity))));
            tv_view_quantity.setText(String.valueOf(object.getOkIntQuantity(object.viewsQuantity)));
            refreshLikeQuantity();
        }

        void refreshLikeQuantity() {
            tv_like_quantity.setText(String.valueOf(bindedObject.getOkIntQuantity(bindedObject.likesQuantity)));
            img_like.setImageResource(bindedObject.isLiked() ? R.drawable.like_active
                    : R.drawable.like_inactive);
        }

        class WebChromeClientHelper extends WebChromeClient {
            public boolean onCreateWindow(WebView view, boolean dialog, boolean userGesture, android.os.Message resultMsg) {
                return true;
            }
        }

        class WebViewClientQ extends WebViewClient {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }
        }

        @OnClick(R.id.ll_like)
        void onLikeClick() {
            mvpPresenter.likeSubscriptionUnsubscribe();
            mvpPresenter.likeNews(bindedObject.getId());
            if (bindedObject.isLiked()) {
                bindedObject.setLikedByMe(false);
                bindedObject.setLikesQuantity(bindedObject.getLikesQuantity() - 1);
            } else {
                bindedObject.setLikedByMe(true);
                bindedObject.setLikesQuantity(bindedObject.getLikesQuantity() + 1);
            }
            refreshLikeQuantity();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // NewsDetailView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void setNews(News object) {
        headerHolder.bindHolder(object);
        adapter.addList(object.getComments());
    }

    @Override
    public void showTransparentIndicator(boolean show) {
        pb_indicator_transparent.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void addNewComment(Comment comment) {
        et_content.setText(EMPTY_STR);
        clearFocusFromAllViews(fl_parent);
        hideSoftKeyboard(fl_parent);
        adapter.addNewComment(comment);
    }
}