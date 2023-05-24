package com.ConnectASU.ServiceTests;

import com.ConnectASU.DAO.DBUtilities.DBSequenceResetter;
import com.ConnectASU.DAO.GroupDAO;
import com.ConnectASU.DAO.PostDAO;
import com.ConnectASU.DAO.UserDAO;
import com.ConnectASU.Service.PostService;
import com.ConnectASU.Service.UserService;
import com.ConnectASU.entities.Group;
import com.ConnectASU.entities.Post;
import com.ConnectASU.entities.User;
import com.ConnectASU.exceptions.CannotCreatePostException;
import com.ConnectASU.exceptions.CannotGetFeedException;
import com.ConnectASU.exceptions.CannotGetGroupPostsException;
import com.ConnectASU.exceptions.CannotGetUserPostsException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.ConnectASU.DAO.DBUtilities.DatabaseTableNames.GROUP_TABLE;
import static com.ConnectASU.DAO.DBUtilities.DatabaseTableNames.POST_TABLE;
import static org.junit.jupiter.api.Assertions.*;

public class PostServiceTest {
    private PostService postService;
    private static PostDAO postDAO;
    private static UserDAO userDAO;
    private static GroupDAO groupDAO;
    private static final String validEmail1 = "john@test.com";
    private static final String validPassword1 = "12345678";
    private static final String validName1 = "John";
    private static final String validEmail2 = "mina@test.com";
    private static final String validPassword2 = "12345678";
    private static final String validName2 = "Mina";
    private static final String validGroupName1 = "group1";
    private static final String validEmail3 = "ahmed@mohsen.com";
    private static final String validName3 = "ahmed";
    private static final String validPassword3 = "XXXXXXXX";
    private static final String postContent1 = "This is a post";
    private static final String postContent2 = "This is another post";
    private static final String postContent3 = "This is a third post";

    @BeforeAll
    public static void setUp() {
        postDAO = new PostDAO();
        userDAO = new UserDAO();
        groupDAO = new GroupDAO();
    }

    @BeforeEach
    public void setUpEach() {
        postService = PostService.getInstance();
        DBSequenceResetter.resetDBSequence(POST_TABLE);
        DBSequenceResetter.resetDBSequence(GROUP_TABLE);
        try {
            userDAO.createUser(validEmail1, validPassword1, validName1);
            userDAO.createUser(validEmail2, validPassword2, validName2);
            userDAO.createUser(validEmail3, validPassword3, validName3);
            groupDAO.createGroup(validGroupName1, validEmail1);
            groupDAO.addMember(1, validEmail2);
        } catch (Exception e) {
            fail("Failed to initialize test");
        }
    }

    @AfterEach
    public void tearDownEach() {
        try {
            userDAO.deleteUserByEmail(validEmail1);
            userDAO.deleteUserByEmail(validEmail2);
            userDAO.deleteUserByEmail(validEmail3);
        } catch (Exception e) {
            fail("Failed to tear down test");
        }
    }

    @Test
    public void testCreatePostValidNoGroup() {
        try {
            User user1 = new User(validName1, validEmail1, validPassword1);
            Post expected = postService.createPost(postContent1, user1, null);
            Post actual = postDAO.getPostByID(expected.getId());
            assert (expected.equals(actual));
        } catch (Exception e) {
            fail("Failed to create post");
        }
    }

    @Test
    public void testCreatePostValidWithGroup() {
        try {
            User user1 = new User(validName1, validEmail1, validPassword1);
            Group group = new Group(1, validGroupName1);
            Post expected = postService.createPost(postContent1, user1, group);
            Post actual = postDAO.getPostByID(expected.getId());
            assert (expected.equals(actual));
        } catch (Exception e) {
            fail("Failed to create post");
        }
    }

    @Test
    public void testCreatePostInvalidUserNotInGroup() {
        assertThrows(CannotCreatePostException.class, () -> {
            User user3 = new User(validName3, validEmail3, validPassword3);
            Group group = new Group(1, validGroupName1);
            postService.createPost(postContent1, user3, group);
        });
    }

