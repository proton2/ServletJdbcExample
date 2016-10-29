package com.java.servlets.model;

import java.util.Collection;

public class User extends Model{
	private String firstName;
	private String lastName;
	private String caption;
	private String email;
    private Collection<WorkTask> userTasks;
    private String login;
    private String password;

    public User() {}

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}