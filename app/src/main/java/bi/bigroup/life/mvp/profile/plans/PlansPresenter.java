package bi.bigroup.life.mvp.profile.plans;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.models.user.plans.Plans;
import bi.bigroup.life.mvp.BaseMvpPresenter;

@InjectViewState
public class PlansPresenter extends BaseMvpPresenter<PlansView> {
    @Override
    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
    }

    public void loadData() {
        String parents[] = {
                "KPI",
                "Индифидуальный план развития",
                "Даты отпуска"
        };

        List<Plans> plansList = new ArrayList<>();
        for (int i = 0; i < parents.length; i++) {
            for (int j = 0; j < 3; j++) {
                if (j == 0) {
                    plansList.add(new Plans(i, parents[i], "Рост прибыли", "Цель"));
                } else if (j == 1) {
                    plansList.add(new Plans(i, parents[i], "Two-line item", "Secondary"));
                } else if (j == 2) {
                    plansList.add(new Plans(i, parents[i], "Three-line item", "Third"));
                }
            }
        }
        getViewState().addPlansList(plansList);
    }
}
