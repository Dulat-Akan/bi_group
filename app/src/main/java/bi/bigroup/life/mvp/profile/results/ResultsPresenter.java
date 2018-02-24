package bi.bigroup.life.mvp.profile.results;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.models.user.results.Results;
import bi.bigroup.life.mvp.BaseMvpPresenter;
import bi.bigroup.life.utils.LOTimber;

@InjectViewState
public class ResultsPresenter extends BaseMvpPresenter<ResultsView> {
    @Override
    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
    }

    public void loadData() {
        String parents[] = {
                "Корпоративные события",
                "Обучение и награды",
                "Оценки и аттестации"
        };

        List<Results> resultsList = new ArrayList<>();
        for (int i = 0; i < parents.length; i++) {
            for (int j = 0; j < 3; j++) {
                if (j == 0) {
                    resultsList.add(new Results(i, parents[i], "BI-марафон", "Интуит от 2018"));
                } else if (j == 1) {
                    resultsList.add(new Results(i, parents[i], "Астана марафон", "17 октября"));
                } else if (j == 2) {
                    resultsList.add(new Results(i, parents[i], "Курс", "Повышение квалиф"));
                }
            }
        }

        getViewState().addResultsList(resultsList);
    }
}
