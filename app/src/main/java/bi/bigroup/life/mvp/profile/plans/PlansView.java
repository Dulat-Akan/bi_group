package bi.bigroup.life.mvp.profile.plans;

import java.util.List;

import bi.bigroup.life.data.models.user.plans.Plans;
import bi.bigroup.life.mvp.BaseMvpView;

public interface PlansView extends BaseMvpView {

    void addPlansList(List<Plans> list);
}
