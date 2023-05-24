package com.ConnectASU.DAO;

import com.ConnectASU.DAO.DBUtilities.DBConnectionManager;
import com.ConnectASU.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.ConnectASU.DAO.DBUtilities.DatabaseTableNames.USER_TABLE;

public class UserDAO {
    public boolean createUser(String email, String name, String password) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsAffected = 0;

        try {
            connection = DBConnectionManager.getConnection();
            String sql = "INSERT INTO " + USER_TABLE + " (Email, Name, Password) VALUES (?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, name);
            statement.setString(3, password);
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to add user", e);
        } finally {
            if (statement != null)
                statement.close();
            DBConnectionManager.closeConnection(connection);
        }
        return rowsAffected == 1;
    }

    public User getUserByEmail(String email) throws SQLException {
        User user = null;
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement statement = null;

        try {
            connection = DBConnectionManager.getConnection();
            String sql = "SELECT * FROM " + USER_TABLE + " WHERE Email = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString(2);
                String password = resultSet.getString(3);
                user = new User(name, email, password);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve user by mail.", e);
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
            DBConnectionManager.closeConnection(connection);
        }

        return user;
    }

    public boolean deleteUserByEmail(String email) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsAffected = 0;

        try {
            connection = DBConnectionManager.getConnection();
            String sql = "DELETE FROM " + USER_TABLE + " WHERE Email = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to delete user by email.", e);
        } finally {
            // Close the resources in reverse order to avoid resource leaks
            if (statement != null) {
                statement.close();
            }
            DBConnectionManager.closeConnection(connection);
        }
        return rowsAffected == 1;
    }

    public ArrayList<User> getUserSearchByName(String name) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<User> users = null;

        try {
            connection = DBConnectionManager.getConnection();
            String sql = "SELECT * FROM " + USER_TABLE + " WHERE Name LIKE ? ORDER BY Name";
            statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + name + "%");
            resultSet = statement.executeQuery();
            users = new ArrayList<User>();
            while (resultSet.next()) {
                String currentEmail = resultSet.getString(1);
                String currentName = resultSet.getString(2);
                String currentPassword = resultSet.getString(3);
                users.add(new User(currentName, currentEmail, currentPassword));
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve users by name.", e);
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
            DBConnectionManager.closeConnection(connection);
        }

        return users;
    }

    public boolean followUser(String followerEmail, String followeeEmail) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsAffected = 0;

        try {
            connection = DBConnectionManager.getConnection();
            String sql = "INSERT INTO Follow (Follower, Followee) VALUES (?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, followerEmail);
            statement.setString(2, followeeEmail);
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to follow user.", e);
        } finally {
            if (statement != null) {
                statement.close();
            }
            DBConnectionManager.closeConnection(connection);
        }
        return rowsAffected == 1;
    }

    public boolean unfollowUser(String followerEmail, String followeeEmail) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsAffected = 0;

        try {
            connection = DBConnectionManager.getConnection();
            String sql = "DELETE FROM Follow WHERE Follower = ? AND Followee = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, followerEmail);
            statement.setString(2, followeeEmail);
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to unfollow user.", e);
        } finally {
            if (statement != null) {
                statement.close();
            }
            DBConnectionManager.closeConnection(connection);
        }
        return rowsAffected == 1;
    }

    public ArrayList<User> getUserFollowersByEmail(String email) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<User> followers = null;

        try {
            connection = DBConnectionManager.getConnection();
            String sql = "SELECT * FROM Follow WHERE Followee = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            followers = new ArrayList<User>();
            while (resultSet.next()) {
                String currentFollowerEmail = resultSet.getString(1);
                String currentFolloweeEmail = resultSet.getString(2);
                User user = getUserByEmail(currentFollowerEmail);
                followers.add(user);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to retrieve followers by email.", e);
        }

        return followers;
    }
}
