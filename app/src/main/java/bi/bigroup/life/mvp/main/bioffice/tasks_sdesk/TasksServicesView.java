package bi.bigroup.life.mvp.main.bioffice.tasks_sdesk;

import java.util.List;

import bi.bigroup.life.mvp.BaseSwipeRefreshMvpView;

public interface TasksServicesView extends BaseSwipeRefreshMvpView {
    void showLoadingItemIndicator(boolean show);

    void setList(List<?> list);

    void addList(List<?> list);
}