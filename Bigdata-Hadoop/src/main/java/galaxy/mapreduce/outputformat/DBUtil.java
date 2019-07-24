package galaxy.mapreduce.outputformat;

import java.sql.*;
import java.util.Map;

public class DBUtil {
    public static void load(Map<String, String> ruleMap) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            DriverManager.getConnection("jdbc:mysql://192.8.0.11:3306/hadoop", "galaxy", "Galaxy123.00");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select url,content from t_rule");

            while (resultSet.next()) {
                String url = resultSet.getString(1);
                String content = resultSet.getString(2);
                ruleMap.put(url, content);
            }
        } catch (Exception e) {

        } finally {
            close(resultSet);
            close(statement);
            close(connection);
        }

    }

    private static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
