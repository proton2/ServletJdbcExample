package com.java.servlets.model;

import java.util.Collection;
import java.util.Date;

/**
 * Created by proton2 on 06.08.2016.
 */
public class WorkTask extends Model {
    private User taskUser;
    private String caption;
    private String taskContext;
    private Date taskDate;
    private Date deadLine;
    private TaskStatus taskStatus;
    private Collection<Attach> attaches;

    public User getTaskUser() {
        return taskUser;
    }

    public void setTaskUser(User taskUser) {
        this.taskUser = taskUser;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getTaskContext() {
        return taskContext;
    }

    public void setTaskContext(String taskContext) {
        this.taskContext = taskContext;
    }

    public Date getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(Date taskDate) {
        this.taskDate = taskDate;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Collection<Attach> getAttaches() {
        return attaches;
    }

    public void setAttaches(Collection<Attach> attaches) {
        this.attaches = attaches;
    }

    @Override
    public String toString() {
        return "WorkTask " + caption;
    }
}
