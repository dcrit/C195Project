package DatabaseQuery;

import DatabaseConnection.DatabaseConnection;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

/**Customer Query*/
public abstract class CustomerQuery {


    /**
     * Retrieves all customers from database.
     * @return Returns list of customers
     * @throws Exception
     */
    public static ObservableList<Customer> getCustomers() throws Exception {

        ObservableList<Customer> customerList  = FXCollections.observableArrayList();

        try {

            String selectCustomers = "SELECT * FROM customers";
            DatabaseConnection.getConnection();
            PreparedStatement ps = DatabaseConnection.connection.prepareStatement(selectCustomers);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                customerList.add(new Customer(
                        rs.getInt("Customer_ID"),
                        rs.getString("Customer_Name"),
                        rs.getString("Address"),
                        rs.getString("Postal_Code"),
                        rs.getString("Phone"),
                        rs.getTimestamp("Create_Date").toLocalDateTime(),
                        rs.getString("Created_By"),
                        rs.getTimestamp("Last_Update").toLocalDateTime(),
                        rs.getString("Last_Updated_By"),
                        rs.getInt("Division_ID")

                ));

            }

            DatabaseConnection.closeConnection();

        }catch (SQLException e){

            e.printStackTrace();
            return null;
        }
        return customerList;

    }

    /**
     * Retrieves customer ID's.
     * @return Returns list of customer ID's
     */
    public static ObservableList<Integer> getCustomerId(){

        ObservableList<Integer> customerIdList  = FXCollections.observableArrayList();

        try {
            String selectAppointments = "SELECT Customer_ID FROM customers";
            DatabaseConnection.getConnection();
            PreparedStatement ps = DatabaseConnection.connection.prepareStatement(selectAppointments);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                   customerIdList.add(rs.getInt("Customer_ID")) ;

            }

            DatabaseConnection.closeConnection();

        }catch (SQLException e){

            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerIdList;

    }

    /**
     * Inserts new customer to database
     * @param name Name
     * @param address Address
     * @param postalCode Postal Code
     * @param phone Phone
     * @param createDate Create Date
     * @param createdBy Created By
     * @param lastUpdate Last Update
     * @param lastUpdatedBy Last Updated By
     * @param divisionID Division ID
     */
    public static void createNewCustomer(String name, String address, String postalCode, String phone,
                                         LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate,
                                         String lastUpdatedBy, int divisionID ){

        try {

            String insertCustomer = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, " +
                    "Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            DatabaseConnection.getConnection();
            PreparedStatement ps = DatabaseConnection.connection.prepareStatement(insertCustomer);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setObject(5, createDate);
            ps.setString(6, createdBy);
            ps.setTimestamp(7, Timestamp.valueOf(lastUpdate));
            ps.setString(8, lastUpdatedBy);
            ps.setInt(9, divisionID);
            int insertSuccessful = ps.executeUpdate();
            if(insertSuccessful > 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Customer Saved");
                alert.setContentText("Customer saved successful");
                alert.showAndWait();
            }
            if(insertSuccessful == 0){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Customer Not Saved");
                alert.setContentText("Error: Customer not saved");
                alert.showAndWait();
            }
            DatabaseConnection.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    /**
     * Updates a customer.
     * @param name Name
     * @param address Address
     * @param postalCode Postal Code
     * @param phone Phone
     * @param lastUpdate Last Update
     * @param lastUpdatedBy Last Updated By
     * @param divisionId Division ID
     * @param customerId Customer ID
     */
    public static void editCustomer(String name, String address, String postalCode, String phone,
                                    LocalDateTime lastUpdate, String lastUpdatedBy, int divisionId, int customerId) {

        try {

            String updateCustomer = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, " +
                    "Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = " + customerId;
            DatabaseConnection.getConnection();
            PreparedStatement ps = DatabaseConnection.connection.prepareStatement(updateCustomer);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setTimestamp(5, Timestamp.valueOf(lastUpdate));
            ps.setString(6, lastUpdatedBy);
            ps.setInt(7, divisionId);
            int insertSuccessful = ps.executeUpdate();
            if(insertSuccessful > 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Customer Updated");
                alert.setContentText("Customer updated successfully");
                alert.showAndWait();
            }
            if(insertSuccessful == 0){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Customer Not Updated");
                alert.setContentText("Error: Customer not updated");
                alert.showAndWait();
            }


            DatabaseConnection.closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Deletes customer
     * @param deleteCustomer Delete Customer
     * @throws Exception
     */
    public static void deleteCustomer(Customer deleteCustomer) throws Exception {

        Alert alert = new Alert(Alert.AlertType.WARNING, "Delete Customer");
        alert.setContentText("Are you sure you want to delete " + deleteCustomer.getCustomerName());
        Optional<ButtonType> ok = alert.showAndWait();
        if(ok.isPresent() && ok.get() == ButtonType.OK) {
            String deleteQuery = "DELETE FROM customers WHERE Customer_ID =  " + deleteCustomer.getCustomerID();
            DatabaseConnection.getConnection();
            PreparedStatement ps = DatabaseConnection.connection.prepareStatement(deleteQuery);
            ps.executeUpdate();
            DatabaseConnection.closeConnection();
        }

    }

}
