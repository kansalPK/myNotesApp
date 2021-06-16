package note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.mynotesapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import model.MainActivity;

public class editNotes extends AppCompatActivity {

    Intent data;
    EditText editNotesTitle, editNotesContent;
    FirebaseFirestore firebaseFirestore;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notes);
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        firebaseFirestore = firebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar2);

        data = getIntent();

        editNotesTitle = findViewById(R.id.editNotesTitle);
        editNotesContent = findViewById(R.id.editNotesContent);

        String noteTitle = data.getStringExtra("title");
        String noteContent = data.getStringExtra("content");

        editNotesTitle.setText(noteTitle);
        editNotesContent.setText(noteContent);

        FloatingActionButton fab = findViewById(R.id.saveEditedNotes);
        fab.setOnClickListener(v -> {
            String nTitle, nContent;
            nTitle = editNotesTitle.getText().toString();
            nContent = editNotesContent.getText().toString();

            if (nContent.isEmpty()) {
                Toast.makeText(editNotes.this, "Cannot save with empty field.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (nTitle.isEmpty()) {
                nTitle = "<Untitled>";
            }

            progressBar.setVisibility(View.VISIBLE);

            DocumentReference documentReference = firebaseFirestore.collection("notes").document(
                    data.getStringExtra("noteID")
            );
            Map<String, Object> note = new HashMap<>();
            note.put("title", nTitle);
            note.put("content", nContent);

            documentReference.update(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(editNotes.this, "Notes Saved", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(editNotes.this, "Error! Try Again", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.VISIBLE);
                }
            });
        });

    }
}