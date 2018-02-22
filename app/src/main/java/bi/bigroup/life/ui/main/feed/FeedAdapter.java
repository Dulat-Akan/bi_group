package bi.bigroup.life.ui.main.feed;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.feed.Feed;
import bi.bigroup.life.data.models.feed.FilterButton;
import bi.bigroup.life.ui.base.recycler_view.RecyclerViewBaseAdapter;
import bi.bigroup.life.utils.LOTimber;
import bi.bigroup.life.views.RoundedImageView;
import bi.bigroup.life.views.circle_page_indicator.CirclePageIndicator;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.sephiroth.android.library.widget.HListView;

import static bi.bigroup.life.data.models.feed.FeedEntityType.FEED_TYPE_NEWS;
import static bi.bigroup.life.data.models.feed.FeedEntityType.FEED_TYPE_QUESTIONNAIRE;
import static bi.bigroup.life.data.models.feed.FeedEntityType.FEED_TYPE_SUGGESTION;

class FeedAdapter extends RecyclerViewBaseAdapter {
    private static final int HEADER_LAYOUT_ID = R.layout.adapter_feed_header;
    private static final int NEWS_LAYOUT_ID = R.layout.adapter_feed_news;
    private static final int SUGGESTION_LAYOUT_ID = R.layout.adapter_feed_suggestion;
    private static final int QUESTIONNAIRE_LAYOUT_ID = R.layout.adapter_feed_questionnaire;

    private List<Feed> data;
    private List<FilterButton> filterButtonList;
    private Context context;
    private Callback callback;
    private boolean loading;

    FeedAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<>();

