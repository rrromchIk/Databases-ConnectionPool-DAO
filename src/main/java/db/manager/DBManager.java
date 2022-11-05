package db.manager;

import db.connectionPool.BasicConnectionPool;
import db.models.City;
import db.models.User;

import static db.queries.Queries.*;
import static db.fields.Fields.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBManager {
    private static final DBManager INSTANCE = new DBManager();
    private final BasicConnectionPool connectionPool;

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

        try(FileInputStream fis = new FileInputStream("app.properties")) {
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
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS)) {
            ResultSet res = statement.executeQuery();
            while(res.next()) {
                listOfUsers.add(mapUser(res));
            }
            connectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfUsers;
    }

    public List<City> findAllCities() {
        List<City> listOfCities = new ArrayList<>();
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_CITIES)) {
            ResultSet res = statement.executeQuery();
            while(res.next()) {
                listOfCities.add(mapCity(res));
            }
            connectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfCities;
    }
    
    public List<String> getUsersCity(User user) {
        List<String> usersCity = new ArrayList<>();
        try(Connection connection = connectionPool.getConnection();
        PreparedStatement statement = connection.prepareStatement(FIND_USERS_CITY)) {
            statement.setInt(1, user.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                usersCity.add(String.format("[%s, %s]", resultSet.getString(USER_NAME),
                        resultSet.getString(CITY_NAME)));
            }
            connectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersCity;
    }

    private City mapCity(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString(CITY_NAME);
        City city = City.createCity(name);
        city.setId(resultSet.getInt(CITY_ID));
        return city;
    }

    private User mapUser(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString(USER_NAME);
        int age = resultSet.getInt(USER_AGE);
        int cityId = resultSet.getInt(USER_CITY_ID);
        User user = User.createUser(name, age, cityId);
        user.setId(resultSet.getInt(USER_ID));
        return user;
    }
}
