package sapiadvertiser.sapiadvertiser;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;

import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;


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
    private ModelList feedItem;

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
        call= (Button) findViewById(R.id.call) ;
        Intent intent = new Intent();

        intent=getIntent();
        feedItem = new ModelList();
        feedItem= (ModelList) intent.getSerializableExtra("advert");
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
                callIntent.setData(Uri.parse("tel:"+ finalFeedItem.getPhone()));

                if (ActivityCompat.checkSelfPermission(MapsActivity.this,
                        android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });


    }

    @Override
    public void onMapReady(GoogleMap map) {
        if(feedItem.getLatitudLoc()!=null && feedItem.getLongLoc()!=null) {
            Log.d("nemjon","de bejott");
            mMap = map;

            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(feedItem.getLatitudLoc(), feedItem.getLongLoc()))
                    .title("Hello world"));

        }




    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }
}