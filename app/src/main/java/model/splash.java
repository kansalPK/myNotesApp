package model;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.android.mynotesapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class splash extends AppCompatActivity {

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        firebaseAuth = FirebaseAuth.getInstance();

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            //Toast.makeText(splash.this, "twoSecsPasses", Toast.LENGTH_SHORT).show();

            if (firebaseAuth.getCurrentUser() != null) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            } else {
                firebaseAuth.signInAnonymously().addOnSuccessListener(authResult -> {
                    Toast.makeText(splash.this, "Logged in with temporary account", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }).addOnFailureListener(e -> {
                    Toast.makeText(splash.this, "Error ! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                });
            }
        }, 1000);

    }
}