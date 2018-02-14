package bi.bigroup.life.data.network.error;

import java.io.IOException;

import bi.bigroup.life.data.network.GsonProvider;
import bi.bigroup.life.data.network.exceptions.APIException;
import bi.bigroup.life.data.network.exceptions.ConnectionTimeOutException;
import bi.bigroup.life.data.network.exceptions.UnAuthorizeException;
import bi.bigroup.life.data.network.exceptions.UnknownException;
import bi.bigroup.life.utils.StringUtils;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

public class RetrofitErrorHandler {
    private static final int UNAUTHORIZED = 401;

    public static void handleException(Throwable e) throws
            APIException, UnknownException, ConnectionTimeOutException, UnAuthorizeException {
        if (e instanceof UnAuthorizeException) {
            throw new UnAuthorizeException();
        } else if (e instanceof HttpException) {
            handleHttpException(e);
        } else if (e instanceof IOException) {
            throw new ConnectionTimeOutException();
        } else {
            throw new UnknownException();
        }
    }

    private static void handleHttpException(Throwable e)
            throws
            APIException,
            UnknownException, UnAuthorizeException {

        HttpException exception = (HttpException) e;
        if (exception.code() == UNAUTHORIZED) {
            throw new UnAuthorizeException();
        }
        Response response = exception.response();
        if (response != null) {
            APIError apiError;
            try {
                apiError = parseError(response);
            } catch (Exception e1) {
                e1.printStackTrace();
                throw new UnknownException();
            }

            if (apiError != null) {
                String errorMessage = StringUtils.replaceNull(apiError.getErrorMessage());
                throw new APIException(errorMessage);
            }
        }
        throw new UnknownException();
    }

    private static APIError parseError(Response<?> response) throws IOException {
        return GsonProvider.gson.fromJson(response.errorBody().string(), APIError.class);
    }
}