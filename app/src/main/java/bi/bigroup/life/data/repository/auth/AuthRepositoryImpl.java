package bi.bigroup.life.data.repository.auth;

import bi.bigroup.life.data.models.auth.Auth;
import bi.bigroup.life.data.network.api.bi_group.API;
import bi.bigroup.life.data.params.auth.AuthEmailPwdParams;
import bi.bigroup.life.data.params.auth.AuthParams;
import bi.bigroup.life.utils.rx.Transformers;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
class AuthRepositoryImpl implements AuthRepository {
    private API api;

    @Override
    public void setAPI(API api) {
        this.api = api;
    }

    @Override
    public Observable<Auth> signInWithEmailAndPassword(AuthEmailPwdParams params) {
        return api
                .signInWithEmailAndPassword(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(Transformers.responseDataExtractor());
    }

    @Override
    public Observable<Auth> signIn(AuthParams params) {
        return null;
    }
}