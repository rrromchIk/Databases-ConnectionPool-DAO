package loginDB.queries;

public interface Queries {
    String GET_ALL_USERS = "SELECT * FROM logintable";
    String GET_USER = "SELECT * FROM logintable WHERE login = ? AND password = sha1(?)";
    String ADD_USER = "INSERT INTO logintable(login, password, email) VALUES(?, sha1(?), ?)";
    String UPDATE_USER_LOGIN = "UPDATE logintable SET login = ? WHERE login = ?";
    String UPDATE_USER_PASSWORD = "UPDATE logintable SET password = ? WHERE login = ?";
    String UPDATE_USER_EMAIL = "UPDATE logintable SET email = ? WHERE login = ?";
    String DELETE_USER = "DELETE FROM logintable WHERE login = ?";
}
