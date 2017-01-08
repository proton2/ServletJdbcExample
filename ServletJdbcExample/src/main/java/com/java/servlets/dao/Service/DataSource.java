package com.java.servlets.dao.Service;

import org.apache.commons.dbcp.BasicDataSource;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by proton2 on 24.12.2016.
 */
public class DataSource {
    private static DataSource datasource;
    private BasicDataSource ds;
    private static String dbproperties;

    public static void setDbproperties(String dbproperties) {
        DataSource.dbproperties = dbproperties;
    }

    private DataSource() throws IOException, SQLException, PropertyVetoException {
        Properties prop = new Properties();
        InputStream inputStream = DataSource.class.getClassLoader().getResourceAsStream(dbproperties);
        prop.load(inputStream);

        ds = new BasicDataSource();
        ds.setDriverClassName(prop.getProperty("driver"));
        ds.setUsername(prop.getProperty("username"));
        ds.setPassword(prop.getProperty("password"));
        ds.setUrl(prop.getProperty("url"));

        // the settings below are optional -- dbcp can work with defaults
        ds.setMinIdle(5);
        ds.setMaxIdle(20);
        ds.setMaxOpenPreparedStatements(180);
    }

    public static DataSource getInstance() {
        if (datasource == null) {
            try {
                datasource = new DataSource();
            } catch (IOException | SQLException | PropertyVetoException e) {
                e.printStackTrace();
            }
            return datasource;
        } else {
            return datasource;
        }
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
