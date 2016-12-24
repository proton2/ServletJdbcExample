package com.java.servlets.listener;

import com.java.servlets.util.DataSource;
//import com.java.servlets.util.DbUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppContextListener implements ServletContextListener{

	private static final Logger LOGGER = LoggerFactory.getLogger(AppContextListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		DataSource.setDbproperties(sce.getServletContext().getInitParameter("dbproperties"));
		//DbUtil.getConnection();
		LOGGER.info("------debug----- context initialized success");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		//DbUtil.closeConnection();
	}
}
