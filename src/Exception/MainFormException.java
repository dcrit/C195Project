package Exception;

import DatabaseConnection.DatabaseConnection;
import Model.Appointment;
import Model.Customer;
import javafx.scene.control.Alert;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**Main Form Exception*/
public class MainFormException {

    /**
     * Checks for null values
     * @param editAppointment Edit Appointment
     */
    public static void nullValueForEditAppointment(Appointment editAppointment){

        if(editAppointment == null){

            Alert alert = new Alert(Alert.AlertType.WARNING, "Null Value");
            alert.setContentText("Please select an appointment to edit");
            alert.showAndWait();

        }

    }

    /**
     * Checks for null value when deleting an appointment.
     * @param deleteAppointment Delete Appointment
     */
    public static void nullValueForDeleteAppointment(Appointment deleteAppointment){

        if(deleteAppointment == null){

            Alert alert = new Alert(Alert.AlertType.WARNING, "Null Value");
            alert.setContentText("Please select an appointment to delete");
            alert.showAndWait();
        }

    }

    /**
     * Checks for null value for edit customer
     * @param editCustomer Edit Customer
     */
    public static void nullValueForEditCustomer(Customer editCustomer){

        if(editCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Null Value");
            alert.setContentText("Please select a customer to edit");
            alert.showAndWait();
        }

    }

    /**
     * Checks for null vale for delete customer
     * @param deleteCustomer
     */
    public static void nullValueForDeleteCustomer(Customer deleteCustomer){

        if(deleteCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Null Value");
            alert.setContentText("Please select a customer to delete");
            alert.showAndWait();
        }
    }

    /**
     * Checks if customer has a schedule appointment
     * @param id ID
     * @return Returns true or false
     * @throws Exception
     */
    public static boolean checkIfCustomerHasAnAppointment(int id) throws Exception {

        try {
            DatabaseConnection.getConnection();
            String selectCustomerWithAppointmentId = "SELECT Customer_ID FROM appointments WHERE Customer_ID = ?";
            PreparedStatement ps = DatabaseConnection.connection.prepareStatement(selectCustomerWithAppointmentId);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Alert alert = new Alert(Alert.AlertType.WARNING, "Scheduled Appointment");
                alert.setContentText("This customer has a scheduled appointment");
                alert.showAndWait();
                return true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        DatabaseConnection.closeConnection();
        return false;
    }
}
