package DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;


/**Database Connection*/
public abstract class DatabaseConnection {


    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jbdURL = protocol + vendor + location + databaseName;
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "root";
    private static final String password = "BurgApp304#$";
    public static Connection connection;

    /**
     * Connection
     */
    public static void getConnection(){

        try{

            Class.forName(driver);
            connection = DriverManager.getConnection(jbdURL, userName, password);
            //System.out.println("Connection Successful!");

        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }

    }

    /**
     * Closing connection
     */
    public static void closeConnection(){

        try{

          connection.close();
            //System.out.println("Connection Closed!");
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }


}
