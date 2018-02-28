package bi.bigroup.life.mvp.main.bioffice.tasks_sdesk.add_sdesk;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.mvp.BaseMvpPresenter;

@InjectViewState
public class AddSdeskPresenter extends BaseMvpPresenter<AddSdeskView> {

    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
    }
}