package main;

import db.manager.DBManager;
import db.models.City;
import db.models.User;

import java.util.Collection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DBManager dbManager = DBManager.getInstance();
        List<User> listOfUsers = dbManager.findAllUsers();
        List<City> listOfCities = dbManager.findAllCities();
        List<String> usersCity = listOfUsers.stream()
                        .map(dbManager::getUsersCity)
                        .flatMap(Collection::stream)
                                .toList();

        System.out.println(usersCity);
        System.out.println(listOfUsers);
        System.out.println(listOfCities);
        System.out.println(listOfUsers);
    }
}
