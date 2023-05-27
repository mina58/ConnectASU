package com.ConnectASU.DAO.DBUtilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBClearer {
    public static void clearDB(){
        try {
            Connection connection = DBConnectionManager.getConnection();
            String sql = "DELETE FROM USER";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
