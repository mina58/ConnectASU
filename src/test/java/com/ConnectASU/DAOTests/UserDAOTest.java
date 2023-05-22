package com.ConnectASU.DAOTests;

import com.ConnectASU.DAO.UserDAO;
import com.ConnectASU.entities.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
    private static UserDAO userDAO;
    private static final String testEmail1 = "john@test.com";
    private static final String testName1 = "john";
    private static final String testPassword1 = "123456";
    private static final String testEmail2 = "mina@test.com";
    private static final String testName2 = "mina";
    private static final String testPassword2 = "654321";
    private static final String testEmail3 = "sarah@test.com";
    private static final String testName3 = "sarah";
    private static final String testPassword3 = "987654";
    private static final String testEmail4 = "malak@test.com";
    private static final String testName4 = "malak";
    private static final String testPassword4 = "111111";
    private static final String testEmail5 = "marita@test.com";
    private static final String testName5 = "marita";
    private static final String testPassword5 = "222222";

    @BeforeAll
    public static void setUp() {
        userDAO = new UserDAO();
    }

    @Test
    public void testCreateUserValid() {
        try {
            assertTrue(userDAO.createUser(testEmail1, testName1, testPassword1));
        } catch (SQLException e) {
            fail("Failed to insert the user, method thrown an exception: " + e.getMessage());
        } finally {
            try {
                userDAO.deleteUserByEmail(testEmail1);
            } catch (SQLException e) {
                fail("Failed to delete the user, method thrown an exception: " + e.getMessage());
            }
        }
    }

    @Test
    public void testCreateUserInvalidDuplicateEmail() {
        assertThrows(SQLException.class, () -> {
            userDAO.createUser(testEmail1, testName1, testPassword1);
            userDAO.createUser(testEmail1, testName1, testPassword1);
        });
        try {
            userDAO.deleteUserByEmail(testEmail1);
        } catch (SQLException e) {
            fail("Failed to delete the user, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void getUserByEmailValid() {
        try {
            userDAO.createUser(testEmail1, testName1, testPassword1);
            User expected = new User(testName1, testEmail1, testPassword1);
            User actual = userDAO.getUserByEmail(testEmail1);
            assertEquals(expected, actual);
        } catch (SQLException e) {
            fail("Failed to query the user, method thrown an exception: " + e.getMessage());
        } finally {
            try {
                userDAO.deleteUserByEmail(testEmail1);
            } catch (SQLException e) {
                fail("Failed to delete the user, method thrown an exception: " + e.getMessage());
            }
        }
    }

    @Test
    public void testGetUserByEmailNotExists() {
        try {
            assertNull(userDAO.getUserByEmail(testEmail1));
        } catch (SQLException e) {
            fail("Failed to query the user, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testGetUserByEmailEmptyMail() {
        try {
            assertNull(userDAO.getUserByEmail(""));
        } catch (SQLException e) {
            fail("Failed to query the user, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteUserByEmailValid() {
        try {
            userDAO.createUser(testEmail1, testName1, testPassword1);
            assertTrue(userDAO.deleteUserByEmail(testEmail1));
        } catch (SQLException e) {
            fail("Failed to delete the user, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteUserByEmailNotExists() {
        try {
            assertFalse(userDAO.deleteUserByEmail(testEmail1));
        } catch (SQLException e) {
            fail("Failed to delete the user, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteUserByEmailEmptyMail() {
        try {
            assertFalse(userDAO.deleteUserByEmail(""));
        } catch (SQLException e) {
            fail("Failed to delete the user, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testGetUserSearchByName() {
        try {
            userDAO.createUser(testEmail4, testName4, testPassword4);
            userDAO.createUser(testEmail5, testName5, testPassword5);
            User expected1 = new User(testName4, testEmail4, testPassword4);
            User expected2 = new User(testName5, testEmail5, testPassword5);
            ArrayList<User> actual = userDAO.getUserSearchByName("ma");
            ArrayList<User> expected = new ArrayList<>();
            expected.add(expected1);
            expected.add(expected2);
            assertEquals(expected, actual);
        } catch (SQLException e) {
            fail("Failed to query the users by name, method thrown an exception: " + e.getMessage());
        } finally {
            try {
                userDAO.deleteUserByEmail(testEmail4);
                userDAO.deleteUserByEmail(testEmail5);
            } catch (SQLException e) {
                fail("Failed to delete the users, method thrown an exception: " + e.getMessage());
            }
        }
    }

    @Test
    public void testGetUserSearchByNameNotExists() {
        try {
            ArrayList<User> actual = userDAO.getUserSearchByName("ma");
            assertEquals(0, actual.size());
        } catch (SQLException e) {
            fail("Failed to query the users by name, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testFollowUserValid() {
        try {
            userDAO.createUser(testEmail1, testName1, testPassword1);
            userDAO.createUser(testEmail2, testName2, testPassword2);
            assertTrue(userDAO.followUser(testEmail1, testEmail2));
        } catch (SQLException e) {
            fail("Failed to follow the user, method thrown an exception: " + e.getMessage());
        } finally {
            try {
                userDAO.deleteUserByEmail(testEmail1);
                userDAO.deleteUserByEmail(testEmail2);
            } catch (SQLException e) {
                fail("Failed to delete the users, method thrown an exception: " + e.getMessage());
            }
        }
    }

    @Test
    public void testFollowUserAlreadyFollowing() {
        assertThrows(SQLException.class, () -> {
            userDAO.createUser(testEmail1, testName1, testPassword1);
            userDAO.createUser(testEmail2, testName2, testPassword2);
            userDAO.followUser(testEmail1, testEmail2);
            userDAO.followUser(testEmail1, testEmail2);
        });
        try {
            userDAO.deleteUserByEmail(testEmail1);
            userDAO.deleteUserByEmail(testEmail2);
        } catch (SQLException e) {
            fail("Failed to delete the users, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testFollowUserFollowerNotExists() {
        assertThrows(SQLException.class, () -> {
            userDAO.createUser(testEmail1, testName1, testPassword1);
            userDAO.followUser(testEmail2, testEmail1);
        });
        try {
            userDAO.deleteUserByEmail(testEmail1);
        } catch (SQLException e) {
            fail("Failed to delete the users, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testFollowUserFolloweeNotExists() {
        assertThrows(SQLException.class, () -> {
            userDAO.createUser(testEmail1, testName1, testPassword1);
            userDAO.followUser(testEmail1, testEmail2);
        });
        try {
            userDAO.deleteUserByEmail(testEmail1);
        } catch (SQLException e) {
            fail("Failed to delete the users, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testUnfollowUserValid() {
        try {
            userDAO.createUser(testEmail1, testName1, testPassword1);
            userDAO.createUser(testEmail2, testName2, testPassword2);
            userDAO.followUser(testEmail1, testEmail2);
            assertTrue(userDAO.unfollowUser(testEmail1, testEmail2));
        } catch (SQLException e) {
            fail("Failed to unfollow the user, method thrown an exception: " + e.getMessage());
        } finally {
            try {
                userDAO.deleteUserByEmail(testEmail1);
                userDAO.deleteUserByEmail(testEmail2);
            } catch (SQLException e) {
                fail("Failed to delete the users, method thrown an exception: " + e.getMessage());
            }
        }
    }

    @Test
    public void testUnfollowUserNotFollowing() {
        try {
            userDAO.createUser(testEmail1, testName1, testPassword1);
            userDAO.createUser(testEmail2, testName2, testPassword2);
            assertFalse(userDAO.unfollowUser(testEmail1, testEmail2));
        } catch (SQLException e) {
            fail("Failed to unfollow the user, method thrown an exception: " + e.getMessage());
        } finally {
            try {
                userDAO.deleteUserByEmail(testEmail1);
                userDAO.deleteUserByEmail(testEmail2);
            } catch (SQLException e) {
                fail("Failed to delete the users, method thrown an exception: " + e.getMessage());
            }
        }
    }
}