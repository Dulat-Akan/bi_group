package bi.bigroup.life.data.models.user.results;

public class Results {
    public long parentId;
    public String parentTitle;
    public String title;
    public String description;

    public Results(long parentId, String parentTitle, String title, String description) {
        this.parentId = parentId;
        this.parentTitle = parentTitle;
        this.title = title;
        this.description = description;
    }
}
