package krasnov.bookcrossing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class RegistrationActivity extends AppCompatActivity {

    private Button create;
    private TextView signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        create = (Button) findViewById(R.id.btn_signup);
        signIn = (TextView) findViewById(R.id.link_login);

        create.setOnClickListener(v -> {

        });

        signIn.setOnClickListener(v -> {
            Intent myIntent = new Intent(this, LoginActivity.class);
            startActivity(myIntent);
        });
    }
}
