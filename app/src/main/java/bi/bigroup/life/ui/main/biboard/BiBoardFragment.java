package bi.bigroup.life.ui.main.biboard;

import android.os.Bundle;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;

import bi.bigroup.life.R;
import bi.bigroup.life.mvp.main.biboard.BiBoardPresenter;
import bi.bigroup.life.mvp.main.biboard.BiBoardView;
import bi.bigroup.life.ui.base.BaseFragment;
import bi.bigroup.life.ui.main.feed.news.AddNewsActivity;
import bi.bigroup.life.ui.main.bioffice.tasks_sdesk.TasksSdeskActivity;
import bi.bigroup.life.ui.main.bioffice.tasks_sdesk.add_sdesk.AddSdeskActivity;
import bi.bigroup.life.ui.main.publication.PublicationActivity;
import bi.bigroup.life.ui.main.question.AddQuestionActivity;
import bi.bigroup.life.ui.main.suggestion.NewSuggestionActivity;
import butterknife.OnClick;

public class BiBoardFragment extends BaseFragment implements BiBoardView {
    @InjectPresenter
    BiBoardPresenter mvpPresenter;

    public static BiBoardFragment newInstance() {
        BiBoardFragment fragment = new BiBoardFragment();
        Bundle data = new Bundle();
//        data.putParcelable(FORM_KEY, Parcels.wrap(authForm));
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.frgm_biboard;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState, View view) {
        handleIntent();
    }

    @OnClick(R.id.btn_tasks_sdesk)
    void onClick() {
        startActivity(TasksSdeskActivity.getIntent(getContext()));
    }

    @OnClick(R.id.btn_add_question)
    void onAddClick() {
        startActivity(AddQuestionActivity.getIntent(getContext()));
    }

    @OnClick(R.id.btn_new_suggestion)
    void onNewSuggestionClick() {
        startActivity(NewSuggestionActivity.getIntent(getContext()));
    }

    @OnClick(R.id.btn_new_publication)
    void onNewPublication() {
        startActivity(PublicationActivity.getIntent(getContext()));
    }

    @OnClick(R.id.btn_add_news)
    void onAddNews() {
        startActivity(AddNewsActivity.getIntent(getContext()));
    }

    @OnClick(R.id.btn_new_sdesk)
    void onNewSdesk() {
        startActivity(AddSdeskActivity.getIntent(getContext()));
    }

    private void handleIntent() {
//        if (getArguments() != null && getArguments().containsKey(FORM_KEY)) {
//            AuthForm form = Parcels.unwrap(getArguments().getParcelable(FORM_KEY));
//            mvpPresenter.init(dataLayer, form);
//        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // BiBoardView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void successSent() {
    }
}