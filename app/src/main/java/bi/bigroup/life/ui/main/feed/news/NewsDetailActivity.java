package bi.bigroup.life.ui.main.feed.news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import bi.bigroup.life.R;
import bi.bigroup.life.data.models.feed.news.News;
import bi.bigroup.life.mvp.main.feed.news.NewsDetailPresenter;
import bi.bigroup.life.mvp.main.feed.news.NewsDetailView;
import bi.bigroup.life.ui.base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

import static android.support.customtabs.CustomTabsIntent.KEY_ID;

public class NewsDetailActivity extends BaseActivity implements NewsDetailView {
    @InjectPresenter
    NewsDetailPresenter mvpPresenter;
    @BindView(R.id.tv_surname) TextView tv_surname;

    private String id;

    public static Intent getIntent(Context context, String id) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(KEY_ID, id);
        return intent;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvpPresenter.init(this, dataLayer);
        handleIntent();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra(KEY_ID);
            mvpPresenter.getNewsDetail(id);
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

    ///////////////////////////////////////////////////////////////////////////
    // NewsDetailView implementation
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void setNews(News object) {
//        GlideUtils.showAvatar(this, img_avatar, getProfilePicture(employee.getCode()), R.drawable.ic_avatar);
//        tv_surname.setText(employee.getFullName());
//        tv_firstname.setText(employee.getFirstName());
//        tv_specialty.setText(employee.getJobPosition());
//        tv_login.setText(employee.getLogin());
//        tv_manager.setText(employee.getLogin()); // TODO: add manager's fullname
//        tv_phone.setText(employee.getMobilePhoneNumber());
//        tv_email.setText(employee.getEmail());

    }
}