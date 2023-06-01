package com.ConnectASU.PerformanceTests;

import com.ConnectASU.entities.User;

import java.util.ArrayList;

public class DataGenerator {
    public static ArrayList<User> generateUsers(int count) {
        String username;
        String email;
        String password;
        ArrayList<User> users = new ArrayList<User>();
        for (int i = 0; i < count; i++) {
            username = "XXXX" + i;
            email = "user" + i + "@gmail.com";
            password = "XXXXXXXX" + i;
            users.add(new User(username, email, password));
        }
        return users;
    }
}
