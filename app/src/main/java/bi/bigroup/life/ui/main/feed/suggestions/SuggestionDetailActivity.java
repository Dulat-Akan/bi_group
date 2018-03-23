package bi.bigroup.life.ui.main.feed.suggestions;

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
import bi.bigroup.life.data.models.bioffice.BiOffice;
import bi.bigroup.life.data.models.feed.news.Comment;
import bi.bigroup.life.data.models.feed.suggestions.Suggestion;
import bi.bigroup.life.mvp.main.feed.suggestion.SuggestionDetailPresenter;
import bi.bigroup.life.mvp.main.feed.suggestion.SuggestionDetailView;
import bi.bigroup.life.ui.base.BaseActivity;
import bi.bigroup.life.ui.main.feed.ViewPagerImage;
import bi.bigroup.life.ui.main.feed.news.CommentsAdapter;
import bi.bigroup.life.utils.picasso.PicassoUtils;
import bi.bigroup.life.views.RoundedImageView;
import bi.bigroup.life.views.circle_page_indicator.CirclePageIndicator;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.customtabs.CustomTabsIntent.KEY_ID;
import static bi.bigroup.life.data.models.feed.news.Comment.VOTE_DEFAULT;
import static bi.bigroup.life.data.models.feed.news.Comment.VOTE_DISLIKED;
import static bi.bigroup.life.data.models.feed.news.Comment.VOTE_LIKED;
import static bi.bigroup.life.utils.Constants.getProfilePicture;
import static bi.bigroup.life.utils.ContextUtils.clearFocusFromAllViews;
import static bi.bigroup.life.utils.ContextUtils.hideSoftKeyboard;
import static bi.bigroup.life.utils.StringUtils.EMPTY_STR;

public class SuggestionDetailActivity extends BaseActivity implements SuggestionDetailView {
    @InjectPresenter
    SuggestionDetailPresenter mvpPresenter;

    @BindView(R.id.lv_detail) ListView lv_detail;
    @BindView(R.id.pb_indicator_transparent) ViewGroup pb_indicator_transparent;
    @BindView(R.id.et_content) MaterialEditText et_content;
    private CommentsAdapter adapter;
    private ViewHeader headerHolder;
    private String id;

    public static Intent getIntent(Context context, String id) {
        Intent intent = new Intent(context, SuggestionDetailActivity.class);
        intent.putExtra(KEY_ID, id);
        return intent;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_suggestion_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvpPresenter.init(this, dataLayer);
        handleIntent();

        // Header view
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.adapter_suggestion_header, lv_detail, false);
        headerHolder = new ViewHeader(this, header);
        lv_detail.addHeaderView(header, null, false);

        adapter = new CommentsAdapter(this, dataLayer.getPicasso());
        adapter.setCallback(new CommentsAdapter.Callback() {
            @Override
            public void onItemClick(BiOffice biOffice) {

            }

            @Override
            public void onCommentLike(String commendId, boolean liked) {
                mvpPresenter.likeCommentSubscriptionUnsubscribe();
                mvpPresenter.likeComment(id, commendId, liked ? VOTE_DISLIKED : VOTE_LIKED);
            }
        });
        lv_detail.setAdapter(adapter);
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra(KEY_ID);
            mvpPresenter.getSuggestionDetail(id);
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
        mvpPresenter.addComment(id, et_content.getText().toString());
    }

    class ViewHeader {
        Context context;
        private Suggestion bindedObject;

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

        @BindView(R.id.img_dislike) ImageView img_dislike;
        @BindView(R.id.tv_dislike_quantity) TextView tv_dislike_quantity;

        @BindView(R.id.tv_comment_quantity) TextView tv_comment_quantity;
        @BindView(R.id.tv_comments) TextView tv_comments;
        private ViewPagerImage adapter;

        ViewHeader(Context context, View view) {
            ButterKnife.bind(this, view);
            this.context = context;
            adapter = new ViewPagerImage(context, dataLayer.getPicasso());
        }

        void bindHolder(Suggestion object) {
            bindedObject = object;
            PicassoUtils.showAvatar(dataLayer.getPicasso(), img_avatar, getProfilePicture(object.getAuthorCode()), R.drawable.ic_user);
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
            refreshLikeQuantity();
        }

        void refreshLikeQuantity() {
            tv_like_quantity.setText(String.valueOf(bindedObject.getOkIntQuantity(bindedObject.likesQuantity)));
            tv_dislike_quantity.setText(String.valueOf(bindedObject.getOkIntQuantity(bindedObject.dislikesQuantity)));

            img_like.setImageResource(bindedObject.getUserVote() == VOTE_LIKED ? R.drawable.like_active
                    : R.drawable.like_inactive);

            img_dislike.setImageResource(bindedObject.getUserVote() == VOTE_DISLIKED ? R.drawable.dislike_active
                    : R.drawable.dislike_inactive);

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
            mvpPresenter.suggestionLikeSubscriptionUnsubscribe();
            mvpPresenter.likeSuggestion(bindedObject.getId(),
                    bindedObject.getUserVote() == VOTE_LIKED ? VOTE_DEFAULT : VOTE_LIKED);

            if (bindedObject.getUserVote() == VOTE_LIKED) {
                bindedObject.setUserVote(VOTE_DEFAULT);
                bindedObject.setLikesQuantity(bindedObject.getLikesQuantity() - 1);
            } else {
                if (bindedObject.getUserVote() == VOTE_DISLIKED) {
                    bindedObject.setDisLikesQuantity(bindedObject.getDisLikesQuantity() - 1);
                }

                bindedObject.setUserVote(VOTE_LIKED);
                bindedObject.setLikesQuantity(bindedObject.getLikesQuantity() + 1);
            }
            refreshLikeQuantity();
        }

        @OnClick(R.id.ll_dislike)
        void onDisLikeClick() {
            mvpPresenter.suggestionLikeSubscriptionUnsubscribe();
            mvpPresenter.likeSuggestion(bindedObject.getId(),
                    bindedObject.getUserVote() == VOTE_DISLIKED ? VOTE_DEFAULT : VOTE_DISLIKED);

            if (bindedObject.getUserVote() == VOTE_DISLIKED) {
                bindedObject.setUserVote(VOTE_DEFAULT);
                bindedObject.setDisLikesQuantity(bindedObject.getDisLikesQuantity() - 1);
            } else {
                if (bindedObject.getUserVote() == VOTE_LIKED) {
                    bindedObject.setLikesQuantity(bindedObject.getLikesQuantity() - 1);
                }
                bindedObject.setUserVote(VOTE_DISLIKED);
                bindedObject.setDisLikesQuantity(bindedObject.getDisLikesQuantity() + 1);
            }
            refreshLikeQuantity();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // SuggestionDetailView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void setSuggestion(Suggestion object) {
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