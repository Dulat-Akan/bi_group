package bi.bigroup.life.data.network.api.bi_group;

import bi.bigroup.life.data.params.auth.AuthParams;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;
public interface API {

    /****** Authorization *******/
    @POST("token/")
    Observable<ResponseBody> signIn(@Body AuthParams params);
}