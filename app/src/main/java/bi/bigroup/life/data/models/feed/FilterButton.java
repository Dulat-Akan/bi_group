package bi.bigroup.life.data.models.feed;

public class FilterButton {
    public String title;
    public int count;
    public int color;

    public FilterButton(String title, int count, int color) {
        this.title = title;
        this.count = count;
        this.color = color;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
