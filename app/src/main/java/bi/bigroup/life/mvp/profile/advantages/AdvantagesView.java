package bi.bigroup.life.mvp.profile.advantages;

import java.util.List;

import bi.bigroup.life.data.models.user.advantages.Advantages;
import bi.bigroup.life.data.models.user.plans.Plans;
import bi.bigroup.life.mvp.BaseMvpView;

public interface AdvantagesView extends BaseMvpView {

    void addAdvantagesList(List<Advantages> list);
}
