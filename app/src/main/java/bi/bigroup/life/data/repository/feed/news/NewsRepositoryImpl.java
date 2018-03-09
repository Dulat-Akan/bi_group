package bi.bigroup.life.data.repository.feed.news;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bi.bigroup.life.data.models.feed.news.AddComment;
import bi.bigroup.life.data.models.feed.news.Comment;
import bi.bigroup.life.data.models.feed.news.News;
import bi.bigroup.life.data.models.feed.news.Tags;
import bi.bigroup.life.data.network.api.bi_group.API;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

class NewsRepositoryImpl implements NewsRepository {
    private API api;

    @Override
    public void setAPI(API api) {
        this.api = api;
    }

    @Override
    public Observable<News> getNews(String id) {
        return api
                .getNews(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<ResponseBody> likeNews(String id) {
        return api
                .likeNews(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Comment> addComment(String id, AddComment addComment) {
        return api
                .addNewsComment(id, addComment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<ResponseBody> likeNewsComment(String id, String commentId, int vote) {
        return api
                .likeNewsComment(id, commentId, vote)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<News>> getPopularNews(int top) {
        return api
                .getPopularNews(top)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Tags>> getNewsTags() {
        return api
                .getNewsTags()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<ResponseBody> addNews(MultipartBody.Part mainImage, List<MultipartBody.Part> secondaryImages,
                                            String title, String text, String rawText, Boolean isHistoryEvent,
                                            List<String> tags, List<String> nsiTagIds, List<String> newTagNames) {
        return api
                .addNews(mainImage, secondaryImages,
                        title, text, rawText, isHistoryEvent,
                        tags, nsiTagIds, newTagNames)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    private Map<String, RequestBody> createPartFromArray(List<String> list, String keyword) {
        Map<String, RequestBody> skill = new HashMap<>();
        RequestBody requestFile;
        for (int i = 0; i < list.size(); i++) {
            requestFile = RequestBody.create(MultipartBody.FORM, list.get(i));
            skill.put(keyword + "[" + i + "]", requestFile);
        }
        return skill;
    }
}