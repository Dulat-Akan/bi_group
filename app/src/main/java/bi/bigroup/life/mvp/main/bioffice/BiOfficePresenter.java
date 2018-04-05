package bi.bigroup.life.mvp.main.bioffice;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.models.ListOf;
import bi.bigroup.life.data.models.biboard.BiBoard;
import bi.bigroup.life.data.models.biboard.top_questions.TopQuestions;
import bi.bigroup.life.data.models.bioffice.tasks_sdesk.CombineServiceTask;
import bi.bigroup.life.data.models.bioffice.tasks_sdesk.Task;
import bi.bigroup.life.data.models.employees.Employee;
import bi.bigroup.life.data.models.employees.Vacancy;
import bi.bigroup.life.data.models.feed.news.News;
import bi.bigroup.life.data.models.feed.questionnaire.Questionnaire;
import bi.bigroup.life.data.models.feed.suggestions.Suggestion;
import bi.bigroup.life.data.repository.biboard.top_questions.TopQuestionsRepositoryProvider;
import bi.bigroup.life.data.repository.bioffice.tasks_sdesk.TasksServicesRepository;
import bi.bigroup.life.data.repository.bioffice.tasks_sdesk.TasksServicesRepositoryProvider;
import bi.bigroup.life.data.repository.employees.EmployeesRepository;
import bi.bigroup.life.data.repository.employees.EmployeesRepositoryProvider;
import bi.bigroup.life.data.repository.feed.news.NewsRepositoryProvider;
import bi.bigroup.life.data.repository.feed.questionnaire.QuestionnaireRepository;
import bi.bigroup.life.data.repository.feed.questionnaire.QuestionnaireRepositoryProvider;
import bi.bigroup.life.data.repository.feed.suggestions.SuggestionsRepository;
import bi.bigroup.life.data.repository.feed.suggestions.SuggestionsRepositoryProvider;
import bi.bigroup.life.mvp.BaseMvpPresenter;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.functions.Func3;
import rx.schedulers.Schedulers;

import static bi.bigroup.life.ui.main.bioffice.BiOfficeAdapter.TYPE_QUESTIONNAIRE;
import static bi.bigroup.life.ui.main.bioffice.BiOfficeAdapter.TYPE_SUGGESTIONS;
import static bi.bigroup.life.utils.Constants.INITIAL_PAGE_NUMBER;
import static bi.bigroup.life.utils.Constants.REQUEST_COUNT;
import static bi.bigroup.life.utils.Constants.TOP_3;

