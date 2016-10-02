package com.java.servlets.controller;

import com.java.servlets.dao.DaoFactory;
import com.java.servlets.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserController extends HttpServlet{
	private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/user.jsp";
    private static String LIST_USER = "/listUser.jsp";

    public UserController(){
    	super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String forward = "";
    	String action = request.getParameter("action");
    		
    	if (action.equalsIgnoreCase("delete")){
    		Long userId = Long.parseLong(request.getParameter("id"));
    		DaoFactory.delete(userId, User.class);
    		forward = LIST_USER;
    		request.setAttribute("users", DaoFactory.getAll(User.class));
    	}
    	else if (action.equalsIgnoreCase("edit")){
    		forward = INSERT_OR_EDIT;
    		Long userId = Long.parseLong(request.getParameter("id"));
    		User user = (User) DaoFactory.getById(userId, true, User.class, "worktask");
			request.setAttribute("user", user);
    	}
		else if (action.equalsIgnoreCase("list")){
    		forward = LIST_USER;
    		request.setAttribute("users", DaoFactory.getAll(User.class));
    	} else {
    		forward = INSERT_OR_EDIT;
    	}
    	
    	RequestDispatcher view = request.getRequestDispatcher(forward);
    	view.forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	User user1 = new User();
    	user1.setFirstName(request.getParameter("firstname"));
    	user1.setLastName(request.getParameter("lastname"));
    	user1.setCaption(request.getParameter("caption"));
    	user1.setEmail(request.getParameter("email"));
    	String userid = request.getParameter("id");

    	if (userid == null || userid.isEmpty()){
			DaoFactory.insert(user1);
    	}
    	else
    	{
    		user1.setId(Long.parseLong(userid));
			DaoFactory.update(user1);
    	}
    	RequestDispatcher view = request.getRequestDispatcher(LIST_USER);
    	request.setAttribute("users", DaoFactory.getAll(User.class));
    	view.forward(request, response);
    }
}