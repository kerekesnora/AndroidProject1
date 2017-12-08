package sapiadvertiser.sapiadvertiser;


import org.json.JSONException;

import java.io.IOException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static sapiadvertiser.sapiadvertiser.Constans.DISTANCE_REQUEST_API_KEY;


public class RetrofitClient {


    public DirectionResult.Route getDistanceInfo(String origin,String destation) throws JSONException, IOException {

        Retrofit retrofitClient = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitMap d = retrofitClient.create(RetrofitMap.class);
        DirectionResult route = d.getJson(origin, destation, DISTANCE_REQUEST_API_KEY).execute().body();
        if(route.getRoutes().size()>0){
            return route.getRoutes().get(0);
        }

        return null;
    }


}
