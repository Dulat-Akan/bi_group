package bi.bigroup.life.mvp.profile.profile_data;

import bi.bigroup.life.data.models.user.User;
import bi.bigroup.life.mvp.BaseMvpView;

public interface ProfileDataView extends BaseMvpView {
    void showUserInfo(User localUser);
}
