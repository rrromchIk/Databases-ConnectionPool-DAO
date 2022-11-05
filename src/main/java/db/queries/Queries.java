package db.queries;

public interface Queries {
    String FIND_ALL_USERS = "SELECT * FROM user";
    String FIND_ALL_CITIES = "SELECT * FROM city";
    String FIND_USERS_CITY = "SELECT name, city_name FROM user AS u JOIN " +
            "city AS c ON u.city_id = c.id WHERE u.id = ?";
}
