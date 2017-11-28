package sapiadvertiser.sapiadvertiser;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Executor;


public class NewWay extends Fragment
        implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback {
    private EditText start;
    private EditText stop;
    private EditText message;
    private TextView clock;
    private EditText phone;
    private TextView datum;
    private Button submit;
    private DatabaseReference ref;
    private DatabaseReference modelRef;
    private ArrayList<ModelList> list = new ArrayList<>();
    private DatePickerFragment newFragment;
    private BroadcastReceiver broadcastReceiver;
    private ModelList elem;

    private FusedLocationProviderClient mFusedLocationClient;
    private GoogleMap mMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        elem = new ModelList();
        location();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment\
        getData();
        ref = FirebaseDatabase.getInstance().getReference();
        modelRef = ref.child("newroad");

        View rootView = inflater.inflate(R.layout.fragment_new_way, container, false);
        start = (EditText) rootView.findViewById(R.id.start);
        stop = (EditText) rootView.findViewById(R.id.finish);
        message = (EditText) rootView.findViewById(R.id.message);
        datum = (TextView) rootView.findViewById(R.id.date);
        clock = (TextView) rootView.findViewById(R.id.clock);
        phone = (EditText) rootView.findViewById(R.id.phone);
        submit = (Button) rootView.findViewById(R.id.submit);



        datum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newFragment = new DatePickerFragment();
                newFragment.show(((ListActivity) getActivity()).getFragmentManager(), "Tag");

            }
        });
        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment clockfragment = new TimePickerFragment();
                clockfragment.show(((ListActivity) getActivity()).getFragmentManager(), "Tag");

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phone.getText().toString().equals("") || stop.getText().toString().equals("")  || start.getText().toString().equals("") ||
                        datum.getText().equals("") || clock.getText().toString().equals("")) {
                    //not string
                }else {

                    elem.setStart(start.getText().toString());
                    elem.setFinish(stop.getText().toString());
                    elem.setMessage(message.getText().toString());
                    elem.setStart(start.getText().toString());
                    elem.setClock(clock.getText().toString());
                    elem.setPhone(phone.getText().toString());
                    elem.setDate(datum.getText().toString());

                    list.add(elem);

                    modelRef.setValue(list);
                    Bundle bundle = new Bundle();
                    bundle.putInt("flag", 1 );
                    Fragment newFragment = new ListFragment();
                    newFragment.setArguments(bundle);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.list, newFragment);
                    transaction.commit();
                }

            }
        });

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null && "date".equals(intent.getAction())) {
                    String date = intent.getStringExtra("date");
                    datum.setText(date);
                    elem.setDate(date);

                }
                if (intent != null && "clock".equals(intent.getAction())) {
                    String clock1 = intent.getStringExtra("clock");
                    clock.setText(clock1);
                    elem.setClock(clock1);

                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("date");
        intentFilter.addAction("clock");
        getActivity().registerReceiver(broadcastReceiver, intentFilter);


        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    public void getData() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference modelRef = ref.child("newroad");
        modelRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                list.clear();
                // Map<String, Object> objectMap = (HashMap<String, Object>)
                //       snapshot.getValue();
                ArrayList<ModelList> objectMap = (ArrayList<ModelList>) snapshot.getValue();
                if (objectMap != null) {
                    for (Object obj : objectMap) {

                        Map<String, Object> mapObj = (Map<String, Object>) obj;
                        ModelList match = new ModelList();
                        match.setStart((String) mapObj.get("start"));
                        match.setFinish((String) mapObj.get("finish"));
                        match.setDate((String) mapObj.get("date"));
                        match.setMessage((String) mapObj.get("message"));
                        match.setPhone((String) mapObj.get("phone"));
                        match.setClock((String) mapObj.get("clock"));
                        match.setLongLoc((Double) mapObj.get("longLoc"));
                        match.setLatitudLoc((Double) mapObj.get("latitudLoc"));
                        list.add(match);

                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("teszt", "1");

            }

        });
    }
    public void location (){

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener( getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            Log.d("LAT", location.getLatitude()+":LAT");
                            Log.d("LAT", location.getLongitude()+":LONG");
                            elem.setLatitudLoc(location.getLatitude());
                            elem.setLongLoc(location.getLongitude());
                        }
                    }
                });
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        if ((ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getMaxZoomLevel();
        mMap.setOnMyLocationClickListener(this);

    }
}

