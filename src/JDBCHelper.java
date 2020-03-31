import java.sql.*;


public class JDBCHelper {
    String className, URL, user, password;
    Connection connection;
    public JDBCHelper() {
        this.className = "com.mysql.jdbc.Driver";
        this.URL = "jdbc:mysql://3.227.166.251:3306/U07BDK";
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
}