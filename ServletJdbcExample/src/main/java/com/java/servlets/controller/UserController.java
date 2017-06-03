package com.java.servlets.controller;

import com.java.servlets.dao.impl.UserDaoImpl;
import com.java.servlets.dao.impl.UserViewDaoImpl;
import com.java.servlets.dao.impl.WorkTaskViewDaoImpl;
import com.java.servlets.model.User;
import com.java.servlets.controller.Service.ServletHelper;

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
	private static String LIST_USER2 = "UserController?action=list";

    public UserController(){
    	super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String forward = "";
    	String action = request.getParameter("action");
    		
    	if (action.equalsIgnoreCase("delete")){
    		Long userId = Long.parseLong(request.getParameter("id"));
			UserDaoImpl userDao = UserDaoImpl.getInstance();
			userDao.delete(userId);
    		forward = LIST_USER2;
    	}
    	else if (action.equalsIgnoreCase("edit")){
    		forward = INSERT_OR_EDIT;
    		Long userId = Long.parseLong(request.getParameter("id"));
			User user = UserDaoImpl.getInstance().getById(userId);
			request.setAttribute("user", user);
			request.setAttribute("userTasks", WorkTaskViewDaoImpl.getInstance().getListById(user.getId()));
    	}
		else if (action.equalsIgnoreCase("insert")){
			forward = INSERT_OR_EDIT;
			request.setAttribute("user", new User());
		}
		else if (action.equalsIgnoreCase("list")){
    		forward = LIST_USER;
    		request.setAttribute("users", UserViewDaoImpl.getInstance().getAll(0, 50));
    	} else {
    		forward = INSERT_OR_EDIT;
    	}
    	
    	RequestDispatcher view = request.getRequestDispatcher(forward);
    	view.forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	User user1 = ServletHelper.getUserFromRequest(request);
    	if (user1.getId() == null){
    		UserDaoImpl.getInstance().insert(user1);
    	}
    	else
    	{
    		UserDaoImpl.getInstance().update(user1);
    	}
    	RequestDispatcher view = request.getRequestDispatcher(LIST_USER);
    	request.setAttribute("users", UserViewDaoImpl.getInstance().getAll(0, 50));
    	view.forward(request, response);
    }
}