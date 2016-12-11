package com.java.servlets.controller;

import com.java.servlets.dao.DaoFactory;
import com.java.servlets.model.User;
import com.java.servlets.model.UserView;
import com.java.servlets.model.WorkTaskView;
import com.java.servlets.util.ServletHelper;

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

	private ServletHelper helper;

    public UserController(){
    	super();
		helper = new ServletHelper();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String forward = "";
    	String action = request.getParameter("action");
    		
    	if (action.equalsIgnoreCase("delete")){
    		Long userId = Long.parseLong(request.getParameter("id"));
    		DaoFactory.delete(userId, User.class);
    		forward = LIST_USER;
    		request.setAttribute("users", DaoFactory.getAll(User.class, 0, 0));
    	}
    	else if (action.equalsIgnoreCase("edit")){
    		forward = INSERT_OR_EDIT;
    		Long userId = Long.parseLong(request.getParameter("id"));
    		User user = (User) DaoFactory.getById(userId, User.class);
			request.setAttribute("user", user);
			request.setAttribute("userTasks", DaoFactory.getListById(user.getId(), WorkTaskView.class));
    	}
		else if (action.equalsIgnoreCase("insert")){
			forward = INSERT_OR_EDIT;
			request.setAttribute("user", new User());
		}
		else if (action.equalsIgnoreCase("list")){
    		forward = LIST_USER;
    		request.setAttribute("users", DaoFactory.getAll(UserView.class, 0, 0));
    	} else {
    		forward = INSERT_OR_EDIT;
    	}
    	
    	RequestDispatcher view = request.getRequestDispatcher(forward);
    	view.forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	User user1 = helper.getUserFromRequest(request);
    	if (user1.getId() == null){
			DaoFactory.insert(user1);
    	}
    	else
    	{
			DaoFactory.update(user1);
    	}
    	RequestDispatcher view = request.getRequestDispatcher(LIST_USER);
    	request.setAttribute("users", DaoFactory.getAll(UserView.class, 0, 0));
    	view.forward(request, response);
    }
}