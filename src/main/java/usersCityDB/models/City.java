package usersCityDB.models;

import java.util.Objects;
import java.util.StringJoiner;

public class City {
    private int id;
    private String name;

    private City(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public static City createCity(String name) {
        return new City(name, 0);
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "[" , "]")
                .add("name: " + name)
                .add("id: " + id)
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || getClass() != obj.getClass()) return false;
        City city = (City) obj;
        return Objects.equals(name, city.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

