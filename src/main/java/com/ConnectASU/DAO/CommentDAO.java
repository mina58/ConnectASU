package com.ConnectASU.DAO;

import com.ConnectASU.DAO.DBUtilities.DBConnectionManager;
import com.ConnectASU.entities.Comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.ConnectASU.DAO.DBUtilities.DatabaseTableNames.COMMENT_TABLE;

public class CommentDAO {
    public Comment createComment(String commentContent, int postID, String authorEmail) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        Comment comment = null;
        ResultSet generatedKeys = null;

        try {
            connection = DBConnectionManager.getConnection();
            String sql = "INSERT INTO " + COMMENT_TABLE + " (CommentContent, PostID, Author) VALUES (?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, commentContent);
            statement.setInt(2, postID);
            statement.setString(3, authorEmail);
            statement.executeUpdate();
            generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int commentID = generatedKeys.getInt(1);
                comment = new Comment(commentID, commentContent, authorEmail);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to create comment", e);
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (generatedKeys != null) {
                generatedKeys.close();
            }
            DBConnectionManager.closeConnection(connection);
        }

        return comment;
    }

    public Comment getCommentByID(int commentID) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Comment comment = null;

        try {
            connection = DBConnectionManager.getConnection();
            String sql = "SELECT * FROM " + COMMENT_TABLE + " WHERE CommentID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, commentID);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String content = resultSet.getString(2);
                int postID = resultSet.getInt(3);
                String authorEmail = resultSet.getString(4);
                comment = new Comment(commentID, content, authorEmail);
            }

        } catch (SQLException e) {
            throw new SQLException("Failed to get comment by ID", e);
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
            DBConnectionManager.closeConnection(connection);
        }

        return comment;
    }

    public ArrayList<Comment> getCommentsByPostID(int postID) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<Comment> comments = null;

        try {
            connection = DBConnectionManager.getConnection();
            String sql = "SELECT * FROM " + COMMENT_TABLE + " WHERE PostID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, postID);
            resultSet = statement.executeQuery();
            comments = new ArrayList<>();
            while (resultSet.next()) {
                int commentID = resultSet.getInt(1);
                String content = resultSet.getString(2);
                String authorEmail = resultSet.getString(4);
                Comment comment = new Comment(commentID, content, authorEmail);
                comments.add(comment);
            }
        } catch (SQLException e) {
            throw new SQLException("Failed to get comments by post ID", e);
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
            DBConnectionManager.closeConnection(connection);
        }

        return comments;
    }

    public boolean deleteCommentByID(int commentID) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsAffected = 0;

        try {
            connection = DBConnectionManager.getConnection();
            String sql = "DELETE FROM " + COMMENT_TABLE + " WHERE CommentID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, commentID);
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Failed to delete comment by ID", e);
        } finally {
            if (statement != null) {
                statement.close();
            }
            DBConnectionManager.closeConnection(connection);
        }

        return rowsAffected == 1;
    }
}
