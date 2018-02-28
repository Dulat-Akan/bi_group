package bi.bigroup.life.mvp.main.bioffice.tasks_sdesk;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.mvp.BaseMvpPresenter;

@InjectViewState
public class TasksSdeskPresenter extends BaseMvpPresenter<TasksSdeskView> {

    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
    }
}