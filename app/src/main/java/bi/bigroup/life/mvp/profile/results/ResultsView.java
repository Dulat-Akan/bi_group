package bi.bigroup.life.mvp.profile.results;

import java.util.List;

import bi.bigroup.life.data.models.user.results.Results;
import bi.bigroup.life.mvp.BaseMvpView;

public interface ResultsView extends BaseMvpView {

    void addResultsList(List<Results> list);
}
