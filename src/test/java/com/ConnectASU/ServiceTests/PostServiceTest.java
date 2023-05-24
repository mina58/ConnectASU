package com.ConnectASU.ServiceTests;

import com.ConnectASU.DAO.DBUtilities.DBSequenceResetter;
import com.ConnectASU.DAO.GroupDAO;
import com.ConnectASU.DAO.PostDAO;
import com.ConnectASU.DAO.UserDAO;
import com.ConnectASU.Service.PostService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.ConnectASU.DAO.DBUtilities.DatabaseTableNames.GROUP_TABLE;
import static com.ConnectASU.DAO.DBUtilities.DatabaseTableNames.POST_TABLE;
import static org.junit.jupiter.api.Assertions.fail;

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
    private static final String invalidEmail = "ahmed@mohsen.com";
    private static final String invalidName = "ahmed";
    private static final String invalidPassword = "XXXXXXXX";
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
            groupDAO.createGroup(validGroupName1, validEmail1);
            groupDAO.addMember(1, validEmail2);
        } catch (Exception e) {
            fail("Failed to initialize test");
        }
    }


}
