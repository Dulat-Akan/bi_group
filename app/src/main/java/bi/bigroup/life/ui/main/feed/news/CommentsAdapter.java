package bi.bigroup.life.ui.main.feed.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.bioffice.BiOffice;
import bi.bigroup.life.data.models.feed.news.Comment;
import butterknife.ButterKnife;

class CommentsAdapter extends BaseAdapter {
    private static final int ITEM_LAYOUT = R.layout.adapter_news_comment;
    private Context context;
    private List<Comment> data = new ArrayList<>();
    private Callback callback;

    CommentsAdapter(Context context) {
        this.context = context;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    void addList(List<Comment> newList) {
        this.data.clear();
        this.data.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Comment getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(ITEM_LAYOUT, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }

        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.bindHolder(getItem(position), position);

        return convertView;
    }

    class ViewHolder {
        Context context;
        Comment comment;
//        @BindView(R.id.tv_row_title) TextView tv_row_title;

        public ViewHolder(View view) {
            context = view.getContext();
            ButterKnife.bind(this, view);
        }

        void bindHolder(Comment object, int position) {
            comment = object;
        }
    }

    public interface Callback {
        void onItemClick(BiOffice biOffice);
    }
}