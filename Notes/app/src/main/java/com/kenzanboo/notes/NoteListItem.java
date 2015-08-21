package com.kenzanboo.notes;

import java.io.Serializable;
import java.util.Calendar;

public class NoteListItem implements Serializable {
    private Long id;
    private String text;
    private String status;
    private Calendar date;

    public NoteListItem(String text) {
        this(null, text, "OPEN", Calendar.getInstance());
    }

    public NoteListItem(Long id, String text, String status, Calendar date){
        setId(id);
        setText(text);
        setStatus(status);
        setDate(date);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}