package com.java.servlets.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Table(name="usertable")
public class User extends Model{
	@Column(name="firstname")
	private String firstName;
	@Column(name="lastname")
	private String lastName;
	@Column(name="caption")
	private String caption;
	@Column(name="email")
	private String email;

    private Collection<WorkTask> userTasks;

    public User() {
    }

    public User (Long userId){
        super.setId(userId);
    }

    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

    public Collection<WorkTask> getUserTasks() {
        return userTasks;
    }

    public void setUserTasks(Collection<WorkTask> userTasks) {
        this.userTasks = userTasks;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}