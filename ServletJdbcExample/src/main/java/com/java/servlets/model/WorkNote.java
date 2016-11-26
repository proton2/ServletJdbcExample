package com.java.servlets.model;

import java.util.Date;

/**
 * Created by proton2 on 21.11.2016.
 */
public class WorkNote extends Model{
    private String caption;
    private Date noteDate;
    private String description;
    private WorkTask subject;
    private User noteUser;

    public WorkTask getSubject() {
        return subject;
    }

    public void setSubject(WorkTask subject) {
        this.subject = subject;
    }

    public User getNoteUser() {
        return noteUser;
    }

    public void setNoteUser(User noteUser) {
        this.noteUser = noteUser;
    }

    public Date getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(Date noteDate) {
        this.noteDate = noteDate;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
