package bi.bigroup.life.data.models.feed.news;

import java.util.List;

import okhttp3.MultipartBody;

public class AddNewsParams {
    public MultipartBody.Part MainImage;
    public List<MultipartBody.Part> SecondaryImages;
    public String Title;
    public String Text;
    public String RawText;
    public Boolean IsHistoryEvent;
    public List<String> Tags;
    public List<String> NsiTagIds;
    public List<String> NewTagNames;

    public AddNewsParams(MultipartBody.Part mainImage, List<MultipartBody.Part> secondaryImages,
                         String title, String text, String rawText, Boolean isHistoryEvent,
                         List<String> tags, List<String> nsiTagIds, List<String> newTagNames) {
        MainImage = mainImage;
        SecondaryImages = secondaryImages;
        Title = title;
        Text = text;
        RawText = rawText;
        IsHistoryEvent = isHistoryEvent;
        Tags = tags;
        NsiTagIds = nsiTagIds;
        NewTagNames = newTagNames;
    }
}
