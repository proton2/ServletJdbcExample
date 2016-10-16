package com.java.servlets.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by proton2 on 06.08.2016.
 */
@Entity
@Table(name="worktask")
public class WorkTask extends Model{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="taskuser_id")
    private User taskUser;
    private String caption;
    @Column(name="taskcontext")
    private String taskContext;
    @Column(name="taskdate")
    private Date taskDate;
    @Column(name="deadline")
    private Date deadLine;
    @Column(name="taskStatus")
    private TaskStatus taskStatus;

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

    @Override
    public String toString() {
        return "WorkTask " + caption;
    }
}
