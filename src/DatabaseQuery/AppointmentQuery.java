package DatabaseQuery;

import DatabaseConnection.DatabaseConnection;
import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**Appointment Query*/
public class AppointmentQuery {


    /**
     * Selecting all appointments from database
     * @return Returns list of appointments
     * @throws Exception
     */
    public static ObservableList<Appointment> getAppointments() throws Exception {

        ObservableList<Appointment> appointmentList  = FXCollections.observableArrayList();

        try {
            String selectAppointments = "SELECT * FROM appointments";
            DatabaseConnection.getConnection();
            PreparedStatement ps = DatabaseConnection.connection.prepareStatement(selectAppointments);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                appointmentList.add(new Appointment(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        timeCovertForLocalDateTimeCurrentZone(rs.getTimestamp("Start").toLocalDateTime()),
                        timeCovertForLocalDateTimeCurrentZone(rs.getTimestamp("End").toLocalDateTime()),
                        timeCovertForLocalDateTimeCurrentZone(rs.getTimestamp("Create_Date").toLocalDateTime()),
                        rs.getString("Created_by"),
                        timeCovertForLocalDateTimeCurrentZone(rs.getTimestamp("Last_Update").toLocalDateTime()),
                        rs.getString("Last_Updated_By"),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"),
                        rs.getInt("Contact_ID")

                ));

            }

            DatabaseConnection.closeConnection();

        }catch (SQLException e){

            e.printStackTrace();
            return null;
        }

