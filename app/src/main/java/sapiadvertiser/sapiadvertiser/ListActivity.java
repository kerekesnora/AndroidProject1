package sapiadvertiser.sapiadvertiser;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Intent intent=getIntent();
        int flag = intent.getIntExtra("flag",0);
        ListFragment newFragment = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("flag", flag );
        newFragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.list, newFragment);
        transaction.commit();
    }
}
