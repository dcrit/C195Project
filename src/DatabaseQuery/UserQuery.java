package DatabaseQuery;

import DatabaseConnection.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;

/**User Query*/
public class UserQuery {


    //Holders for User name and Time Zone
    public static String user;
    public static ZoneId zoneId;


    /**
     * Checks if username and password are correct.
     * @param userName User Name
     * @param password Password
     * @return Returns false if incorrect
     */
    public static boolean checkUserNameAndPassword(String userName, String password) {

        try {

            DatabaseConnection.getConnection();
            String selectLoginInfo = "SELECT User_Name, Password FROM users WHERE User_Name = ? AND Password = ?";
            PreparedStatement ps = DatabaseConnection.connection.prepareStatement(selectLoginInfo);
            ps.setString(1, userName);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.isBeforeFirst()) {

                return true;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DatabaseConnection.closeConnection();
        return false;
    }

    /**
     * Retrieves user Id's from database.
     * @return Returns list of user Id's
     * @throws Exception Throws Exception
     */
    public static ObservableList<Integer> getUserId() throws Exception {

        ObservableList<Integer> userIdList = FXCollections.observableArrayList();

        try {

            String selectUserId = "SELECT User_ID FROM users";
            DatabaseConnection.getConnection();
            PreparedStatement ps = DatabaseConnection.connection.prepareStatement(selectUserId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                userIdList.add(rs.getInt("User_ID"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        DatabaseConnection.closeConnection();
        return userIdList;
    }


}
