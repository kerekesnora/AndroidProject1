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


public class NewWay extends Fragment {
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

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment\
        new GetAllRows(list).get();

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
                    elem.setFlag((long) 1);

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
}

