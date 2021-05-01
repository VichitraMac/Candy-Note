package com.example.candynote.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.candynote.R;
import com.example.candynote.db.DataBaseHelper;
import com.example.candynote.model.NoteData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class EditNotes extends AppCompatActivity {
    FloatingActionButton edite;
    TextView tvTitle,tvDes;

    private final static String KEY_TITLE = "TITLE";
    private final static String KEY_NOTE = "TITLE";

    NoteData data;
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notes);

        init();
        data = (NoteData) getIntent().getSerializableExtra(KEY_TITLE);
        db = new DataBaseHelper(this);

        upDateUI();

        edite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title =  tvTitle.getText().toString().trim();
                String des =  tvDes.getText().toString().trim();

                if (title.isEmpty() && des.isEmpty()){
                    tvTitle.setError("field are empty");
                }else {

                    boolean result = db.updateData(data.getId(), title,
                            des, data.getDate(),
                            data.getTime());

                    if (result){
                       /* MotionToast.createColorToast(EditNotes.this," Note save!", "please don't waste water",
                                MotionToast.TOAST_SUCCESS,
                                MotionToast.GRAVITY_BOTTOM,
                                MotionToast.LONG_DURATION,
                                ResourcesCompat.getFont(EditNotes.this,R.font.helvetica_regular));*/
                        Toast.makeText(EditNotes.this,"succesfully",Toast.LENGTH_SHORT).show();

                        data.setTitle(title);
                        data.setDesrcription(des);
                        Intent intent = new Intent();
                        intent.putExtra(KEY_NOTE,data);
                        setResult(RESULT_OK,intent);

                    }else {
                        Toast.makeText(EditNotes.this,"fail", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
            }
        });
    }

    private void init() {
        edite = findViewById(R.id.btn_save);
        tvTitle = findViewById(R.id.ed_title);
        tvDes = findViewById(R.id.ed_des);

    }

    public void upDateUI(){
        tvTitle.setText(data.getTitle());
        tvDes.setText(data.getDesrcription());
    }
}
