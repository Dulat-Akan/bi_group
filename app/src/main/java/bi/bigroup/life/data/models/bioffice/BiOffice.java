package bi.bigroup.life.data.models.bioffice;

import bi.bigroup.life.data.models.bioffice.tasks_sdesk.CombineServiceTask;

public class BiOffice {
    public int title;
    public int first;
    public int second;
    public int third;
    public CombineServiceTask combined;

    public BiOffice(int title, int first, int second, int third, CombineServiceTask combined) {
        this.title = title;
        this.first = first;
        this.second = second;
        this.third = third;
        this.combined = combined;
    }
}
