package application;


import java.sql.*;

public class ConnectDB {

    // Connect to your database.
    // Replace server name, username, and password with your credentials
    public static void main(String[] args) {
        String connectionUrl =
                "jdbc:sqlserver://w1-sqldiatest1:1433;"
                        + "database=Test72;"
                        + "user=dca;"
                        + "password=1;"
                        + "encrypt=false;"
                        + "trustServerCertificate=false;"
                        + "loginTimeout=30;";

        ResultSet resultSet = null;

        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {

            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT TOP 10 InstitutionID, Name from dbo.tInstitution";
            resultSet = statement.executeQuery(selectSql);

            // Print results from select statement
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}