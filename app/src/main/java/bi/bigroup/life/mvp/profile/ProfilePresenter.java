package bi.bigroup.life.mvp.profile;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.mvp.BaseMvpPresenter;

@InjectViewState
public class ProfilePresenter extends BaseMvpPresenter<ProfileView> {

    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
    }
}