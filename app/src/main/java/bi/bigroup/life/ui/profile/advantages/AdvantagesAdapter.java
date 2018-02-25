package bi.bigroup.life.ui.profile.advantages;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.user.advantages.Advantages;
import bi.bigroup.life.utils.LOTimber;

class AdvantagesAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {
    private static final int ITEM_LAYOUT_ID = R.layout.adapter_advantages;
    private static final int ITEM_HEADER_LAYOUT_ID = R.layout.adapter_advantages_header;
    private List<Advantages> data = Collections.emptyList();

    AdvantagesAdapter() {
        setHasStableIds(true);
    }

    public void setData(List<Advantages> newList) {
        if (newList == null || newList.isEmpty()) {
            data = new ArrayList<>();
        } else {
            data = new ArrayList<>(newList);
        }
        notifyDataSetChanged();
    }

//    public void addData(List<Results> newList) {
//        if (newList == null || newList.isEmpty()) {
//            return;
//        }
//        data.addAll(newList);
//        notifyDataSetChanged();
//    }

    @Override
    public long getItemId(int position) {
        return data.get(position).hashCode();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        LOTimber.d("sakldjaskld viewType=" + viewType);
        View view = LayoutInflater.from(parent.getContext()).inflate(ITEM_LAYOUT_ID, parent, false);
        return (VH) new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView tv_title = holder.itemView.findViewById(R.id.tv_title);
        tv_title.setText(data.get(position).title);

        TextView tv_description = holder.itemView.findViewById(R.id.tv_description);
        tv_description.setText(data.get(position).description);

        View view_divider = holder.itemView.findViewById(R.id.view_divider);
        view_divider.setVisibility(position == 0 ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    public long getHeaderId(int position) {
        return data.get(position).parentId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(ITEM_HEADER_LAYOUT_ID, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView tv_title = holder.itemView.findViewById(R.id.tv_title);
        tv_title.setText(String.valueOf(data.get(position).parentTitle));

        View view_divider = holder.itemView.findViewById(R.id.view_divider);
        view_divider.setVisibility(position == 0 ? View.INVISIBLE : View.VISIBLE);
    }
}