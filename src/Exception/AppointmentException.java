package Exception;

import DatabaseConnection.DatabaseConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Objects;

/**Appointment Exception*/
public class AppointmentException {


    /**
     * Checks for over lapping times when scheduling an appointment.
     * @param start Start
     * @param end End
     * @return Returns true or false
     * @throws Exception
     */
    public static boolean checkForOverlappingTimes(LocalDateTime start , LocalDateTime end) throws Exception {

        try {

            String selectAppointments = "SELECT * FROM appointments WHERE Start AND End  between ? AND ?";
            DatabaseConnection.getConnection();
            PreparedStatement ps = DatabaseConnection.connection.prepareStatement(selectAppointments);

            ps.setTimestamp(1, Timestamp.valueOf(start));
            ps.setTimestamp(2, Timestamp.valueOf(end));

            ResultSet rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                System.out.println("Match");
                Alert alert = new Alert(Alert.AlertType.WARNING, "Scheduling error");
                alert.setContentText("Appointment has already been scheduled for that time");
                alert.showAndWait();
                DatabaseConnection.closeConnection();
                return true;


            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Checks for over lapping times expect for current appointment being edited.
     * @param start Start
     * @param end End
     * @param id Id
     * @return Returns
     * @throws Exception
     */
    public static boolean checkForOverLappingTimesForEditedAppointment(LocalDateTime start, LocalDateTime end, int id) throws Exception {

        try {
            String selectAppointmentsExpectForEditedQuery = "select*from appointments WHERE Start AND END " +
                    "BETWEEN ? AND ? AND Appointment_ID != ?";

            DatabaseConnection.getConnection();
            PreparedStatement ps = DatabaseConnection.connection.prepareStatement(selectAppointmentsExpectForEditedQuery);

            ps.setTimestamp(1, Timestamp.valueOf(start));
            ps.setTimestamp(2, Timestamp.valueOf(end));
            ps.setInt(3, id);

            ResultSet rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                System.out.println("Match");
                Alert alert = new Alert(Alert.AlertType.WARNING, "Scheduling error");
                alert.setContentText("Appointment has already been scheduled for that time");
                alert.showAndWait();
                DatabaseConnection.closeConnection();
                return true;

            }
        } catch (Exception e){

            e.printStackTrace();
        }

        return false;
    }

    /**
     * Checks for null values.
     * @param title Title
     * @param description Description
     * @param location Location
     * @param type Type
     * @param customerId Customer ID
     * @param contactId Contact ID
     * @param userId User ID
     * @param startDate Start Date
     * @param startHr Start Hour
     * @param startMin Start Minute
     * @param endDate End Date
     * @param endHr End Hour
     * @param endMin End Minute
     * @return Returns true or false
     * @throws Exception
     */
    public static boolean checkingForNullValues(TextField title, TextField description, TextField location,
                                                TextField type, ComboBox<Integer> customerId,
                                                ComboBox<Integer> contactId, ComboBox<Integer> userId,
                                                DatePicker startDate, ComboBox<Integer> startHr,
                                                ComboBox<Integer> startMin, ComboBox<String> startTimeAMPM, DatePicker endDate,
                                                ComboBox<Integer> endHr, ComboBox<Integer> endMin, ComboBox<String> endTimeAMPM) throws Exception {
        if(Objects.equals(title.getText(), "")){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Null Value");
            alert.setContentText("Title field is empty");
            alert.showAndWait();
            throw new Exception("Null Value");
        }
        if(Objects.equals(description.getText(), "")){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Null Value");
            alert.setContentText("Description field is empty");
            alert.showAndWait();
            throw new Exception("Null Value");
        }
        if(Objects.equals(location.getText(), "")){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Null Value");
            alert.setContentText("Location field is empty");
            alert.showAndWait();
            throw new Exception("Null Value");
        }
        if(Objects.equals(type.getText(), "")){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Null Value");
            alert.setContentText("Type field is empty");
            alert.showAndWait();
            throw new Exception("Null Value");
        }
        if(customerId.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Null Value");
            alert.setContentText("Customer ID field is empty");
            alert.showAndWait();
            throw new Exception("Null Value");
        }
        if(contactId.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Null Value");
            alert.setContentText("Contact ID field is empty");
            alert.showAndWait();
            throw new Exception("Null Value");
        }
        if(userId.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Null Value");
            alert.setContentText("User ID field is empty");
            alert.showAndWait();
            throw new Exception("Null Value");
        }
        if(startDate.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Null Value");
            alert.setContentText("Start Date field is empty");
            alert.showAndWait();
            throw new Exception("Null Value");
        }
        if (startHr.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Null Value");
            alert.setContentText("Start Hour field is empty");
            alert.showAndWait();
            throw new Exception("Null Value");
        }
        if(startMin.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Null Value");
            alert.setContentText("Start Minute field is empty");
            alert.showAndWait();
            throw new Exception("Null Value");
        }
        if(endDate.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Null Value");
            alert.setContentText("End Date field is empty");
            alert.showAndWait();
            throw new Exception("Null Value");
        }
        if(endHr.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Null Value");
            alert.setContentText("End Hour field is empty");
            alert.showAndWait();
            throw new Exception("Null Value");
        }
        if(endMin.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Null Value");
            alert.setContentText("End Minute field is empty");
            alert.showAndWait();
            throw new Exception("Null Value");
        }
        if(startTimeAMPM.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Null Value");
            alert.setContentText("Start AM/PM field is empty");
            alert.showAndWait();
            throw new Exception("Null Value");
        }
        if(endTimeAMPM.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Null Value");
            alert.setContentText("End AM/PM field is empty");
            alert.showAndWait();
            throw new Exception("Null Value");
        }

        return false;
    }

    /**
     * Checks the schedule times are with business hours.
     * @param start Start
     * @param end End
     * @return Returns true or false
     * @throws Exception
     */
    public static boolean checkForESTWorkHours(LocalDateTime start, LocalDateTime end) throws Exception {

        //Converts appointment times from UTC to EST
        ZoneId utcZone = ZoneId.of("UTC");
        ZoneId estZone = ZoneId.of("America/New_York");
        LocalDateTime startDateTime = start.atZone(utcZone).withZoneSameInstant(estZone).toLocalDateTime();
        LocalDateTime endDateTime = end.atZone(utcZone).withZoneSameInstant(estZone).toLocalDateTime();

        //Parsing clock hours to compare against business hours
        LocalTime checkingStartToBusinessHours = LocalTime.parse(startDateTime.toString().substring(11, 16));
        LocalTime checkingEndToBusinessHours = LocalTime.parse(endDateTime.toString().substring(11,16));

        //Business hours
        LocalTime openHours = LocalTime.of(8,0,0);
        LocalTime closeHours = LocalTime.of(22, 0,0);

        //Variables to hold if a time is outside hours represented by -1,1
        int valueOfStartOpen = checkingStartToBusinessHours.compareTo(openHours);
        int valueOfStartClosed = checkingStartToBusinessHours.compareTo(closeHours);
        int valueOfEndOpen = checkingEndToBusinessHours.compareTo(openHours);
        int valueOfEndClosed = checkingEndToBusinessHours.compareTo(closeHours);

        //Checking and throwing an exception if a time is outside business hours
        if(valueOfStartOpen < 0){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Scheduling Error");
            alert.setContentText("Scheduled start time is outside of business hours");
            alert.showAndWait();
            throw new Exception("Scheduled start time is outside of business hours");
        }
        if(valueOfStartClosed > 0){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Scheduling Error");
            alert.setContentText("Scheduled start time is outside of business hours");
            alert.showAndWait();
            throw new Exception("Scheduled start time is outside of business hours");
        }
        if(valueOfEndOpen < 0){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Scheduling Error");
            alert.setContentText("Scheduled end time is outside of business hours");
            alert.showAndWait();
            throw new Exception("Scheduled end time is outside of business hours");
        }
        if(valueOfEndClosed > 0){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Scheduling Error");
            alert.setContentText("Scheduled end time is outside of business hours");
            alert.showAndWait();
            throw new Exception("Scheduled end time is outside of business hours");
        }

        //Checking scheduled times for weekend congruence and throws exception
        if(Objects.equals(start.getDayOfWeek().toString(), "SATURDAY")){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Scheduling Error");
            alert.setContentText("Scheduled start time is outside of business hours");
            alert.showAndWait();
            throw new Exception("Scheduled start time is outside of business hours");
        }
        if(Objects.equals(start.getDayOfWeek().toString(), "SUNDAY")){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Scheduling Error");
            alert.setContentText("Scheduled start time is outside of business hours");
            alert.showAndWait();
            throw new Exception("Scheduled start time is outside of business hours");
        }
        if(Objects.equals(end.getDayOfWeek().toString(), "SATURDAY")){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Scheduling Error");
            alert.setContentText("Scheduled start time is outside of business hours");
            alert.showAndWait();
            throw new Exception("Scheduled start time is outside of business hours");
        }
        if(Objects.equals(end.getDayOfWeek().toString(), "SUNDAY")){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Scheduling Error");
            alert.setContentText("Scheduled start time is outside of business hours");
            alert.showAndWait();
            throw new Exception("Scheduled start time is outside of business hours");
        }

        //Converts current user time and TZ to EST
       /* ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime ldt = LocalDateTime.now();
        ZonedDateTime zdt = ldt.atZone(estZone);
        ZonedDateTime convertZDT = zdt.withZoneSameInstant(ZoneId.of(String.valueOf(zoneId)));*/

        return false;

    }


}
