package bi.bigroup.life.data.repository.extra;

import bi.bigroup.life.data.models.user.User;
import bi.bigroup.life.data.network.api.bi_group.API;
import rx.Observable;

public interface ExtraRepository {

    void setAPI(API api);

    Observable<User> getUser();
}