@InjectViewState
public class BiOfficePresenter extends BaseMvpPresenter<BiOfficeView> {
    @Override
    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
        getPopularNews();
        combineServicesTasks();
        combineSuggestionsRequests();
        combineQuestionnaireRequests();
//        combineEmployeeRequests();
        getTopQuestions();
    }

    private void getPopularNews() {
        Subscription subscription = NewsRepositoryProvider.provideRepository(dataLayer.getApi())
                .getPopularNews(TOP_3)
                .doOnSubscribe(() -> getViewState().showLoadingIndicator(true))
                .doOnTerminate(() -> getViewState().showLoadingIndicator(false))
                .subscribe(new Subscriber<List<News>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        handleResponseError(context, e);
                    }

                    @Override
                    public void onNext(List<News> object) {
                        if (object != null && object.size() > 0) {
                            getViewState().setPopularNews(object);
                        }
                    }
                });
        compositeSubscription.add(subscription);
    }

    private void combineServicesTasks() {
        TasksServicesRepository repository = TasksServicesRepositoryProvider.provideRepository(dataLayer.getApi());
        Observable.zip(
//                repository.getServiceDeskOutbox(),
                repository.getInboxTasks(false),
                repository.getOutboxTasks(),
                new Func2<List<Task>, List<Task>, CombineServiceTask>() {//List<Service>,
                    @Override
                    public CombineServiceTask call(
                            List<Task> inboxTasks, List<Task> outboxTasks) {//List<Service> outboxServices,
                        return new CombineServiceTask(null, inboxTasks, outboxTasks);
                    }
                })
                .doOnSubscribe(() -> getViewState().showLoadingIndicator(true))
                .doOnTerminate(() -> getViewState().showLoadingIndicator(false))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CombineServiceTask>() {
                    @Override
                    public void onNext(CombineServiceTask object) {
                        if (object != null) {
                            getViewState().setCombinedServiceTask(object);
                        }
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        handleResponseError(context, e);
                    }
                });
    }

    // ************************** Bi Board **************************
    // Suggestions
    private void combineSuggestionsRequests() {
        SuggestionsRepository repository = SuggestionsRepositoryProvider.provideRepository(dataLayer.getApi());
        Observable.zip(
                repository.getPopularSuggestions(),
                repository.getAllSuggestions(),
                new Func2<List<Suggestion>, List<Suggestion>, BiBoard>() {
                    @Override
                    public BiBoard call(List<Suggestion> popularSuggestions, List<Suggestion> allSuggestions) {
                        return new BiBoard(R.string.predlojeniya, R.string.label_all, R.string.label_all, R.string.label_popular,
                                popularSuggestions, allSuggestions, null, null, null, null, -1);
                    }
                })
                .doOnSubscribe(() -> getViewState().showLoadingIndicator(true))
                .doOnTerminate(() -> getViewState().showLoadingIndicator(false))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BiBoard>() {
                    @Override
                    public void onNext(BiBoard biBoard) {
                        if (biBoard != null) {
                            getViewState().setBiBoardData(biBoard, TYPE_SUGGESTIONS);
                        }
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        handleResponseError(context, e);
                    }
                });
    }

    // Questionnaire
    private void combineQuestionnaireRequests() {
        QuestionnaireRepository repository = QuestionnaireRepositoryProvider.provideRepository(dataLayer.getApi());
        Observable.zip(
                repository.getPopularQuestionnaires(),
                repository.getAllQuestionnaire(),
                new Func2<List<Questionnaire>, List<Questionnaire>, BiBoard>() {
                    @Override
                    public BiBoard call(List<Questionnaire> popularQuestionnaires, List<Questionnaire> allQuestionnaires) {
                        return new BiBoard(R.string.oprosnik, R.string.label_all, R.string.label_all, R.string.label_popular,
                                null, null, popularQuestionnaires, allQuestionnaires, null, null, -1);
                    }
                })
                .doOnSubscribe(() -> getViewState().showLoadingIndicator(true))
                .doOnTerminate(() -> getViewState().showLoadingIndicator(false))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BiBoard>() {
                    @Override
                    public void onNext(BiBoard biBoard) {
                        if (biBoard != null) {
                            getViewState().setBiBoardData(biBoard, TYPE_QUESTIONNAIRE);
                        }
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        handleResponseError(context, e);
                    }
                });
    }

    // Employees
    private void combineEmployeeRequests() {
        EmployeesRepository repository = EmployeesRepositoryProvider.provideRepository(dataLayer.getApi());
        Observable.zip(
                repository.getEmployees(REQUEST_COUNT, INITIAL_PAGE_NUMBER, false),
                repository.getEmployeesEvents(),
                repository.getVacancies(),
                new Func3<ListOf<Employee>, List<Employee>, List<Vacancy>, BiBoard>() {
                    @Override
                    public BiBoard call(ListOf<Employee> allEmployees, List<Employee> employees, List<Vacancy> vacancies) {
                        return new BiBoard(R.string.employees, R.string.label_all, R.string.dni_rojdenya, R.string.vacancii,
                                null, null, null, null, employees, vacancies, allEmployees.getCount());
                    }
                })
                .doOnSubscribe(() -> getViewState().showLoadingIndicator(true))
                .doOnTerminate(() -> getViewState().showLoadingIndicator(false))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BiBoard>() {
                    @Override
                    public void onNext(BiBoard biBoard) {
                        if (biBoard != null) {
//                            getViewState().setBiBoardData(biBoard, TYPE_EMPLOYEES);
                        }
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        handleResponseError(context, e);
                    }
                });
    }


    // Top Questions
    private void getTopQuestions() {
        Subscription subscription = TopQuestionsRepositoryProvider.provideRepository(dataLayer.getApi())
                .getTopQuestions()
                .doOnSubscribe(() -> getViewState().showLoadingIndicator(true))
                .doOnTerminate(() -> getViewState().showLoadingIndicator(false))
                .subscribe(new Subscriber<List<TopQuestions>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        handleResponseError(context, e);
                    }

                    @Override
                    public void onNext(List<TopQuestions> list) {
                        if (list != null && list.size() > 0) {
                            getViewState().setTopQuestions(list);
                        }
                    }
                });
        compositeSubscription.add(subscription);
    }
}