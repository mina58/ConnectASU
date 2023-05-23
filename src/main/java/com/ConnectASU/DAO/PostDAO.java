package com.ConnectASU.DAO;

import com.ConnectASU.DAO.DBUtilities.DBConnectionManager;
import com.ConnectASU.entities.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.ConnectASU.DAO.DBUtilities.DatabaseTableNames.LIKES_TABLE;
import static com.ConnectASU.DAO.DBUtilities.DatabaseTableNames.POST_TABLE;

public class PostDAO {
    public boolean createPost(String content, String userEmail, int groupId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsAffected = 0;

        try {
            connection = DBConnectionManager.getConnection();
            String sql = "INSERT INTO " + POST_TABLE + " (PostContent, Author, GroupID)  VALUES (?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, content);
            statement.setString(2, userEmail);
            if (groupId == 0) {
                statement.setNull(3, java.sql.Types.INTEGER);
            } else {
                statement.setInt(3, groupId);
            }
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to create post", e);
        } finally {
            if (statement != null) {
                statement.close();
            }
            DBConnectionManager.closeConnection(connection);
        }
        return rowsAffected == 1;
    }

    public boolean deletePostByID(int postId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsAffected = 0;

        try {
            connection = DBConnectionManager.getConnection();
            String sql = "DELETE FROM " + POST_TABLE + " WHERE PostID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, postId);
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to delete post by ID", e);
        } finally {
            if (statement != null) {
                statement.close();
            }
            DBConnectionManager.closeConnection(connection);
        }
        return rowsAffected == 1;
    }

    public Post getPostByID(int postId) throws SQLException {
        Post post = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnectionManager.getConnection();
            String sql = "SELECT * FROM " + POST_TABLE + " WHERE PostID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, postId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String content = resultSet.getString(2);
                String author = resultSet.getString(3);
                post = new Post(postId, content, author);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get post by ID", e);
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
            DBConnectionManager.closeConnection(connection);
        }

        return post;
    }

    public ArrayList<Post> getUserPosts(String userEmail) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<Post> posts = null;

        try {
            connection = DBConnectionManager.getConnection();
            String sql = "SELECT * FROM " + POST_TABLE + " WHERE Author = ? ORDER BY PostID";
            statement = connection.prepareStatement(sql);
            statement.setString(1, userEmail);
            resultSet = statement.executeQuery();
            posts = new ArrayList<Post>();
            while (resultSet.next()) {
                int postId = resultSet.getInt(1);
                String content = resultSet.getString(2);
                String author = resultSet.getString(3);
                posts.add(new Post(postId, content, author));
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get user posts", e);
        } finally {
            if (statement != null) {
                statement.close();
            }

            if (resultSet != null) {
                resultSet.close();
            }

            DBConnectionManager.closeConnection(connection);
        }

        return posts;
    }

    public boolean addPostLike(int postID, String userEmail) throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsAffected = 0;

        try {
            connection = DBConnectionManager.getConnection();
            String sql = "INSERT INTO " + LIKES_TABLE + " (PostID, UserEmail) VALUES (?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, postID);
            statement.setString(2, userEmail);
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to add post like", e);
        } finally {
            if (statement != null) {
                statement.close();
            }
            DBConnectionManager.closeConnection(connection);
        }
        return rowsAffected == 1;
    }

    public ArrayList<Post> getGroupPosts(int groupId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<Post> posts = null;

        try {
            connection = DBConnectionManager.getConnection();
            String sql = "SELECT * FROM " + POST_TABLE + " WHERE GroupID = ? ORDER BY PostID";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, groupId);
            resultSet = statement.executeQuery();
            posts = new ArrayList<Post>();
            while (resultSet.next()) {
                int postId = resultSet.getInt(1);
                String content = resultSet.getString(2);
                String author = resultSet.getString(3);
                posts.add(new Post(postId, content, author));
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get group posts", e);
        } finally {
            if (statement != null) {
                statement.close();
            }

            if (resultSet != null) {
                resultSet.close();
            }

            DBConnectionManager.closeConnection(connection);
        }

        return posts;
    }
}
