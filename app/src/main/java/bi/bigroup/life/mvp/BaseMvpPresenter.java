package bi.bigroup.life.mvp;

import android.content.Context;

import com.arellomobile.mvp.MvpPresenter;

import bi.bigroup.life.R;
import bi.bigroup.life.config.DebugConfig;
import bi.bigroup.life.data.DataLayer;
import bi.bigroup.life.data.cache.preferences.Preferences;
import bi.bigroup.life.data.network.error.RetrofitErrorHandler;
import bi.bigroup.life.data.network.exceptions.APIException;
import bi.bigroup.life.data.network.exceptions.ConnectionTimeOutException;
import bi.bigroup.life.data.network.exceptions.UnAuthorizeException;
import bi.bigroup.life.data.network.exceptions.UnknownException;
import bi.bigroup.life.utils.StringUtils;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseMvpPresenter<T extends BaseMvpView> extends MvpPresenter<T> {
    protected Context context;
    protected DataLayer dataLayer;
    protected Preferences preferences;
    protected CompositeSubscription compositeSubscription = new CompositeSubscription();

    public void init(Context context, DataLayer dataLayer) {
        this.context = context;
        this.dataLayer = dataLayer;
        preferences = dataLayer.getPreferences();
    }

    protected void handleResponseError(Context context, Throwable e) {
        if (DebugConfig.DEV_BUILD) {
            e.printStackTrace();
        }
        try {
            RetrofitErrorHandler.handleException(e);
        } catch (APIException e1) {
            getViewState().showRequestError(StringUtils.replaceNull(e1.getErrorMessage()));
        } catch (UnknownException e1) {
            getViewState().showRequestError(context.getString(R.string.server_error));
        } catch (ConnectionTimeOutException e1) {
            getViewState().showRequestError(context.getString(R.string.connection_error));
        } catch (UnAuthorizeException e1) {
            getViewState().onInvalidToken();
        }
    }

    public void onDestroyView() {
        unsubscribe();
    }

    private void unsubscribe() {
        if (compositeSubscription != null
                && !compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
        }
    }
}
