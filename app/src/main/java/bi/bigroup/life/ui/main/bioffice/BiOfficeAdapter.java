package bi.bigroup.life.ui.main.bioffice;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.bioffice.BiOffice;
import bi.bigroup.life.data.models.bioffice.tasks_sdesk.Task;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

class BiOfficeAdapter extends BaseAdapter {
    private static final int ITEM_LAYOUT = R.layout.adapter_bioffice;
    private Context context;
    private List<BiOffice> data = new ArrayList<>();
    private Callback callback;
    private LayoutInflater inflater;

    BiOfficeAdapter(Context context) {
        this.context = context;
        inflater = ((Activity) context).getLayoutInflater();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    void clearData() {
        this.data.clear();
    }

    void addItem(BiOffice item) {
        this.data.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public BiOffice getItem(int position) {
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
        BiOffice bindedBusTicket;
        int bindedPosition;
        @BindView(R.id.tv_row_title) TextView tv_row_title;

        @BindView(R.id.tv_first_value) TextView tv_first_value;
        @BindView(R.id.tv_second_value) TextView tv_second_value;
        @BindView(R.id.tv_third_value) TextView tv_third_value;
        @BindView(R.id.tv_first_label) TextView tv_first_label;
        @BindView(R.id.tv_second_label) TextView tv_second_label;
        @BindView(R.id.tv_third_label) TextView tv_third_label;

        @BindView(R.id.ll_programmatically) LinearLayout ll_programmatically;

        @BindView(R.id.exp_layout) ExpandableLayout exp_layout;

        public ViewHolder(View view) {
            super(view);
            context = view.getContext();
            ButterKnife.bind(this, view);
        }

        void bindHolder(BiOffice object, int position) {
            bindedBusTicket = object;
            bindedPosition = position;
            tv_row_title.setText(context.getString(object.title));
            tv_first_label.setText(context.getString(object.first));
            tv_second_label.setText(context.getString(object.second));
            tv_third_label.setText(context.getString(object.third));

            if (object.combined != null) {
                int allCount = 0;
                int inboxCount = 0;
                int outboxCount = 0;
                if (object.combined.inboxTasks != null && object.combined.inboxTasks.size() > 0) {
                    inboxCount = object.combined.inboxTasks.size();
                    allCount = inboxCount;
                }

                if (object.combined.outboxServices != null && object.combined.outboxServices.size() > 0) {
                    outboxCount = object.combined.outboxServices.size();
                    allCount += outboxCount;
                }

                if (object.combined.outboxTasks != null && object.combined.outboxTasks.size() > 0) {
                    outboxCount += object.combined.outboxTasks.size();
                    allCount += outboxCount;
                }
                tv_first_value.setText(String.valueOf(allCount));
                tv_second_value.setText(String.valueOf(inboxCount));
                tv_third_value.setText(String.valueOf(outboxCount));
            }

            // Inbox services and tasks list
            if (object.combined != null && object.combined.inboxTasks != null) {
                List<Task> sList = object.combined.inboxTasks;
                if (sList != null && sList.size() > 0) {
                    ll_programmatically.removeAllViews();
                    for (int i = 0; i < sList.size(); i++) {
                        Object item = sList.get(i);
                        View itemView = inflater.inflate(R.layout.inc_item_bioffice_items, null);
                        TextView tv_title = itemView.findViewById(R.id.tv_title);
                        TextView tv_desc = itemView.findViewById(R.id.tv_description);

//                        if (item instanceof Service) {
//                            tv_title.setText(((Service) item).getTopic());
//                            tv_desc.setText(((Service) item).getStartDate() + "\n" +
//                                    context.getString(R.string.service_status, ((Service) item).getStatus()));
//                        } else if (item instanceof Task) {
                        tv_title.setText(((Task) item).getTopic());
                        tv_desc.setText(((Task) item).getStartDate() + "\n" +
                                context.getString(((Task) item).getStatusCode()));
//                        }
                        ll_programmatically.addView(itemView);
                    }
                }
            }
        }

        @OnClick(R.id.ll_expand_collapse)
        void onExpandCollapseClick() {
            if (exp_layout.isExpanded()) {
                exp_layout.collapse();
            } else {
                exp_layout.expand();
            }
        }

        @OnClick(R.id.ll_zayavki_zadachi)
        void onZayavkiZadachi() {
            if (bindedPosition == 0) {
                callback.openTasksSdeskActivity();
            }
        }
    }

    public interface Callback {
        void openTasksSdeskActivity();

        void onItemClick();
    }
}
