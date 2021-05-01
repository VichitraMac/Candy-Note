package com.example.candynote.model;

import java.io.Serializable;

public class NoteData  implements Serializable {
    private String id;
    private String title;
    private String desrcription;
    private String date;
    private String time;

    public NoteData(String id,String title,String description,String date, String time){
        this.id = id;
        this.title = title;
        this.desrcription = description;
        this.date = date;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }


    public String getDesrcription() {
        return desrcription;
    }
    public void setDesrcription(String desrcription) {
        this.desrcription = desrcription;
    }


    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }


    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

