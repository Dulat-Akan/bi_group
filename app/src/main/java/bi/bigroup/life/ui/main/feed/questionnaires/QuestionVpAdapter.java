package bi.bigroup.life.ui.main.feed.questionnaires;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.feed.questionnaire.Question;
import bi.bigroup.life.data.models.feed.questionnaire.Variants;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

import static bi.bigroup.life.utils.StringUtils.EMPTY_STR;

public class QuestionVpAdapter extends PagerAdapter {
    private Context context;
    private Callback callback;
    private List<Question> data = new ArrayList<>();

    QuestionVpAdapter(Context context) {
        this.context = context;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void addData(List<Question> newData) {
        if (newData == null || newData.isEmpty()) {
            data = new ArrayList<>();
        } else {
            data = new ArrayList<>(newData);
        }
        notifyDataSetChanged();
    }

    public List<Question> getData() {
        return data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View itemView = inflater.inflate(R.layout.adapter_vp_question, container, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        viewHolder.bindQuestion(data.get(position), position);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    class ViewHolder {
        Context context;
        Question bindedObject;

        @BindView(R.id.tv_title) TextView tv_title;
        @BindView(R.id.tv_variant_title) TextView tv_variant_title;
        @BindView(R.id.tv_out_of_questions) TextView tv_out_of_questions;
        @BindView(R.id.ll_variants) LinearLayout ll_variants;
        @BindView(R.id.ll_comment_input) LinearLayout ll_comment_input;
        @BindView(R.id.et_content) MaterialEditText et_content;
        @BindView(R.id.tv_poll_quantity) TextView tv_poll_quantity;
        @BindString(R.string.add_another_in_comment) String labelAnotherComment;

        ViewHolder(View view) {
            context = view.getContext();
            ButterKnife.bind(this, view);
        }

        void bindQuestion(Question question, int position) {
            this.bindedObject = question;
            List<String> userAnswer = question.userAnswer;

            tv_title.setText(question.getText());
            tv_variant_title.setText(context.getString(R.string.choose_variant, String.valueOf(position + 1)));
            tv_out_of_questions.setText(context.getString(R.string.out_of_questions,
                    String.valueOf(position + 1), String.valueOf(getCount())));
            tv_poll_quantity.setText(context.getString(R.string.label_votes, String.valueOf(question.getTotalAnswers())));

            ll_variants.removeAllViews();
            if (question.variants != null && question.variants.size() > 0) {
                List<Variants> variants = question.variants;
                // Define already answered variants
                if (userAnswer != null && userAnswer.size() > 0) {
                    for (int i = 0; i < userAnswer.size(); i++) {
                        for (Variants item : variants) {
                            if (!item.isVariantChecked()) {
                                item.setVariantChecked(item.getId().equals(userAnswer.get(i)));
                            }
                        }
                    }
                }

                // Add multiple views with loop
                for (Variants item : variants) {
                    setVariants(item, item.getText(), false);
                }
            }

            // If userCanComment boolean parameter true then we should show editText for adding extra comment
            if (question.userCanComment()) {
                ll_comment_input.setVisibility(View.VISIBLE);
                et_content.setText(question.getUserComment()); // show already entered text
                et_content.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (charSequence.length() != 0) {
                            bindedObject.setUserComment(et_content.getText().toString());
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                setVariants(null, labelAnotherComment, question.hasUserAnswer());
            } else {
                ll_comment_input.setVisibility(View.GONE);
            }
        }

        void setVariants(Variants variant, String text, boolean selectExtraComment) {
            View variantLayout = ((Activity) context).getLayoutInflater().inflate(R.layout.adapter_questionnaire_variants_input, null);
            CheckBox cb_variant = variantLayout.findViewById(R.id.cb_variant);
            cb_variant.setOnCheckedChangeListener((compoundButton, checked) -> {
                if (variant != null) {
                    variant.setVariantChecked(checked);
                } else {
                    bindedObject.setCommented(checked);
                    et_content.setText(!checked ? EMPTY_STR : et_content.getText().toString());
                }
            });

            if (variant != null) {
                cb_variant.setChecked(variant.isVariantChecked());
            } else if (selectExtraComment) {
                // If "questionnaire" allows to comment user, then we just check our checkBox
                cb_variant.setChecked(true);
            }
            TextView tv_variant_title = variantLayout.findViewById(R.id.tv_variant_title);
            tv_variant_title.setText(text);

            ll_variants.addView(variantLayout);
        }
    }

    public interface Callback {
        void onImageClick();
    }
}

