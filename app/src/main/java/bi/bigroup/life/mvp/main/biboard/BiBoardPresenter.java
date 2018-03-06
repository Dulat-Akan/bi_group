package bi.bigroup.life.mvp.main.biboard;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import bi.bigroup.life.R;
import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.models.biboard.BiBoard;
import bi.bigroup.life.data.models.employees.Employee;
import bi.bigroup.life.data.models.employees.Vacancy;
import bi.bigroup.life.data.models.feed.news.News;
import bi.bigroup.life.data.models.feed.questionnaire.Questionnaire;
import bi.bigroup.life.data.models.feed.suggestions.Suggestion;
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
import rx.schedulers.Schedulers;

import static bi.bigroup.life.ui.main.biboard.BiBoardAdapter.TYPE_EMPLOYEES;
import static bi.bigroup.life.ui.main.biboard.BiBoardAdapter.TYPE_QUESTIONNAIRE;
import static bi.bigroup.life.ui.main.biboard.BiBoardAdapter.TYPE_SUGGESTIONS;

@InjectViewState
public class BiBoardPresenter extends BaseMvpPresenter<BiBoardView> {
    private final static int TOP_3 = 3;

    @Override
    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
        getPopularNews();
        combineSuggestionsRequests();
        combineQuestionnaireRequests();
        combineEmployeeRequests();
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

    // Suggestions
    private void combineSuggestionsRequests() {
        SuggestionsRepository repository = SuggestionsRepositoryProvider.provideRepository(dataLayer.getApi());
        Observable.zip(
                repository.getPopularSuggestions(),
                repository.getAllSuggestions(),
                new Func2<List<Suggestion>, List<Suggestion>, BiBoard>() {
                    @Override
                    public BiBoard call(List<Suggestion> popularSuggestions, List<Suggestion> allSuggestions) {
                        return new BiBoard(R.string.predlojeniya, R.string.temp_novyh, R.string.temp_novyh, R.string.temp_novyh,
                                popularSuggestions, allSuggestions, null, null, null, null);
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
                        return new BiBoard(R.string.oprosnik, R.string.temp_novyh, R.string.temp_novyh, R.string.temp_novyh,
                                null, null, popularQuestionnaires, allQuestionnaires, null, null);
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
                repository.getEmployeesEvents(),
                repository.getVacancies(),
                new Func2<List<Employee>, List<Vacancy>, BiBoard>() {
                    @Override
                    public BiBoard call(List<Employee> employees, List<Vacancy> vacancies) {
                        return new BiBoard(R.string.employees, R.string.novyh, R.string.dni_rojdenya, R.string.vacancii,
                                null, null, null, null, employees, vacancies);
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
                            getViewState().setBiBoardData(biBoard, TYPE_EMPLOYEES);
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
}
