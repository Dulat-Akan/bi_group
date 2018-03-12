package bi.bigroup.life.data.models;

import java.util.List;

import static bi.bigroup.life.utils.StringUtils.getOkInt;

public class ListOf<T> {

    public List<T> list;
    public Integer count;

    public Integer getCount() {
        return getOkInt(count);
    }
}
