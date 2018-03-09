package bi.bigroup.life.ui.main.feed.news;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.feed.news.Tags;

class TagsAdapter extends ArrayAdapter<Tags> {
    private LayoutInflater layoutInflater;
    private List<Tags> data;

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    TagsAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        data = new ArrayList<>();
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    void addData(List<Tags> newItems) {
        data.addAll(newItems);
        notifyDataSetChanged();
    }

    List<Tags> getData() {
        return data;
    }

    private Filter mFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return ((Tags) resultValue).getName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null) {
                ArrayList<Tags> tags = new ArrayList<>();
                for (Tags item : data) {
                    if (item.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tags.add(item);
                    }
                }
                results.values = tags;
                results.count = tags.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            if (results != null && results.count > 0) {
                // we have filtered results
                addAll((ArrayList<Tags>) results.values);
            } else {
                addAll(data);
            }
            notifyDataSetChanged();
        }
    };

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.adapter_tags_list, null);
        }
        Tags tag = getItem(position);

        TextView name = view.findViewById(R.id.tv_title);
        LinearLayout ll_content = view.findViewById(R.id.ll_content);
        if (tag != null) {
            name.setText(tag.getName());
            ll_content.setOnClickListener(view1 -> {
                if (callback != null) {
                    callback.onTabSelected(tag);
                }
            });
        }
        return view;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return mFilter;
    }

    interface Callback {
        void onTabSelected(Tags tag);
    }
}
