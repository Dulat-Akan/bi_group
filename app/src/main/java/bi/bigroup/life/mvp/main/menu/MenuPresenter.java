package bi.bigroup.life.mvp.main.menu;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.models.user.User;
import bi.bigroup.life.mvp.BaseMvpPresenter;

@InjectViewState
public class MenuPresenter extends BaseMvpPresenter<MenuView> {
    @Override
    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
        User localUser = preferences.getUser();
        if (localUser != null) {
            getViewState().showUserInfo(localUser);
        }
    }
}
