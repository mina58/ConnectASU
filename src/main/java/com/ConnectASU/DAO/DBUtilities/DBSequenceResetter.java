package com.ConnectASU.DAO.DBUtilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBSequenceResetter {
    public static void resetDBSequence(String tableName){
        try {
            Connection connection = DBConnectionManager.getConnection();
            String sql = "DELETE FROM sqlite_sequence WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, tableName);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
