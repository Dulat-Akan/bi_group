package bi.bigroup.life.data.network.api.bi_group;

import java.util.List;

import bi.bigroup.life.data.models.ListOf;
import bi.bigroup.life.data.models.auth.Auth;
import bi.bigroup.life.data.models.employees.Employee;
import bi.bigroup.life.data.models.feed.Feed;
import bi.bigroup.life.data.params.auth.AuthParams;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface API {

    /****** Authorization *******/
    @POST("token/")
    Observable<Auth> signIn(@Body AuthParams params);

    @GET("Lenta/")
    Observable<List<Feed>> getFeedList(@Query("rows") int rows,
                                       @Query("offset") int offset,
                                       @Query("withDescription") Boolean withDescription);

    @GET("employees/")
    Observable<ListOf<Employee>> getEmployees(@Query("Rows") int Rows,
                                              @Query("Offset") int Offset,
                                              @Query("IsBirthdayToday") Boolean IsBirthdayToday);
}