package bi.bigroup.life.mvp.main.bioffice;

import bi.bigroup.life.data.models.bioffice.tasks_sdesk.CombineServiceTask;
import bi.bigroup.life.mvp.BaseMvpView;

public interface BiOfficeView extends BaseMvpView {
    void setCombinedServiceTask(CombineServiceTask object);
}
