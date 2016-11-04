package com.java.servlets.model;

/**
 * Created by proton2 on 05.11.2016.
 */
public class Attach extends Model{
    private String fileName;
    private String caption;
    private WorkTask workTask;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public WorkTask getWorkTask() {
        return workTask;
    }

    public void setWorkTask(WorkTask workTask) {
        this.workTask = workTask;
    }
}
