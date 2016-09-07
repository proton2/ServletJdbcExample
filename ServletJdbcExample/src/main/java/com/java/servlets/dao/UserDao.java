package com.java.servlets.dao;

import com.java.servlets.model.User;
import com.java.servlets.model.WorkTask;
import com.java.servlets.util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDao implements ModelDao<User>{
	private String insertSql = "insert into usertable(firstname, lastname, caption, email) values (?, ?, ?, ?)";
	private String deleteSql = "delete from usertable where id = ?";
	private String updateSql = "update usertable set firstname=?, lastname=?, caption=?, email=? where id=?";
	private String getAllSql = "select * from usertable";
	private String getUserSql = "select * from usertable where id = ?";
	
	private String getUserWorkTasks = "select * from WorkTask where taskuser_id = ?";
	private String getUserWorkTasksId = "select id from WorkTask where taskuser_id = ?";

	private Connection connection;
	
	public UserDao() {
        connection = DbUtil.getConnection();
    }

	@Override
	public void insert(User user) {
		try{
			PreparedStatement ps = connection.prepareStatement(insertSql);
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getCaption());
			ps.setString(4, user.getEmail());
			ps.executeUpdate();
		}
		catch (SQLException e) {
            e.printStackTrace();
        }
	}

	@Override
	public void delete(Long userId) {
		try{
			PreparedStatement ps = connection.prepareStatement(deleteSql);
			ps.setLong(1, userId);
			ps.executeUpdate();
		}
		catch (SQLException e) {
            e.printStackTrace();
        }
	}

	@Override
	public void update(User user) {
		try{
			PreparedStatement ps = connection.prepareStatement(updateSql);
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getCaption());
			ps.setString(4, user.getEmail());
			ps.setLong(5, user.getId());
			ps.executeUpdate();
		}
		catch (SQLException e) {
            e.printStackTrace();
        }
	}

	@Override
	public List<User> getAll(String... fields){
		List<User> users = new ArrayList<>();
		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(getAllSql);
			Boolean eagerLoading = Arrays.stream(fields).filter(e->e.equals("worktask")).findAny().isPresent();
			while(rs.next()){
				User user = new User();
				user.setId(rs.getLong("id"));
				
				user.setUserTasks(getUserWorkTasks(user, eagerLoading));
				
				user.setFirstName(rs.getString("firstname"));
				user.setLastName(rs.getString("lastname"));
				user.setCaption(rs.getString("caption"));
				user.setEmail(rs.getString("email"));

				users.add(user);
			}
		}catch (SQLException e) {
            e.printStackTrace();
        }
		
		return users;
	}

	@Override
	public User getById(Long userId, String... fields){
		User user = new User();
		try{
			PreparedStatement ps = connection.prepareStatement(getUserSql);
			ps.setLong(1, userId);
			ResultSet rs = ps.executeQuery();
			Boolean eagerLoading = Arrays.stream(fields).filter(e->e.equals("worktask")).findAny().isPresent();
			
			if (rs.next()){
				user.setId(rs.getLong("id"));
				user.setFirstName(rs.getString("firstname"));
				user.setLastName(rs.getString("lastname"));
				user.setCaption(rs.getString("caption"));
				user.setEmail(rs.getString("email"));

				user.setUserTasks(getUserWorkTasks(user, eagerLoading));
			}
		} catch (SQLException e) {
            e.printStackTrace();
        }
		return user;
	}
	
    public List<WorkTask> getUserWorkTasks(User user, Boolean eagerLoading) {
        
    	List<WorkTask> workTasks = new ArrayList<>();
        try {
        	PreparedStatement ps = connection.prepareStatement(eagerLoading ? getUserWorkTasks : getUserWorkTasksId );
            ps.setLong(1, user.getId());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                WorkTask workTask = new WorkTask();
                workTask.setId(rs.getLong("id"));
                if (eagerLoading){
					workTask.setTaskUser(user);
					workTask.setCaption(rs.getString("caption"));
                	workTask.setTaskContext(rs.getString("taskcontext"));
                	workTask.setTaskDate(rs.getDate("taskdate"));
                	workTask.setDeadLine(rs.getDate("deadline"));
                }
                workTasks.add(workTask);
                
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return workTasks;
    }
}