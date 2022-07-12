package DatabaseQuery;

import DatabaseConnection.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**Contact Query*/
public class ContactQuery {


    /**
     * Retrieves contact ID's from Database
     * @return Returns list of contact ID's
     * @throws Exception
     */
    public static ObservableList<Integer> getContactId() throws Exception {

        ObservableList<Integer> contactIdList = FXCollections.observableArrayList();

        try {

            String selectContactId = "SELECT Contact_ID FROM contacts";
            DatabaseConnection.getConnection();
            PreparedStatement ps = DatabaseConnection.connection.prepareStatement(selectContactId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                contactIdList.add(rs.getInt("Contact_ID"));

            }


        }catch (SQLException e){

            e.printStackTrace();
            return null;
        }
        DatabaseConnection.closeConnection();
        return contactIdList;
    }

}