    @Test
    public void testCreatePostInvalidUserNull() {
        assertThrows(CannotCreatePostException.class, () -> {
            postService.createPost(postContent1, null, null);
        });
    }

    @Test
    public void testCreatePostInvalidEmptyContent() {
        assertThrows(CannotCreatePostException.class, () -> {
            User user1 = new User(validName1, validEmail1, validPassword1);
            postService.createPost("", user1, null);
        });
    }

    @Test
    public void testGetUserPostsValid() {
        try {
            User user1 = new User(validName1, validEmail1, validPassword1);
            Post post1 = postService.createPost(postContent1, user1, null);
            Post post2 = postService.createPost(postContent2, user1, null);
            Post post3 = postService.createPost(postContent3, user1, null);
            ArrayList<Post> expected = new ArrayList<>();
            expected.add(post3);
            expected.add(post2);
            expected.add(post1);
            ArrayList<Post> actual = postService.getUserPosts(user1);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail("Failed to get user posts");
        }
    }

    @Test
    public void testGetUserPostsValidWithGroup() {
        try {
            User user1 = new User(validName1, validEmail1, validPassword1);
            Group group = new Group(1, validGroupName1);
            Post post1 = postService.createPost(postContent1, user1, group);
            Post post2 = postService.createPost(postContent2, user1, group);
            Post post3 = postService.createPost(postContent3, user1, group);
            ArrayList<Post> expected = new ArrayList<>();
            expected.add(post3);
            expected.add(post2);
            expected.add(post1);
            ArrayList<Post> actual = postService.getUserPosts(user1);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail("Failed to get user posts");
        }
    }

    @Test
    public void testGetUserPostsValidEmpty() {
        try {
            User user1 = new User(validName1, validEmail1, validPassword1);
            ArrayList<Post> expected = new ArrayList<>();
            ArrayList<Post> actual = postService.getUserPosts(user1);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail("Failed to get user posts");
        }
    }

    @Test
    public void testGetUserPostsInvalidUserNull() {
        assertThrows(CannotGetUserPostsException.class, () -> {
            postService.getUserPosts(null);
        });
    }

    @Test
    public void testGetUserPostsInvalidUser() {
        try {
            User user1 = new User("XXXXXXXX", "XXXXXXXX", "XXXXXXXX");
            ArrayList<Post> actual = postService.getUserPosts(user1);
            assertTrue(actual.isEmpty());
        } catch (Exception e) {
            fail("Failed to get user posts");
        }
    }

    @Test
    public void testGetGroupPostsValid() {
        try {
            User user1 = new User(validName1, validEmail1, validPassword1);
            Group group = new Group(1, validGroupName1);
            Post post1 = postService.createPost(postContent1, user1, group);
            Post post2 = postService.createPost(postContent2, user1, group);
            Post post3 = postService.createPost(postContent3, user1, group);
            ArrayList<Post> expected = new ArrayList<>();
            expected.add(post3);
            expected.add(post2);
            expected.add(post1);
            ArrayList<Post> actual = postService.getGroupPosts(group);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail("Failed to get group posts");
        }
    }

    @Test
    public void testGetGroupPostsInvalidGroupNull() {
        assertThrows(CannotGetGroupPostsException.class, () -> {
            postService.getGroupPosts(null);
        });
    }

    @Test
    public void testGetGroupPostsInvalidGroup() {
        try {
            User user1 = new User(validName1, validEmail1, validPassword1);
            Group group = new Group(2, "XXXXXXXX");
            ArrayList<Post> actual = postService.getGroupPosts(group);
            assertTrue(actual.isEmpty());
        } catch (Exception e) {
            fail("Failed to get group posts");
        }
    }

