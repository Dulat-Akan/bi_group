package bi.bigroup.life.data.models.bioffice;

import java.util.List;

public class BiOffice {
    public int title;
    public int first;
    public int second;
    public int third;
    public List<Object> items;

    public BiOffice(int title, int first, int second, int third, List<Object> items) {
        this.title = title;
        this.first = first;
        this.second = second;
        this.third = third;
        this.items = items;
    }
}
