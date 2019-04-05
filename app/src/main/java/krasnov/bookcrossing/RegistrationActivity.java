package krasnov.bookcrossing;

import android.content.Intent;
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

public class RegistrationActivity extends AppCompatActivity {

    private Button createBtn;
    private TextView textViewSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextName;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        createBtn = (Button) findViewById(R.id.btn_signup);
        textViewSignIn = (TextView) findViewById(R.id.link_login);
        editTextEmail = (EditText) findViewById(R.id.input_email);
        editTextPassword = (EditText) findViewById(R.id.input_password);
        editTextName = (EditText) findViewById(R.id.input_name);

        mAuth = FirebaseAuth.getInstance();

        createBtn.setOnClickListener(v -> {
            mAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(),
                    editTextPassword.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                User user = new User(editTextName.getText().toString().trim(),
                                        editTextEmail.getText().toString().trim());

                                Toast.makeText(RegistrationActivity.this, "Registered Successfully",
                                        Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(RegistrationActivity.this, task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        });

        textViewSignIn.setOnClickListener(v -> {
            Intent myIntent = new Intent(this, LoginActivity.class);
            startActivity(myIntent);
        });
    }
}
