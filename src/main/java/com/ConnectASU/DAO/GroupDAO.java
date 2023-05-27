package com.ConnectASU.DAO;

import com.ConnectASU.DAO.DBUtilities.DBConnectionManager;
import com.ConnectASU.entities.Group;
import com.ConnectASU.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.ConnectASU.DAO.DBUtilities.DatabaseTableNames.*;

public class GroupDAO {
    public synchronized Group createGroup(String groupName, String adminEmail) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;
        Group group = null;

        try {
            connection = DBConnectionManager.getConnection();
            String sql = "INSERT INTO \"" + GROUP_TABLE + "\" (GroupName, Admin) VALUES (?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, groupName);
            statement.setString(2, adminEmail);
            statement.executeUpdate();
            generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int groupId = generatedKeys.getInt(1);
                group = new Group(groupId, groupName);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to add group", e);
        } finally {
            if (statement != null)
                statement.close();
            if (generatedKeys != null)
                generatedKeys.close();
            DBConnectionManager.closeConnection(connection);
        }

        return group;
    }

    public Group getGroupByID(int groupId) throws SQLException {
        Group group = null;
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement statement = null;

        try {
            connection = DBConnectionManager.getConnection();
            String sql = "SELECT * FROM \"" + GROUP_TABLE + "\" WHERE GroupID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, groupId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String groupName = resultSet.getString(2);
                group = new Group(groupId, groupName);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get group by ID", e);
        } finally {
            if (statement != null)
                statement.close();
            if (resultSet != null)
                resultSet.close();
            DBConnectionManager.closeConnection(connection);
        }
        return group;
    }

    public synchronized boolean deleteGroupByID(int groupId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowAffected = 0;

        try {
            connection = DBConnectionManager.getConnection();
            String sql = "DELETE FROM \"" + GROUP_TABLE + "\" WHERE GroupID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, groupId);
            rowAffected = statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to delete group by ID", e);
        } finally {
            if (statement != null)
                statement.close();
            DBConnectionManager.closeConnection(connection);
        }

        return rowAffected == 1;
    }

    public synchronized boolean addMember(int groupID, String userEmail) throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        int rowAffected = 0;

        try {
            connection = DBConnectionManager.getConnection();
            String sql = "INSERT INTO " + JOINS_TABLE + " (UserEmail, GroupID) VALUES (?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, userEmail);
            statement.setInt(2, groupID);
            rowAffected = statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to add member to group", e);
        } finally {
            if (statement != null)
                statement.close();
            DBConnectionManager.closeConnection(connection);
        }
        return rowAffected == 1;
    }

    public synchronized boolean removeMember(int groupID, String userEmail) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowAffected = 0;

        try {
            connection = DBConnectionManager.getConnection();
            String sql = "DELETE FROM " + JOINS_TABLE + " WHERE UserEmail = ? AND GroupID = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, userEmail);
            statement.setInt(2, groupID);
            rowAffected = statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to remove member from group", e);
        } finally {
            if (statement != null)
                statement.close();
            DBConnectionManager.closeConnection(connection);
        }
        return rowAffected == 1;
    }

    public ArrayList<Group> getGroupSearchByName(String name) throws SQLException {
        ArrayList<Group> groups = null;
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement statement = null;

        try {
            connection = DBConnectionManager.getConnection();
            String sql = "SELECT * FROM \"" + GROUP_TABLE + "\" WHERE GroupName LIKE ? ORDER BY GroupID";
            statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + name + "%");
            resultSet = statement.executeQuery();
            groups = new ArrayList<Group>();
            while (resultSet.next()) {
                int groupId = resultSet.getInt(1);
                String groupName = resultSet.getString(2);
                groups.add(new Group(groupId, groupName));
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get groups by name", e);
        } finally {
            if (statement != null)
                statement.close();
            if (resultSet != null)
                resultSet.close();
            DBConnectionManager.closeConnection(connection);
        }
        return groups;
    }

    public ArrayList<User> getGroupMembers(int groupId) throws SQLException {
        ArrayList<User> users = null;
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement statement = null;

        try {
            connection = DBConnectionManager.getConnection();
            String sql = "SELECT User.Email, User.Name, User.Password FROM " + JOINS_TABLE +
                    " INNER JOIN " + USER_TABLE + " ON Joins.UserEmail = User.Email WHERE GroupID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, groupId);
            resultSet = statement.executeQuery();
            users = new ArrayList<User>();
            while (resultSet.next()) {
                String email = resultSet.getString(1);
                String name = resultSet.getString(2);
                String password = resultSet.getString(3);
                users.add(new User(name, email, password));
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get group members", e);
        } finally {
            if (statement != null)
                statement.close();
            if (resultSet != null)
                resultSet.close();
            DBConnectionManager.closeConnection(connection);
        }
        return users;
    }
}
