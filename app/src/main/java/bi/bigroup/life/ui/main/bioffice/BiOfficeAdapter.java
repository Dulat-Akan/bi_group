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
import bi.bigroup.life.data.models.biboard.BiBoard;
import bi.bigroup.life.data.models.bioffice.BiOffice;
import bi.bigroup.life.data.models.bioffice.tasks_sdesk.Task;
import bi.bigroup.life.data.models.feed.questionnaire.Questionnaire;
import bi.bigroup.life.data.models.feed.suggestions.Suggestion;
import bi.bigroup.life.utils.LOTimber;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BiOfficeAdapter extends BaseAdapter {
    static final int TYPE_TASKS_SERVICES = 0;
    public static final int TYPE_SUGGESTIONS = 1;
    public static final int TYPE_QUESTIONNAIRE = 2;
//    public static final int TYPE_EMPLOYEES = 3;

    private static final int ITEM_LAYOUT = R.layout.adapter_bioffice;
    private static final int ITEM_BI_BOARD_LAYOUT = R.layout.adapter_biboard;

    private List<Object> data = new ArrayList<>();
    private Callback callback;
    private CallbackBiBoard callbackBiBoard;
    private LayoutInflater inflater;

    BiOfficeAdapter(Context context) {
        data.add(null);
        data.add(new BiBoard(R.string.predlojeniya));
        data.add(new BiBoard(R.string.oprosnik));
        inflater = ((Activity) context).getLayoutInflater();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    void setCallbackBiBoard(CallbackBiBoard callbackBiBoard) {
        this.callbackBiBoard = callbackBiBoard;
    }

    void setItem(Object item, int position) {
        this.data.set(position, item);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == TYPE_TASKS_SERVICES) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        if (convertView == null) {
            convertView = inflater.inflate(viewType == 0 ? ITEM_LAYOUT : ITEM_BI_BOARD_LAYOUT, parent, false);
            convertView.setTag(viewType == 0
                    ? new ViewHolder(convertView)
                    : new ViewHolderBiBoard(convertView));
        }

        if (viewType == 0) {
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.bindHolder((BiOffice) getItem(position), position);
        } else {
            ViewHolderBiBoard viewHolder = (ViewHolderBiBoard) convertView.getTag();
            viewHolder.bindHolder((BiBoard) getItem(position), position);
        }
        return convertView;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        Context context;
        BiOffice bindedBusTicket;
        int bindedPosition;
        int bindedAllCount;
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
            if (object == null) return;
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
                bindedAllCount = allCount;
//                exp_layout.setExpanded(bindedAllCount > 0, true);
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
                        tv_desc.setText(String.valueOf(
                                ((Task) item).getStartDate() + "\n" +
                                context.getString(((Task) item).getStatusCode())));
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
                if (bindedAllCount > 0) {
                    exp_layout.expand();
                }
            }
        }

        @OnClick(R.id.ll_zayavki_zadachi)
        void onZayavkiZadachi() {
            if (bindedPosition == 0) {
                callback.openTasksSdeskActivity();
            }
        }
    }

    class ViewHolderBiBoard extends RecyclerView.ViewHolder {
        Context context;
        BiBoard bindedBusTicket;
        int bindedPosition;
        int bindedSuggestionsCount;
        int bindedQuestionnaireCount;

        @BindView(R.id.tv_row_title) TextView tv_row_title;
        @BindView(R.id.tv_first_value) TextView tv_first_value;
        @BindView(R.id.tv_second_value) TextView tv_second_value;
        @BindView(R.id.tv_third_value) TextView tv_third_value;
        @BindView(R.id.tv_first_label) TextView tv_first_label;
        @BindView(R.id.tv_second_label) TextView tv_second_label;
        @BindView(R.id.tv_third_label) TextView tv_third_label;

        @BindView(R.id.exp_layout) ExpandableLayout exp_layout;
        @BindView(R.id.ll_programmatically) LinearLayout ll_programmatically;

        ViewHolderBiBoard(View view) {
            super(view);
            context = view.getContext();
            ButterKnife.bind(this, view);
        }

        void bindHolder(BiBoard object, int position) {
            if (object == null) return;
            bindedBusTicket = object;
            bindedPosition = position;

            tv_row_title.setText(context.getString(object.title));
            if (object.first != 0) tv_first_label.setText(context.getString(object.first));
            if (object.second != 0) tv_second_label.setText(context.getString(object.second));
            if (object.third != 0) tv_third_label.setText(context.getString(object.third));

            if (object.allSuggestions != null) {
                tv_first_value.setText(String.valueOf(object.allSuggestions.size()));
                tv_second_value.setText(String.valueOf(object.allSuggestions.size()));
                tv_third_value.setText(String.valueOf(object.popularSuggestions.size()));
            } else if (object.allQuestionnaires != null) {
                tv_first_value.setText(String.valueOf(object.allQuestionnaires.size()));
                tv_second_value.setText(String.valueOf(object.allQuestionnaires.size()));
                tv_third_value.setText(String.valueOf(object.popularQuestionnaires.size()));
            }
//            else if (object.employees != null && object.vacancies != null) {
//                tv_first_value.setText(String.valueOf(object.allEmployeesCount));
//                tv_second_value.setText(String.valueOf(object.employees.size()));
//                tv_third_value.setText(String.valueOf(object.vacancies.size()));
//                ll_area.setClickable(true);
//            }

            // Suggestions list
            List<Suggestion> sList = object.popularSuggestions;
            if (sList != null && sList.size() > 0) {
                bindedSuggestionsCount = sList.size();
                ll_programmatically.removeAllViews();
                for (int i = 0; i < sList.size(); i++) {
                    Suggestion item = sList.get(i);
                    View itemView = inflater.inflate(R.layout.inc_item_bioffice_items, null);
                    TextView tv_title = itemView.findViewById(R.id.tv_title);
                    TextView tv_desc = itemView.findViewById(R.id.tv_description);
                    tv_title.setText(item.getTitle());
                    tv_desc.setText(item.getDate(context));
                    LinearLayout ll_row = itemView.findViewById(R.id.ll_row);
                    ll_row.setOnClickListener(view ->
                            LOTimber.d("askdlajsldkajsld title=" + item.getTitle()));
                    ll_programmatically.addView(itemView);
                }
            }

            // Questionnaires list
            List<Questionnaire> qList = object.popularQuestionnaires;
            if (qList != null && qList.size() > 0) {
                bindedQuestionnaireCount = qList.size();
                ll_programmatically.removeAllViews();
                for (int i = 0; i < qList.size(); i++) {
                    Questionnaire item = qList.get(i);
                    View itemView = inflater.inflate(R.layout.inc_item_bioffice_items, null);
                    TextView tv_title = itemView.findViewById(R.id.tv_title);
                    TextView tv_desc = itemView.findViewById(R.id.tv_description);
                    tv_title.setText(item.getTitle());
                    tv_desc.setText(item.getDate(context));
                    LinearLayout ll_row = itemView.findViewById(R.id.ll_row);
                    ll_row.setOnClickListener(view ->
                            LOTimber.d("askdlajsldkajsld title=" + item.getTitle()));
                    ll_programmatically.addView(itemView);
                }
            }

//            // Employees list
//            List<Employee> eList = object.employees;
//            if (eList != null && eList.size() > 0) {
//                ll_programmatically.removeAllViews();
//                for (int i = 0; i < eList.size(); i++) {
//                    Employee item = eList.get(i);
//                    View itemView = inflater.inflate(R.layout.inc_item_bioffice_items, null);
//                    TextView tv_title = itemView.findViewById(R.id.tv_title);
//                    TextView tv_desc = itemView.findViewById(R.id.tv_description);
//                    tv_title.setText(item.getFullName());
//                    tv_desc.setText(item.getJobPosition());
//                    LinearLayout ll_row = itemView.findViewById(R.id.ll_row);
//                    ll_row.setOnClickListener(view -> callback.openEmployeePage(item.getCode()));
//                    ll_programmatically.addView(itemView);
//                }
//            }
        }

        @OnClick(R.id.ll_expand_collapse)
        void onExpandCollapseClick() {
            if (exp_layout.isExpanded()) {
                exp_layout.collapse();
            } else {
                if (bindedPosition == TYPE_SUGGESTIONS && bindedSuggestionsCount > 0) {
                    exp_layout.expand();
                } else if (bindedPosition == TYPE_QUESTIONNAIRE && bindedQuestionnaireCount > 0) {
                    exp_layout.expand();
                }
            }
        }

        @OnClick(R.id.ll_item_container)
        void onAreaClick() {
            if (bindedPosition == TYPE_SUGGESTIONS) {
                callbackBiBoard.onSuggestionClick();
            } else if (bindedPosition == TYPE_QUESTIONNAIRE) {
                callbackBiBoard.onQuestionnaireClick();
            }
        }
    }

    public interface CallbackBiBoard {
        void onSuggestionClick();

        void onQuestionnaireClick();

        void selectEmployeesTab();
    }

    public interface Callback {
        void openTasksSdeskActivity();

        void onItemClick();
    }
}
