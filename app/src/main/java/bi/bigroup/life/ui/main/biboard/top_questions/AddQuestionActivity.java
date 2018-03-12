package bi.bigroup.life.ui.main.biboard.top_questions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.feed.news.Tags;
import bi.bigroup.life.mvp.main.biboard.top_questions.AddQuestionPresenter;
import bi.bigroup.life.mvp.main.biboard.top_questions.AddQuestionView;
import bi.bigroup.life.ui.base.BaseActivity;
import bi.bigroup.life.ui.main.feed.news.TagsAdapter;
import bi.bigroup.life.utils.ToastUtils;
import butterknife.BindView;
import butterknife.OnClick;

import static bi.bigroup.life.utils.ContextUtils.clearFocusFromAllViews;
import static bi.bigroup.life.utils.ContextUtils.hideSoftKeyboard;
import static bi.bigroup.life.utils.StringUtils.EMPTY_STR;
import static bi.bigroup.life.utils.StringUtils.isStringOk;

public class AddQuestionActivity extends BaseActivity implements AddQuestionView {
    @InjectPresenter
    AddQuestionPresenter mvpPresenter;
    @BindView(R.id.pb_indicator_transparent) ViewGroup pb_indicator_transparent;
    @BindView(R.id.et_content) MaterialEditText et_content;
    @BindView(R.id.et_tags) AutoCompleteTextView et_tags;
    @BindView(R.id.cb_anonymous) CheckBox cb_anonymous;

    @BindView(R.id.tags_layout) TagFlowLayout tags_layout;
    private TagAdapter<Tags> selectedTagsAdapter;
    private List<Tags> selectedTagsList;
    // Tags
    private TagsAdapter tagsAdapter;

    public static Intent getIntent(Context context) {
        return new Intent(context, AddQuestionActivity.class);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_add_question;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvpPresenter.init(this, dataLayer);
        tagsAdapter = new TagsAdapter(this, R.layout.adapter_tags_list);
        tagsAdapter.setCallback(this::addTag);
        et_tags.setThreshold(1);
        et_tags.setAdapter(tagsAdapter);

        configureTagsLayout();
    }

    private void configureTagsLayout() {
        selectedTagsList = new ArrayList<>();
        final LayoutInflater mInflater = LayoutInflater.from(this);
        tags_layout.setMaxSelectCount(5);
        tags_layout.setAdapter(selectedTagsAdapter = new TagAdapter<Tags>(selectedTagsList) {
            @Override
            public View getView(FlowLayout parent, int position, Tags tags) {
                View tagsLayout = mInflater.inflate(R.layout.tags_layout, tags_layout, false);
                TextView tv_name = tagsLayout.findViewById(R.id.tv_name);
                tv_name.setText(tags.getName());
                return tagsLayout;
            }
        });
        tags_layout.setOnTagClickListener((view, position, parent) -> {
            selectedTagsList.remove(position);
            selectedTagsAdapter.notifyDataChanged();
            return true;
        });
    }

    void addTag(Tags tag) {
        selectedTagsList.add(tag);
        selectedTagsAdapter.notifyDataChanged();
        et_tags.setText(EMPTY_STR);
        clearFocusFromAllViews(fl_parent);
        hideSoftKeyboard(fl_parent);
    }

    @OnClick(R.id.img_close)
    void onCloseClick() {
        finish();
    }

    @OnClick(R.id.btn_ask_question)
    void onAskQuestionClick() {
        clearFocusFromAllViews(fl_parent);
        hideSoftKeyboard(fl_parent);
        mvpPresenter.addQuestion(et_content.getText().toString(),
                selectedTagsList,
                cb_anonymous.isChecked());
    }

    @OnClick(R.id.btn_add_new_tag)
    void onAddNewTag() {
        String tagStr = et_tags.getText().toString();
        if (isStringOk(tagStr)) {
            List<Tags> tagsDuplicates = tagsAdapter.getData();
            boolean isAlreadyExist = false;
            for (int i = 0; i < tagsDuplicates.size(); i++) {
                if (tagStr.equals(tagsDuplicates.get(i).getName())) {
                    isAlreadyExist = true;
                    break;
                }
            }

            if (isAlreadyExist) {
                ToastUtils.showCenteredToast(this, R.string.tag_already_exist);
            } else {
                addTag(new Tags(null, tagStr));
            }
        } else {
            ToastUtils.showCenteredToast(this, R.string.field_error);
        }
    }

    private void showInputFieldError(MaterialEditText inputField, @StringRes int errorRes) {
        inputField.setError(getString(errorRes));
    }

    ///////////////////////////////////////////////////////////////////////////
    // AddQuestionView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void showContentError(@StringRes int errorRes) {
        showInputFieldError(et_content, errorRes);
    }

    @Override
    public void setTags(List<Tags> object) {
        tagsAdapter.addData(object);
    }

    @Override
    public void showTransparentIndicator(boolean show) {
        pb_indicator_transparent.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showToastError(@StringRes int please_select_tag) {
        ToastUtils.showCenteredToast(this, please_select_tag);
    }

    @Override
    public void questionAddedSuccessfully() {
        ToastUtils.showCenteredToast(this, R.string.success_response);
        finish();
    }
}