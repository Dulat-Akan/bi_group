package bi.bigroup.life.ui.main.feed.questionnaires;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.etiennelawlor.imagegallery.library.activities.FullScreenImageGalleryActivity;

import java.util.ArrayList;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.feed.questionnaire.Questionnaire;
import bi.bigroup.life.mvp.main.feed.questionnaires.QuestionnaireDetailPresenter;
import bi.bigroup.life.mvp.main.feed.questionnaires.QuestionnaireDetailView;
import bi.bigroup.life.ui.base.BaseActivity;
import bi.bigroup.life.ui.main.feed.ViewPagerImage;
import bi.bigroup.life.utils.picasso.PicassoUtils;
import bi.bigroup.life.views.RoundedImageView;
import bi.bigroup.life.views.circle_page_indicator.CirclePageIndicator;
import bi.bigroup.life.views.viewpager.ViewPagerDisabled;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

import static android.support.customtabs.CustomTabsIntent.KEY_ID;
import static bi.bigroup.life.utils.Constants.SHARE_QUESTIONNAIRES;
import static bi.bigroup.life.utils.Constants.buildShareUrl;
import static bi.bigroup.life.utils.Constants.getProfilePicture;
import static bi.bigroup.life.utils.StringUtils.isStringOk;

public class QuestionnaireDetailActivity extends BaseActivity implements QuestionnaireDetailView {
    @InjectPresenter
    QuestionnaireDetailPresenter mvpPresenter;

    @BindView(R.id.pb_indicator_transparent) ViewGroup pb_indicator_transparent;
    @BindView(R.id.vp_images) ViewPager vp_images;
    @BindView(R.id.vp_questions) ViewPagerDisabled vp_questions;
    @BindView(R.id.ci_images) CirclePageIndicator ci_images;
    @BindView(R.id.img_avatar) RoundedImageView img_avatar;
    @BindView(R.id.tv_username) TextView tv_username;
    @BindView(R.id.tv_time) TextView tv_time;
    @BindView(R.id.tv_title) TextView tv_title;
    @BindView(R.id.btn_back) Button btn_back;
    @BindView(R.id.btn_next) Button btn_next;
    @BindString(R.string.finish) String labelFinish;
    @BindString(R.string.continue_label) String labelNext;

    private Questionnaire bindedObject;
    private ViewPagerImage adapter;
    private QuestionVpAdapter questAdapter;
    private String questionnaireId;

    public static Intent getIntent(Context context, String id) {
        Intent intent = new Intent(context, QuestionnaireDetailActivity.class);
        intent.putExtra(KEY_ID, id);
        return intent;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_questionnaire_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvpPresenter.init(this, dataLayer);
        adapter = new ViewPagerImage(this, dataLayer.getPicasso());
        questAdapter = new QuestionVpAdapter(this);
        vp_questions.setPagingEnabled(false);
        vp_questions.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                btn_next.setText(position == questAdapter.getCount() - 1
                        ? labelFinish : labelNext);
                btn_back.setVisibility(position > 0 ? View.VISIBLE : View.INVISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        handleIntent();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            questionnaireId = intent.getStringExtra(KEY_ID);
            mvpPresenter.getQuestionnaireDetail(questionnaireId);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mvpPresenter.onDestroyView();
    }

    @OnClick(R.id.img_close)
    void onCloseClick() {
        finish();
    }

    @OnClick(R.id.fb_share)
    void onShareClick() {
        if (bindedObject != null && isStringOk(bindedObject.getTitle()) && isStringOk(bindedObject.getId())) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, bindedObject.getTitle() + "\n" +
                    buildShareUrl(SHARE_QUESTIONNAIRES, bindedObject.getId()));
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
    }

    @OnClick({R.id.btn_back, R.id.btn_next})
    void onStepsClick(Button btn) {
        if (btn.getId() == R.id.btn_back && vp_questions.getCurrentItem() > 0) {
            vp_questions.setCurrentItem(vp_questions.getCurrentItem() - 1);
        } else if (btn.getId() == R.id.btn_next) {
            if (vp_questions.getCurrentItem() < questAdapter.getCount() - 1) {
                vp_questions.setCurrentItem(vp_questions.getCurrentItem() + 1);
            } else {
                // Send answeres to server
                if (questAdapter != null &&
                        questAdapter.getData() != null && questAdapter.getData().size() > 0) {
                    mvpPresenter.setUserAnswers(questionnaireId, questAdapter.getData());
                }
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // QuestionnaireDetailView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void showTransparentIndicator(boolean show) {
        pb_indicator_transparent.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setQuestionnaire(Questionnaire object) {
        bindedObject = object;
        PicassoUtils.showAvatar(dataLayer.getPicasso(), img_avatar, getProfilePicture(object.getAuthorCode()), R.drawable.ic_user);
        tv_title.setText(object.getTitle());
        tv_time.setText(object.getDate(this));
        tv_username.setText(object.getAuthorName());

        // Image slider
        ArrayList<String> sliderImages = new ArrayList<>();
        sliderImages.add(object.getImageUrl());
        if (object.secondaryImages != null && object.secondaryImages.size() > 0) {
            for (int i = 0; i < object.secondaryImages.size(); i++) {
                String imgUrl = object.secondaryImages.get(i).getImageUrl();
                if (isStringOk(imgUrl)) {
                    sliderImages.add(imgUrl);
                }
            }
        }

        adapter.addImages(sliderImages);
        adapter.setCallback(position -> {
            Intent intent = new Intent(QuestionnaireDetailActivity.this, FullScreenImageGalleryActivity.class);
            Bundle bundle = new Bundle();
            bundle.putStringArrayList(FullScreenImageGalleryActivity.KEY_IMAGES, sliderImages);
            bundle.putInt(FullScreenImageGalleryActivity.KEY_POSITION, position);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        vp_images.setAdapter(adapter);
        ci_images.setViewPager(vp_images);

        if (object.questions != null && object.questions.size() > 0) {
            questAdapter.addData(object.questions);
            vp_questions.setAdapter(questAdapter);
        }
    }

    @Override
    public void showRequestSuccess(int message) {
        super.showRequestSuccess(message);
        finish();
    }
}