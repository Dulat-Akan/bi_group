package bi.bigroup.life.data.repository.auth;

import bi.bigroup.life.data.models.auth.Auth;
import bi.bigroup.life.data.network.api.bi_group.API;
import bi.bigroup.life.data.params.auth.AuthParams;
import okhttp3.ResponseBody;
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
    public Observable<Auth> signIn(AuthParams params) {
        return api
                .signIn(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}