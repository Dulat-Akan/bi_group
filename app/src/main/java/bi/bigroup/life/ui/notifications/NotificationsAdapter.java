package bi.bigroup.life.ui.notifications;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.notifications.Notification;
import bi.bigroup.life.ui.base.recycler_view.RecyclerViewBaseAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

class NotificationsAdapter extends RecyclerViewBaseAdapter {
    private static final int LAYOUT_ID = R.layout.adapter_notifications;

    private List<Notification> data;
    private Callback callback;
    private boolean loading;

    NotificationsAdapter() {
        this.data = new ArrayList<>();
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

    void addNotificationsList(List<Notification> newItems) {
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
            case PROGRESS_BAR_LAYOUT_ID:
                return new SimpleViewHolder(inflate(parent, PROGRESS_BAR_LAYOUT_ID));
            default:
                throw incorrectOnCreateViewHolder();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (loading && position == getItemCount() - 1) {
            return PROGRESS_BAR_LAYOUT_ID;
        } else {
            return LAYOUT_ID;
        }
    }

    @Override
    public int getItemCount() {
        int count = data.size() + 10;
        if (loading) {
            count += 1;
        }
        return count;
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case LAYOUT_ID:
                ((BViewHolder) holder).bind(position);//data.get(position),
                break;
        }
    }

    class BViewHolder extends MainViewHolder {
        @BindView(R.id.tv_title) TextView tv_title;
        @BindView(R.id.tv_time) TextView tv_time;

        Notification bindedObject;
        int bindedPosition;

        BViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        void bind(int position) {
            //Notification notification,
//                bindedObject = notification;
            bindedPosition = position;

//            if (notification == null) {
//                return;
//            }
        }

        @OnClick(R.id.ll_content)
        void onItemViewClick() {
            if (callback != null) {
                callback.onItemClick(bindedObject);
            }
        }
    }

    interface Callback {
        void onItemClick(Notification notification);
    }
}
