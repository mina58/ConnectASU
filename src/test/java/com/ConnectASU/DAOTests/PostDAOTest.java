package com.ConnectASU.DAOTests;

import com.ConnectASU.DAO.DBUtilities.DBSequenceResetter;
import com.ConnectASU.DAO.GroupDAO;
import com.ConnectASU.DAO.PostDAO;
import com.ConnectASU.DAO.UserDAO;
import com.ConnectASU.entities.Post;
import com.ConnectASU.entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static com.ConnectASU.DAO.DBUtilities.DatabaseTableNames.GROUP_TABLE;
import static com.ConnectASU.DAO.DBUtilities.DatabaseTableNames.POST_TABLE;
import static org.junit.jupiter.api.Assertions.*;

class PostDAOTest {
    private static PostDAO postDAO;
    private static UserDAO userDAO;
    private static GroupDAO groupDAO;
    private static final int testID1 = 1;
    private static final String testContent1 = "Test Post Content";
    private static final String testAuthor1 = "john@test.com";
    private static final int testGroupID = 1;
    private static final int testID2 = 2;
    private static final String testContent2 = "Test Post Content 2";
    private static final String testEmail1 = "john@test.com";
    private static final String testName1 = "John";
    private static final String testPassword1 = "password";
    private static final String testGroupName = "Group 1";

    @BeforeAll
    public static void setup() {
        postDAO = new PostDAO();
        userDAO = new UserDAO();
        groupDAO = new GroupDAO();
    }

    @BeforeEach
    public void setupEach() {
        DBSequenceResetter.resetDBSequence(POST_TABLE);
        DBSequenceResetter.resetDBSequence(GROUP_TABLE);
        try {
            userDAO.createUser(testEmail1, testName1, testPassword1);
        } catch (SQLException e) {
            fail("Failed to insert the user, method thrown an exception: " + e.getMessage());
        }
    }

    @AfterEach
    public void tearDownEach() {
        try {
            userDAO.deleteUserByEmail(testEmail1);
        } catch (SQLException e) {
            fail("Failed to delete the user, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testCreatePostValid() {
        try {
            assertNotNull(postDAO.createPost(testContent1, testAuthor1, 0));
        } catch (SQLException e) {
            fail("Failed to insert the post, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testCreatePostInvalidAuthorNotExits() {
        assertThrows(SQLException.class, () -> {
            postDAO.createPost(testContent1, "invalidemail.co", testGroupID);
        });
    }

    @Test
    public void testGetPostByIDValid() {
        try {
            postDAO.createPost(testContent1, testAuthor1, 0);
            Post expected = new Post(testID1, testContent1, testAuthor1);
            Post actual = postDAO.getPostByID(testID1);
            assertEquals(expected, actual);
        } catch (SQLException e) {
            fail("Failed to query the post, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testGetPostByIDNotExits() {
        try {
            Post actual = postDAO.getPostByID(testID1);
            assertNull(actual);
        } catch (SQLException e) {
            fail("Failed to query the post, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testGetUserPostsValid() {
        try {
            postDAO.createPost(testContent1, testAuthor1, 0);
            postDAO.createPost(testContent2, testAuthor1, 0);
            Post expected1 = new Post(testID1, testContent1, testAuthor1);
            Post expected2 = new Post(testID2, testContent2, testAuthor1);
            ArrayList<Post> expected = new ArrayList<Post>();
            expected.add(expected1);
            expected.add(expected2);
            ArrayList<Post> actual = postDAO.getUserPosts(testAuthor1);
            assertEquals(expected, actual);
        } catch (SQLException e) {
            fail("Failed to query the post, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testGetUserPostsUserNotExits() {
        try {
            ArrayList<Post> actual = postDAO.getUserPosts("nouser");
            assertTrue(actual.isEmpty());
        } catch (SQLException e) {
            fail("Failed to query the post, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testGetUserPostsNoPosts() {
        try {
            ArrayList<Post> actual = postDAO.getUserPosts(testAuthor1);
            assertTrue(actual.isEmpty());
        } catch (SQLException e) {
            fail("Failed to query the post, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testAddLikeValid() {
        try {
            postDAO.createPost(testContent1, testAuthor1, 0);
            assertTrue(postDAO.addPostLike(testID1, testAuthor1));
        } catch (SQLException e) {
            fail("Failed to query the post, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testAddLikePostNotExits() {
        assertThrows(SQLException.class, () -> {
            postDAO.addPostLike(testID1, testAuthor1);
        });
    }

    @Test
    public void testAddLikeUserAlreadyLiked() {
        assertThrows(SQLException.class, () -> {
            postDAO.createPost(testContent1, testAuthor1, 0);
            postDAO.addPostLike(testID1, testAuthor1);
            postDAO.addPostLike(testID1, testAuthor1);
        });
    }

    @Test
    public void testGetGroupPostsValid() {
        try {
            groupDAO.createGroup(testGroupName, testAuthor1);
            postDAO.createPost(testContent1, testAuthor1, testGroupID);
            postDAO.createPost(testContent2, testAuthor1, testGroupID);
            Post expected1 = new Post(testID1, testContent1, testAuthor1);
            Post expected2 = new Post(testID2, testContent2, testAuthor1);
            ArrayList<Post> expected = new ArrayList<Post>();
            expected.add(expected1);
            expected.add(expected2);
            ArrayList<Post> actual = postDAO.getGroupPosts(testGroupID);
            assertEquals(expected, actual);
        } catch (SQLException e) {
            fail("Failed to query the post, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testDeletePostByIDValid() {
        try {
            postDAO.createPost(testContent1, testAuthor1, 0);
            assertTrue(postDAO.deletePostByID(testID1));
        } catch (SQLException e) {
            fail("Failed to query the post, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testDeletePostByIDNotExits() {
        try {
            assertFalse(postDAO.deletePostByID(testID1));
        } catch (SQLException e) {
            fail("Failed to query the post, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testGetPostLikesByIDValid() {
        try {
            postDAO.createPost(testContent1, testAuthor1, 0);
            postDAO.addPostLike(testID1, testAuthor1);
            ArrayList<User> expected = new ArrayList<User>();
            expected.add(new User(testName1, testEmail1 , testPassword1));
            ArrayList<User> actual = postDAO.getPostLikesByID(testID1);
            assertEquals(expected, actual, expected.get(0).getEmail() + " " + (actual.get(0)).getEmail());
        } catch (SQLException e) {
            fail("Failed to query the post, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testGetPostLikesByIDValidNoLikes() {
        try {
            postDAO.createPost(testContent1, testAuthor1, 0);
            ArrayList<User> expected = new ArrayList<User>();
            ArrayList<User> actual = postDAO.getPostLikesByID(testID1);
            assertEquals(expected, actual);
        } catch (SQLException e) {
            fail("Failed to query the post, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testGetPostLikesByIDNotExits() {
        try {
            ArrayList<User> actual = postDAO.getPostLikesByID(testID1);
            assertTrue(actual.isEmpty());
        } catch (SQLException e) {
            fail("Failed to query the post, method thrown an exception: " + e.getMessage());
        }
    }
}