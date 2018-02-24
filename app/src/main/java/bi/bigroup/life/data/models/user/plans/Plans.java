package bi.bigroup.life.data.models.user.plans;

public class Plans {
    public long parentId;
    public String parentTitle;
    public String title;
    public String description;

    public Plans(long parentId, String parentTitle, String title, String description) {
        this.parentId = parentId;
        this.parentTitle = parentTitle;
        this.title = title;
        this.description = description;
    }
}
