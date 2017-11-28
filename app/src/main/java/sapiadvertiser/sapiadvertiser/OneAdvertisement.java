package sapiadvertiser.sapiadvertiser;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OneAdvertisement extends AppCompatActivity  {
    private TextView start;
    private TextView date;
    private TextView message;
    private TextView finish;
    private TextView clock;
    private TextView phone;
    private Button call;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_advertisement);
        this.start = (TextView) findViewById(R.id.start);
        this.finish = (TextView) findViewById(R.id.stop);
        this.date = (TextView) findViewById(R.id.date);
        this.message = (TextView) findViewById(R.id.message);
        this.clock = (TextView) findViewById(R.id.clock);
        this.phone = (TextView) findViewById(R.id.phone);
        call= (Button) findViewById(R.id.call) ;
        Intent intent = new Intent();

        intent=getIntent();
        ModelList feedItem = new ModelList();
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
                callIntent.setData(Uri.parse("tel:"+ finalFeedItem.getPhone().toString()));

                if (ActivityCompat.checkSelfPermission(OneAdvertisement.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });
    }
}
