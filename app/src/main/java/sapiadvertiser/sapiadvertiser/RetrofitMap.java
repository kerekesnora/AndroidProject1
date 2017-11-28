package sapiadvertiser.sapiadvertiser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Elod on 11/28/2017.
 */

public interface RetrofitMap {
    @GET("/users/{user}/repos")
    Call<List<ModelMap>> reposForUser(
            @Path("user") String user
    );
}
