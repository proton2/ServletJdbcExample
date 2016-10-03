package com.java.servlets.dao;

import com.java.servlets.model.Model;
import com.java.servlets.model.User;
import com.java.servlets.model.WorkTask;
import com.java.servlets.util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class UserDao implements ModelDao<User>{
	private String insertSql = "insert into usertable(firstname, lastname, caption, email) values (?, ?, ?, ?)";
	private String deleteSql = "delete from usertable where id = ?";
	private String updateSql = "update usertable set firstname=?, lastname=?, caption=?, email=? where id=?";
	private String getAllSql = "select * from usertable";
	private String getUserSql = "select * from usertable where id = ?";

	private Connection connection;
	
	public UserDao() {
        connection = DbUtil.getConnection();
    }

	@Override
	public Long insert(Model item) {
		try{
			User user = (User)item;
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

		Long insertCount = -1L;
		try {
			Statement select = connection.createStatement();
			ResultSet result = select.executeQuery("SELECT max(id) FROM User");
			while (result.next()) {
				insertCount = result.getLong(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return insertCount;
	}

	@Override
	public void update(Model item) {
		try{
			User user = (User)item;
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
	public List<User> getAll(boolean eager, String... fields){
		List<User> users = new ArrayList<>();
		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(getAllSql);
			Boolean eagerWorktask = Arrays.stream(fields).filter(e->e.equals("worktask")).findAny().isPresent();
			while(rs.next()){
				User user = new User();
				user.setId(rs.getLong("id"));
				if(eager) {
					user.setFirstName(rs.getString("firstname"));
					user.setLastName(rs.getString("lastname"));
					user.setCaption(rs.getString("caption"));
					user.setEmail(rs.getString("email"));
					user.setUserTasks(
							(Collection<WorkTask>) DaoFactory.getListById(user.getId(), WorkTask.class, eagerWorktask)
					);
				}
				users.add(user);
			}
		}catch (SQLException e) {
            e.printStackTrace();
        }
		
		return users;
	}

	@Override
	public User getById(Long userId, boolean eager, String... fields){
		User user = new User();
		try{
			PreparedStatement ps = connection.prepareStatement(getUserSql);
			ps.setLong(1, userId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()){
				user.setId(rs.getLong("id"));
				if (eager) {
					user.setFirstName(rs.getString("firstname"));
					user.setLastName(rs.getString("lastname"));
					user.setCaption(rs.getString("caption"));
					user.setEmail(rs.getString("email"));
					Boolean eagerWorktask = Arrays.stream(fields).filter(e->e.equals("worktask")).findAny().isPresent();
					user.setUserTasks(
							(Collection<WorkTask>) DaoFactory.getListById(user.getId(), WorkTask.class, eagerWorktask)
					);
				}
			}
		} catch (SQLException e) {
            e.printStackTrace();
        }
		return user;
	}

	@Override
	public List<User> getListById(Long itemId, boolean eager, String... fields) {
		return null;
	}
}