package bi.bigroup.life.mvp.main.menu;

import bi.bigroup.life.data.models.user.User;
import bi.bigroup.life.mvp.BaseMvpView;

public interface MenuView extends BaseMvpView {
    void showUserInfo(User localUser);
}