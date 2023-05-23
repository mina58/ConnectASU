package com.ConnectASU.DAOTests;

import com.ConnectASU.DAO.GroupDAO;
import com.ConnectASU.DAO.UserDAO;
import com.ConnectASU.DAO.DBUtilities.DBSequenceResetter;
import com.ConnectASU.entities.Group;
import com.ConnectASU.entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static com.ConnectASU.DAO.DBUtilities.DatabaseTableNames.GROUP_TABLE;
import static org.junit.jupiter.api.Assertions.*;

class GroupDAOTest {
    private static GroupDAO groupDAO;
    private static UserDAO userDAO;
    private static final String testGroupName1 = "Test Group 1";
    private static final String testGroupName2 = "Test Group 2";
    private static final String testUserEmail1 = "john@test.com";
    private static final String testUserName1 = "john";
    private static final String testUserPassword1 = "123456";
    private static final String testUserEmail2 = "mina@test.com";
    private static final String testUserName2 = "mina";
    private static final String testUserPassword2 = "123456";

    @BeforeAll
    public static void setup() {
        groupDAO = new GroupDAO();
        userDAO = new UserDAO();
    }

    @BeforeEach
    public void setupEach() {
        DBSequenceResetter.resetDBSequence(GROUP_TABLE);
        try {
            userDAO.createUser(testUserEmail1, testUserName1, testUserPassword1);
            userDAO.createUser(testUserEmail2, testUserName2, testUserPassword2);
        } catch (SQLException e) {
            fail("Failed to insert the user, method thrown an exception: " + e.getMessage());
        }
    }

    @AfterEach
    public void tearDownEach() {
        try {
            userDAO.deleteUserByEmail(testUserEmail1);
            userDAO.deleteUserByEmail(testUserEmail2);
        } catch (SQLException e) {
            fail("Failed to delete the user, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testCreateGroupValid() {
        try {
            assertNotNull(groupDAO.createGroup(testGroupName1, testUserEmail1));
        } catch (SQLException e) {
            fail("Failed to create the group, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testCreateGroupInvalidAdminNotExists() {
        assertThrows(SQLException.class, () -> groupDAO.createGroup(testGroupName1, "XXXXXXXXXXXXXXXXXX"));
    }

    @Test
    public void testGetGroupByIDValid() {
        try {
            groupDAO.createGroup(testGroupName1, testUserEmail1);
            Group expected = new Group(1, testGroupName1);
            Group actual = groupDAO.getGroupByID(1);
            assertEquals(expected, actual);
        } catch (SQLException e) {
            fail("Failed to get the group, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testGetGroupByIDGroupNotExists() {
        try {
            assertNull(groupDAO.getGroupByID(-1));
        } catch (SQLException e) {
            fail("Failed to get the group, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testGetGroupSearchByName() {
        try {
            groupDAO.createGroup(testGroupName1, testUserEmail1);
            groupDAO.createGroup(testGroupName2, testUserEmail1);
            ArrayList<Group> actual = groupDAO.getGroupSearchByName("Group");
            Group expected1 = new Group(1, testGroupName1);
            Group expected2 = new Group(2, testGroupName2);
            ArrayList<Group> expected = new ArrayList<>();
            expected.add(expected1);
            expected.add(expected2);
            assertEquals(expected, actual);
        } catch (SQLException e) {
            fail("Failed to get the group, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testGetGroupSearchByNameNoResults() {
        try {
            groupDAO.createGroup(testGroupName1, testUserEmail1);
            groupDAO.createGroup(testGroupName2, testUserEmail1);
            ArrayList<Group> actual = groupDAO.getGroupSearchByName("XXXXXXXXXXXXXXXXXX");
            assertTrue(actual.isEmpty());
        } catch (SQLException e) {
            fail("Failed to get the group, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteGroupByIDValid() {
        try {
            groupDAO.createGroup(testGroupName1, testUserEmail1);
            assertTrue(groupDAO.deleteGroupByID(1));
        } catch (SQLException e) {
            fail("Failed to delete the group, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteGroupByIDGroupNotExists() {
        try {
            assertFalse(groupDAO.deleteGroupByID(-1));
        } catch (SQLException e) {
            fail("Failed to delete the group, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testAddMember() {
        try {
            groupDAO.createGroup(testGroupName1, testUserEmail1);
            assertTrue(groupDAO.addMember(1, testUserEmail1));
        } catch (SQLException e) {
            fail("Failed to add the member, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testAddMemberInvalidGroupNotExists() {
        assertThrows(SQLException.class, () -> {
            groupDAO.addMember(-1, testUserEmail1);
        });
    }

    @Test
    public void testAddMemberInvalidMemberNotExists() {
        assertThrows(SQLException.class, () -> {
            groupDAO.createGroup(testGroupName1, "XXXXX");
        });
    }

    @Test
    public void testRemoveMemberValid() {
        try {
            groupDAO.createGroup(testGroupName1, testUserEmail1);
            groupDAO.addMember(1, testUserEmail1);
            assertTrue(groupDAO.removeMember(1, testUserEmail1));
        } catch (SQLException e) {
            fail("Failed to remove the member, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testRemoveMemberInvalidGroupNotExists() {
        try {
            assertFalse(groupDAO.removeMember(-1, testUserEmail1));
        } catch (SQLException e) {
            fail("Failed to remove the member, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testRemoveMemberInvalidMemberNotExists() {
        try {
            groupDAO.createGroup(testGroupName1, testUserEmail1);
            assertFalse(groupDAO.removeMember(1, "XXXXX"));
        } catch (SQLException e) {
            fail("Failed to remove the member, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testGetGroupMembersValid() {
        try {
            groupDAO.createGroup(testGroupName1, testUserEmail1);
            groupDAO.addMember(1, testUserEmail1);
            groupDAO.addMember(1, testUserEmail2);
            ArrayList<User> actual = groupDAO.getGroupMembers(1);
            ArrayList<User> expected = new ArrayList<>();
            User expected1 = new User(testUserName1, testUserEmail1, testUserPassword1);
            User expected2 = new User(testUserName2, testUserEmail2, testUserPassword2);
            expected.add(expected1);
            expected.add(expected2);
            assertEquals(expected, actual);
        } catch (SQLException e) {
            fail("Failed to get the group members, method thrown an exception: " + e.getMessage());
        }
    }

    @Test
    public void testGetGroupMembersInvalidGroupNotExists() {
        try {
            ArrayList<User> actual = groupDAO.getGroupMembers(-1);
            assertTrue(actual.isEmpty());
        } catch (SQLException e) {
            fail("Failed to get the group members, method thrown an exception: " + e.getMessage());
        }
    }
}
