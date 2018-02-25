package bi.bigroup.life.mvp.profile.advantages;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import java.util.ArrayList;
import java.util.List;

import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.models.user.advantages.Advantages;
import bi.bigroup.life.data.models.user.plans.Plans;
import bi.bigroup.life.mvp.BaseMvpPresenter;

@InjectViewState
public class AdvantagesPresenter extends BaseMvpPresenter<AdvantagesView> {
    @Override
    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
    }

    public void loadData() {
        String parents[] = {
                "Фитнес",
                "Жилье",
                "BI Club"
        };

        List<Advantages> list = new ArrayList<>();
        for (int i = 0; i < parents.length - 1; i++) {
            for (int j = 0; j < 3; j++) {
                if (j == 0) {
                    list.add(new Advantages(i, parents[i], "Рост прибыли", "Цель"));
                } else if (j == 1) {
                    list.add(new Advantages(i, parents[i], "Two-line item", "Secondary"));
                } else if (j == 2) {
                    list.add(new Advantages(i, parents[i], "Three-line item", "Third"));
                }
            }
        }
        getViewState().addAdvantagesList(list);
    }
}