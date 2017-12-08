package sapiadvertiser.sapiadvertiser;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class ListActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

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

        mAuth = FirebaseAuth.getInstance();
        Button signoutButton = (Button) findViewById(R.id.sign_out_button);
        signoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });


    }


    private void signOut(){
        mAuth.signOut();
    }

}