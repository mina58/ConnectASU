package com.ConnectASU.ServiceTests;

import com.ConnectASU.DAO.DBUtilities.DBSequenceResetter;
import com.ConnectASU.DAO.GroupDAO;
import com.ConnectASU.DAO.PostDAO;
import com.ConnectASU.DAO.UserDAO;
import com.ConnectASU.Service.SearchService;
import com.ConnectASU.entities.Group;
import com.ConnectASU.entities.User;
import com.ConnectASU.exceptions.InvalidSearchException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.ConnectASU.DAO.DBUtilities.DatabaseTableNames.GROUP_TABLE;
import static com.ConnectASU.DAO.DBUtilities.DatabaseTableNames.POST_TABLE;
import static org.junit.jupiter.api.Assertions.*;

public class SearchServiceTest {
    private SearchService searchService;
    private static PostDAO postDAO;
    private static UserDAO userDAO;
    private static GroupDAO groupDAO;
    private static final String validEmail1 = "john@test.com";
    private static final String validPassword1 = "12345678";
    private static final String validName1 = "Malak";
    private static final String validEmail2 = "mina@test.com";
    private static final String validPassword2 = "12345678";
    private static final String validName2 = "Marita";
    private static final String validGroupName1 = "group1";
    private static final String validGroupName2 = "group2";
    private static final String validGroupName3 = "group3";
    private static final String validEmail3 = "ahmed@mohsen.com";
    private static final String validName3 = "Mina";
    private static final String validPassword3 = "XXXXXXXX";
    private static final String postContent1 = "This is a post";
    private static final String postContent2 = "This is another post";
    private static final String postContent3 = "This is a third post";

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
            userDAO.createUser(validEmail1, validName1, validPassword1);
            userDAO.createUser(validEmail2, validName2, validPassword2);
            userDAO.createUser(validEmail3, validName3, validPassword3);
            groupDAO.createGroup(validGroupName1, validEmail1);
            groupDAO.createGroup(validGroupName2, validEmail2);
            groupDAO.createGroup(validGroupName3, validEmail3);
            postDAO.createPost(postContent1, validEmail1, 0);
            postDAO.createPost(postContent2, validEmail2, 0);
            postDAO.createPost(postContent3, validEmail3, 0);
            searchService = SearchService.getInstance();
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
            fail("Failed to clean up test");
        }
    }

    @Test
    public void testSearchUserValid1() {
        try {
            ArrayList<User> actual = searchService.searchUser("m");
            User expected1 = new User(validName1, validEmail1, validPassword1);
            User expected2 = new User(validName2, validEmail2, validPassword2);
            User expected3 = new User(validName3, validEmail3, validPassword3);
            ArrayList<User> expected = new ArrayList<>();
            expected.add(expected1);
            expected.add(expected2);
            expected.add(expected3);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail("Failed to search user");
        }
    }

    @Test
    public void testSearchUserValid2() {
        try {
            ArrayList<User> actual = searchService.searchUser("ma");
            User expected1 = new User(validName1, validEmail1, validPassword1);
            User expected2 = new User(validName2, validEmail2, validPassword2);
            ArrayList<User> expected = new ArrayList<>();
            expected.add(expected1);
            expected.add(expected2);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail("Failed to search user");
        }
    }

    @Test
    public void testSearchUserValid3() {
        try {
            try {
                ArrayList<User> actual = searchService.searchUser("jo");
                User expected1 = new User(validEmail1, validName1, validPassword1);
                User expected2 = new User(validEmail2, validName2, validPassword2);
                ArrayList<User> expected = new ArrayList<>();
                assertEquals(expected, actual);
            } catch (Exception e) {
                fail("Failed to search user");
            }
        } catch (Exception e) {
            fail("Failed to search user");
        }
    }

    @Test
    public void testSearchUserInvalidEmptyString() {
        assertThrows(InvalidSearchException.class, () -> searchService.searchUser(""));
    }

    @Test
    public void testSearchUserInvalidNullString() {
        assertThrows(InvalidSearchException.class, () -> searchService.searchUser(null));
    }

    ;

    @Test
    public void testSearchGroupValid1() {
        try {
            ArrayList<Group> actual = searchService.searchGroup("group");
            ArrayList<Group> expected = new ArrayList<>();
            Group group1 = new Group(1, validGroupName1);
            Group group2 = new Group(2, validGroupName2);
            Group group3 = new Group(3, validGroupName3);
            expected.add(group1);
            expected.add(group2);
            expected.add(group3);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail("Failed to search group");
        }
    }

    @Test
    public void testSearchGroupValid2() {
        try {
            ArrayList<Group> actual = searchService.searchGroup("group1");
            ArrayList<Group> expected = new ArrayList<>();
            Group group1 = new Group(1, validGroupName1);
            expected.add(group1);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail("Failed to search group");
        }
    }

    @Test
    public void testSearchGroupValid3() {
        try {
            ArrayList<Group> actual = searchService.searchGroup("groupp1");
            ArrayList<Group> expected = new ArrayList<>();
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail("Failed to search group");
        }
    }

    @Test
    public void testSearchGroupInvalidEmptyString() {
        assertThrows(InvalidSearchException.class, () -> searchService.searchGroup(""));
    }

    @Test
    public void testSearchGroupInvalidNullString() {
        assertThrows(InvalidSearchException.class, () -> searchService.searchGroup(null));
    }
}
