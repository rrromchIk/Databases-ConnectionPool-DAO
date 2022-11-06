package usersCityDB.model;

import java.util.Objects;
import java.util.StringJoiner;

public class User {
    private int id;
    private String name;
    private int age;
    private int cityId;

    private User(int id, String name, int age, int cityId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.cityId = cityId;
    }

    public static User createUser(String name, int age, int cityId) {
        return new User(0, name, age, cityId);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getCityId() {
        return cityId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCityID(int cityId) {
        this.cityId = cityId;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "\n[", "]")
                .add("name: " + name)
                .add("age: " + age)
                .add("city id: " + cityId)
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return Objects.equals(name, user.name)
                && Objects.equals(age, user.age)
                && Objects.equals(cityId, user.cityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, cityId);
    }
}