package bi.bigroup.life.ui.main.bioffice.tasks_sdesk;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.bioffice.tasks_sdesk.Service;
import bi.bigroup.life.data.models.bioffice.tasks_sdesk.Task;
import bi.bigroup.life.ui.base.recycler_view.RecyclerViewBaseAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

class TasksServicesAdapter extends RecyclerViewBaseAdapter {
    private static final int TASKS_LAYOUT_ID = R.layout.adapter_tasks;
    private static final int SERVICES_LAYOUT_ID = R.layout.adapter_tservices;

    private List<Object> data;
    private Context context;
    private Callback callback;
    private boolean loading;

    TasksServicesAdapter(Context context) {
        this.context = context;
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

    public List<Object> getData() {
        return data;
    }

    void clearData() {
        data.clear();
        notifyDataSetChanged();
    }

    void addData(List<?> newItems) {
        int positionStart = data.size();
        int itemCount = newItems.size();
        data.addAll(newItems);
        notifyItemRangeChanged(positionStart, itemCount);
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TASKS_LAYOUT_ID:
                return new TasksViewHolder(inflate(parent, TASKS_LAYOUT_ID));
            case SERVICES_LAYOUT_ID:
                return new ServicesViewHolder(inflate(parent, SERVICES_LAYOUT_ID));
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
        } else if (data.get(position) instanceof Task) {
            return TASKS_LAYOUT_ID;
        } else {
            return SERVICES_LAYOUT_ID;
        }
    }

    @Override
    public int getItemCount() {
        int count = data.size();
        if (loading) {
            count += 1;
        }
        return count;
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TASKS_LAYOUT_ID:
                ((TasksViewHolder) holder).bind((Task)data.get(position), position);
                break;
            case SERVICES_LAYOUT_ID:
                ((ServicesViewHolder) holder).bind((Service) data.get(position), position);
                break;
        }
    }

    class TasksViewHolder extends MainViewHolder {
        @BindView(R.id.tv_title) TextView tv_title;
        @BindView(R.id.tv_description) TextView tv_description;
        Task bindedObject;
        int bindedPosition;

        TasksViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        void bind(Task object, int position) {
            bindedObject = object;
            bindedPosition = position;
            if (object == null) {
                return;
            }
            tv_title.setText(object.getTopic());
            tv_description.setText(object.getStartDate());
        }

        @OnClick(R.id.ll_content)
        void onRowClick() {
            callback.onItemClick(bindedObject.getId(), true);
        }
    }

    class ServicesViewHolder extends MainViewHolder {
        @BindView(R.id.tv_title) TextView tv_title;
        @BindView(R.id.tv_description) TextView tv_description;
        @BindView(R.id.tv_status) TextView tv_status;
        Service bindedObject;
        int bindedPosition;

        ServicesViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        void bind(Service object, int position) {
            bindedObject = object;
            bindedPosition = position;
            if (object == null) {
                return;
            }
            tv_title.setText(object.getTopic());
            tv_description.setText(object.getStartDate());
            tv_status.setText(context.getString(R.string.service_status, object.getStatus()));
        }

        @OnClick(R.id.ll_content)
        void onRowClick() {
            callback.onItemClick(bindedObject.getTaskNumber(), false);
        }
    }

    interface Callback {
        void onItemClick(String id, boolean isTask);
    }
}