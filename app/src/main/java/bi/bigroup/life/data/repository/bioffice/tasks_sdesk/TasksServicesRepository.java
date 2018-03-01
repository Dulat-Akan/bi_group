package bi.bigroup.life.data.repository.bioffice.tasks_sdesk;

import java.util.List;

import bi.bigroup.life.data.models.bioffice.tasks_sdesk.Service;
import bi.bigroup.life.data.models.bioffice.tasks_sdesk.Task;
import bi.bigroup.life.data.network.api.bi_group.API;
import rx.Observable;

public interface TasksServicesRepository {

    void setAPI(API api);

    Observable<List<Task>> getOutboxTasks();

    Observable<List<Task>> getInboxTasks(Boolean isOnlyToday);

    Observable<List<Service>> getServiceDeskOutbox();

    Observable<List<Service>> getServiceDeskInbox();
}