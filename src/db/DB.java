package db;

import java.sql.*;

public class DB {
    final static String url = "jdbc:mysql://localhost:3306/product_dao";
    final static String user = "alcides";
    final static String password = "Junit03.";

    public static Connection connection() {
        Connection connection = null;
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                throw new DBException(e.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DBException(e.getMessage());
            }
        } else {
            throw new DBException("[ERROR] an error unexpected happened!");
        }
    }

    public static void closePreparedStatement(PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DBException(e.getMessage());
            }
        } else {
            throw new DBException("[ERROR] an error unexpected happened!");
        }
    }

    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new DBException(e.getMessage());
            }
        } else {
            throw new DBException("[ERROR] an error unexpected happened!");
        }
    }
}