        return appointmentList;

    }


    /**
     * Inserts new customer to database
     * @param title Title
     * @param description Description
     * @param location Location
     * @param type Type
     * @param startTimestamp Start Timestamp
     * @param endTimestamp End Timestamp
     * @param createDate Create Date
     * @param createdBy Created By
     * @param lastUpdate Last Update
     * @param lastUpdatedBy Last Updated by
     * @param customerId Customer ID
     * @param userId User ID
     * @param contactId Contact ID
     */
    public static void createNewAppointment(String title, String description, String location, String type,
                                            LocalDateTime startTimestamp, LocalDateTime endTimestamp, LocalDateTime createDate,
                                            String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy,
                                            int customerId, int userId, int contactId){

        try {
            String insertAppointment = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, " +
                    "Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?) ";
            DatabaseConnection.getConnection();
            PreparedStatement ps = DatabaseConnection.connection.prepareStatement(insertAppointment);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4,type);
            ps.setTimestamp(5, Timestamp.valueOf(startTimestamp));
            ps.setTimestamp(6, Timestamp.valueOf(endTimestamp));
            ps.setTimestamp(7, Timestamp.valueOf(createDate));
            ps.setString(8, createdBy);
            ps.setTimestamp(9, Timestamp.valueOf(lastUpdate));
            ps.setString(10, lastUpdatedBy);
            ps.setInt(11, customerId);
            ps.setInt(12,userId);
            ps.setInt(13, contactId);
            int insertSuccessful = ps.executeUpdate();

            if(insertSuccessful > 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment Saved");
                alert.setContentText("Appointment saved successfully");
                alert.showAndWait();
            }
            if(insertSuccessful == 0){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment Not Saved");
                alert.setContentText("Error: Appointment not saved");
                alert.showAndWait();
            }
            DatabaseConnection.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Updates appointment
     * @param title Title
     * @param description Description
     * @param location Location
     * @param type Type
     * @param startTimestamp Start Timestamp
     * @param endTimestamp End Timestamp
     * @param createDate Create Date
     * @param createdBy Created By
     * @param lastUpdate Last Update
     * @param lastUpdatedBy Last Updated by
     * @param customerId Customer ID
     * @param userId User ID
     * @param contactId Contact ID
     * @param appointmentId Appointment ID
     */
    public static void editAppointment(String title, String description, String location, String type,
                                       LocalDateTime startTimestamp, LocalDateTime endTimestamp, LocalDateTime createDate,
                                       String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy,
                                       int customerId, int userId, int contactId, int appointmentId){

        try {
            String insertAppointment = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, " +
                    "Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? " +
                    "WHERE Appointment_ID = " + appointmentId;
            DatabaseConnection.getConnection();
            PreparedStatement ps = DatabaseConnection.connection.prepareStatement(insertAppointment);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4,type);
            ps.setTimestamp(5, Timestamp.valueOf(startTimestamp));
            ps.setTimestamp(6, Timestamp.valueOf(endTimestamp));
            ps.setTimestamp(7, Timestamp.valueOf(createDate));
            ps.setString(8, createdBy);
            ps.setTimestamp(9, Timestamp.valueOf(lastUpdate));
            ps.setString(10, lastUpdatedBy);
            ps.setInt(11, customerId);
            ps.setInt(12,userId);
            ps.setInt(13, contactId);
            int insertSuccessful = ps.executeUpdate();

            if(insertSuccessful > 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment Updated");
                alert.setContentText("Appointment updated successfully");
                alert.showAndWait();
            }
            if(insertSuccessful == 0){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment not saved");
                alert.setContentText("Error: Appointment not updated");
                alert.showAndWait();
            }
            DatabaseConnection.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    /**
     * Deletes appointment from database with given appointment ID.
     * @param appointment Appointment
     * @throws Exception
     */
    public static void deleteAppointment(Appointment appointment) throws Exception {

        String deleteQuery = "DELETE FROM appointments WHERE Appointment_ID = " + appointment.getAppointmentId();
        DatabaseConnection.getConnection();
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.WARNING, "Cancel Appointment", okButton, cancelButton);
        alert.setContentText("Are you sure you want to cancel this appointment?\n" +
                appointment.getAppointmentId() + " " + appointment.getType());
        Optional<ButtonType> result = alert.showAndWait();
        try {

            if(result.isPresent()) {
                if (result.get() == okButton) {
                    PreparedStatement ps = DatabaseConnection.connection.prepareStatement(deleteQuery);
                    ps.executeUpdate();
                    DatabaseConnection.closeConnection();
                }
            }
            if(result.isPresent()) {
                if (result.get() == cancelButton) {

                    //Do Nothing

                }
            }
            DatabaseConnection.closeConnection();
        } catch (Exception e){
            e.printStackTrace();
        }

    }


    /**
     * Select appointments from database for current month.
     * @param year Year
     * @param month Month
     * @return Return list of appointments by month
     */
    public static ObservableList<Appointment> appointmentByMonth(int year, int month){

            ObservableList<Appointment> monthAppointments = FXCollections.observableArrayList();

        try {
            String monthAppointmentQuery = "SELECT * FROM appointments where month(Start) = ? AND year(Start) = ?";
            DatabaseConnection.getConnection();
            PreparedStatement ps = DatabaseConnection.connection.prepareStatement(monthAppointmentQuery);
            ps.setInt(1, month);
            ps.setInt(2, year);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                monthAppointments.add(new Appointment(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        timeCovertForLocalDateTimeCurrentZone(rs.getTimestamp("Start").toLocalDateTime()),
                        timeCovertForLocalDateTimeCurrentZone(rs.getTimestamp("End").toLocalDateTime()),
                        timeCovertForLocalDateTimeCurrentZone(rs.getTimestamp("Create_Date").toLocalDateTime()),
                        rs.getString("Created_by"),
                        timeCovertForLocalDateTimeCurrentZone(rs.getTimestamp("Last_Update").toLocalDateTime()),
                        rs.getString("Last_Updated_By"),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"),
                        rs.getInt("Contact_ID")
                ));
            }

            DatabaseConnection.closeConnection();

        } catch (Exception e){
            e.printStackTrace();
        }

        return monthAppointments;
    }

    /**
     * Selects appointments for current week
     * @param weekNumber Week Number
     * @return Returns list of appointments for the week
     */
    public static ObservableList<Appointment> appointmentByWeek(int weekNumber){

        ObservableList<Appointment> weekAppointments = FXCollections.observableArrayList();

        try{
            String weekAppointmentQuery = "SELECT * FROM appointments where week(Start, 7) = ?";
            DatabaseConnection.getConnection();
            PreparedStatement ps = DatabaseConnection.connection.prepareStatement(weekAppointmentQuery);
            ps.setInt(1, weekNumber);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                weekAppointments.add(new Appointment(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        timeCovertForLocalDateTimeCurrentZone(rs.getTimestamp("Start").toLocalDateTime()),
                        timeCovertForLocalDateTimeCurrentZone(rs.getTimestamp("End").toLocalDateTime()),
                        timeCovertForLocalDateTimeCurrentZone(rs.getTimestamp("Create_Date").toLocalDateTime()),
                        rs.getString("Created_by"),
                        timeCovertForLocalDateTimeCurrentZone(rs.getTimestamp("Last_Update").toLocalDateTime()),
                        rs.getString("Last_Updated_By"),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"),
                        rs.getInt("Contact_ID")
                ));

            }
        } catch (Exception e){
            e.printStackTrace();
        }

        DatabaseConnection.closeConnection();
        return weekAppointments;
    }

    /**
     * Checks for upcoming appointments
     * @throws ParseException
     */
    public static void upcomingAppointments() throws ParseException {

        LocalDateTime ldt = LocalDateTime.now();
        ldt = convertToUTC(ldt);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String ldtString = ldt.format(formatter);

        int id = 0;
        LocalDateTime start = null;
        LocalDateTime end = null;

        try{

            String selectUpcomingAppointments = "select distinct Appointment_ID, Start, End from appointments where " +
                    "Start between ? AND adddate( ?, interval 15 minute)";
            DatabaseConnection.getConnection();
            PreparedStatement ps = DatabaseConnection.connection.prepareStatement(selectUpcomingAppointments);
            ps.setTimestamp(1, Timestamp.valueOf(ldtString));
            ps.setTimestamp(2, Timestamp.valueOf(ldtString));
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                id = rs.getInt("Appointment_ID");
                start = rs.getTimestamp("Start").toLocalDateTime();
                end = rs.getTimestamp("End").toLocalDateTime();
            }

            if(id != 0 ) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Up Coming Appointment");
                alert.setContentText("Upcoming Appointment! \n" +
                        "Appointment ID: " + id + "\n" +
                        "Start Time: " + timeCovertForLocalDateTimeCurrentZone(start)+ "\n" +
                        "End Time: " + timeCovertForLocalDateTimeCurrentZone(end));
                alert.showAndWait();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Timer convertor for users time zone.
     * @param localDateTime Local Date Time
     * @return Returns a LocalDateTime
     */
    public static LocalDateTime timeCovertForLocalDateTimeCurrentZone(LocalDateTime localDateTime){

      /*  ZoneId zoneOfUTC = ZoneId.of("UTC");
        ZoneId zone = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneOfUTC);
        ZonedDateTime convertedZDT = zdt.withZoneSameInstant(ZoneId.of(String.valueOf(zone)));

        return convertedZDT.toLocalDateTime();
        */
       /* System.out.println("UTC Time " + localDateTime);
        String input = localDateTime.toString();
        LocalDateTime ldt = LocalDateTime.parse(input);
        OffsetDateTime odt = ldt.atOffset(ZoneOffset.UTC);
        ZoneId z = ZoneId.systemDefault();
        ZonedDateTime  zdt = odt.atZoneSameInstant(z);*/
       // System.out.println("System Default Time " + zdt.toLocalDateTime());
        ZonedDateTime utcStartConvert = localDateTime.atZone(ZoneId.systemDefault());
        return utcStartConvert.toLocalDateTime();

    }

        public static LocalDateTime convertToUTC(LocalDateTime utcString) throws ParseException {

            ZonedDateTime utcStartConvert = utcString.atZone(ZoneId.systemDefault());
            ZonedDateTime start = utcStartConvert.withZoneSameInstant(ZoneOffset.UTC);

            return start.toLocalDateTime();
        }


}
