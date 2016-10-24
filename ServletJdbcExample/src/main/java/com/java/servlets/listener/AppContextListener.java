package com.java.servlets.listener;

import com.java.servlets.util.DbUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppContextListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		DbUtil.setDbproperties(sce.getServletContext().getInitParameter("dbproperties"));
		DbUtil.getConnection();
		System.out.println("------debug----- context initialized success");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		DbUtil.closeConnection();
	}
}
