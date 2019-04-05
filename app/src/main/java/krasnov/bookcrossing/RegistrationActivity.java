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
import com.google.firebase.database.FirebaseDatabase;

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
            registerUser();
        });

        textViewSignIn.setOnClickListener(v -> {
            Intent myIntent = new Intent(this, LoginActivity.class);
            startActivity(myIntent);
        });
    }

    private void registerUser() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(RegistrationActivity.this, "Заполните все поля",
                    Toast.LENGTH_LONG).show();
        } else {
            mAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(),
                    editTextPassword.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                User user = new User(name, email);

                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            Toast.makeText(RegistrationActivity.this, "Registered Successfully",
                                                    Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(RegistrationActivity.this, task.getException().getMessage(),
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                            } else {
                                Toast.makeText(RegistrationActivity.this, task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }

    }
}
