package bi.bigroup.life.mvp.main.bioffice.tasks_sdesk.add_task;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.mvp.BaseMvpPresenter;

@InjectViewState
public class AddTaskPresenter extends BaseMvpPresenter<AddTaskView> {

    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
    }
}