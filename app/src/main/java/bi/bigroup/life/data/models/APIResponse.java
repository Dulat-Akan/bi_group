package bi.bigroup.life.data.models;


import bi.bigroup.life.data.network.exceptions.APIException;

public class APIResponse<T> {
    public T data;

    public T extractData() throws APIException {
        return data;
    }
}
