package bi.bigroup.life.ui.main.employees;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.employees.Employee;
import bi.bigroup.life.ui.base.recycler_view.RecyclerViewBaseAdapter;
import bi.bigroup.life.utils.GlideUtils;
import bi.bigroup.life.views.RoundedImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static bi.bigroup.life.utils.Constants.getProfilePicture;

class EmployeesAdapter extends RecyclerViewBaseAdapter {
    private static final int HEADER_LAYOUT_ID = R.layout.adapter_employees_header;
    private static final int LAYOUT_ID = R.layout.adapter_employees;

    private List<Employee> data;
    private Context context;
    private Callback callback;
    private boolean loading;

    EmployeesAdapter(Context context) {
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

    boolean getLoading() {
        return loading;
    }

    public List<Employee> getData() {
        return data;
    }

    void clearData() {
        data.clear();
        notifyDataSetChanged();
    }

    void addData(List<Employee> newItems) {
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
        if (position == 0) {
            return HEADER_LAYOUT_ID;
        } else if (loading && position == getItemCount() - 1) {
            return PROGRESS_BAR_LAYOUT_ID;
        } else {
            return LAYOUT_ID;
        }
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
            case LAYOUT_ID:
                ((BViewHolder) holder).bind(data.get(position - 1), position - 1);
                break;
            case HEADER_LAYOUT_ID:
                ((HeaderViewHolder) holder).bindHeader();
                break;
        }
    }

    class HeaderViewHolder extends MainViewHolder {
        //        @BindView(R.id.tv_answer_text) TextView tv_answer_text;
        HeaderViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        void bindHeader() {

        }
    }

    class BViewHolder extends MainViewHolder {
        @BindView(R.id.tv_fullname) TextView tv_fullname;
        @BindView(R.id.tv_specialty) TextView tv_specialty;
        @BindView(R.id.img_avatar) RoundedImageView img_avatar;
        Employee bindedObject;
        int bindedPosition;

        BViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        void bind(Employee object, int position) {
            bindedObject = object;
            bindedPosition = position;
            if (object == null) {
                return;
            }
            tv_fullname.setText(object.getFullName());
            tv_specialty.setText(object.getJobPosition());
            GlideUtils.showAvatar(context, img_avatar, getProfilePicture(object.getCode()), R.drawable.ic_avatar);
        }

        @OnClick(R.id.ll_row)
        void onRowClick() {
            callback.onItemClick(bindedObject.getCode());
        }
    }

    interface Callback {
        void onItemClick(String code);
    }
}