package krasnov.bookcrossing;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn;
    private TextView signUp;
    private EditText editTextEmail;
    private EditText editTextPassword;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        loginBtn = (Button) findViewById(R.id.btn_login);
        signUp = (TextView) findViewById(R.id.link_signup);
        editTextEmail = (EditText) findViewById(R.id.input_login);
        editTextPassword = (EditText) findViewById(R.id.input_password);

        loginBtn.setOnClickListener(this::onClickLogin);

        signUp.setOnClickListener(v->{
            Intent myIntent = new Intent(this, RegistrationActivity.class);
            startActivity(myIntent);
        });
    }


    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this, BottomBarActivity.class));
                }
                else {
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void userLoginTest() throws IOException {
       
    }

    private void onClickLogin(View v) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                String mEmail = editTextEmail.getText().toString().trim();
                String mPassword = editTextPassword.getText().toString().trim();
                try {
                    URLConnection connection = new URL(String.format("http://192.168.1.46:8080/singin?email=%s&password=%s", mEmail, mPassword)).openConnection();

                    InputStream is = connection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(is);
                    char[] buffer = new char[256];
                    int rc;

                    StringBuilder sb = new StringBuilder();

                    while ((rc = reader.read(buffer)) != -1)
                        sb.append(buffer, 0, rc);

                    reader.close();
                    System.out.println(sb);

                    if (sb.toString().equals("true")) {
                        Intent intent = new Intent(LoginActivity.this, BottomBarActivity.class);
                        startActivity(intent);
                    }
                }
                catch (IOException e) {
                    System.out.println("EXCEPTION");
                }
            }
        });
    }
    /*@Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, BottomBarActivity.class));
        }
    }*/
}
