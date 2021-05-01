package com.example.candynote.interfaces;

import android.view.View;

import com.example.candynote.model.NoteData;

public interface ClickViewListrener {
    void clickViewListener(View view, int pos, NoteData contact);
    void cLickLongListener(View view, int pos, NoteData contact);
}
