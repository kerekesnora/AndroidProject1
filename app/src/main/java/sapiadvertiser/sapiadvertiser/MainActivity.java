package sapiadvertiser.sapiadvertiser;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button login =(Button) findViewById(R.id.login);
        Button notlogin = (Button) findViewById(R.id.notlogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplication(),ListActivity.class);
                intent.putExtra("flag",1);
                getClass().getCanonicalName();
                startActivity(intent);

            }
        });
        notlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplication(),ListActivity.class);
                intent.putExtra("flag",0);
                startActivity(intent);
            }
        });





    }

}
