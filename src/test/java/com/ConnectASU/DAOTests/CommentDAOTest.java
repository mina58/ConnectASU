package com.ConnectASU.DAOTests;

import com.ConnectASU.DAO.CommentDAO;
import com.ConnectASU.DAO.PostDAO;
import com.ConnectASU.DAO.UserDAO;
import com.ConnectASU.DAO.DBUtilities.DBSequenceResetter;
import com.ConnectASU.entities.Comment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static com.ConnectASU.DAO.DBUtilities.DatabaseTableNames.COMMENT_TABLE;
import static com.ConnectASU.DAO.DBUtilities.DatabaseTableNames.POST_TABLE;
import static org.junit.jupiter.api.Assertions.*;

class CommentDAOTest {
    private static CommentDAO commentDAO;
    private static UserDAO userDAO;
    private static PostDAO postDAO;
    private static final int testID1 = 1;
    private static final String testCommentContent1 = "Test Comment Content";
    private static final int testPostID1 = 1;
    private static final String testPostContent1 = "Test Post Content";
    private static final String testCommentContent2 = "Test Comment Content 2";
    private static final String testAuthor1 = "john@test.com";
    private static final String testName1 = "John";
    private static final String testPassword1 = "password";

    @BeforeAll
    static void setup() {
        commentDAO = new CommentDAO();
        userDAO = new UserDAO();
        postDAO = new PostDAO();
    }

    @BeforeEach
    public void setupEach() {
        DBSequenceResetter.resetDBSequence(POST_TABLE);
        DBSequenceResetter.resetDBSequence(COMMENT_TABLE);
        try {
            userDAO.createUser(testAuthor1, testName1, testPassword1);
            postDAO.createPost(testPostContent1, testAuthor1, 0);
        } catch (SQLException e) {
            fail("Failed to create the user and post, method thrown an exception: ", e);
        }
    }

    @AfterEach
    public void tearDownEach() {
        try {
            userDAO.deleteUserByEmail(testAuthor1);
        } catch (SQLException e) {
            fail("Failed to delete the user, method thrown an exception: ", e);
        }
    }

    @Test
    public void testCreateCommentValid() {
        try {
            assertNotNull(commentDAO.createComment(testCommentContent1, testPostID1, testAuthor1));
        } catch (SQLException e) {
            fail("Failed to create the comment, method thrown an exception: ", e);
        }
    }

    @Test
    public void testCreateCommentInvalidPostID() {
        assertThrows(SQLException.class, () -> {
            commentDAO.createComment(testCommentContent1, testPostID1 + 1, testAuthor1);
        });
    }

    @Test
    public void testCreateCommentInvalidAuthor() {
        assertThrows(SQLException.class, () -> {
            commentDAO.createComment(testCommentContent1, testPostID1, testAuthor1 + "1");
        });
    }

    @Test
    public void testGetCommentByIDValid() {
        try {
            commentDAO.createComment(testCommentContent1, testPostID1, testAuthor1);
            Comment expected = new Comment(testID1, testCommentContent1, testAuthor1);
            Comment actual = commentDAO.getCommentByID(testID1);
            assertEquals(expected, actual);
        } catch (SQLException e) {
            fail("Failed to get the comment, method thrown an exception: ", e);
        }
    }

    @Test
    public void testGetCommentByIDInvalidNotExists() {
        try {
            Comment actual = commentDAO.getCommentByID(testID1);
            assertNull(actual);
        } catch (SQLException e) {
            fail("Failed to get the comment, method thrown an exception: ", e);
        }
    }

    @Test
    public void testGetCommentsByPostIDValid() {
        try {
            commentDAO.createComment(testCommentContent1, testPostID1, testAuthor1);
            commentDAO.createComment(testCommentContent2, testPostID1, testAuthor1);
            Comment expected1 = new Comment(testID1, testCommentContent1, testAuthor1);
            Comment expected2 = new Comment(testID1 + 1, testCommentContent2, testAuthor1);
            ArrayList<Comment> expected = new ArrayList<>();
            expected.add(expected1);
            expected.add(expected2);
            ArrayList<Comment> actual = commentDAO.getCommentsByPostID(testPostID1);
            assertEquals(expected, actual);
        } catch (SQLException e) {
            fail("Failed to get the comment, method thrown an exception: ", e);
        }
    }


    @Test
    public void testGetCommentsByPostIDInvalidPostNotExists() {
        try {
            ArrayList<Comment> actual = commentDAO.getCommentsByPostID(testPostID1 + 1);
            assertTrue(actual.isEmpty());
        } catch (SQLException e) {
            fail("Failed to get the comment, method thrown an exception: ", e);
        }
    }

    @Test
    public void testGetCommentsByPostIDNoComments() {
        try {
            ArrayList<Comment> actual = commentDAO.getCommentsByPostID(testPostID1);
            assertTrue(actual.isEmpty());
        } catch (SQLException e) {
            fail("Failed to get the comment, method thrown an exception: ", e);
        }
    }

    @Test
    public void testDeleteCommentValid() {
        try {
            commentDAO.createComment(testCommentContent1, testPostID1, testAuthor1);
            assertTrue(commentDAO.deleteCommentByID(testID1));
        } catch (SQLException e) {
            fail("Failed to delete the comment, method thrown an exception: ", e);
        }
    }

    @Test
    public void testDeleteCommentInvalidNotExists() {
        try {
            assertFalse(commentDAO.deleteCommentByID(testID1));
        } catch (SQLException e) {
            fail("Failed to delete the comment, method thrown an exception: ", e);
        }
    }
}