package bi.bigroup.life.data.network.api.bi_group;

import bi.bigroup.life.data.models.APIResponse;
import bi.bigroup.life.data.models.auth.Auth;
import bi.bigroup.life.data.params.auth.AuthEmailPwdParams;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;
public interface API {

    /****** Authorization *******/
    @POST("signInWithEmailAndPassword/")
    Observable<APIResponse<Auth>> signInWithEmailAndPassword(@Body AuthEmailPwdParams params);

}