package bi.bigroup.life.data.models.feed.news;

import static bi.bigroup.life.utils.Constants.buildStreamUrl;
import static bi.bigroup.life.utils.StringUtils.EMPTY_STR;
import static bi.bigroup.life.utils.StringUtils.isStringOk;

public class SecondaryImage {
    public String streamId;
    public String name;

    public String getImageUrl() {
        return isStringOk(streamId) ? buildStreamUrl(streamId) : EMPTY_STR;
    }
}