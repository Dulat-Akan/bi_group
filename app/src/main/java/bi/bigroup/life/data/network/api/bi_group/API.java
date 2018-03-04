package bi.bigroup.life.data.network.api.bi_group;

import java.util.List;

import bi.bigroup.life.data.models.ListOf;
import bi.bigroup.life.data.models.auth.Auth;
import bi.bigroup.life.data.models.bioffice.tasks_sdesk.Service;
import bi.bigroup.life.data.models.bioffice.tasks_sdesk.Task;
import bi.bigroup.life.data.models.employees.Employee;
import bi.bigroup.life.data.models.employees.Vacancy;
import bi.bigroup.life.data.models.feed.Feed;
import bi.bigroup.life.data.models.feed.news.AddComment;
import bi.bigroup.life.data.models.feed.news.Comment;
import bi.bigroup.life.data.models.feed.news.News;
import bi.bigroup.life.data.models.notifications.Notification;
import bi.bigroup.life.data.models.user.User;
import bi.bigroup.life.data.params.auth.AuthParams;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    /****** User *******/
    @GET("UserProfile/")
    Observable<User> getUserProfile();

    /****** News *******/
    @GET("News/{id}")
    Observable<News> getNews(@Path("id") String id);

    @PUT("News/{id}/like")
    Observable<ResponseBody> likeNews(@Path("id") String id);

    @POST("News/{id}/comments")
    Observable<Comment> addComment(@Path("id") String id,
                                   @Body AddComment params);

    /****** Notifications *******/
    @GET("Notifications/")
    Observable<List<Notification>> getNotifications();

    @PUT("Notifications/{id}")
    Observable<ResponseBody> removeNotification(@Path("id") String id);

    /****** Employees *******/
    @GET("employees/")
    Observable<ListOf<Employee>> getEmployees(@Query("Rows") int Rows,
                                              @Query("Offset") int Offset,
                                              @Query("IsBirthdayToday") Boolean IsBirthdayToday);

    @GET("employees/search/")
    Observable<List<Employee>> searchEmployees(@Query("filterText") String filterText,
                                               @Query("top") String top);

    @GET("vacancies/")
    Observable<List<Vacancy>> getVacancies();

    @GET("employees/{code}")
    Observable<Employee> getEmployee(@Path("code") String code);

    /****** Tasks & Service *******/
    @GET("tasks/outbox/")
    Observable<List<Task>> getOutboxTasks();

    @GET("tasks/inbox/")
    Observable<List<Task>> getInboxTasks(@Query("isOnlyToday") Boolean isOnlyToday);

    @GET("Requests/servicedesk/outbox/")
    Observable<List<Service>> getServiceDeskOutbox();

    @GET("Requests/servicedesk/inbox/")
    Observable<List<Service>> getServiceDeskInbox();
}