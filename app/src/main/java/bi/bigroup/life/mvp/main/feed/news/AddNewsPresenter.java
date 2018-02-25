package bi.bigroup.life.mvp.main.feed.news;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.mvp.BaseMvpPresenter;

@InjectViewState
public class AddNewsPresenter extends BaseMvpPresenter<AddNewsView> {

    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
    }
}