package bi.bigroup.life.data.models.bioffice.tasks_sdesk;

import java.util.List;

public class CombineServiceTask {
    public List<Service> outboxServices;
    public List<Task> inboxTasks;
    public List<Task> outboxTasks;

    public CombineServiceTask(List<Service> outboxServices, List<Task> inboxTasks, List<Task> outboxTasks) {
        this.outboxServices = outboxServices;
        this.inboxTasks = inboxTasks;
        this.outboxTasks = outboxTasks;
    }
}
