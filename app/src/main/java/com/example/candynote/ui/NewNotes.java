package com.example.candynote.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.candynote.R;
import com.example.candynote.db.DataBaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import www.sanju.motiontoast.MotionToast;

public class NewNotes extends AppCompatActivity {
    private static final String TAG = "jjjjjjjjjjj";
    FloatingActionButton save;
    EditText tvTitle, tvDes;
    DataBaseHelper db;
    MainActivity mainActivity;
    private Date timeDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_notes);

        db = new DataBaseHelper(this);

        tvTitle = findViewById(R.id.ed_titles);
        tvDes = findViewById(R.id.ed_dess);
        save = findViewById(R.id.btn_new_note);
        //AddData();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = tvTitle.getText().toString();
                String des = tvDes.getText().toString();
                String date = getDate();

                if (title.isEmpty() && des.isEmpty()) {
                    tvTitle.setError("field are empty");
                } else {
                    boolean isInserted =
                            db.insertData(tvTitle.getText().toString(),
                                    tvDes.getText().toString(),
                                    date,
                                    getTime());

                    if (isInserted) {

                        MotionToast.createColorToast(NewNotes.this, "note saved", "don't forget goal",
                                MotionToast.TOAST_SUCCESS,
                                MotionToast.GRAVITY_BOTTOM,
                                MotionToast.LONG_DURATION,
                                ResourcesCompat.getFont(NewNotes.this, R.font.helvetica_regular));
                        //Toast.makeText(NewNotes.this, "note saved", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                    } else {
                        MotionToast.createColorToast(NewNotes.this, "Failed to save note.", "don't forget goal",
                                MotionToast.TOAST_SUCCESS,
                                MotionToast.GRAVITY_BOTTOM,
                                MotionToast.LONG_DURATION,
                                ResourcesCompat.getFont(NewNotes.this, R.font.helvetica_regular));
                        // Toast.makeText(NewNotes.this, "failed to save note", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
            }
        });
    }

    public String getDate() {
      /*  Calendar calendar = Calendar.getInstance();
        DateFormat date = new SimpleDateFormat("EEEE", Locale.getDefault());
        String dayName = date.format(calendar.getTime()); //Monday
        date = new SimpleDateFormat("dd", Locale.getDefault());
        String dayNumber = date.format(calendar.getTime()); //20
        String formatDate = dayNumber + '/' + dayName;
        Log.d(TAG, "getDate:" + formatDate);*/

        String str1 = "dd EEEE yyyy ";
        Date d = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(str1, Locale.ENGLISH);
        return sdf.format(d);
        
        /*timeDate = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/EEEE ");
        return simpleDateFormat.format(timeDate.getTime());*/
    }

    public String getTime() {
        timeDate = Calendar.getInstance().getTime();
        SimpleDateFormat times = new SimpleDateFormat("hh:mm:ss");
        return times.format(timeDate.getTime());
    }
/*
    public String getFormatterDay(){
        date= new SimpleDateFormat("MMM", Locale.getDefault());
        String monthName= date.format(calendar.getTime()); //Apr

        date= new SimpleDateFormat("MM", Locale.getDefault());
        String monthNumber= date.format(calendar.getTime()); //04

        date= new SimpleDateFormat("yyyy", Locale.getDefault());
        String year= date.format(calendar.getTime()); //2020
    }*/
}