    @Test
    public void testGetGroupPostsValidEmpty() {
        try {
            User user1 = new User(validName1, validEmail1, validPassword1);
            Group group = new Group(1, validGroupName1);
            ArrayList<Post> expected = new ArrayList<>();
            ArrayList<Post> actual = postService.getGroupPosts(group);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail("Failed to get group posts");
        }
    }

    @Test
    public void testGetUserFeedValidNoUsersPosts() {
        try {
            User user1 = new User(validName1, validEmail1, validPassword1);
            User user2 = new User(validName2, validEmail2, validPassword2);
            UserService userService = UserService.getInstance();
            userService.followUser(user1, user2);
            postService.createPost(postContent1, user2, null);
            postService.createPost(postContent2, user2, null);
            postService.createPost(postContent3, user2, null);
            ArrayList<Post> expected = new ArrayList<>();
            expected.add(new Post(3, postContent3, user2.getName()));
            expected.add(new Post(2, postContent2, user2.getName()));
            expected.add(new Post(1, postContent1, user2.getName()));
            ArrayList<Post> actual = postService.getUserFeed(user1);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail("Failed to get user feed");
        }
    }

    @Test
    public void testGetUserFeedValidGroupPosts() {
        try {
            User user1 = new User(validName1, validEmail1, validPassword1);
            User user2 = new User(validName2, validEmail2, validPassword2);
            Group group = new Group(1, validGroupName1);
            Post post1 = postService.createPost(postContent1, user2, group);
            Post post2 = postService.createPost(postContent2, user2, group);
            Post post3 = postService.createPost(postContent3, user2, group);
            ArrayList<Post> expected = new ArrayList<>();
            expected.add(post3);
            expected.add(post2);
            expected.add(post1);
            ArrayList<Post> actual = postService.getUserFeed(user1);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail("Failed to get user feed");
        }
    }

    @Test
    public void testGetUserFeedValidUserPostsAndGroupPosts() {
        try {
            User user1 = new User(validName1, validEmail1, validPassword1);
            User user2 = new User(validName2, validEmail2, validPassword2);
            Group group = new Group(1, validGroupName1);
            Post post1 = postService.createPost(postContent1, user2, null);
            Post post2 = postService.createPost(postContent2, user2, group);
            Post post3 = postService.createPost(postContent3, user2, group);
            UserService userService = UserService.getInstance();
            userService.followUser(user1, user2);
            ArrayList<Post> expected = new ArrayList<>();
            expected.add(post1);
            expected.add(post3);
            expected.add(post2);
            ArrayList<Post> actual = postService.getUserFeed(user1);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail("Failed to get user feed");
        }
    }

    @Test
    public void testGetUserFeedInvalidUserNull() {
        assertThrows(CannotGetFeedException.class, () -> {
            postService.getUserFeed(null);
        });
    }

    @Test
    public void testGetUserFeedInvalidUser() {
        try {
            User user1 = new User("XXXXXXXX", "XXXXXXXX", "XXXXXXXX");
            ArrayList<Post> actual = postService.getUserFeed(user1);
            assertTrue(actual.isEmpty());
        } catch (Exception e) {
            fail("Failed to get user feed");
        }
    }

    @Test
    public void testGetUserFeedInvalidUserNoPosts() {
        try {
            User user1 = new User(validName1, validEmail1, validPassword1);
            ArrayList<Post> actual = postService.getUserFeed(user1);
            assertTrue(actual.isEmpty());
        } catch (Exception e) {
            fail("Failed to get user feed");
        }
    }

    @Test
    public void testLikePostValid() {
        try {
            User user1 = new User(validName1, validEmail1, validPassword1);
            Post post = postService.createPost(postContent1, user1, null);
            User user2 = new User(validName2, validEmail2, validPassword2);
            postService.likePost(user2, post);
            ArrayList<User> actual = postDAO.getPostLikesByID(post.getId());
            ArrayList<User> expected = new ArrayList<>();
            expected.add(user2);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail("Failed to like post");
        }
    }
}
