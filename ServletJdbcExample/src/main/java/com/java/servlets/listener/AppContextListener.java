package com.java.servlets.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.java.servlets.util.DbUtil;

public class AppContextListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		DbUtil.getConnection();
		System.out.println("------debug----- context initialized success");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		DbUtil.closeConnection();
	}
}
