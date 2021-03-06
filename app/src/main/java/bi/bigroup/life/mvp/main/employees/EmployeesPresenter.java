package bi.bigroup.life.mvp.main.employees;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;

import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.cache.db.AppDatabase;
import bi.bigroup.life.mvp.BaseMvpPresenter;

@InjectViewState
public class EmployeesPresenter extends BaseMvpPresenter<EmployeesView> {
    @Override
    public void init(Context context, DataLayer dataLayer) {
        super.init(context, dataLayer);
    }

}
