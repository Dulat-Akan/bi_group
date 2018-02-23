package bi.bigroup.life.mvp.main;

import bi.bigroup.life.data.models.user.User;
import bi.bigroup.life.mvp.BaseMvpView;

public interface MainView extends BaseMvpView {

    void showUserInfo(User localUser);
}
