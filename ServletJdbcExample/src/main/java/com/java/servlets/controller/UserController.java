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
    		DaoFactory.getUserDao().delete(userId);
    		forward = LIST_USER;
    		request.setAttribute("users", DaoFactory.getUserDao().getAll());
    	}
    	else if (action.equalsIgnoreCase("edit")){
    		forward = INSERT_OR_EDIT;
    		Long userId = Long.parseLong(request.getParameter("id"));
    		User user = DaoFactory.getUserDao().getById(userId);
    		request.setAttribute("user", user);
    	}
		else if (action.equalsIgnoreCase("list")){
    		forward = LIST_USER;
    		request.setAttribute("users", DaoFactory.getUserDao().getAll());
    	} else {
    		forward = INSERT_OR_EDIT;
    	}
    	
    	RequestDispatcher view = request.getRequestDispatcher(forward);
    	view.forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	User user = new User();
    	user.setFirstName(request.getParameter("firstname"));
    	user.setLastName(request.getParameter("lastname"));
    	user.setCaption(request.getParameter("caption"));
    	user.setEmail(request.getParameter("email"));
    	String userid = request.getParameter("id");

    	if (userid == null || userid.isEmpty()){
			DaoFactory.getUserDao().insert(user);
    	}
    	else
    	{
    		user.setId(Long.parseLong(userid));
			DaoFactory.getUserDao().update(user);
    	}
    	RequestDispatcher view = request.getRequestDispatcher(LIST_USER);
    	request.setAttribute("users", DaoFactory.getUserDao().getAll());
    	view.forward(request, response);
    }
}