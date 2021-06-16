package authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.mynotesapp.R;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import model.MainActivity;

public class Register extends AppCompatActivity {
    EditText userName, userEmai, userPswrd, usercnfmpswrd;
    Button accSync;
    TextView loginActivity;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Create new account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userName = findViewById(R.id.userName);
        userEmai = findViewById(R.id.userEmail);
        userPswrd = findViewById(R.id.password);
        usercnfmpswrd = findViewById(R.id.passwordConfirm);

        accSync = findViewById(R.id.createAccount);
        loginActivity = findViewById(R.id.login);
        progressBar = findViewById(R.id.progressBar4);
        firebaseAuth = FirebaseAuth.getInstance();

        loginActivity.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), Login.class));
        });

        accSync.setOnClickListener(v -> {
            String name = userName.getText().toString();
            String mail = userEmai.getText().toString();
            String pass = userPswrd.getText().toString();
            String cnfmPass = usercnfmpswrd.getText().toString();

            if (name.isEmpty() || mail.isEmpty() || pass.isEmpty() || cnfmPass.isEmpty()) {
                Toast.makeText(Register.this, " All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!pass.equals(cnfmPass)) {
                Toast.makeText(Register.this, "Password do not match", Toast.LENGTH_SHORT).show();
            }

            AuthCredential authCredential = EmailAuthProvider.getCredential(mail, pass);
            firebaseAuth.getCurrentUser().linkWithCredential(authCredential).addOnSuccessListener(authResult -> {
                Toast.makeText(Register.this, "Notes synced", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                        .setDisplayName(name).build();
                firebaseUser.updateProfile(request);

                startActivity(new Intent(getApplicationContext(), MainActivity.class));


            }).addOnFailureListener(e -> {
                Toast.makeText(Register.this, "Failed to connect! Try again", Toast.LENGTH_SHORT).show();
            });

        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        return super.onOptionsItemSelected(item);
    }
}