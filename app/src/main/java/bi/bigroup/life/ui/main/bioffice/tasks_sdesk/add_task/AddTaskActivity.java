package bi.bigroup.life.ui.main.bioffice.tasks_sdesk.add_task;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.arellomobile.mvp.presenter.InjectPresenter;

import bi.bigroup.life.R;
import bi.bigroup.life.mvp.main.bioffice.tasks_sdesk.add_task.AddTaskPresenter;
import bi.bigroup.life.mvp.main.bioffice.tasks_sdesk.add_task.AddTaskView;
import bi.bigroup.life.ui.base.BaseActivity;
import butterknife.OnClick;

public class AddTaskActivity extends BaseActivity implements AddTaskView {
    @InjectPresenter
    AddTaskPresenter mvpPresenter;

    public static Intent getIntent(Context context) {
        return new Intent(context, AddTaskActivity.class);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_add_task;
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

    ///////////////////////////////////////////////////////////////////////////
    // AddSdeskView implementation
    ///////////////////////////////////////////////////////////////////////////
}