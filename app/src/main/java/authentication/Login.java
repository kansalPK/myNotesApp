package authentication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.mynotesapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import model.MainActivity;
import model.splash;

public class Login extends AppCompatActivity {
    EditText mail, pass;
    Button logIn;
    TextView forgetPswrd, createAcc;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Login");

        mail = findViewById(R.id.userEmail);
        pass = findViewById(R.id.password);
        logIn = findViewById(R.id.loginBtn);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        forgetPswrd = findViewById(R.id.forgotPasword);
        createAcc = findViewById(R.id.createAccount);
        progressBar = findViewById(R.id.progressBar3);

        showWarning();

        logIn.setOnClickListener(v -> {
            String eMail = mail.getText().toString();
            String pswrd = pass.getText().toString();

            if (eMail.isEmpty() || pswrd.isEmpty()) {
                Toast.makeText(Login.this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            if (firebaseAuth.getCurrentUser().isAnonymous()) {

                firebaseFirestore.collection("notes").document(firebaseUser.getUid()).delete().addOnSuccessListener(aVoid -> {
                   Toast.makeText(Login.this, "Temporary notes deleted", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> {
                    Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });

                firebaseUser.delete().addOnSuccessListener(aVoid -> {
                   Toast.makeText(Login.this, "Temporary User Deleted", Toast.LENGTH_SHORT);
                }).addOnFailureListener(e -> {
                    Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });

            }

            firebaseAuth.signInWithEmailAndPassword(eMail, pswrd).addOnSuccessListener(authResult -> {
                Toast.makeText(Login.this, "Successfully signed in!" ,Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }).addOnFailureListener(e -> {
                Toast.makeText(Login.this, "Login Failed ! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            });


        });

    }

    private void showWarning() {
        AlertDialog.Builder warning = new AlertDialog.Builder(this)
                .setTitle("Are you Sure ?")
                .setMessage("Linking to an existing account will delete all the temporary notes. Create new account to save them")
                .setPositiveButton("Save Notes", (dialog, which) -> {
                    startActivity(new Intent(getApplicationContext(), Register.class));
                    finish();
                }).setNegativeButton("Its OK", (dialog, which) -> {
                    // it's ok!
                });
        warning.show();
    }
}