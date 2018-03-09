package bi.bigroup.life.data.models.feed.news;

public class Tags {
    public String nsiTagId;
    public String name;

    public String getNsiTagId() {
        return nsiTagId;
    }

    public String getName() {
        return name;
    }

    public Tags(String nsiTagId, String name) {
        this.nsiTagId = nsiTagId;
        this.name = name;
    }
}
