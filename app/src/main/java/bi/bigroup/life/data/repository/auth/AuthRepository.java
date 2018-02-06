package bi.bigroup.life.data.repository.auth;

import bi.bigroup.life.data.models.auth.Auth;
import bi.bigroup.life.data.network.api.bi_group.API;
import bi.bigroup.life.data.params.auth.AuthEmailPwdParams;
import bi.bigroup.life.data.params.auth.AuthParams;
import rx.Observable;

public interface AuthRepository {

    void setAPI(API api);

    Observable<Auth> signInWithEmailAndPassword(AuthEmailPwdParams params);

    Observable<Auth> signIn(AuthParams params);
}