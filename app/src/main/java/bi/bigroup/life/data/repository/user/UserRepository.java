package bi.bigroup.life.data.repository.user;

import bi.bigroup.life.data.models.auth.Auth;
import bi.bigroup.life.data.models.user.User;
import bi.bigroup.life.data.network.api.bi_group.API;
import bi.bigroup.life.data.params.auth.AuthParams;
import rx.Observable;

public interface UserRepository {

    void setAPI(API api);

    Observable<User> getUser();
}