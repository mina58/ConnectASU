package com.ConnectASU.entitiesTests;


import com.ConnectASU.entities.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    private static User user;

    @BeforeAll
    public static void setUp() {
        String name = "John";
        String email = "john@example.com";
        String password = "password";
        user = new User(name, email, password);
    }

    @Test
    public void testGetName() {
        String expectedName = "John";
        String actualName = user.getName();
        assertEquals(expectedName, actualName);
    }

    @Test
    public void testGetEmail() {
        String expectedEmail = "john@example.com";
        String actualEmail = user.getEmail();
        assertEquals(expectedEmail, actualEmail);
    }

    @Test
    public void testGetPassword() {
        String expectedPassword = "password";
        String actualPassword = user.getPassword();
        assertEquals(expectedPassword, actualPassword);
    }
}