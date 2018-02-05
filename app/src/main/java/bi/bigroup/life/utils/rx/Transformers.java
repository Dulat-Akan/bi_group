package bi.bigroup.life.utils.rx;

import bi.bigroup.life.data.models.APIResponse;
import bi.bigroup.life.data.network.exceptions.APIException;
import rx.Observable;

public class Transformers {
    private static final Observable.Transformer responseDataExtractor =
            (Observable.Transformer<APIResponse, Object>) o -> o.map(response -> {
                try {
                    return response.extractData();
                } catch (APIException e) {
                    e.printStackTrace();
                    return null;
                }
            });

    @SuppressWarnings("unchecked")
    public static <T> Observable.Transformer<APIResponse<T>, T> responseDataExtractor() {
        return (Observable.Transformer<APIResponse<T>, T>) responseDataExtractor;
    }
}
