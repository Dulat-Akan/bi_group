package bi.bigroup.life.data.network.api.bi_group;

import java.util.List;

import bi.bigroup.life.data.models.ListOf;
import bi.bigroup.life.data.models.auth.Auth;
import bi.bigroup.life.data.models.employees.Employee;
import bi.bigroup.life.data.models.employees.Vacancy;
import bi.bigroup.life.data.models.feed.Feed;
import bi.bigroup.life.data.models.feed.news.News;
import bi.bigroup.life.data.params.auth.AuthParams;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface API {

    /****** Authorization *******/
    @POST("token/")
    Observable<Auth> signIn(@Body AuthParams params);

    /****** Feed *******/
    @GET("Lenta/")
    Observable<List<Feed>> getFeedList(@Query("rows") int rows,
                                       @Query("offset") int offset,
                                       @Query("withDescription") Boolean withDescription);

    /****** News *******/
    @GET("News/{id}")
    Observable<News> getNews(@Path("id") String id);

    /****** Employees *******/

    @GET("employees/")
    Observable<ListOf<Employee>> getEmployees(@Query("Rows") int Rows,
                                              @Query("Offset") int Offset,
                                              @Query("IsBirthdayToday") Boolean IsBirthdayToday);

    @GET("vacancies/")
    Observable<List<Vacancy>> getVacancies();

    @GET("employees/{code}")
    Observable<Employee> getEmployee(@Path("code") String code);
}