        filterButtonList = new ArrayList<>();
        filterButtonList.add(new FilterButton(context.getString(R.string.filter_news), 30,
                ContextCompat.getColor(context, R.color.filter_news)));
        filterButtonList.add(new FilterButton(context.getString(R.string.filter_poll), 43,
                ContextCompat.getColor(context, R.color.filter_poll)));
        filterButtonList.add(new FilterButton(context.getString(R.string.filter_offer), 223,
                ContextCompat.getColor(context, R.color.filter_offer)));
    }

    void setCallback(Callback callback) {
        this.callback = callback;
    }

    void setLoading(boolean loading) {
        if (this.loading && !loading) {
            this.loading = false;
            notifyItemRemoved(getItemCount());
        } else if (!this.loading && loading) {
            this.loading = true;
            notifyItemInserted(getItemCount() - 1);
        }
    }

    boolean getLoading() {
        return loading;
    }

    void clearData() {
        data.clear();
        notifyDataSetChanged();
    }

    void addData(List<Feed> newItems) {
        int positionStart = data.size();
        int itemCount = newItems.size();
        data.addAll(newItems);
        notifyItemRangeChanged(positionStart, itemCount);
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case HEADER_LAYOUT_ID:
                return new HeaderViewHolder(inflate(parent, HEADER_LAYOUT_ID));
            case NEWS_LAYOUT_ID:
                return new NewsViewHolder(inflate(parent, NEWS_LAYOUT_ID));
            case SUGGESTION_LAYOUT_ID:
                return new SuggestionViewHolder(inflate(parent, SUGGESTION_LAYOUT_ID));
            case QUESTIONNAIRE_LAYOUT_ID:
                return new QuestionnaireViewHolder(inflate(parent, QUESTIONNAIRE_LAYOUT_ID));
            case PROGRESS_BAR_LAYOUT_ID:
                return new SimpleViewHolder(inflate(parent, PROGRESS_BAR_LAYOUT_ID));
            default:
                throw incorrectOnCreateViewHolder();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_LAYOUT_ID;
        } else if (loading && position == getItemCount() - 1) {
            return PROGRESS_BAR_LAYOUT_ID;
        } else {
            if (data.get(position).getLayoutType() == FEED_TYPE_NEWS) {
                return NEWS_LAYOUT_ID;
            } else if (data.get(position).getLayoutType() == FEED_TYPE_SUGGESTION) {
                return SUGGESTION_LAYOUT_ID;
            } else if (data.get(position).getLayoutType() == FEED_TYPE_QUESTIONNAIRE) {
                return QUESTIONNAIRE_LAYOUT_ID;
            }
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        int count = data.size() + 1; // header position
        if (loading) {
            count += 1;
        }
        return count;
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case NEWS_LAYOUT_ID:
                ((NewsViewHolder) holder).bind(data.get(position - 1), position - 1);
                break;
            case SUGGESTION_LAYOUT_ID:
                ((SuggestionViewHolder) holder).bind(data.get(position - 1), position - 1);
                break;
            case QUESTIONNAIRE_LAYOUT_ID:
                ((QuestionnaireViewHolder) holder).bind(data.get(position - 1), position - 1);
                break;
            case HEADER_LAYOUT_ID:
                ((HeaderViewHolder) holder).bindHeader();
                break;
        }
    }

    class HeaderViewHolder extends MainViewHolder {
        @BindView(R.id.hlv_filters) HListView hlv_filters;
        private FilterButtonsAdapter horizontalAdapter;

        HeaderViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        void bindHeader() {
            horizontalAdapter = new FilterButtonsAdapter(context);
            horizontalAdapter.setCallback((position) -> LOTimber.d("salkdasjd"));
            hlv_filters.setAdapter(horizontalAdapter);
            horizontalAdapter.setData(filterButtonList);
        }
    }

    class NewsViewHolder extends MainViewHolder {
        @BindView(R.id.img_avatar) RoundedImageView img_avatar;
        @BindView(R.id.tv_username) TextView tv_username;
        @BindView(R.id.tv_time) TextView tv_time;
        @BindView(R.id.tv_content) TextView tv_content;
        @BindView(R.id.vp_images) ViewPager vp_images;
        @BindView(R.id.ci_images) CirclePageIndicator ci_images;
        @BindView(R.id.tv_like_quantity) TextView tv_like_quantity;
        @BindView(R.id.tv_comment_quantity) TextView tv_comment_quantity;
        @BindView(R.id.tv_view_quantity) TextView tv_view_quantity;

        Feed bindedObject;
        int bindedPosition;

        NewsViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        void bind(Feed feed, int position) {
            bindedObject = feed;
            bindedPosition = position;
            if (feed == null) {
                return;
            }

            ViewPagerImage adapter = new ViewPagerImage(context);
            adapter.addImages(Collections.singletonList(feed.getImageUrl()));
            vp_images.setAdapter(adapter);
            ci_images.setViewPager(vp_images);

            tv_content.setText(feed.title);
            tv_time.setText(feed.getDate(context));
            tv_username.setText(feed.authorName);

            tv_like_quantity.setText(String.valueOf(feed.getOkIntQuantity(feed.likesQuantity)));
            tv_comment_quantity.setText(String.valueOf(feed.getOkIntQuantity(feed.commentsQuantity)));
            tv_view_quantity.setText(String.valueOf(feed.getOkIntQuantity(feed.viewsQuantity)));
        }

        @OnClick(R.id.img_more)
        void onMoreClick() {
        }

        @OnClick(R.id.ll_content)
        void onItemClick() {
            callback.onNewsItemClick(bindedObject.getId());
        }
    }

    class SuggestionViewHolder extends MainViewHolder {
        @BindView(R.id.img_avatar) RoundedImageView img_avatar;
        @BindView(R.id.tv_username) TextView tv_username;
        @BindView(R.id.tv_time) TextView tv_time;
        @BindView(R.id.tv_content) TextView tv_content;
        @BindView(R.id.tv_like_quantity) TextView tv_like_quantity;
        @BindView(R.id.tv_dislike_quantity) TextView tv_dislike_quantity;
        @BindView(R.id.tv_view_quantity) TextView tv_view_quantity;

        Feed bindedObject;
        int bindedPosition;

        SuggestionViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        void bind(Feed feed, int position) {
            bindedObject = feed;
            bindedPosition = position;
            if (feed == null) {
                return;
            }
            tv_content.setText(feed.title);
            tv_time.setText(feed.getDate(context));
            tv_username.setText(feed.authorName);

            tv_like_quantity.setText(String.valueOf(feed.getOkIntQuantity(feed.likesQuantity)));
            tv_dislike_quantity.setText(String.valueOf(feed.getOkIntQuantity(feed.dislikesQuantity)));
            tv_view_quantity.setText(String.valueOf(feed.getOkIntQuantity(feed.viewsQuantity)));
        }

        @OnClick(R.id.img_more)
        void onMoreClick() {
//            if (callback != null) {
//                callback.onItemClick(bindedObject);
//            }
        }
    }

    class QuestionnaireViewHolder extends MainViewHolder {
        @BindView(R.id.img_avatar) RoundedImageView img_avatar;
        @BindView(R.id.tv_username) TextView tv_username;
        @BindView(R.id.tv_time) TextView tv_time;
        @BindView(R.id.tv_content) TextView tv_content;
        @BindView(R.id.tv_like_quantity) TextView tv_like_quantity;
        @BindView(R.id.tv_comment_quantity) TextView tv_comment_quantity;
        @BindView(R.id.tv_poll_quantity) TextView tv_poll_quantity;

        Feed bindedObject;
        int bindedPosition;

        QuestionnaireViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        void bind(Feed feed, int position) {
            bindedObject = feed;
            bindedPosition = position;
            if (feed == null) {
                return;
            }
            tv_content.setText(feed.title);
            tv_time.setText(feed.getDate(context));
            tv_username.setText(feed.authorName);

            tv_like_quantity.setText(String.valueOf(feed.getOkIntQuantity(feed.likesQuantity)));
            tv_comment_quantity.setText(String.valueOf(feed.getOkIntQuantity(feed.commentsQuantity)));
            tv_poll_quantity.setText(String.valueOf(feed.getOkIntQuantity(feed.questionsQuantity)));
        }

        @OnClick(R.id.img_more)
        void onMoreClick() {
//            if (callback != null) {
//                callback.onItemClick(bindedObject);
//            }
        }
    }

    interface Callback {
        void onNewsItemClick(String newsId);

        void onSuggestionItemClick(String suggestionId);
    }
}
