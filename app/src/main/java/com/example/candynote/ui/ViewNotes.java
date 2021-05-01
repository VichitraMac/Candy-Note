package com.example.candynote.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.candynote.R;
import com.example.candynote.model.NoteData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewNotes extends AppCompatActivity {
    private final static String KEY_TITLE = "TITLE";
    private final static String KEY_TITLE_DATA = "TITLE";
    private final static int EDITE_NOTE_KEY = 4;
    private FloatingActionButton btnEdit;
    TextView tvTitle,tvDes;
    NoteData note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_notes);
        btnEdit = findViewById(R.id.btn_edit_note);
        tvTitle = findViewById(R.id.title_tv);
        tvDes = findViewById(R.id.description_tv);

        note = (NoteData) getIntent().getSerializableExtra(KEY_TITLE);

        updateUI();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewNotes.this, EditNotes.class);
                intent.putExtra(KEY_TITLE, note);
                startActivityForResult(intent,EDITE_NOTE_KEY);
            }
        });
    }

    private void updateUI(){
        if (note != null){
            tvTitle.setText(note.getTitle());
            tvDes.setText(note.getDesrcription());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == EDITE_NOTE_KEY){
            if (resultCode == RESULT_OK){
                note = (NoteData) data.getSerializableExtra(KEY_TITLE_DATA);
                updateUI();
                setResult(RESULT_OK);
            }
        }
    }
}
