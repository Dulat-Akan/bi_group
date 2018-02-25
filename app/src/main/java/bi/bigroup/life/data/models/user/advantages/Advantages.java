package bi.bigroup.life.data.models.user.advantages;

public class Advantages {
    public long parentId;
    public String parentTitle;
    public String title;
    public String description;

    public Advantages(long parentId, String parentTitle, String title, String description) {
        this.parentId = parentId;
        this.parentTitle = parentTitle;
        this.title = title;
        this.description = description;
    }
}

