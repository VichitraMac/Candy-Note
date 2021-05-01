package com.example.candynote.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.candynote.R;
import com.example.candynote.interfaces.ClickViewListrener;
import com.example.candynote.model.NoteData;

import java.util.List;
import java.util.Random;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<NoteData> noteData;
    private ClickViewListrener clickViewListener;
    public NoteAdapter(List<NoteData> noteData){
        this.noteData = noteData;
    }

    public void setClickViewListrener(ClickViewListrener clickVIewListrener){
        this.clickViewListener = clickVIewListrener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        NoteData data = noteData.get(position);

        if (data.getTitle() == null || data.getTitle().isEmpty()){
            holder.title.setVisibility(View.GONE);
        }else {
            holder.title.setVisibility(View.VISIBLE);
            holder.title.setText(data.getTitle());
        }

        if (data.getDesrcription() == (null) || data.getDesrcription().isEmpty()){
            holder.description.setVisibility(View.GONE);
        }else {
            holder.description.setVisibility(View.VISIBLE);
            holder.description.setText(data.getDesrcription());
        }

        holder.time.setText(data.getDate());
        holder.date.setText(data.getDate());
        holder.cardView.setCardBackgroundColor(getRandomColorCode());

        holder.itemView.setOnLongClickListener(view -> {
            clickViewListener.cLickLongListener(view,position,data);
            Log.d("Adapter", "Long-pos :"+ position);
            return true;
        });

        holder.itemView.setOnClickListener(view -> {
            clickViewListener.clickViewListener(view, position, data);
            Log.d("Adapter", "Click-pos :"+ position);
        });
    }

    public int getRandomColorCode(){
        Random random = new Random();
        return Color.argb(255, random.nextInt(256), random.nextInt(256),     random.nextInt(256));
    }

    @Override
    public int getItemCount() {
        return noteData.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;
        private TextView date;
        private TextView time;
        CardView cardView;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            description = itemView.findViewById(R.id.tv_description);
            date = itemView.findViewById(R.id.tv_date);
            time = itemView.findViewById(R.id.tv_time);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}

