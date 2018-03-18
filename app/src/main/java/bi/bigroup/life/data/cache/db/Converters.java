package bi.bigroup.life.data.cache.db;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import bi.bigroup.life.data.models.feed.Feed;
import bi.bigroup.life.data.models.feed.FeedEntityType;

public class Converters {
    @TypeConverter
    public static FeedEntityType entityTypeFromString(String value) {
        Type listType = new TypeToken<FeedEntityType>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String entityTypeFromObject(FeedEntityType entityType) {
        Gson gson = new Gson();
        return gson.toJson(entityType);
    }

    @TypeConverter
    public static Feed.ImageSize imageSizeFromString(String value) {
        Type listType = new TypeToken<Feed.ImageSize>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String imageSizeFromObject(Feed.ImageSize imageSize) {
        Gson gson = new Gson();
        return gson.toJson(imageSize);
    }
}
