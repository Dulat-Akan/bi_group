package bi.bigroup.life.data.network.api.bi_group;

import java.util.List;

import bi.bigroup.life.data.models.ListOf;
import bi.bigroup.life.data.models.auth.Auth;
import bi.bigroup.life.data.models.biboard.top_questions.TopQuestions;
import bi.bigroup.life.data.models.bioffice.tasks_sdesk.Service;
import bi.bigroup.life.data.models.bioffice.tasks_sdesk.Task;
import bi.bigroup.life.data.models.bioffice.top_questions.AddQuestionParams;
import bi.bigroup.life.data.models.employees.Employee;
import bi.bigroup.life.data.models.employees.Vacancy;
import bi.bigroup.life.data.models.feed.Feed;
import bi.bigroup.life.data.models.feed.news.AddComment;
import bi.bigroup.life.data.models.feed.news.Comment;
import bi.bigroup.life.data.models.feed.news.News;
import bi.bigroup.life.data.models.feed.questionnaire.Questionnaire;
import bi.bigroup.life.data.models.feed.suggestions.Suggestion;
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

    @GET("News/popular")
    Observable<List<News>> getPopularNews();

    @GET("News/")
    Observable<List<News>> getPopularNews(@Query("top") int top);

    @PUT("News/{id}/like")
    Observable<ResponseBody> likeNews(@Path("id") String id);

    @PUT("News/{id}/comments/{commentId}/like/{voteType}")
    Observable<ResponseBody> likeNewsComment(@Path("id") String id,
                                             @Path("commentId") String commentId,
                                             @Path("voteType") Integer voteType);

    @POST("News/{id}/comments")
    Observable<Comment> addNewsComment(@Path("id") String id,
                                       @Body AddComment params);

    /****** Suggestions *******/
    @GET("Suggestions/{id}")
    Observable<Suggestion> getSuggestion(@Path("id") String id);

    @GET("Suggestions/popular")
    Observable<List<Suggestion>> getPopularSuggestions();

    @GET("Suggestions/")
    Observable<List<Suggestion>> getAllSuggestions();

    @PUT("Suggestions/{id}/like/{voteType}")
    Observable<ResponseBody> likeSuggestion(@Path("id") String id,
                                            @Path("voteType") Integer voteType);

    @PUT("Suggestions/{id}/comments/{commentId}/like/{voteType}")
    Observable<ResponseBody> likeSuggestionComment(@Path("id") String id,
                                                   @Path("commentId") String commentId,
                                                   @Path("voteType") Integer voteType);

    @POST("Suggestions/{id}/comments")
    Observable<Comment> addSuggestionsComment(@Path("id") String id,
                                              @Body AddComment params);

    /****** Questionnaires *******/
    @GET("Questionnaires/popular")
    Observable<List<Questionnaire>> getPopularQuestionnaires();

    @GET("Questionnaires/")
    Observable<List<Questionnaire>> getAllQuestionnaires();

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

    @GET("employeesEvents/")
    Observable<List<Employee>> getEmployeesEvents();

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

    /****** Top Questions *******/
    @GET("topQuestions/")
    Observable<List<TopQuestions>> getTopQuestions();

    @POST("topQuestions/")
    Observable<ResponseBody> addQuestion(@Body AddQuestionParams params);

}