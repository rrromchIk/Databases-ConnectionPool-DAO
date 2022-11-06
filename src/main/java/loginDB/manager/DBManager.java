package loginDB.manager;

import db.connectionPool.*;

import loginDB.models.User;

import static loginDB.queries.Queries.*;
import static loginDB.fields.Fields.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
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
        List<User> listOfUsers = new ArrayList<>();
        Connection connection = connectionPool.getConnection();
        try(Statement statement = connection.createStatement()) {
            ResultSet res = statement.executeQuery(GET_ALL_USERS);
            while(res.next()) {
                listOfUsers.add(mapUser(res));
            }
            connectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return listOfUsers;
    }

    public boolean addUser(User user) {
        boolean success = false;
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement statement = connection.prepareStatement(ADD_USER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            success = statement.executeUpdate() > 0;
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return success;
    }

    public User getUser(String login, String password) {
        User user = null;
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement statement = connection.prepareStatement(GET_USER)) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet  = statement.executeQuery();
            if(resultSet.next()) {
                user = mapUser(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return user;
    }

    public boolean deleteUser(String login) {
        boolean success = false;
        Connection connection = connectionPool.getConnection();
        try(PreparedStatement statement = connection.prepareStatement(DELETE_USER)) {
            statement.setString(1, login);
            success = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return success;
    }

    private User mapUser(ResultSet resultSet) throws SQLException {
        User user = User.createUser(resultSet.getString(USER_LOGIN),
                resultSet.getString(USER_PASSWORD),
                resultSet.getString(USER_EMAIL));
        user.setId(resultSet.getInt(USER_ID));
        return user;
    }

    public void shutdown() {
        try {
            connectionPool.shutdown();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
