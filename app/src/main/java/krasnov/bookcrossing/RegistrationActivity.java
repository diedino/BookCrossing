package krasnov.bookcrossing;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RegistrationActivity extends AppCompatActivity {

    private Button createBtn;
    private TextView textViewSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextName;
    private String name, email, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        createBtn = (Button) findViewById(R.id.btn_signup);
        textViewSignIn = (TextView) findViewById(R.id.link_login);
        editTextEmail = (EditText) findViewById(R.id.input_email);
        editTextPassword = (EditText) findViewById(R.id.input_password);
        editTextName = (EditText) findViewById(R.id.input_name);


        createBtn.setOnClickListener(v -> {
            registerUser();
        });

        textViewSignIn.setOnClickListener(v -> {
            Intent myIntent = new Intent(this, LoginActivity.class);
            startActivity(myIntent);
        });
    }

    private void registerUser() {
        name = editTextName.getText().toString().trim();
        email = editTextEmail.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(RegistrationActivity.this, "Заполните все поля",
                    Toast.LENGTH_LONG).show();
        } else {
            AsyncCall task = new AsyncCall();
            task.execute();
            try {
                task.get(2000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(intent);
        }

    }

    private class AsyncCall extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String testURL = String.format("http://192.168.1.46:8080/singup?" +
                        "name=%s&" +
                        "email=%s&" +
                        "password=%s", name.replaceAll(" ", "%20"), email, password);
                System.out.println(testURL);
                URLConnection connection = new URL(String.format("http://192.168.1.46:8080/singup?" +
                        "name=%s&" +
                        "email=%s&" +
                        "password=%s", name.replaceAll(" ", "%20"), email, password)).openConnection();
                InputStream is = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(is);

                char[] buffer = new char[256];
                int rc;

                StringBuilder sb = new StringBuilder();

                while ((rc = reader.read(buffer)) != -1)
                    sb.append(buffer, 0, rc);

                reader.close();

                System.out.println(sb);

            }
            catch (IOException e) {
                System.out.println("EXCEPTION");
            }
            return null;
        }
    }
}
