package bi.bigroup.life.ui.main.biboard.top_questions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.rengwuxian.materialedittext.MaterialEditText;

import bi.bigroup.life.R;
import bi.bigroup.life.mvp.main.biboard.top_questions.AddQuestionPresenter;
import bi.bigroup.life.mvp.main.biboard.top_questions.AddQuestionView;
import bi.bigroup.life.ui.base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

import static bi.bigroup.life.utils.ContextUtils.clearFocusFromAllViews;
import static bi.bigroup.life.utils.ContextUtils.hideSoftKeyboard;

public class AddQuestionActivity extends BaseActivity implements AddQuestionView {
    @InjectPresenter
    AddQuestionPresenter mvpPresenter;

    @BindView(R.id.et_content) MaterialEditText et_content;
    @BindView(R.id.et_tags) MaterialEditText et_tags;

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
    }

    @OnClick(R.id.img_close)
    void onCloseClick() {
        finish();
    }

    @OnClick(R.id.btn_ask_question)
    void onAskQuestionClick() {
        clearFocusFromAllViews(fl_parent);
        hideSoftKeyboard(fl_parent);
        mvpPresenter.setFields(et_content.getText().toString(), et_tags.getText().toString());
    }

    private void showInputFieldError(MaterialEditText inputField, @StringRes int errorRes) {
        inputField.setError(getString(errorRes));
    }

    ///////////////////////////////////////////////////////////////////////////
    // AddQuestionView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void showTagsError(@StringRes int errorRes) {
        showInputFieldError(et_tags, errorRes);
    }

    @Override
    public void showContentError(@StringRes int errorRes) {
        showInputFieldError(et_content, errorRes);
    }
}