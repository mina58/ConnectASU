package com.ConnectASU.ServiceTests;

import com.ConnectASU.DAO.DBUtilities.DBSequenceResetter;
import com.ConnectASU.DAO.GroupDAO;
import com.ConnectASU.DAO.UserDAO;
import com.ConnectASU.Service.GroupService;
import com.ConnectASU.entities.Group;
import com.ConnectASU.entities.User;
import com.ConnectASU.exceptions.CannotJoinGroupException;
import com.ConnectASU.exceptions.CannotLeaveGroupException;
import com.ConnectASU.exceptions.InvalidGroupNameException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static com.ConnectASU.DAO.DBUtilities.DatabaseTableNames.GROUP_TABLE;
import static org.junit.jupiter.api.Assertions.*;

class GroupServiceTest {
    private GroupService groupService;
    private static GroupDAO groupDAO;
    private static UserDAO userDAO;
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

    @BeforeAll
    public static void setup() {
        groupDAO = new GroupDAO();
        userDAO = new UserDAO();
    }

    @BeforeEach
    public void setupEach() {
        groupService = GroupService.getInstance();
        try {
            userDAO.createUser(validEmail1, validName1, validPassword1);
            userDAO.createUser(validEmail2, validName2, validPassword2);
        } catch (SQLException e) {
            fail("Failed to create user");
        }
        DBSequenceResetter.resetDBSequence(GROUP_TABLE);
    }

    @AfterEach
    public void teardownEach() {
        try {
            userDAO.deleteUserByEmail(validEmail1);
            userDAO.deleteUserByEmail(validEmail2);
        } catch (SQLException e) {
            fail("Failed to delete user");
        }
    }

    @Test
    public void testCreateGroupValid() {
        try {
            User user = new User(validName1, validEmail1, validPassword1);
            Group actual = groupService.createGroup(user, validGroupName1);
            Group expected = new Group(1, validGroupName1);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail("Failed to create group");
        }
    }

    @Test
    public void testCreateGroupInvalidGroupName() {
        assertThrows(InvalidGroupNameException.class, () -> {
            User user = new User(validName1, validEmail1, validPassword1);
            groupService.createGroup(user, "");
        });
    }

    @Test
    public void deleteGroupValid() {
        try {
            User user = new User(validName1, validEmail1, validPassword1);
            Group group = groupService.createGroup(user, validGroupName1);
            groupService.deleteGroup(group);
            assertNull(groupDAO.getGroupByID(group.getId()));
        } catch (Exception e) {
            fail("Failed to delete group");
        }
    }

    @Test
    public void testJoinGroupValid() {
        try {
            User user = new User(validName1, validEmail1, validPassword1);
            Group group = groupService.createGroup(user, validGroupName1);
            User user2 = new User(validName2, validEmail2, validPassword2);
            groupService.joinGroup(user2, group);
            ArrayList<User> users = groupDAO.getGroupMembers(group.getId());
            assertTrue(users.contains(user2));
        } catch (Exception e) {
            fail("Failed to join group");
        }
    }

    @Test
    public void testJoinGroupInvalidGroup() {
        assertThrows(CannotJoinGroupException.class, () -> {
            User user = new User(validName1, validEmail1, validPassword1);
            Group group = new Group(1, "group1");
            groupService.joinGroup(user, group);
        });
    }

    @Test
    public void testJoinGroupInvalidUser() {
        assertThrows(CannotJoinGroupException.class, () -> {
            groupDAO.createGroup(validGroupName1, validEmail1);
            User user = new User(invalidName, invalidEmail, invalidPassword);
            Group group = new Group(1,  validGroupName1);
            groupService.joinGroup(user, group);
        });
    }

    @Test
    public void testLeaveGroupValid() {
        try {
            User user = new User(validName1, validEmail1, validPassword1);
            Group group = groupService.createGroup(user, validGroupName1);
            User user2 = new User(validName2, validEmail2, validPassword2);
            groupService.joinGroup(user2, group);
            groupService.leaveGroup(user2, group);
            ArrayList<User> users = groupDAO.getGroupMembers(group.getId());
            assertFalse(users.contains(user2));
        } catch (Exception e) {
            fail("Failed to leave group");
        }
    }

    @Test
    public void testLeaveGroupInvalidGroup() {
        assertThrows(CannotLeaveGroupException.class, () -> {
            User user = new User(validName1, validEmail1, validPassword1);
            Group group = new Group(1, "group1");
            groupService.leaveGroup(user, group);
        });
    }

    @Test
    public void testLeaveGroupInvalidUser() {
        assertThrows(CannotLeaveGroupException.class, () -> {
            groupDAO.createGroup(validGroupName1, validEmail1);
            User user = new User(invalidName, invalidEmail, invalidPassword);
            Group group = new Group(1, validGroupName1);
            groupService.leaveGroup(user, group);
        });
    }
}