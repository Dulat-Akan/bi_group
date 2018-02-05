package bi.bigroup.life.data.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonProvider {
    public static Gson gson = new GsonBuilder()
//            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

}