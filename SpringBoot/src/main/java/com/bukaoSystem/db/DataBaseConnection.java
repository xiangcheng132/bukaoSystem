package com.bukaoSystem.db;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnection implements DataSource {
    private Connection con = null;

    public Connection getCon() {
        if (con == null) {
            try {
                Properties properties = new Properties();
                InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties");
                if (input == null) {
                    System.out.println("Sorry, unable to find application.properties");
                    return null;
                }
                properties.load(input);

                Class.forName("com.mysql.cj.jdbc.Driver"); // 驱动程序名
                String url = properties.getProperty("db.url"); // URL指向要访问的数据库名mysql
                String r_username = properties.getProperty("db.username"); // mysql配置时的用户名
                String r_pwd = properties.getProperty("db.password"); // mysql配置时的密码

                con = DriverManager.getConnection(url, r_username, r_pwd); // 获取用户输入的用户名和密码
            } catch (ClassNotFoundException | SQLException | IOException e) {
                e.printStackTrace();
            }
        }
        return con;
    }

    public void close() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    @Override
    public Connection getConnection() throws SQLException {
        return getCon();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        throw new UnsupportedOperationException();
    }

    // Other DataSource methods can throw UnsupportedOperationException
    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public java.io.PrintWriter getLogWriter() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLogWriter(java.io.PrintWriter out) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public java.util.logging.Logger getParentLogger() {
        throw new UnsupportedOperationException();
    }
}
