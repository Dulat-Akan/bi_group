package bi.bigroup.life.ui.main.feed.news;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.etiennelawlor.imagegallery.library.activities.FullScreenImageGalleryActivity;
import com.etiennelawlor.imagegallery.library.adapters.FullScreenImageGalleryAdapter;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.bioffice.BiOffice;
import bi.bigroup.life.data.models.feed.news.Comment;
import bi.bigroup.life.data.models.feed.news.News;
import bi.bigroup.life.mvp.main.feed.news.NewsDetailPresenter;
import bi.bigroup.life.mvp.main.feed.news.NewsDetailView;
import bi.bigroup.life.ui.base.BaseActivity;
import bi.bigroup.life.ui.main.feed.ViewPagerImage;
import bi.bigroup.life.utils.picasso.PicassoUtils;
import bi.bigroup.life.views.RoundedImageView;
import bi.bigroup.life.views.circle_page_indicator.CirclePageIndicator;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.customtabs.CustomTabsIntent.KEY_ID;
import static bi.bigroup.life.data.models.feed.news.Comment.VOTE_DISLIKED;
import static bi.bigroup.life.data.models.feed.news.Comment.VOTE_LIKED;
import static bi.bigroup.life.utils.Constants.getProfilePicture;
import static bi.bigroup.life.utils.ContextUtils.clearFocusFromAllViews;
import static bi.bigroup.life.utils.ContextUtils.hideSoftKeyboard;
import static bi.bigroup.life.utils.StringUtils.EMPTY_STR;

public class NewsDetailActivity extends BaseActivity implements NewsDetailView, FullScreenImageGalleryAdapter.FullScreenImageLoader {
    @InjectPresenter
    NewsDetailPresenter mvpPresenter;

    @BindView(R.id.lv_news_detail) ListView lv_news_detail;
    @BindView(R.id.pb_indicator_transparent) ViewGroup pb_indicator_transparent;
    @BindView(R.id.et_content) MaterialEditText et_content;
    @BindColor(R.color.black_transparent) int black_transparent;
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
        FullScreenImageGalleryActivity.setFullScreenImageLoader(this);

        // Header view
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.adapter_news_header, lv_news_detail, false);
        headerHolder = new NewsHeader(this, header);
        lv_news_detail.addHeaderView(header, null, false);

        adapter = new CommentsAdapter(this, dataLayer.getPicasso());
        adapter.setCallback(new CommentsAdapter.Callback() {
            @Override
            public void onItemClick(BiOffice biOffice) {

            }

            @Override
            public void onCommentLike(String commendId, boolean liked) {
                mvpPresenter.likeCommentSubscriptionUnsubscribe();
                mvpPresenter.likeComment(newsId, commendId, liked ? VOTE_DISLIKED : VOTE_LIKED);
            }
        });
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
        @BindString(R.string.filter_news) String filter_news;
        private ViewPagerImage adapter;
        private String currentImageUrl;

        NewsHeader(Context context, View view) {
            ButterKnife.bind(this, view);
            this.context = context;
            adapter = new ViewPagerImage(context, dataLayer.getPicasso());
        }

        @SuppressLint("ClickableViewAccessibility")
        void bindHolder(News object) {
            bindedObject = object;
            PicassoUtils.showAvatar(dataLayer.getPicasso(), img_avatar, getProfilePicture(object.getAuthorCode()), R.drawable.ic_user);
            tv_subhead_top.setText(filter_news);
            tv_title.setText(object.getTitle());
            tv_time.setText(object.getDate());
            tv_username.setText(object.getAuthorName());
            // TODO add images list
            adapter.addImages(Collections.singletonList(object.getImageUrl()));
            vp_images.setAdapter(adapter);
            ci_images.setViewPager(vp_images);

            wv_content.getSettings().setJavaScriptEnabled(true);
            wv_content.setWebChromeClient(new WebChromeClientHelper());
            wv_content.setWebViewClient(new WebViewClientQ());
            wv_content.setOnTouchListener((v, event) -> {
                long eventDuration = event.getEventTime() - event.getDownTime();
                if(event.getAction() == MotionEvent.ACTION_UP && eventDuration > 100) {
                    WebView.HitTestResult result = ((WebView) v).getHitTestResult();
                    if (result != null) {
                        int type = result.getType();
                        if (type == WebView.HitTestResult.IMAGE_TYPE || type == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                            currentImageUrl = result.getExtra();
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                                wv_content.evaluateJavascript("getAllLinks();", null);
                            } else {
                                wv_content.loadUrl("javascript:getAllLinks();");
                            }
                        }
                    }
                }
                return false;
            });
            wv_content.loadDataWithBaseURL(null,
                    "<html>" +
                            "<head>" +
                            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\">" +
                            "<style>" +
                            "            @font-face {\n" +
                            "                font-family: 'Roboto Regular';\n" +
                            "                src: url(\"file:///android_asset/fonts/Roboto-Regular.ttf\")" +
                            "            }\n" +
                            "\n" +
                            "            body {\n" +
                            "                font-family: 'Roboto Regular';\n" +
                            "                font-size: 14px;\n" +
                            "                color: black;\n" +
                            "            }\n" +
                            "\n" +
                            "            img {\n" +
                            "                width: 100%;\n" +
                            "                height: auto;\n" +
                            "            }\n" +
                            "</style>" +
                            "<script type=\"text/javascript\">\n" +
                            "   function getAllLinks(){\n" +
                            "       var imgs = document.getElementsByTagName(\"img\");\n" +
                            "       var imgURLs = [];\n" +
                            "       for (var i = 0; i < imgs.length; i++) {\n" +
                            "           imgURLs.push(imgs[i].src);\n" +
                            "       }" +
                            "       window.JSInterface.imageLinks(imgURLs);" +
                            "   }\n" +
                            "</script>" +
                            "</head>" +
                            "<body>" +
                            EMPTY_STR + object.getText() +
                            "</body>" +
                            "</html>", "text/html", "UTF-8", null);
            wv_content.addJavascriptInterface(new JavaScriptInterface(), "JSInterface");

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

        class JavaScriptInterface {
            @JavascriptInterface
            public void imageLinks(String[] imageLinks) {
                int imgPosition = 0;
                for (int i = 0; i < imageLinks.length; i++) {
                    if (imageLinks[i].equals(currentImageUrl)) {
                        imgPosition = i;
                        break;
                    }
                }
                Intent intent = new Intent(NewsDetailActivity.this, FullScreenImageGalleryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList(FullScreenImageGalleryActivity.KEY_IMAGES, new ArrayList<>(Arrays.asList(imageLinks)));
                bundle.putInt(FullScreenImageGalleryActivity.KEY_POSITION, imgPosition);
                intent.putExtras(bundle);
                startActivity(intent);
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
    // FullScreenImageGalleryAdapter.FullScreenImageLoader implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void loadFullScreenImage(ImageView iv, String imageUrl, int width, LinearLayout bgLinearLayout) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.with(iv.getContext())
                    .load(imageUrl)
                    .resize(width, 0)
                    .into(iv);
        } else {
            iv.setImageDrawable(null);
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