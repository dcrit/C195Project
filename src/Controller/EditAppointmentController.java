package Controller;

import DatabaseQuery.*;
import Exception.*;
import Model.Appointment;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**Edit Appointment*/
public class EditAppointmentController implements Initializable {


    //Text Fields
    @FXML private TextField appointmentIdTextField;
    @FXML private TextField titleTextField;
    @FXML private TextField descriptionTextField;
    @FXML private TextField locationTextField;
    @FXML private TextField typeTextField;

    //Date Pickers
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;


    //Combo Boxes
    @FXML private ComboBox<Integer> customerIdComboBox;
    @FXML private ComboBox<String> contactIdComboBox;
    @FXML private ComboBox<Integer> userIdComboBox;
    @FXML private ComboBox<Integer> startTimeHrComboBoxFXID;
    @FXML private ComboBox<Integer> startTimeMinComboBoxFXID;
    @FXML private ComboBox<Integer> endTimeHrComboBoxFXID;
    @FXML private ComboBox<Integer> endTimeMinComboBoxFXID;
    @FXML private ComboBox<String> startTimeAMPMComboBox;
    @FXML private ComboBox<String> endTimeAMPMComboBox;

    //String for AM/PM combo boxes
    private final String[] amPm = {"AM", "PM"};

    //Buttons
    @FXML private Button cancelButton;

    /**Holds appointment that is being edited.*/
    public static Appointment passingAppointmentFromMain;
    /**Sets appointment that is being edited.*/
    public static void setPassingAppointmentFromMain(Appointment passingAppointmentFromMain) {
        EditAppointmentController.passingAppointmentFromMain = passingAppointmentFromMain;
    }


    /**
     * Initializer
     * Setting combo boxes with values and ID's.
     * Using a lambda expression with button listener to return to main.
     * @param url URL
     * @param resourceBundle Resource Bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        startTimeHrComboBoxFXID.getItems().setAll(IntStream.rangeClosed(1,12).boxed().collect(Collectors.toList()));
        startTimeMinComboBoxFXID.getItems().setAll(IntStream.rangeClosed(0,60).boxed().collect(Collectors.toList()));
        endTimeHrComboBoxFXID.getItems().setAll(IntStream.rangeClosed(1,12).boxed().collect(Collectors.toList()));
        endTimeMinComboBoxFXID.getItems().setAll(IntStream.rangeClosed(0,60).boxed().collect(Collectors.toList()));
        startTimeAMPMComboBox.getItems().setAll(amPm);
        endTimeAMPMComboBox.getItems().setAll(amPm);

        try {
            gettingAndSettingIds();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Setting values to be edited
        appointmentIdTextField.setText(String.valueOf(passingAppointmentFromMain.getAppointmentId()));
        titleTextField.setText(passingAppointmentFromMain.getTitle());
        descriptionTextField.setText(passingAppointmentFromMain.getDescription());
        locationTextField.setText(passingAppointmentFromMain.getLocation());
        typeTextField.setText(passingAppointmentFromMain.getType());
        customerIdComboBox.setValue(passingAppointmentFromMain.getCustomerID());
        contactIdComboBox.setValue(ContactQuery.getContactsForEdit(passingAppointmentFromMain.getContactID()));
        userIdComboBox.setValue(passingAppointmentFromMain.getUserID());
        try {
            settingDatesForDatePickers();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Lambda Expression
        cancelButton.setOnAction((actionEvent -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainForm.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            assert root != null;
            Scene scene = new Scene(root, 925, 600);
            stage.setTitle("Main Form");
            stage.setScene(scene);
            stage.show();
        }));

    }


    /**
     * Save Button
     * Updating appointment to database and using a lambda function for combo boxes.
     * @param actionEvent Takes in an action event(button click).
     * @throws Exception Throws Exception
     */
    public void save(ActionEvent actionEvent) throws Exception {

        //Lambda Expression function that makes it easier to parse combo boxes
        Function<Integer, Integer> convertComboBoxesToInteger = Integer::valueOf;

        //Checking for null values
        AppointmentException.checkingForNullValues(titleTextField, descriptionTextField, locationTextField,
                typeTextField, customerIdComboBox, contactIdComboBox, userIdComboBox, startDatePicker,
                startTimeHrComboBoxFXID, startTimeMinComboBoxFXID, startTimeAMPMComboBox, endDatePicker, endTimeHrComboBoxFXID,
                endTimeMinComboBoxFXID, endTimeAMPMComboBox);

        //Text Fields
        String title = titleTextField.getText();
        String description = descriptionTextField.getText();
        String location = locationTextField.getText();
        String type = typeTextField.getText();
        String createdBy = UserQuery.user;
        String lastUpdatedBy = UserQuery.user;
        LocalDateTime lastUpdate = LocalDateTime.now(ZoneOffset.UTC);
        int customerId = convertComboBoxesToInteger.apply(customerIdComboBox.getValue());
        String contactIdAndName =  contactIdComboBox.getValue();
        int contactId = Integer.parseInt(contactIdAndName.substring(0, contactIdAndName.indexOf(' ')));
        int userId = convertComboBoxesToInteger.apply(userIdComboBox.getValue());


        //Converting scheduling times in string and them formatting to DateFormat
        String startDayAndTime =
                startDatePicker.getValue() + " " +
                        startTimeHrComboBoxFXID.getValue() + ":" +
                        paddingTimesForDBEntry(String.valueOf(startTimeMinComboBoxFXID.getValue())) +
                        " " + startTimeAMPMComboBox.getValue();
        String endDayAndTime = endDatePicker.getValue() + " "+
                endTimeHrComboBoxFXID.getValue() + ":" +
                paddingTimesForDBEntry(String.valueOf(endTimeMinComboBoxFXID.getValue())) +
                " " + endTimeAMPMComboBox.getValue();

        //Converting scheduled start and end times to UTC and LocalDateTime
        LocalDateTime start = convertToUTC(startDayAndTime);
        LocalDateTime end = convertToUTC(endDayAndTime);

        //Getting current time for create date and setting to UTC
        LocalDateTime createDate = LocalDateTime.now(ZoneOffset.UTC);

        //Checking if overlapping and inside work hours, then inserting.
        if(!AppointmentException.checkForOverLappingTimesForEditedAppointment(start, end,
                passingAppointmentFromMain.getAppointmentId()) &&
                !AppointmentException.checkForESTWorkHours(start, end) &&
                !AppointmentException.checkingEndTimeIsNotBeforeStartTime(start, end)) {
            //Updating Appointment
            AppointmentQuery.editAppointment(title, description, location, type, start, end, createDate, createdBy,
                    lastUpdate, lastUpdatedBy, customerId, userId, contactId,
                    passingAppointmentFromMain.getAppointmentId());

            //Returning the user to main form
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainForm.fxml")));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 925, 600);
            stage.setTitle("Main Menu");
            stage.setScene(scene);
            stage.show();

            //Clearing fields
            clearFields();
        }
    }

