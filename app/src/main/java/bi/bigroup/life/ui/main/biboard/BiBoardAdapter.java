package bi.bigroup.life.ui.main.biboard;

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
import bi.bigroup.life.data.models.feed.questionnaire.Questionnaire;
import bi.bigroup.life.data.models.feed.suggestions.Suggestion;
import bi.bigroup.life.utils.LOTimber;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BiBoardAdapter extends BaseAdapter {
    public static final int TYPE_SUGGESTIONS = 0;
    public static final int TYPE_QUESTIONNAIRE = 1;
//    public static final int TYPE_EMPLOYEES = 2;

    private static final int ITEM_LAYOUT = R.layout.adapter_biboard;
    private Context context;
    private List<BiBoard> data = new ArrayList<>();
    private LayoutInflater inflater;
    private Callback callback;

    BiBoardAdapter(Context context) {
        this.context = context;
        data.add(null);
        data.add(null);
//        data.add(null);
        inflater = ((Activity) context).getLayoutInflater();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    void addItem(BiBoard item, int position) {
        this.data.set(position, item);
        notifyDataSetChanged();
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
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
        @BindView(R.id.ll_area) LinearLayout ll_area;

        public ViewHolder(View view) {
            super(view);
            context = view.getContext();
            ButterKnife.bind(this, view);
        }

        void bindHolder(BiBoard object, int position) {
            if (object == null) return;
            bindedBusTicket = object;
            bindedPosition = position;

            tv_row_title.setText(context.getString(object.title));
            tv_first_label.setText(context.getString(object.first));
            tv_second_label.setText(context.getString(object.second));
            tv_third_label.setText(context.getString(object.third));

            if (object.allSuggestions != null) {
                tv_first_value.setText(String.valueOf(object.allSuggestions.size()));
                tv_second_value.setText(String.valueOf(object.allSuggestions.size()));
                tv_third_value.setText(String.valueOf(object.popularSuggestions.size()));
                ll_area.setClickable(false);
            } else if (object.allQuestionnaires != null) {
                tv_first_value.setText(String.valueOf(object.allQuestionnaires.size()));
                tv_second_value.setText(String.valueOf(object.allQuestionnaires.size()));
                tv_third_value.setText(String.valueOf(object.popularQuestionnaires.size()));
                ll_area.setClickable(false);
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

        @OnClick(R.id.ll_area)
        void onAreaClick() {
//            if (bindedPosition == TYPE_EMPLOYEES) {
//                callback.selectEmployeesTab();
//            }
        }
    }

    public interface Callback {
        void onItemClick(BiOffice biOffice);

        void openEmployeePage(String code);

        void selectEmployeesTab();
    }
}