package bi.bigroup.life.data.models.feed.news;

import android.content.Context;

import static bi.bigroup.life.utils.DateUtils.getDisplayableTime;
import static bi.bigroup.life.utils.StringUtils.getOkInt;
import static bi.bigroup.life.utils.StringUtils.replaceNull;
import static bi.bigroup.life.utils.StringUtils.replaceNullTrim;

public class Comment {
    public static final int VOTE_LIKED = 1;
    public static final int VOTE_DISLIKED = -1;

    public String id;
    public String authorCode;
    public String authorName;
    public String createDate;
    public String text;
    public Integer likesQuantity;
    public Integer dislikesQuantity;
    public Integer vote;

    public int getOkIntQuantity(Integer sum) {
        return getOkInt(sum);
    }

    public boolean isLiked() {
        return getOkInt(vote) == VOTE_LIKED;
    }

    public int getLikesQuantity() {
        return getOkInt(likesQuantity);
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public void setLikesQuantity(int likesQuantity) {
        this.likesQuantity = likesQuantity;
    }

    public String getDate(Context context) {
        return getDisplayableTime(createDate, context);
    }

    public String getCode() {
        return replaceNullTrim(authorCode);
    }

    public String getFullName() {
        return replaceNull(authorName);
    }

    public String getContent() {
        return replaceNull(text);
    }

}