    /**
     * Setting the date picker from the passed appointment
     * @throws ParseException Throws Parse Exception
     */
    public void settingDatesForDatePickers() throws ParseException {

        //Start Time
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime startTimeLocalDateTime = passingAppointmentFromMain.getStartTime();
        String startTimeFormattedDate = startTimeLocalDateTime.format(dateTimeFormatter);

        String startDateString = startTimeFormattedDate.substring(0,10);
        String startTime = convert12(startTimeFormattedDate.substring(11,16));
        String startTimeHr = startTime.substring(0,2);
        String startTimeMin = startTime.substring(3,5);
        String startAMPM = startTime.substring(6,8);

        LocalDate localDateStartDate = LocalDate.parse(startDateString);
        startDatePicker.setValue(localDateStartDate);
        startTimeHrComboBoxFXID.setValue(Integer.valueOf(startTimeHr));
        startTimeMinComboBoxFXID.setValue(Integer.valueOf(startTimeMin));
        startTimeAMPMComboBox.setValue(startAMPM);


        //End time
        LocalDateTime endTimeLocalDateTime = passingAppointmentFromMain.getEndTime();
        String endTimeFormattedDate = endTimeLocalDateTime.format(dateTimeFormatter);

        String endDateString = endTimeFormattedDate.substring(0,10);
        String endTime = convert12(endTimeFormattedDate.substring(11,16));
        String endTimeHr = endTime.substring(0,2);
        String endTimeMin =  endTime.substring(3,5);
        String endAMPM = endTime.substring(6,8);

        LocalDate localDateEndDate = LocalDate.parse(endDateString);
        endDatePicker.setValue(localDateEndDate);
        endTimeHrComboBoxFXID.setValue(Integer.valueOf(endTimeHr));
        endTimeMinComboBoxFXID.setValue(Integer.valueOf(endTimeMin));
        endTimeAMPMComboBox.setValue(endAMPM);

    }

    /**
     * Converts the 24-hour time to a 12-hour time
     * @param str Takes in a date and time string
     * @return Returns the converted 12-hour time as a string
     * @throws ParseException Throws Parse Exception
     */
    public String convert12(String str) throws ParseException {

        SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
        SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
        java.util.Date _24HourDt = _24HourSDF.parse(str);
        return _12HourSDF.format(_24HourDt);
    }


    /**
     * Getting Id's from database and setting them in combo boxes
     * @throws Exception Throws Exception
     */
    public void gettingAndSettingIds() throws Exception {

        ObservableList<Integer> customerIds = CustomerQuery.getCustomerId();
        customerIdComboBox.setItems(customerIds);

        ObservableList<String> contactIds = ContactQuery.getContactIdAndName();
        contactIdComboBox.setItems(contactIds);

        ObservableList<Integer> userIds = UserQuery.getUserId();
        userIdComboBox.setItems(userIds);


    }

    /**
     * Padding the numbers 1-9 from the  minute combo box
     * @param minChange Takes in the minute as string to pad
     * @return Returns a padded int as a string
     */
    public String paddingTimesForDBEntry(String minChange){


        int checkingMinutes = Integer.parseInt(minChange);

        if(checkingMinutes <= 9 ){

            return String.format("%02d", checkingMinutes);

        }

        return minChange;
    }

    private LocalDateTime convertToUTC(String utcString) throws ParseException {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
        DateFormat outputFormat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Timestamp startTimestamp = Timestamp.valueOf(outputFormat.format(df.parse(utcString)));
        LocalDateTime startLocalDateTime = startTimestamp.toLocalDateTime();
        ZonedDateTime utcStartConvert = startLocalDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime start = utcStartConvert.withZoneSameInstant(ZoneOffset.UTC);

        return start.toLocalDateTime();
    }

    /**Clearing fields*/
    public void clearFields(){

        titleTextField.clear();
        descriptionTextField.clear();
        locationTextField.clear();
        typeTextField.clear();
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        customerIdComboBox.setValue(null);
        contactIdComboBox.setValue(null);
        userIdComboBox.setValue(null);
        startTimeHrComboBoxFXID.setValue(null);
        startTimeMinComboBoxFXID.setValue(null);
        endTimeHrComboBoxFXID.setValue(null);
        endTimeMinComboBoxFXID.setValue(null);
        passingAppointmentFromMain = null;

    }
}
