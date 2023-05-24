package com.ConnectASU.ServiceTests;

import com.ConnectASU.DAO.UserDAO;
import com.ConnectASU.Service.UserService;
import com.ConnectASU.entities.User;
import com.ConnectASU.exceptions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserService userService;
    private static UserDAO userDAO;
    private static final String validEmail1 = "john@test.com";
    private static final String validPassword1 = "12345678";
    private static final String validName1 = "John";
    private static final String validEmail2 = "mina@test.com";
    private static final String validPassword2 = "12345678";
    private static final String validName2 = "Mina";
    private static final String validEmail3 = "sarah@test.com";
    private static final String validPassword3 = "12345678";
    private static final String validName3 = "Sarah";
    private static final String invalidEmail = "johntest";
    private static final String invalidPassword = "";
    private static final String invalidName = "";

    @BeforeAll
    public static void setup() {
        userDAO = new UserDAO();
    }

    @BeforeEach
    public void setupEach() {
        userService = UserService.getInstance();
    }

    @Test
    public void testCreateAccountValid() {
        try {
            userService.createAccount(validEmail1, validPassword1, validName1);
            assertNotNull(userDAO.getUserByEmail(validEmail1));
        } catch (Exception e) {
            fail("Failed to create account");
        } finally {
            try {
                userDAO.deleteUserByEmail(validEmail1);
            } catch (SQLException e) {
                fail("Failed to delete account");
            }
        }
    }

    @Test
    public void testCreateAccountInvalidEmail() {
        assertThrows(InvalidEmailException.class, () -> {
            userService.createAccount(invalidEmail, validPassword1, validName1);
        });
    }

    @Test
    public void testCreateAccountInvalidPassword() {
        assertThrows(InvalidPasswordException.class, () -> {
            userService.createAccount(validEmail1, validName1, invalidPassword);
        });
    }

    @Test
    public void testCreateAccountInvalidName() {
        assertThrows(InvalidUserNameException.class, () -> {
            userService.createAccount(validEmail1, invalidName, validPassword1);
        });
    }

    @Test
    public void testDeleteAccountValid() {
        try {
            userService.createAccount(validEmail1, validName1, validPassword1);
            User user = new User(validName1, validEmail1, validPassword1);
            userService.deleteAccount(user);
            assertNull(userDAO.getUserByEmail(validEmail1));
        } catch (Exception e) {
            fail("Failed to delete account");
        }
    }

    @Test
    public void testDeleteAccountInvalid() {
        try {
            userService.createAccount(validEmail1, validName1, validPassword1);
            User user = new User(validName1, invalidEmail, validPassword1);
            userService.deleteAccount(user);
            assertNotNull(userDAO.getUserByEmail(validEmail1));
        } catch (Exception e) {
            fail("Failed to delete account");
        } finally {
            try {
                userDAO.deleteUserByEmail(validEmail1);
            } catch (SQLException e) {
                fail("Failed to delete account");
            }
        }
    }

    @Test
    public void testLogUserInValid() {
        try {
            userService.createAccount(validEmail1, validName1, validPassword1);
            User expected = new User(validName1, validEmail1, validPassword1);
            User actual = (userService.logUserIn(validEmail1, validPassword1));
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail("Failed to log in");
        } finally {
            try {
                userDAO.deleteUserByEmail(validEmail1);
            } catch (SQLException e) {
                fail("Failed to delete account");
            }
        }
    }

    @Test
    public void testLogUserInInvalidPassword() {
        try {
            userService.createAccount(validEmail1, validName1, validPassword1);
            assertThrows(InvalidLoginException.class, () -> {
                User user = userService.logUserIn(validEmail1, invalidPassword);
            });
        } catch (Exception e) {
            fail("Failed to log in");
        } finally {
            try {
                userDAO.deleteUserByEmail(validEmail1);
            } catch (SQLException e) {
                fail("Failed to delete account");
            }
        }
    }

    @Test
    public void testLogUserInInvalidEmail() {
        assertThrows(InvalidLoginException.class, () -> {
            User user = userService.logUserIn(invalidEmail, invalidPassword);
        });
    }

    @Test
    public void testFollowUserValid() {
        try {
            userService.createAccount(validEmail1, validName1, validPassword1);
            userService.createAccount(validEmail2, validName2, validPassword2);
            User follower = new User(validName1, validEmail1, validPassword1);
            User followee = new User(validName2, validEmail2, validPassword2);
            userService.followUser(follower, followee);
        } catch (Exception e) {
            fail("Failed to follow user");
        } finally {
            try {
                userDAO.deleteUserByEmail(validEmail1);
                userDAO.deleteUserByEmail(validEmail2);
            } catch (SQLException e) {
                fail("Failed to delete account");
            }
        }
    }

    @Test
    public void testFollowUserInvalidAlreadyFollowing() {
        try {
            userService.createAccount(validEmail1, validName1, validPassword1);
            userService.createAccount(validEmail2, validName2, validPassword2);
            User follower = new User(validName1, validEmail1, validPassword1);
            User followee = new User(validName2, validEmail2, validPassword2);
            userService.followUser(follower, followee);
            assertThrows(FailedFollowException.class, () -> {
                userService.followUser(follower, followee);
            });
        } catch (Exception e) {
            fail("Failed to follow user");
        } finally {
            try {
                userDAO.deleteUserByEmail(validEmail1);
                userDAO.deleteUserByEmail(validEmail2);
            } catch (SQLException e) {
                fail("Failed to delete account");
            }
        }
    }

    @Test
    public void testFollowUserInvalidFollowerNotExists() {
        try {
            userService.createAccount(validEmail2, validName2, validPassword2);
            User follower = new User(validName1, invalidEmail, validPassword1);
            User followee = new User(validName2, validEmail2, validPassword2);
            assertThrows(FailedFollowException.class, () -> {
                userService.followUser(follower, followee);
            });
        } catch (Exception e) {
            fail("Failed to follow user");
        } finally {
            try {
                userDAO.deleteUserByEmail(validEmail2);
            } catch (SQLException e) {
                fail("Failed to delete account");
            }
        }
    }

    @Test
    public void testFollowUserInvalidFolloweeNotExists() {
        try {
            userService.createAccount(validEmail1, validName1, validPassword1);
            User follower = new User(validName1, validEmail1, validPassword1);
            User followee = new User(validName2, invalidEmail, validPassword2);
            assertThrows(FailedFollowException.class, () -> {
                userService.followUser(follower, followee);
            });
        } catch (Exception e) {
            fail("Failed to follow user");
        } finally {
            try {
                userDAO.deleteUserByEmail(validEmail1);
            } catch (SQLException e) {
                fail("Failed to delete account");
            }
        }
    }

    @Test
    public void testUnfollowUserValid() {
        try {
            userService.createAccount(validEmail1, validName1, validPassword1);
            userService.createAccount(validEmail2, validName2, validPassword2);
            User follower = new User(validName1, validEmail1, validPassword1);
            User followee = new User(validName2, validEmail2, validPassword2);
            userService.followUser(follower, followee);
            userService.unfollowUser(follower, followee);
        } catch (Exception e) {
            fail("Failed to unfollow user");
        } finally {
            try {
                userDAO.deleteUserByEmail(validEmail1);
                userDAO.deleteUserByEmail(validEmail2);
            } catch (SQLException e) {
                fail("Failed to delete account");
            }
        }
    }

    @Test
    public void testUnfollowUserInvalidNotFollowing() {
        try {
            userService.createAccount(validEmail1, validName1, validPassword1);
            userService.createAccount(validEmail2, validName2, validPassword2);
            User follower = new User(validName1, validEmail1, validPassword1);
            User followee = new User(validName2, validEmail2, validPassword2);
            assertThrows(FailedUnfollowException.class, () -> {
                userService.unfollowUser(follower, followee);
            });
        } catch (Exception e) {
            fail("Failed to unfollow user");
        } finally {
            try {
                userDAO.deleteUserByEmail(validEmail1);
                userDAO.deleteUserByEmail(validEmail2);
            } catch (SQLException e) {
                fail("Failed to delete account");
            }
        }
    }

    @Test
    public void testUnfollowUserInvalidFollowerNotExists() {
        try {
            userService.createAccount(validEmail2, validName2, validPassword2);
            User follower = new User(validName1, invalidEmail, validPassword1);
            User followee = new User(validName2, validEmail2, validPassword2);
            assertThrows(FailedUnfollowException.class, () -> {
                userService.unfollowUser(follower, followee);
            });
        } catch (Exception e) {
            fail("Failed to unfollow user");
        } finally {
            try {
                userDAO.deleteUserByEmail(validEmail2);
            } catch (SQLException e) {
                fail("Failed to delete account");
            }
        }
    }

    @Test
    public void testUnfollowUserInvalidFolloweeNotExists() {
        try {
            userService.createAccount(validEmail1, validName1, validPassword1);
            User follower = new User(validName1, validEmail1, validPassword1);
            User followee = new User(validName2, invalidEmail, validPassword2);
            assertThrows(FailedUnfollowException.class, () -> {
                userService.unfollowUser(follower, followee);
            });
        } catch (Exception e) {
            fail("Failed to unfollow user");
        } finally {
            try {
                userDAO.deleteUserByEmail(validEmail1);
            } catch (SQLException e) {
                fail("Failed to delete account");
            }
        }
    }

    @Test
    public void testGetUserFollowersValid() {
        try {
            userService.createAccount(validEmail1, validName1, validPassword1);
            userService.createAccount(validEmail2, validName2, validPassword2);
            userService.createAccount(validEmail3, validName3, validPassword3);
            User follower1 = new User(validName1, validEmail1, validPassword1);
            User followee = new User(validName2, validEmail2, validPassword2);
            User follower2 = new User(validName3, validEmail3, validPassword3);
            ArrayList<User> expected = new ArrayList<>();
            expected.add(follower1);
            expected.add(follower2);
            userService.followUser(follower1, followee);
            userService.followUser(follower2, followee);
            ArrayList<User> followers = userService.getUserFollowers(followee);
            assertEquals(expected, followers);
        } catch (Exception e) {
            fail("Failed to get user followers");
        } finally {
            try {
                userDAO.deleteUserByEmail(validEmail1);
                userDAO.deleteUserByEmail(validEmail2);
                userDAO.deleteUserByEmail(validEmail3);
            } catch (SQLException e) {
                fail("Failed to delete account");
            }
        }
    }

    @Test
    public void testGetUserFollowersInvalidUserNotExists() {
        try {
            ArrayList<User> actual = userService.getUserFollowers(new User(validName1, validEmail1, validPassword1));
            assertTrue(actual.isEmpty());
        } catch (Exception e) {
            fail("Failed to get user followers");
        }
    }

    @Test
    public void testGetUserFollowersInvalidUserNull() {
        assertThrows(CannotGetFollowersException.class, () -> {
            userService.getUserFollowers(null);
        });
    }

    @Test
    public void testGetUserFollowersNoFollowers() {
        try {
            userService.createAccount(validEmail1, validName1, validPassword1);
            User user = new User(validName1, validEmail1, validPassword1);
            ArrayList<User> actual = userService.getUserFollowers(user);
            assertTrue(actual.isEmpty());
        } catch (Exception e) {
            fail("Failed to get user followers");
        } finally {
            try {
                userDAO.deleteUserByEmail(validEmail1);
            } catch (SQLException e) {
                fail("Failed to delete account");
            }
        }
    }
}