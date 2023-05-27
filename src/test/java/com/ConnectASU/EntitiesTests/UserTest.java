package com.ConnectASU.EntitiesTests;


import com.ConnectASU.entities.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
        assertEquals(expectedName, actualName, "Expected: " + expectedName + ", Actual: " + actualName);
    }

    @Test
    public void testGetEmail() {
        String expectedEmail = "john@example.com";
        String actualEmail = user.getEmail();
        assertEquals(expectedEmail, actualEmail, "Expected: " + expectedEmail + ", Actual: " + actualEmail);
    }

    @Test
    public void testGetPassword() {
        String expectedPassword = "password";
        String actualPassword = user.getPassword();
        assertEquals(expectedPassword, actualPassword, "Expected: " + expectedPassword + ", Actual: " + actualPassword);
    }

    @Test
    public void testEqualsTrue() {
        User user2 = new User("John", "john@example.com", "password");
        assertEquals(user, user2, "Expected the user objects to be equal");
    }

    @Test
    public void testEqualsFalse() {
        User user2 = new User("jane@example.com", "Jane", "password");
        assertNotEquals(user, user2, "Expected the user objects to be not equal");
    }

    @Test
    public void testEqualsFalse2() {
        User user2 = new User("john@example.com", "John", "differentpassword");
        assertNotEquals(user, user2, "Expected the user objects to be not equal");
    }

    @Test
    public void testEqualsFalse3() {
        User user2 = new User("john@example.com", "Jane", "password");
        assertNotEquals(user, user2, "Expected the user objects to be not equal");
    }

}