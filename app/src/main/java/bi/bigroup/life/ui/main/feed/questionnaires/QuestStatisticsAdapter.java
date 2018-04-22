package bi.bigroup.life.ui.main.feed.questionnaires;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.feed.questionnaire.Question;
import bi.bigroup.life.data.models.feed.questionnaire.Variants;
import bi.bigroup.life.ui.base.recycler_view.RecyclerViewBaseAdapter;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

class QuestStatisticsAdapter extends RecyclerViewBaseAdapter {
    private static final int LAYOUT_ID = R.layout.adapter_quest_statistics;

    private List<Question> data;
    private Callback callback;
    private Context context;
    private boolean loading;

    QuestStatisticsAdapter(Context context) {
        this.data = new ArrayList<>();
        this.context = context;
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

    void addQuestionnaireList(List<Question> newItems) {
        data.addAll(newItems);
        notifyDataSetChanged();
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
        int count = data.size();
        if (loading) {
            count += 1;
        }
        return count;
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case LAYOUT_ID:
                ((BViewHolder) holder).bind(data.get(position), position);
                break;
        }
    }

    class BViewHolder extends MainViewHolder {
        @BindView(R.id.tv_question_name) TextView tv_question_name;
        @BindView(R.id.tv_poll_quantity) TextView tv_poll_quantity;
        @BindView(R.id.ll_row) LinearLayout ll_row;
        @BindString(R.string.another_in_comment) String another_in_comment;
        Question bindedObject;
        int bindedPosition;

        BViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        void bind(Question object, int position) {
            bindedObject = object;
            bindedPosition = position;
            if (object == null) {
                return;
            }
            tv_question_name.setText(object.getQuestionText());
            tv_poll_quantity.setText(context.getString(R.string.label_votes, String.valueOf(object.getUserVote())));

            ll_row.removeAllViews();
            if (object.variants != null && object.variants.size() > 0) {
                List<Variants> variants = object.variants;
                for (Variants item : variants) {
                    setStatistics(item.getVariantText(), item.getPercentage());
                }
            }

            if (object.comments != null && object.comments.size() > 0) {
                setStatistics(another_in_comment, object.comments.size() * 100 / object.getUserVote());
            }
        }

        void setStatistics(String variantText, int percentage) {
            View variantLayout = ((Activity) context).getLayoutInflater().inflate(R.layout.adapter_questionnaire_variants, null);
            ProgressBar progressBar = variantLayout.findViewById(R.id.progressBar);
            progressBar.setProgress(percentage);
            TextView tv_variant = variantLayout.findViewById(R.id.tv_variant);
            tv_variant.setText(variantText);
            TextView tv_percentage = variantLayout.findViewById(R.id.tv_percentage);
            tv_percentage.setText(String.valueOf(percentage + "%"));

            ll_row.addView(variantLayout);
        }

        @OnClick(R.id.ll_content)
        void onItemViewClick() {
            if (callback != null) {
                callback.onItemClick(bindedObject);
            }
        }
    }

    interface Callback {
        void onItemClick(Question question);
    }
}
