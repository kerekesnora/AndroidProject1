package sapiadvertiser.sapiadvertiser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SingUp extends AppCompatActivity {
    private EditText name;
    private EditText email;
    private EditText password;
    private EditText phone;
    private Button singup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        singup = (Button) findViewById(R.id.singup);

        singup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();

                DatabaseReference ref = database.getReference("user");
                DatabaseReference usersRef = ref.child("users");

                Map<String, User> users = new HashMap<>();
                String s = phone.getText().toString();
                Double phone =  Double.parseDouble(s);
                users.put(name.getText().toString(), new User(name.getText().toString(),
                                                              password.getText().toString(),
                                                              email.getText().toString(),
                                                              phone));
                usersRef.setValue(users);
                //usersRef();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
