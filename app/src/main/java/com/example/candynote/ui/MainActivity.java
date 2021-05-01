package com.example.candynote.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.candynote.R;
import com.example.candynote.adapter.NoteAdapter;
import com.example.candynote.db.DataBaseHelper;
import com.example.candynote.interfaces.ClickViewListrener;
import com.example.candynote.model.NoteData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tuesda.walker.circlerefresh.CircleRefreshLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.sanju.motiontoast.MotionToast;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private final static String KEY_TITLE = "TITLE";
    private final static int NEW_NOTE_KEY = 2;
    private final static int VIEW_NOTE_KEY = 3;

    @BindView(R.id.login)
     FloatingActionButton btnLogin;
    @BindView(R.id.recycle_view)
     RecyclerView recyclerView;
    @BindView(R.id.tv_empty_box_sms)
     TextView tvEmptySms;
    @BindView(R.id.refresh_layout_note)
     CircleRefreshLayout mRerfesh;

    private NoteAdapter adapter;
    private List<NoteData> notes;
    private DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        db = new DataBaseHelper(this);
        
        getNotes();

        adapter = new NoteAdapter(notes);

        adapter.setClickViewListrener(new ClickViewListrener() {

            @Override
            public void cLickLongListener(View view, int pos, NoteData note) {

                PopupMenu popup = new PopupMenu(MainActivity.this, view);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.deleteNotes) {
                            deleteNote(note, pos);
                        }
                        return false;
                    }
                });
                popup.show();
            }

            @Override
            public void clickViewListener(View view, int pos, NoteData date) {
                Intent intent = new Intent(MainActivity.this, ViewNotes.class);
                intent.putExtra(KEY_TITLE, date);
                startActivityForResult(intent, VIEW_NOTE_KEY);
            }

        });

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        mRerfesh.setOnRefreshListener(new CircleRefreshLayout.OnCircleRefreshListener() {
            @Override
            public void completeRefresh() {
                Toast.makeText(MainActivity.this,"update finish",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void refreshing() {
                getNotes();
            }

        });

        btnLogin.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewNotes.class);
            startActivityForResult(intent, NEW_NOTE_KEY);
        });
    }

    public void refreshStop() {
        new Handler(getMainLooper()).postDelayed(() -> {
            mRerfesh.finishRefreshing();
        }, 2000);
    }

    public void deleteNote(NoteData contact, int pos) {
        notes.remove(pos);
//        recyclerView.removeViewAt(pos);
        adapter.notifyItemRemoved(pos);
        adapter.notifyItemRangeChanged(pos, notes.size());
//        adapter.notifyDataSetChanged();
        Log.d("fun", "pos" + pos);

        Integer result = db.deleteData(contact.getId());

        if (result > 0) {
            MotionToast.createColorToast(MainActivity.this, "DELETE", "secssesfully done",
                    MotionToast.TOAST_DELETE,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(MainActivity.this, R.font.helvetica_regular));
         //   Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
        } else {
            tvEmptySms.setVisibility(View.GONE);
           MotionToast.createColorToast(MainActivity.this, "failed", "some went wrong",
                    MotionToast.TOAST_INFO,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(MainActivity.this, R.font.poppins_thin));
            //Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
        }
    }
            //if (notes.isEmpty())
    public void getNotes(){
        Cursor res = db.getAllData();

        if (notes == null) {
            notes = new ArrayList<>();
        } else {
            notes.clear();
        }

        if (res == null || res.getCount() == 0) {
            tvEmptySms.setVisibility(View.VISIBLE);
            refreshStop();
            MotionToast.createColorToast(MainActivity.this, " emptynotes", "write you new notes",
                    MotionToast.TOAST_INFO,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(MainActivity.this, R.font.helvetica_regular));
//            Toast.makeText(this, "empty notes", Toast.LENGTH_SHORT).show();
             return;
        }

        while (res.moveToNext()) {
            String id = res.getString(0);
            String title = res.getString(1);
            String des = res.getString(2);
            String date = res.getString(3);
            String time = res.getString(4);
            notes.add(new NoteData(id, title, des, date, time));
        }
        Log.d(TAG, "getNotes: list updated");
        refreshStop();
    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_NOTE_KEY || requestCode == VIEW_NOTE_KEY) {
            if (resultCode == RESULT_OK) {
                tvEmptySms.setVisibility(View.GONE);
                updateAdapter();
            }
        }
    }

    public void updateAdapter () {
        getNotes();
        adapter.notifyDataSetChanged();
    }
}
