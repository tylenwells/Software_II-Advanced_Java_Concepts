import java.sql.*;
import java.util.function.Function;


public class JDBCHelper {
    String className, URL, user, password;
    Connection connection;
    public JDBCHelper() {
        this.className = "com.mysql.jdbc.Driver";
        this.URL = "jdbc:mysql://3.227.166.251:3306/U07BDK?serverTimezone=UTC";
        this.user = "U07BDK";
        this.password = "53688976414";
        this.connection = null;
    }

    public void initConnection() {
        try {
            Class.forName(className);
        } catch (ClassNotFoundException ex) {
            System.out.println("Unable to load the class. Terminating the program");
            System.exit(-1);
        }
        try {
            connection = DriverManager.getConnection(URL, user, password);
            Statement stmt = connection.createStatement();
            stmt.executeQuery("SET time_zone = '+00:00'");
            ResultSet r = this.runQuery.apply("SELECT @@global.time_zone, @@session.time_zone;");
            while (r.next()){
                System.out.println("Global Time Zone: " + r.getObject(1).toString());
                System.out.println("Session Time Zone: " + r.getObject(2).toString());
            }
            System.out.println();
        } catch (SQLException ex) {
            System.out.println("Error getting connection: " + ex.getMessage());
            System.exit(-1);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            System.exit(-1);
        }
    }

    public void closeConnection(){
        try{
        connection.close();
    }
    catch(SQLException e){
            System.out.println("SQL ERROR: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean checkLogin(String s1, String s2) {
        ResultSet resultSet = null;
        String query = "SELECT COUNT(1) FROM user WHERE userName = '" + s1 + "' AND password = '" + s2 + "' LIMIT 1;";
        try {
            //executing query
            Statement stmt = connection.createStatement();
            resultSet = stmt.executeQuery(query);
            while(resultSet.next()){
                String string = resultSet.getObject(1).toString();
                if (string.equals("1")){
                    return true;
                };

            }

        }
        catch (SQLException ex)
        {
            System.out.println("Exception while executing statement. Terminating program... " + ex.getMessage());
        }
        catch (Exception ex)
        {
            System.out.println("General exception while executing query. Terminating the program..." + ex.getMessage());
        }
        return false;
    }

    Function<String, ResultSet> runQuery = (String s1) -> {
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(s1);
        }
        catch (SQLException ex)
        {
            System.out.println("Exception while executing statement. Terminating program... " + ex.getMessage());
        }

        return null;
    } ;

    Function<String, Integer> runDMS  = (String s1) -> {

        try {
            Statement stmt = connection.createStatement();
            return stmt.executeUpdate(s1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    };
}