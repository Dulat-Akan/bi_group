package bi.bigroup.life.data.cache.db;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import bi.bigroup.life.data.models.feed.Feed;

@Dao
public interface FeedDao {
    @Query("select * from feed")
    List<Feed> loadAllFeed();

    @Insert
    void insertFeed(List<Feed> feedList);

    @Query("DELETE FROM feed")
    void deleteFeed();
}
