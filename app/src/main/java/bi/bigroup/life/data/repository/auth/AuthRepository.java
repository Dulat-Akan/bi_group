package bi.bigroup.life.data.repository.auth;

import bi.bigroup.life.data.network.api.bi_group.API;
import bi.bigroup.life.data.params.auth.AuthParams;
import okhttp3.ResponseBody;
import rx.Observable;

public interface AuthRepository {

    void setAPI(API api);

    Observable<ResponseBody> signIn(AuthParams params);
}