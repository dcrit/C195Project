package DatabaseQuery;

import DatabaseConnection.DatabaseConnection;
import Model.Contact;
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
     * @throws Exception Throws Exception
     */
    public static ObservableList<String> getContactIdAndName() throws Exception {

        ObservableList<String> contacts = FXCollections.observableArrayList();

        try {

            String selectContactId = "SELECT Contact_ID, Contact_Name  FROM contacts";
            DatabaseConnection.getConnection();
            PreparedStatement ps = DatabaseConnection.connection.prepareStatement(selectContactId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                contacts.add(rs.getInt("Contact_ID") + " " +rs.getString("Contact_Name"));
            }


        }catch (SQLException e){

            e.printStackTrace();
            return null;
        }
        DatabaseConnection.closeConnection();
        return contacts;
    }

    public static String getContactsForEdit(int id){


        String currentContact = "";

        try {

            String selectContactId = "SELECT Contact_ID, Contact_Name, Email FROM contacts WHERE Contact_ID = ?";
            DatabaseConnection.getConnection();
            PreparedStatement ps = DatabaseConnection.connection.prepareStatement(selectContactId);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){

                currentContact = rs.getInt("Contact_ID") + " " + rs.getString("Contact_Name");
            }


        }catch (SQLException e){

            e.printStackTrace();
            return null;
        }
        DatabaseConnection.closeConnection();
        return currentContact;

    }

}
