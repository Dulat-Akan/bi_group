package bi.bigroup.life.ui.main.biboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.biboard.BiBoard;
import bi.bigroup.life.data.models.bioffice.BiOffice;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

class BiBoardAdapter extends BaseAdapter {
    private static final int ITEM_LAYOUT = R.layout.adapter_biboard;
    private Context context;
    private List<BiBoard> data = new ArrayList<>();
    private Callback callback;

    BiBoardAdapter(Context context) {
        this.context = context;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    void addList(List<BiBoard> newList) {
        this.data.clear();
        this.data.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public BiBoard getItem(int position) {
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

    class ViewHolder extends RecyclerView.ViewHolder {
        Context context;
        BiBoard bindedBusTicket;
        @BindView(R.id.tv_row_title) TextView tv_row_title;
        @BindView(R.id.tv_first) TextView tv_first;
        @BindView(R.id.tv_second) TextView tv_second;
        @BindView(R.id.tv_third) TextView tv_third;
        @BindView(R.id.img_add) ImageView img_add;
        @BindView(R.id.exp_layout) ExpandableLayout exp_layout;

        public ViewHolder(View view) {
            super(view);
            context = view.getContext();
            ButterKnife.bind(this, view);
        }

        void bindHolder(BiBoard object, int position) {
            bindedBusTicket = object;
            tv_row_title.setText(object.title);
            tv_first.setText(object.first);
            tv_second.setText(object.second);
            tv_third.setText(object.third);
            img_add.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
        }

        @OnClick(R.id.ll_expand_collapse)
        void onExpandCollapseClick() {
            if (exp_layout.isExpanded()) {
                exp_layout.collapse();
            } else {
                exp_layout.expand();
            }
        }
    }

    public interface Callback {
        void onItemClick(BiOffice biOffice);
    }
}