package bi.bigroup.life.mvp.main.biboard.top_questions;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.mvp.BaseMvpPresenter;

@InjectViewState
public class TopQuestionsPresenter extends BaseMvpPresenter<TopQuestionsView> {

    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
    }
}