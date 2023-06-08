package com.ConnectASU.PerformanceTests;

import com.ConnectASU.DAO.DBUtilities.DBClearer;
import com.ConnectASU.DAO.UserDAO;
import com.ConnectASU.PerformanceTests.Tasks.LoginTask;
import com.ConnectASU.PerformanceTests.Tasks.SignUpTask;
import com.ConnectASU.Service.UserService;
import com.ConnectASU.entities.User;
import com.ConnectASU.exceptions.InvalidEmailException;
import com.ConnectASU.exceptions.InvalidPasswordException;
import com.ConnectASU.exceptions.InvalidUserNameException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestsRunner {

    public static final int NUMBER_OF_USERS = 1000;

    public static ExecutorService executor;
    public static ArrayList<User> users;

    @BeforeEach
    public void setupEach() {
        users = DataGenerator.generateUsers(NUMBER_OF_USERS);
        executor = Executors.newFixedThreadPool(NUMBER_OF_USERS);
        DBClearer.clearDB();
    }

    @AfterEach
    public void teardown() {
        executor.shutdown();
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSignUp() {
        for (User user : users) {
            SignUpTask signUpTask = new SignUpTask(user);
            executor.execute(signUpTask);
        }
        UserDAO userDAO = new UserDAO();
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            ArrayList<User> users = userDAO.getUserSearchByName("");
            assertEquals(NUMBER_OF_USERS, users.size());
        } catch (SQLException e) {
            fail("Failed to get the users from the database");
        }
    }

    @Test
    public void testLogin() {
        UserService userService = UserService.getInstance();
        for (User user : users) {
            try {
                userService.createAccount(user.getEmail(), user.getName(), user.getPassword());
            } catch (InvalidEmailException | InvalidPasswordException | InvalidUserNameException e) {
                fail("Failed to create the users account");
            }
        }

        try {
            UserDAO userDAO = new UserDAO();
            ArrayList<User> users = userDAO.getUserSearchByName("");
            assertEquals(NUMBER_OF_USERS, users.size(), "Users were not added");
        } catch (SQLException e) {
            fail("Failed to get the users from the database");
        }

        for (User user : users) {
            try {
                LoginTask loginTask = new LoginTask(user);
                executor.execute(loginTask);
            } catch (RuntimeException e) {
                fail("Failed to login user: " + user.getEmail() + " " + user.getPassword());
            }
        }
    }
}
