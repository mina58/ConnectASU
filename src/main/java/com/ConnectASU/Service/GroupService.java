package com.ConnectASU.Service;

import com.ConnectASU.DAO.GroupDAO;
import com.ConnectASU.entities.Group;
import com.ConnectASU.entities.User;
import com.ConnectASU.exceptions.CannotGetMembersException;
import com.ConnectASU.exceptions.CannotJoinGroupException;
import com.ConnectASU.exceptions.CannotLeaveGroupException;
import com.ConnectASU.exceptions.InvalidGroupNameException;

import java.sql.SQLException;
import java.util.ArrayList;

public class GroupService {
    private static final GroupService instance = new GroupService();

    private GroupService() {
    }

    public static GroupService getInstance() {
        return instance;
    }

    public synchronized Group createGroup(User admin, String groupName) throws InvalidGroupNameException {
        validateGroupName(groupName);
        Group group = null;
        try {
            GroupDAO groupDAO = new GroupDAO();
            if (admin != null)
                group = groupDAO.createGroup(groupName, admin.getEmail());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            joinGroup(admin, group);
        } catch (CannotJoinGroupException e) {
            e.printStackTrace();
        }
        return group;
    }

    public void validateGroupName(String groupName) throws InvalidGroupNameException {
        if (groupName.length() == 0) {
            throw new InvalidGroupNameException();
        }
    }

    public synchronized void deleteGroup(Group group) {
        try {
            GroupDAO groupDAO = new GroupDAO();
            if (group != null)
                groupDAO.deleteGroupByID(group.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void joinGroup(User user, Group group) throws CannotJoinGroupException {
        try {
            GroupDAO groupDAO = new GroupDAO();
            if (user == null || group == null || !groupDAO.addMember(group.getId(), user.getEmail()))
                throw new CannotJoinGroupException();
        } catch (SQLException e) {
            throw new CannotJoinGroupException();
        }
    }

    public void leaveGroup(User user, Group group) throws CannotLeaveGroupException {
        try {
            GroupDAO groupDAO = new GroupDAO();
            if (user == null || group == null || !groupDAO.removeMember(group.getId(), user.getEmail()))
                throw new CannotLeaveGroupException();
        } catch (SQLException e) {
            throw new CannotLeaveGroupException();
        }
    }

    public ArrayList<User> getGroupMembers(Group group) throws CannotGetMembersException {
        if (group == null) {
            throw new CannotGetMembersException();
        }
        try {
            GroupDAO groupDAO = new GroupDAO();
            ArrayList<User> groupMembers = groupDAO.getGroupMembers(group.getId());
            if (groupMembers.isEmpty()) {
                throw new CannotGetMembersException();
            }
            return groupMembers;
        } catch (SQLException e) {
            throw new CannotGetMembersException();
        }
    }
}
