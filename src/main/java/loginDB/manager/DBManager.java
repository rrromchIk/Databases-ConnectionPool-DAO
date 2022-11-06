package loginDB.manager;

import db.connectionPool.*;
import loginDB.dao.UserDAO;
import loginDB.model.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class DBManager {
    private static final DBManager INSTANCE = new DBManager();
    private final ConnectionPool connectionPool;

    private DBManager()  {
        BasicConnectionPool pool = null;
        try {
            pool = BasicConnectionPool.create(getUrl());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool = pool;
        }
    }

    private String getUrl() {
        Properties properties = new Properties();
        String url = null;

        try(FileInputStream fis = new FileInputStream("database.properties")) {
            properties.load(fis);
            url = properties.getProperty("connection.url");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static synchronized DBManager getInstance() {
        return INSTANCE;
    }

    public List<User> findAllUsers() {
        List<User> listOfUsers;
        Connection connection = connectionPool.getConnection();
        UserDAO userDAO = new UserDAO(connection);
        listOfUsers = userDAO.readAll();
        connectionPool.releaseConnection(connection);
        return listOfUsers;
    }

    public boolean addUser(User user) {
        Connection connection = connectionPool.getConnection();
        UserDAO userDAO = new UserDAO(connection);
        boolean result = userDAO.create(user);
        connectionPool.releaseConnection(connection);
        return result;
    }

    public User getUser(String login) {
        Connection connection = connectionPool.getConnection();
        UserDAO userDAO = new UserDAO(connection);
        User user = userDAO.read(login);
        connectionPool.releaseConnection(connection);
        return user;
    }

    public boolean deleteUser(String login, String password) {
        Connection connection = connectionPool.getConnection();
        UserDAO userDAO = new UserDAO(connection);
        boolean result = userDAO.delete(User.createUser(login, password, ""));
        connectionPool.releaseConnection(connection);
        return result;
    }

    public void shutdown() {
        try {
            connectionPool.shutdown();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
