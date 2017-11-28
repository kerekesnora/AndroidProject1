package sapiadvertiser.sapiadvertiser;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Elod on 11/28/2017.
 */

public class ModelMap {
    @SerializedName("lng")
    private Double startlong;
    @SerializedName("lat")
    private Double startlat;
    @SerializedName("lng")
    private Double finishlong;
    @SerializedName("lat")
    private Double finishlat;

    public ModelMap() {
    }

    public Double getStartlong() {
        return startlong;
    }

    public void setStartlong(Double startlong) {
        this.startlong = startlong;
    }

    public Double getStartlat() {
        return startlat;
    }

    public void setStartlat(Double startlat) {
        this.startlat = startlat;
    }

    public Double getFinishlong() {
        return finishlong;
    }

    public void setFinishlong(Double finishlong) {
        this.finishlong = finishlong;
    }

    public Double getFinishlat() {
        return finishlat;
    }

    public void setFinishlat(Double finishlat) {
        this.finishlat = finishlat;
    }
}
