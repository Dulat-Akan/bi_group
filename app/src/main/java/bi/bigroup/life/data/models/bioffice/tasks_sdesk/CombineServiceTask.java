package bi.bigroup.life.data.models.bioffice.tasks_sdesk;

import java.util.List;

public class CombineServiceTask {
    public List<Service> services;
    public List<Task> tasks;

    public CombineServiceTask(List<Service> services, List<Task> tasks) {
        this.services = services;
        this.tasks = tasks;
    }
}
