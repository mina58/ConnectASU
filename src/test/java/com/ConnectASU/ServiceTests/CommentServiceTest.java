package com.ConnectASU.ServiceTests;

import com.ConnectASU.DAO.CommentDAO;
import com.ConnectASU.DAO.DBUtilities.DBSequenceResetter;
import com.ConnectASU.DAO.GroupDAO;
import com.ConnectASU.DAO.PostDAO;
import com.ConnectASU.DAO.UserDAO;
import com.ConnectASU.Service.CommentService;
import com.ConnectASU.entities.Comment;
import com.ConnectASU.entities.Post;
import com.ConnectASU.entities.User;
import com.ConnectASU.exceptions.CannotGetCommentsException;
import com.ConnectASU.exceptions.InvalidCommentException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.ConnectASU.DAO.DBUtilities.DatabaseTableNames.*;
import static org.junit.jupiter.api.Assertions.*;

public class CommentServiceTest {
    private CommentService commentService;
    private static CommentDAO commentDAO;
    private static PostDAO postDAO;
    private static UserDAO userDAO;
    private static GroupDAO groupDAO;
    private static final String validEmail1 = "john@test.com";
    private static final String validPassword1 = "12345678";
    private static final String validName1 = "John";
    private static final String validEmail2 = "mina@test.com";
    private static final String validPassword2 = "12345678";
    private static final String validName2 = "Mina";
    private static final User user1 = new User(validName1, validEmail1, validPassword1);
    private static final User user2 = new User(validName2, validEmail2, validPassword2);
    private static Post post;
    private static final String postContent1 = "This is a post";
    private static final String postContent2 = "This is another post";
    private static final String postContent3 = "This is a third post";
    private static final String commentContent1 = "This is a comment";
    private static final String commentContent2 = "This is another comment";
    private static final String commentContent3 = "This is a third comment";

    @BeforeAll
    public static void setUp() {
        postDAO = new PostDAO();
        userDAO = new UserDAO();
        groupDAO = new GroupDAO();
        commentDAO = new CommentDAO();
    }

    @BeforeEach
    public void setUpEach() {
        commentService = CommentService.getInstance();
        DBSequenceResetter.resetDBSequence(POST_TABLE);
        DBSequenceResetter.resetDBSequence(COMMENT_TABLE);
        DBSequenceResetter.resetDBSequence(GROUP_TABLE);
        try {
            userDAO.createUser(validEmail1, validName1, validPassword1);
            userDAO.createUser(validEmail2, validName2, validPassword2);
            groupDAO.createGroup("testGroup", validEmail1);
            post = postDAO.createPost(postContent1, validEmail1, 1);
        } catch (Exception e) {
            fail("Failed to initialize test");
        }
    }

    @AfterEach
    public void tearDownEach() {
        try {
            userDAO.deleteUserByEmail(validEmail1);
            userDAO.deleteUserByEmail(validEmail2);
        } catch (Exception e) {
            fail("Failed to tear down test");
        }
    }

    @Test
    public void testAddCommentValid() {
        try {
            Comment comment1 = commentService.addComment(post, commentContent1, user1);
            Comment comment2 = commentService.addComment(post, commentContent2, user2);
            ArrayList<Comment> actual = commentDAO.getCommentsByPostID(post.getId());
            ArrayList<Comment> expected = new ArrayList<>();
            expected.add(comment1);
            expected.add(comment2);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail("Failed to add comment");
        }
    }

    @Test
    public void testAddCommentInvalidPost() {
        assertThrows(InvalidCommentException.class, () -> {
            Post invalidPost = new Post(0, postContent2, user1.getEmail());
            commentService.addComment(invalidPost, commentContent1, user1);
            fail("Failed to throw exception");
        });
    }

    @Test
    public void testAddCommentInvalidEmptyContent() {
        assertThrows(InvalidCommentException.class, () -> {
            commentService.addComment(post, "", user1);
            fail("Failed to throw exception");
        });
    }

    @Test
    public void testAddCommentInvalidNullContent() {
        assertThrows(InvalidCommentException.class, () -> {
            commentService.addComment(post, null, user1);
            fail("Failed to throw exception");
        });
    }

    @Test
    public void testAddCommentInvalidNullUser() {
        assertThrows(InvalidCommentException.class, () -> {
            commentService.addComment(post, commentContent1, null);
        });
    }

    @Test
    public void testGetPostCommentsValid() {
        try {
            Comment comment1 = commentService.addComment(post, commentContent1, user1);
            Comment comment2 = commentService.addComment(post, commentContent2, user2);
            ArrayList<Comment> actual = commentService.getPostComments(post);
            ArrayList<Comment> expected = new ArrayList<>();
            expected.add(comment1);
            expected.add(comment2);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail("Failed to get post comments");
        }
    }

    @Test
    public void testGetPostCommentsInvalidPost() {
        try {
            Comment comment1 = commentService.addComment(post, commentContent1, user1);
            Comment comment2 = commentService.addComment(post, commentContent2, user2);
            ArrayList<Comment> actual = commentService.getPostComments(new Post(0, postContent2, user1.getEmail()));
            assertTrue(actual.isEmpty());
        } catch (Exception e) {
            fail("Failed to get post comments");
        }
    }

    @Test
    public void testGetPostCommentsInvalidNullPost() {
        assertThrows(CannotGetCommentsException.class, () -> {
            commentService.getPostComments(null);
        });
    }

    @Test
    public void testGetPostCommentsValidNoComments() {
        try {
            ArrayList<Comment> actual = commentService.getPostComments(post);
            ArrayList<Comment> expected = new ArrayList<>();
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail("Failed to get post comments");
        }
    }
}
