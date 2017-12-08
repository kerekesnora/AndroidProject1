package sapiadvertiser.sapiadvertiser;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UpdateAcvtivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_acvtivity);
        Intent intent = getIntent();
        elem = (ModelList) intent.getSerializableExtra("elem");
        ref = FirebaseDatabase.getInstance().getReference();
        modelRef = ref.child("newroad");


        start = (EditText) findViewById(R.id.start);
        stop = (EditText) findViewById(R.id.finish);
        message = (EditText) findViewById(R.id.message);
        datum = (TextView) findViewById(R.id.date);
        clock = (TextView) findViewById(R.id.clock);
        phone = (EditText) findViewById(R.id.phone);
        submit = (Button) findViewById(R.id.submit);


        start.setText(elem.getStart());
        stop.setText(elem.getFinish());
        message.setText(elem.getMessage());
        datum.setText(elem.getDate());
        clock.setText(elem.getClock());
        phone.setText(elem.getPhone());



        datum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "Tag");

            }
        });
        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment clockfragment = new TimePickerFragment();
                clockfragment.show(getFragmentManager(), "Tag");

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

                    new GetAllRows().update(elem.getId(),elem);
                    Intent intent = new Intent(getApplication(),ListActivity.class);
                    intent.putExtra("flag",1);
                    startActivity(intent);
                    finish();
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
        getApplication().registerReceiver(broadcastReceiver, intentFilter);


    }
}
