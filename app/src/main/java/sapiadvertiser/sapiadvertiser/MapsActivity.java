package sapiadvertiser.sapiadvertiser;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.maps.android.PolyUtil;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import org.json.JSONException;
import java.io.IOException;
import java.util.List;



public class MapsActivity extends FragmentActivity
        implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback {

    private TextView start;
    private TextView date;
    private TextView message;
    private TextView finish;
    private TextView clock;
    private TextView phone;
    private Button call;
    private Button update;
    private Button delete;
    private ModelList feedItem;
    private int index;


    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        this.start = (TextView) findViewById(R.id.start);
        this.finish = (TextView) findViewById(R.id.stop);
        this.date = (TextView) findViewById(R.id.date);
        this.message = (TextView) findViewById(R.id.message);
        this.clock = (TextView) findViewById(R.id.clock);
        this.phone = (TextView) findViewById(R.id.phone);
        call = (Button) findViewById(R.id.call);
        delete = (Button) findViewById(R.id.delete);
        update = (Button) findViewById(R.id.update);
        Intent intent = new Intent();

        intent = getIntent();
        feedItem = new ModelList();
        feedItem = (ModelList) intent.getSerializableExtra("advert");
        index = intent.getIntExtra("index",-1);
        start.setText(feedItem.getStart());
        date.setText(feedItem.getDate());
        message.setText(feedItem.getMessage());
        finish.setText(feedItem.getFinish());
        clock.setText(feedItem.getClock());
        phone.setText(feedItem.getPhone());
        final ModelList finalFeedItem = feedItem;
        call.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + finalFeedItem.getPhone()));
                if (ActivityCompat.checkSelfPermission(MapsActivity.this,
                        android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplication(),ListActivity.class);
                intent.putExtra("flag",1);
                feedItem.setFlag((long) 0);
                new GetAllRows().delete(feedItem.getId());
                startActivity(intent);
                finish();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplication(),UpdateAcvtivity.class);
                intent.putExtra("elem",feedItem);
                startActivity(intent);
                finish();

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        new PoliAsync(mMap, feedItem).execute("my string paramater");
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }
}

class PoliAsync extends AsyncTask<String, Void, DirectionResult.Route> {

    private Exception exception;
    private GoogleMap map;
    private ModelList feedItem;

    public PoliAsync(GoogleMap mMap, ModelList feedItem) {
        this.feedItem = feedItem;
        this.map = mMap;
    }


    protected DirectionResult.Route doInBackground(String... urls) {
        RetrofitClient ret = new RetrofitClient();
        DirectionResult.Route det = new DirectionResult.Route();
        //DirectionResult det = new DirectionResult();
        try {

            det = ret.getDistanceInfo(feedItem.getStart(), feedItem.getFinish());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return det;
    }

    protected void onPostExecute(DirectionResult.Route feed) {
            if(feed!=null) {
                    List<LatLng> polypoints = PolyUtil.decode(feed.getPolyline().getPoints());
                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.add(polypoints.get(0));
                polylineOptions.width(10);
                polylineOptions.color(Color.BLUE);
                polypoints.remove(0);
                for (LatLng ll : polypoints) {
                    if (ll != null) {
                        polylineOptions.add(ll);
                    }
                }
                Polyline olyline = map.addPolyline(polylineOptions);
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(polypoints.get(0), 8), 1000, null);
            }



    }
}