package sapiadvertiser.sapiadvertiser;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Elod on 11/29/2017.
 */

public class DirectionResult {

    @SerializedName("routes")
    private List<Route> routes;

    public List<Route> getRoutes() {
        return routes;
    }


    public static class Route {
        @SerializedName("overview_polyline")
        public OverviewPolyline polyline;



        public OverviewPolyline getPolyline() {
            return polyline;
        }


    }



    public class OverviewPolyline {
        @SerializedName("points")
        public String points;

        public String getPoints() {
            return points;
        }
    }
}