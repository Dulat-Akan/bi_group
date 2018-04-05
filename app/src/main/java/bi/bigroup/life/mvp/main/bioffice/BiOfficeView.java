package bi.bigroup.life.mvp.main.bioffice;

import java.util.List;

import bi.bigroup.life.data.models.biboard.BiBoard;
import bi.bigroup.life.data.models.biboard.top_questions.TopQuestions;
import bi.bigroup.life.data.models.bioffice.tasks_sdesk.CombineServiceTask;
import bi.bigroup.life.data.models.feed.news.News;
import bi.bigroup.life.mvp.BaseMvpView;

public interface BiOfficeView extends BaseMvpView {
    void setCombinedServiceTask(CombineServiceTask object);

    void setPopularNews(List<News> object);

    void setBiBoardData(BiBoard biBoard, int position);

    void setTopQuestions(List<TopQuestions> list);

}
