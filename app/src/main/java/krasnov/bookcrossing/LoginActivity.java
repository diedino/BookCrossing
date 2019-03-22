package krasnov.bookcrossing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn;
    private TextView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = (Button) findViewById(R.id.btn_login);

        signUp = (TextView) findViewById(R.id.link_signup);

        loginBtn.setOnClickListener(v -> {
            Intent myIntent = new Intent(this, BottomBarActivity.class);
            startActivity(myIntent);
        });

        signUp.setOnClickListener(v->{

        });
    }
}
