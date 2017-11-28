package sapiadvertiser.sapiadvertiser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Elod on 11/28/2017.
 */

public class Retrofit {


    private static retrofit2.Retrofit retrofit = null;

    private static Gson gson = null;

    public static retrofit2.Retrofit getClient(String baseUrl) {
        if (gson == null) {
            gson = new GsonBuilder().create();
        }
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
