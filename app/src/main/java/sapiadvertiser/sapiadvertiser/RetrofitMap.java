package sapiadvertiser.sapiadvertiser;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Elod on 11/28/2017.
 */

public interface RetrofitMap{
    @GET("/maps/api/directions/json")
    Call<DirectionResult> getJson(@Query("origin") String origin, @Query("destination") String destination, @Query("key") String key);
}

