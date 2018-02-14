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
import bi.bigroup.life.data.models.feed.FilterButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

class FilterButtonsAdapter extends BaseAdapter {
    private final static int LAYOUT_ID = R.layout.adapter_filter_buttons;
    private Context context;

    private List<FilterButton> filterButtonList = new ArrayList<>();
    private Callback callback;

    FilterButtonsAdapter(Context context) {
        this.context = context;
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
