package bi.bigroup.life.ui.main.feed;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.feed.Feed;
import bi.bigroup.life.data.models.feed.FilterButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static bi.bigroup.life.data.models.feed.FeedEntityType.FEED_TYPE_NEWS;
import static bi.bigroup.life.data.models.feed.FeedEntityType.FEED_TYPE_QUESTIONNAIRE;
import static bi.bigroup.life.data.models.feed.FeedEntityType.FEED_TYPE_SUGGESTION;

class FilterButtonsAdapter extends BaseAdapter {
    private final static int LAYOUT_ID = R.layout.adapter_filter_buttons;
    private Context context;

    private List<FilterButton> filterButtonList = new ArrayList<>();
    private Callback callback;
    private int newsCount = 0, suggestionCount = 0, questionnaireCount = 0;
    FilterButtonsAdapter(Context context, List<Feed> data) {
        this.context = context;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getLayoutType() == FEED_TYPE_NEWS) {
                newsCount++;
            } else if (data.get(i).getLayoutType() == FEED_TYPE_SUGGESTION) {
                suggestionCount++;
            } else if (data.get(i).getLayoutType() == FEED_TYPE_QUESTIONNAIRE) {
                questionnaireCount++;
            }
        }
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    void setData(List<FilterButton> filterButtons) {
        if (filterButtons == null || filterButtons.isEmpty()) {
            filterButtonList = new ArrayList<>();
        } else {
            filterButtonList = new ArrayList<>(filterButtons);
        }
        filterButtonList.get(0).setCount(newsCount);
        filterButtonList.get(1).setCount(questionnaireCount);
        filterButtonList.get(2).setCount(suggestionCount);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return filterButtonList.size();
    }

    @Override
    public FilterButton getItem(int position) {
        return filterButtonList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(LAYOUT_ID, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }

        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.bindHolder(getItem(position), position);
        return convertView;
    }

    class ViewHolder {
        FilterButton bindedObject;
        int bindedPosition;
        @BindView(R.id.cv_container) CardView cv_container;
        @BindView(R.id.tv_title) TextView tv_title;
        @BindView(R.id.tv_count) TextView tv_count;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        void bindHolder(FilterButton object, int position) {
            bindedObject = object;
            bindedPosition = position;
            if (object == null) {
                return;
            }

            tv_title.setText(object.title);
            tv_count.setText(String.valueOf(object.count));
            cv_container.setCardBackgroundColor(object.color);
        }

        @OnClick(R.id.cv_container)
        void onItemClick() {
            if (callback != null) {
                callback.onItemClick(bindedPosition);
            }
        }
    }

    interface Callback {
        void onItemClick(int position);
    }
}
