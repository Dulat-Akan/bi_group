package bi.bigroup.life.data.models.feed;

import android.content.Context;

import static bi.bigroup.life.utils.DateUtils.getDisplayableTime;
import static bi.bigroup.life.utils.StringUtils.getOkInt;
import static bi.bigroup.life.utils.StringUtils.isOkBoolean;
import static bi.bigroup.life.utils.StringUtils.replaceNull;

public class Feed {
    public String id;
    public String authorCode;
    public String authorName;
    public String title;
    public String description;
    public String imageUrl;
    public String createDate;
    public String imageStreamId;
    public Integer likesQuantity;
    public Integer dislikesQuantity;
    public Integer userVote;
    public Integer commentsQuantity;
    public Integer questionsQuantity;
    public Integer viewsQuantity;
    public Boolean isLikedByMe;
    public Boolean isFromSharepoint;
    public FeedEntityType entityType;
    public ImageSize imageSize;

    public boolean isLiked() {
        return isOkBoolean(isLikedByMe);
    }

    public int getLikesQuantity() {
        return getOkInt(likesQuantity);
    }

    public void setLikedByMe(Boolean likedByMe) {
        isLikedByMe = likedByMe;
    }

    public void setLikesQuantity(int likesQuantity) {
        this.likesQuantity = likesQuantity;
    }

    public int getLayoutType() {
        if (entityType != null) {
            return getOkInt(entityType.code);
        } else {
            return 0;
        }
    }

    public String getId() {
        return replaceNull(id);
    }

    public String getImageUrl() {
        return replaceNull(imageUrl);
    }

    public int getOkIntQuantity(Integer sum) {
        return getOkInt(sum);
    }

    public String getDate(Context context) {
        return getDisplayableTime(createDate, context);
    }

    public class ImageSize {
        public Integer width;
        public Integer height;

        public int getWidth() {
            return getOkInt(width);
        }

        public int getHeight() {
            return getOkInt(height);
        }
    }
}