package bi.bigroup.life.data.models.feed;

public class FeedEntityType {
    public static final int FEED_TYPE_NEWS = 10;
    public static final int FEED_TYPE_SUGGESTION = 20;
    public static final int FEED_TYPE_QUESTIONNAIRE = 30;
    public Integer code;
    public String name;

    public void setCode(int code) {
        this.code = code;
    }

    public FeedEntityType(int code) {
        this.code = code;
    }
}