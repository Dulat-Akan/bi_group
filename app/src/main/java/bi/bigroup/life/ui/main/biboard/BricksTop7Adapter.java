package bi.bigroup.life.ui.main.biboard;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.user.advantages.BricksGallery;
import bi.bigroup.life.ui.base.recycler_view.RecyclerViewBaseAdapter;
import butterknife.ButterKnife;
import butterknife.OnClick;

class BricksTop7Adapter extends RecyclerViewBaseAdapter {
    private static final int LAYOUT_ID = R.layout.adapter_biboard_bricks_top7;

    private Context context;
    private List<BricksGallery> data;
    private Callback callback;

    BricksTop7Adapter(Context context) {
        this.context = context;
        this.data = new ArrayList<>();
    }

    void setCallback(Callback callback) {
        this.callback = callback;
    }

    void clearData() {
        data.clear();
        notifyDataSetChanged();
    }

    void addList(List<BricksGallery> newItems) {
        int positionStart = data.size();
        int itemCount = newItems.size();
        data.addAll(newItems);
        notifyItemRangeChanged(positionStart, itemCount);
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case LAYOUT_ID:
                return new BViewHolder(inflate(parent, LAYOUT_ID));
            default:
                throw incorrectOnCreateViewHolder();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return LAYOUT_ID;
    }

    @Override
    public int getItemCount() {
        return data.size() + 7;
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case LAYOUT_ID:
                ((BViewHolder) holder).bind(position);//data.get(position)
                break;
        }
    }

    class BViewHolder extends MainViewHolder {
//        @BindView(R.id.tv_title) TextView tv_title;

        BricksGallery bindedObject;
        int bindedPosition;

        BViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        void bind(int position) {
//            bindedObject = bricksGallery;
            bindedPosition = position;
//            if (bricksGallery == null) {
//                return;
//            }
//            tv_title.setText(bricksGallery.title);
        }

        @OnClick(R.id.ll_content)
        void onItemViewClick() {
            if (callback != null) {
                callback.onItemClick(bindedObject);
            }
        }
    }

    interface Callback {
        void onItemClick(BricksGallery notification);
    }
}
