package Controller;

import DatabaseQuery.*;
import Exception.*;
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
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**Add Appointment*/
public class AddAppointmentController implements Initializable {



    //Text Fields
    @FXML private TextField titleTextField;
    @FXML private TextField descriptionTextField;
    @FXML private  TextField locationTextField;
    @FXML private TextField typeTextField;

    //Date Pickers
    @FXML private DatePicker startDatePicker;
    @FXML private  DatePicker endDatePicker;


    //Combo Boxes
    @FXML private ComboBox<Integer> customerIdComboBox;
    @FXML private ComboBox<Integer> contactIdComboBox;
    @FXML private ComboBox<Integer> userIdComboBox;
    @FXML private ComboBox<Integer> startTimeHrComboBoxFXID;
    @FXML private ComboBox<Integer> startTimeMinComboBoxFXID;
    @FXML private  ComboBox<Integer> endTimeHrComboBoxFXID;
    @FXML private  ComboBox<Integer> endTimeMinComboBoxFXID;
    @FXML private ComboBox<String> startTimeAMPMComboBox;
    @FXML private ComboBox<String> endTimeAMPMComboBox;

    //String for AM/PM combo boxes
    private final String[] amPm = {"AM", "PM"};

    //Buttons
    @FXML private  Button cancelButton;

    /**Initializer
     * Setting combo boxes with values and ID's.
     * Using a lambda expression with button listener to return to main.
     * @param url URL
     * @param resourceBundle Resource Bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            gettingAndSettingIds();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Setting combo boxes
        startTimeHrComboBoxFXID.getItems().setAll(IntStream.rangeClosed(1, 12).boxed().collect(Collectors.toList()));
        startTimeMinComboBoxFXID.getItems().setAll(IntStream.rangeClosed(0, 60).boxed().collect(Collectors.toList()));
        endTimeHrComboBoxFXID.getItems().setAll(IntStream.rangeClosed(1, 12).boxed().collect(Collectors.toList()));
        endTimeMinComboBoxFXID.getItems().setAll(IntStream.rangeClosed(0, 60).boxed().collect(Collectors.toList()));
        startTimeAMPMComboBox.getItems().setAll(amPm);
        endTimeAMPMComboBox.getItems().setAll(amPm);



        //Lambda Expression
        cancelButton.setOnAction((actionEvent -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainForm.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 925, 600);
            stage.setTitle("Main Form");
            stage.setScene(scene);
            stage.show();
        }));


    }

    /** Save Button
     *  Saving appointment to database and using a lambda function for combo boxes
     *  @param actionEvent Takes in an action event(button click)
     *  @throws Exception
     */
    @FXML
    public  void save(ActionEvent actionEvent) throws Exception {


        //Lambda Expression function that makes it easier to parse combo boxes
        Function<Integer, Integer> convertComboBoxesToInteger = Integer::valueOf;

        //Checking for null values
        AppointmentException.checkingForNullValues(titleTextField, descriptionTextField, locationTextField,
                typeTextField, customerIdComboBox, contactIdComboBox, userIdComboBox, startDatePicker,
                startTimeHrComboBoxFXID,startTimeMinComboBoxFXID, startTimeAMPMComboBox, endDatePicker, endTimeHrComboBoxFXID,
                endTimeMinComboBoxFXID,  endTimeAMPMComboBox);

        //Placeholders for data
        String title = titleTextField.getText();
        String description = descriptionTextField.getText();
        String location = locationTextField.getText();
        String type = typeTextField.getText();
        String createdBy = UserQuery.user;
        String lastUpdatedBy = UserQuery.user;
        LocalDateTime lastUpdate = LocalDateTime.now(ZoneOffset.UTC);
        int customerId = convertComboBoxesToInteger.apply(customerIdComboBox.getValue());
        int contactId = convertComboBoxesToInteger.apply(contactIdComboBox.getValue());
        int userId = convertComboBoxesToInteger.apply(userIdComboBox.getValue());


        //Converting scheduling times in string and them formatting to DateFormat
        String startDayAndTime =
                startDatePicker.getValue() + " " +
                        startTimeHrComboBoxFXID.getValue() + ":" +
                        paddingTimesForDBEntry(String.valueOf(startTimeMinComboBoxFXID.getValue())) +
                                " " + startTimeAMPMComboBox.getValue();
        System.out.println(startDayAndTime);
        String endDayAndTime = endDatePicker.getValue() + " "+
                endTimeHrComboBoxFXID.getValue() + ":" +
                paddingTimesForDBEntry(String.valueOf(endTimeMinComboBoxFXID.getValue())) +
                        " " + endTimeAMPMComboBox.getValue();


        //Converting scheduled start and end times to UTC and LocalDateTime
        LocalDateTime start = convertToUTC(startDayAndTime);
        System.out.println("Start UTC Time " + start);
        LocalDateTime end = convertToUTC(endDayAndTime);

        //Getting current time for create date and setting to UTC
        LocalDateTime createDate = LocalDateTime.now(ZoneOffset.UTC);



        //Checks for overlapping times and within business hours, then inserts appointment
        if(!AppointmentException.checkForESTWorkHours(start, end) &&
                !AppointmentException.checkForOverlappingTimes(start, end) &&
                !AppointmentException.checkingEndTimeIsNotBeforeStartTime(start, end)) {
            //Inserting Appointment
            DatabaseQuery.AppointmentQuery.createNewAppointment(title, description, location, type, start, end,
                    createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, contactId, userId);

            //Sends user back to main form
            Parent root = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
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


    /**
     * Pulling Customers Ids, Contact Ids, and User Ids to load in combo boxes
     * @throws Exception
     */
    public void gettingAndSettingIds() throws Exception {

        ObservableList<Integer> customerIds = CustomerQuery.getCustomerId();
        customerIdComboBox.setItems(customerIds);

        ObservableList<Integer> contactIds = ContactQuery.getContactId();
        contactIdComboBox.setItems(contactIds);

        ObservableList<Integer> userIds = UserQuery.getUserId();
        userIdComboBox.setItems(userIds);

    }

    /**
     * Converts the scheduled appointment time to UTC for database use
     * @param utcString Takes in a date and time string
     * @return Returns the adjusted UTC time
     * @throws ParseException
     */
    public LocalDateTime convertToUTC(String utcString) throws ParseException {

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


    }


}
